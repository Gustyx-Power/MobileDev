@echo off
setlocal
cd /d "%~dp0"

echo ===================================================
echo Menjalankan build-debug untuk MuMuPlayer...
echo ===================================================

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0build-debug.ps1" %*
set BUILD_STATUS=%ERRORLEVEL%

if %BUILD_STATUS% neq 0 (
    echo.
    echo [ERROR] Terjadi kesalahan saat proses build atau install.
    echo Exit Code: %BUILD_STATUS%
    pause
    exit /b %BUILD_STATUS%
)

endlocal

