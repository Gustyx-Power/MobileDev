<#
.SYNOPSIS
    Script otomatis untuk kompilasi debug APK dan install langsung ke MuMuPlayer.
.DESCRIPTION
    1. Otomatis mendeteksi dan menghubungkan ADB ke MuMuPlayer (port 16384 / 7555).
    2. Menjalankan Gradle assembleDebug.
    3. Menginstall app-debug.apk langsung ke MuMuPlayer.
    4. Menjalankan (launch) MainActivity aplikasi di MuMuPlayer.
#>

[CmdletBinding()]
param(
    [switch]$SkipLaunch,
    [switch]$Clean
)

# Menggunakan Continue agar output stderr adb (seperti status daemon) tidak memicu error terminasi PowerShell
$ErrorActionPreference = "Continue"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "   LuminaWalls - Build & Install to MuMu  " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

# 1. Pastikan lokasi adb
$adb = (Get-Command adb -ErrorAction SilentlyContinue).Source
if (-not $adb -or -not (Test-Path $adb)) {
    $fallbackPaths = @(
        "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe",
        "$env:ANDROID_HOME\platform-tools\adb.exe",
        "$env:ANDROID_SDK_ROOT\platform-tools\adb.exe",
        "D:\MuMuPlayerGlobal\nx_device\15.0\shell\adb.exe",
        "D:\MuMuPlayerGlobal\nx_main\adb.exe"
    )
    foreach ($path in $fallbackPaths) {
        if ($path -and (Test-Path $path)) {
            $adb = $path
            break
        }
    }
}

if (-not $adb -or -not (Test-Path $adb)) {
    Write-Host "[ERROR] adb.exe tidak ditemukan di PATH maupun Android SDK!" -ForegroundColor Red
    Write-Host "Silakan pastikan Android SDK Platform-tools sudah terpasang." -ForegroundColor Yellow
    exit 1
}

Write-Host "[1/4] Mencari dan menghubungkan ke MuMuPlayer..." -ForegroundColor Green

# Hubungkan port umum MuMuPlayer (16384 untuk MuMu 12 instance 0, 7555 untuk MuMu versi klasik)
& $adb connect 127.0.0.1:16384 *>$null
& $adb connect 127.0.0.1:7555 *>$null

# Ambil daftar device yang statusnya 'device'
$devicesRaw = & $adb devices
$activeDevices = @()

foreach ($line in $devicesRaw) {
    if ($line -match '^([^\s]+)\s+device$') {
        $activeDevices += $matches[1]
    }
}

if ($activeDevices.Count -eq 0) {
    Write-Host "[WARNING] MuMuPlayer belum terdeteksi aktif." -ForegroundColor Yellow
    # Cek apakah proses MuMu berjalan
    $mumuProc = Get-Process | Where-Object { $_.ProcessName -match "mumu|nemu" }
    if (-not $mumuProc) {
        Write-Host "MuMuPlayer tampaknya belum dibuka. Silakan jalankan MuMuPlayer terlebih dahulu!" -ForegroundColor Red
    } else {
        Write-Host "Menunggu 5 detik untuk inisialisasi emulator..." -ForegroundColor Yellow
        Start-Sleep -Seconds 5
        & $adb connect 127.0.0.1:16384 *>$null
        $devicesRaw = & $adb devices
        foreach ($line in $devicesRaw) {
            if ($line -match '^([^\s]+)\s+device$') {
                $activeDevices += $matches[1]
            }
        }
    }
}

if ($activeDevices.Count -eq 0) {
    Write-Host "[ERROR] Tidak dapat mendeteksi emulator MuMuPlayer yang terhubung." -ForegroundColor Red
    Write-Host "Pastikan MuMuPlayer sudah terbuka dan opsi USB Debugging / ADB aktif di pengaturan MuMu." -ForegroundColor Yellow
    exit 1
}

# Pilih target device (utamakan port MuMu 127.0.0.1:16384 atau 127.0.0.1:7555 atau yang pertama)
$targetDevice = $activeDevices | Where-Object { $_ -match "16384" } | Select-Object -First 1
if (-not $targetDevice) {
    $targetDevice = $activeDevices | Where-Object { $_ -match "7555" } | Select-Object -First 1
}
if (-not $targetDevice) {
    $targetDevice = $activeDevices[0]
}

Write-Host "  -> Terhubung ke emulator: $targetDevice" -ForegroundColor Cyan

# 2. Compile APK
Write-Host "[2/4] Mengompilasi Debug APK via Gradle..." -ForegroundColor Green
$gradleArgs = @("assembleDebug")
if ($Clean) {
    $gradleArgs = @("clean", "assembleDebug")
}

$gradlew = ".\gradlew.bat"
if (-not (Test-Path $gradlew)) {
    Write-Host "[ERROR] gradlew.bat tidak ditemukan di direktori saat ini ($PWD)!" -ForegroundColor Red
    exit 1
}

& $gradlew @gradleArgs
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Kompilasi Gradle gagal dengan exit code $LASTEXITCODE!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# 3. Verifikasi file APK
$apkPath = "app\build\outputs\apk\debug\app-debug.apk"
if (-not (Test-Path $apkPath)) {
    Write-Host "[ERROR] File APK tidak ditemukan di $apkPath!" -ForegroundColor Red
    exit 1
}

$apkInfo = Get-Item $apkPath
$apkSizeMB = [math]::Round($apkInfo.Length / 1MB, 2)
Write-Host "  -> APK berhasil diverifikasi ($apkSizeMB MB): $apkPath" -ForegroundColor Cyan

# 4. Install ke MuMuPlayer
Write-Host "[3/4] Menginstall APK ke MuMuPlayer ($targetDevice)..." -ForegroundColor Green
$installOutput = & $adb -s $targetDevice install -r $apkPath 2>&1
Write-Host ($installOutput -join "`n")

if (-not ($installOutput -match "Success")) {
    Write-Host "[ERROR] Gagal menginstall APK ke $targetDevice!" -ForegroundColor Red
    exit 1
}
Write-Host "  -> Install berhasil!" -ForegroundColor Green

# 5. Jalankan Aplikasi
if (-not $SkipLaunch) {
    Write-Host "[4/4] Membuka aplikasi di MuMuPlayer..." -ForegroundColor Green
    $packageName = "com.gdgocwidyatama.sukiwalls"
    $mainActivity = ".MainActivity"
    & $adb -s $targetDevice shell am start -n "$packageName/$mainActivity"
    Write-Host "  -> Aplikasi $packageName berhasil dijalankan!" -ForegroundColor Green
}

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "        SELESAI - APLIKASI AKTIF          " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan
