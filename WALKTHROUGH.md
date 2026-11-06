# Walkthrough Pembaruan: Scroll-Up to Carousel (SukiWalls)

Arah gestur interaktif lockscreen telah disesuaikan:

---

## Alur Interaksi:

1. **Pratinjau Awal (Terkunci)**:
   - Geser horizontal dikunci (`userScrollEnabled = false`).
   - Wallpaper berada pada tampilan fokus penuh (*scale 1.0f*).
   - Terdapat indikator pill: *"↑ Scroll ke atas untuk ganti wallpaper"*.

2. **Aktivasi Carousel via Scroll ke Atas (*Swipe Up*)**:
   - Pengguna melakukan gestur **scroll / geser ke atas** pada kartu lockscreen (atau mengetuk indikator pill).
   - Kanvas ponsel mengecil halus (*zoom-out spring physics* ke skala `0.78f`) dengan getaran haptik.
   - Paging horizontal **diaktifkan** (`userScrollEnabled = true`) dan memunculkan efek **3D Carousel** dengan kartu wallpaper di sisi kiri dan kanan (*content padding 56.dp*).
   - Pengguna kini dapat menggeser horizontal ke kiri dan kanan untuk memilih katalog wallpaper.

3. **Mengunci Kembali Wallpaper**:
   - Pengguna melakukan gestur **scroll ke bawah** atau **mengetuk (*tap*)** kartu wallpaper yang sedang aktif.
   - Paging horizontal kembali dikunci dan kartu kembali ke tampilan fokus penuh.

---

## File APK Terbaru

📦 **[`/sdcard/MobileDev/app/build/outputs/apk/debug/app-debug.apk`](file:///sdcard/MobileDev/app/build/outputs/apk/debug/app-debug.apk)** (Ukuran: 21 MB, waktu kompilasi: 20:52)

Instal langsung via Termux:
```bash
termux-open /sdcard/MobileDev/app/build/outputs/apk/debug/app-debug.apk
```
