# Rencana Arsitektur & Modularisasi SukiWalls (GDGoC Widyatama)
*OriginOS & HyperOS Interactive Lockscreen Carousel + Expressive Material 3*

Dokumen ini adalah revisi rencana teknis berdasarkan kebutuhan:
1. **Identitas Baru**: Mengubah nama aplikasi menjadi **SukiWalls** dengan package `com.gdgocwidyatama.sukiwalls`.
2. **Lingkungan Termux**: Penyesuaian konfigurasi Gradle (JVM args, heap memory, noexec handling pada `/sdcard`).
3. **Prinsip Modular (Anti-Monolitik)**: Pemecahan antarmuka menjadi komponen-komponen mandiri berukuran kecil (< 120 baris per file/composable).
4. **Git Commit**: Perubahan dikerjakan langsung di file sumber tanpa commit git hingga seluruh alur teruji stabil.

---

## 1. Penyesuaian Lingkungan Termux

| Kendala Termux | Penyebab | Solusi Teknis |
| :--- | :--- | :--- |
| `Permission denied` saat `./gradlew` | `/sdcard` di-mount dengan flag `noexec` pada Android OS | Eksekusi selalu melalui interpreter shell: `bash gradlew <task>` |
| Android LMK / OOM Killer | Termux berjalan sebagai user app Android dengan batasan RAM | Atur `org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m` pada `gradle.properties` |
| Lock daemon Gradle | File `.lock` sering tertahan saat sesi shell tertutup paksa | Gunakan `--no-daemon` atau jalankan `pkill -f gradle` saat sinkronisasi terhenti |

---

## 2. Refaktor Package & Penamaan (*Branding*)

- **Nama Aplikasi**: `SukiWalls`
- **Application ID & Namespace**: `com.gdgocwidyatama.sukiwalls`
- **Direktori Sumber**:
  - Dari: `app/src/main/kotlin/com/gdgoc/luminawalls/`
  - Menjadi: `app/src/main/kotlin/com/gdgocwidyatama/sukiwalls/`
- **Tema & Entry Point**:
  - `SukiMainApp.kt` (menggantikan `LuminaMainApp`)
  - `SukiTheme.kt` (menggantikan `LuminaTheme`)
  - Resource string di `strings.xml`: Nilai `app_name` dan teks panduan diperbarui menjadi SukiWalls.

---

## 3. Dekomposisi Komponen UI Modular (Anti-Monolitik)

Seluruh komponen UI dibagi ke dalam sub-paket khusus dengan tanggung jawab tunggal (*Single Responsibility Principle*):

```
app/src/main/kotlin/com/gdgocwidyatama/sukiwalls/
├── MainActivity.kt                      (< 50 baris: Mengaktifkan Edge-to-Edge & SukiMainApp)
├── data/
│   ├── model/
│   │   ├── Wallpaper.kt                 (Data class Wallpaper kurasi & remote)
│   │   ├── Contributor.kt               (Data class kontributor GDGoC)
│   │   └── LockscreenState.kt           (State holder gaya jam, warna teks, efek depth)
│   └── repository/
│       ├── WallpaperRepository.kt       (Daftar wallpaper kurasi lokal + sinkronisasi API)
│       ├── UserPreferencesRepository.kt (DataStore persistensi tema & onboarding)
│       └── ContributorRepository.kt     (REST API kontributor GitHub GDGoC Widyatama)
├── theme/
│   ├── Color.kt                         (Semantic tokens Material 3 Expressive)
│   ├── Theme.kt                         (SukiTheme dengan Dynamic Color Monet)
│   └── Type.kt                          (Tipografi memanfaatkan gflex & genre variable fonts)
└── ui/
    ├── SukiMainApp.kt                   (Root screen routing & navigasi tab)
    ├── navigation/
    │   ├── AppScreen.kt                 (Sealed interface navigasi layar)
    │   └── SukiTab.kt                   (Enum tab navigasi: Beranda & Kontributor)
    ├── components/
    │   ├── BouncyClickable.kt           (Modifier pegas saat disentuh)
    │   ├── FloatingNavBar.kt            (Bottom nav pill melayang)
    │   ├── NetworkImage.kt              (Async image loader Coil + fallback dummy)
    │   ├── SukiScreenScaffold.kt        (Scaffold header & lembar permukaan berlekuk)
    │   └── RichSelectionCard.kt         (Kartu opsi seleksi)
    ├── screens/
    │   ├── home/
    │   │   ├── HomeScreen.kt            (Scaffold beranda dengan 2-column grid)
    │   │   └── components/
    │   │       ├── WallpaperCard.kt     (Kartu stacking wallpaper beranda)
    │   │       └── WallpaperTag.kt      (Chip kategori wallpaper)
    │   ├── preview/
    │   │   ├── PreviewScreen.kt         (Orchestrator layar pratinjau & gestur tekan lama)
    │   │   └── components/
    │   │       ├── LockscreenClockWidget.kt      (Widget jam HyperOS Stacked & OriginOS Split)
    │   │       ├── LockscreenStatusHeader.kt     (Baris status mini: sinyal, baterai, provider)
    │   │       ├── LockscreenQuickShortcuts.kt   (Tombol pintasan senter & kamera bulat)
    │   │       ├── LockscreenCanvasCard.kt       (Miniatur layar ponsel rasio 9:19.5 berlekuk)
    │   │       ├── LockscreenCarouselPager.kt    (HorizontalPager 3D depth transform)
    │   │       └── LockscreenCustomizerDock.kt   (Dock editor bawah: tab jam, warna, terapkan)
    │   ├── settings/
    │   │   ├── SettingsScreen.kt        (Layar preferensi tema)
    │   │   └── components/
    │   │       └── ThemeOptionItem.kt   (Item baris seleksi tema)
    │   ├── contributors/
    │   │   ├── ContributorsScreen.kt    (Layar tim GDGoC Widyatama & kontributor)
    │   │   └── components/
    │   │       ├── GdgocTeamBlock.kt    (Kartu tim instruktur GDGoC)
    │   │       └── ContributorItem.kt   (Daftar kontributor GitHub)
    │   └── setup/
    │       ├── SetupScreen.kt           (Wizard onboarding 3 langkah)
    │       └── components/
    │           ├── SetupHeader.kt       (Header sambutan wizard)
    │           └── SetupBottomBar.kt    (Tombol navigasi lanjut/mulai)
```

---

## 4. Rincian Desain Lockscreen Carousel (OriginOS / HyperOS)

### A. Gestur Tekan Lama (*Long-Press Activation*)
Pada `LockscreenCanvasCard`, gestur ditangkap dengan `detectTapGestures`:
```kotlin
Modifier.pointerInput(Unit) {
    detectTapGestures(
        onLongPress = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            isEditMode = true
        },
        onTap = {
            if (isEditMode) isEditMode = false
        }
    )
}
```
Ketika `isEditMode == true`, kanvas ponsel mengecil (*spring zoom-out*) dari skala `1.0f` ke `0.82f` menggunakan `animateFloatAsState(spring(dampingRatio = Spring.DampingRatioMediumBouncy))`.

### B. 3D Carousel (`HorizontalPager`)
Ketika mode kustomisasi aktif:
- `HorizontalPager` memungkinkan transisi antar wallpaper dengan fluiditas tinggi.
- Setiap halaman tetangga dikalkulasi transformasinya:
  ```kotlin
  val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
  val scale = lerp(0.85f, 1.0f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
  val alpha = lerp(0.50f, 1.0f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
  ```

### C. Kustomisasi Jam & Widget
- **HyperOS Stacked**: Angka jam di atas angka menit dengan tipografi font variabel ekstra tebal (`genre_variable`).
- **OriginOS Split Horizontal**: Jam mendatar dengan kapsul pill tanggal di atasnya.
- **Pilihan Warna**: Dynamic Monet System, Pure White, Warm Terracotta, Deep Slate.

---

## 5. Rencana Eksekusi Bertahap

1. **Persiapan Dependensi & Konfigurasi Gradle**:
   - Memastikan `app/build.gradle.kts` memiliki dependensi `coil-compose`, `androidx-datastore-preferences`, dan `androidx-core-splashscreen`.
   - Mengubah namespace dan applicationId menjadi `com.gdgocwidyatama.sukiwalls`.
   - Menyesuaikan `gradle.properties` untuk alokasi RAM yang ramah Termux.
2. **Refaktor Struktur Direktori & Paket**:
   - Memindahkan folder kode sumber dari `com/gdgoc/luminawalls` ke `com/gdgocwidyatama/sukiwalls`.
   - Memperbarui package declaration di seluruh file Kotlin.
3. **Penyusunan Komponen Modular**:
   - Menghubungkan `MainActivity` langsung ke `SukiMainApp`.
   - Menyusun komponen-komponen terisolasi untuk `HomeScreen`, `SetupScreen`, `SettingsScreen`, dan `ContributorsScreen`.
   - Mengimplementasikan seluruh komponen modular lockscreen carousel di `screens/preview/components/`.
4. **Verifikasi Lingkungan Termux**:
   - Memastikan file kode bebas dari error sintaks, import yang salah, atau circular dependencies.
