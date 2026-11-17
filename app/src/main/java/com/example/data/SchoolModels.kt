package com.example.data

import androidx.annotation.DrawableRes
import com.example.R

enum class NavSection(val label: String, val tag: String) {
    BERANDA("Beranda", "nav_beranda"),
    JURUSAN("Jurusan", "nav_jurusan"),
    BIAYA("Biaya", "nav_biaya"),
    GALERI("Galeri", "nav_galeri"),
    KONTAK("Kontak", "nav_kontak")
}

data class ProgramKeahlian(
    val id: String,
    val kode: String,
    val nama: String,
    val akreditasi: String,
    val mitraIndustri: String,
    val ringkasan: String,
    val kompetensiUtama: List<String>,
    val prospekKarir: List<String>,
    val fasilitas: String,
    val sppBulanan: Int,
    val biayaPraktikTahunan: Int,
    @DrawableRes val gambarRes: Int
)

data class GelombangPpdb(
    val id: String,
    val nama: String,
    val periode: String,
    val uangPangkal: Int,
    val seragamDanAtribut: Int,
    val potonganGelombang: Int,
    val keterangan: String
)

data class JalurBeasiswa(
    val id: String,
    val nama: String,
    val potonganUangPangkal: Int,
    val syaratSingkat: String
)

enum class GaleriKategori(val label: String) {
    SEMUA("Semua"),
    GEDUNG("Gedung"),
    BENGKEL("Bengkel"),
    LAB("Lab & Studio")
}

data class GaleriFoto(
    val id: String,
    val judul: String,
    val deskripsi: String,
    val kategori: GaleriKategori,
    @DrawableRes val gambarRes: Int
)

data class KritikPesanItem(
    val id: String,
    val namaPengirim: String,
    val asalSekolah: String,
    val noKontak: String,
    val kategori: String,
    val isiPesan: String,
    val waktuLabel: String
)

object SchoolRepository {
    const val SCHOOL_NAME = "SMK Muhammadiyah Pakem"
    const val SCHOOL_BADGE = "SMK Pusat Keunggulan • Akreditasi A"
    const val SCHOOL_ADDRESS = "Jl. Kaliurang Km. 17,5, Pakembinangun, Pakem, Sleman, D.I. Yogyakarta 55582"
    const val WHATSAPP_NUMBER = "628112654321"
    const val WHATSAPP_DISPLAY = "+62 811-2654-321"
    const val SCHOOL_EMAIL = "ppdb@smkmuhpakem.sch.id"
    const val MAPS_QUERY_URL = "https://maps.google.com/?q=SMK+Muhammadiyah+Pakem+Sleman+Yogyakarta"
    const val SERVICE_HOURS = "Senin - Sabtu • 07.30 - 15.00 WIB"

    val programKeahlianList: List<ProgramKeahlian> = listOf(
        ProgramKeahlian(
            id = "tkr",
            kode = "TKR",
            nama = "Teknik Kendaraan Ringan",
            akreditasi = "Akreditasi A",
            mitraIndustri = "Kelas Industri Daihatsu",
            ringkasan = "Program unggulan SMK Pusat Keunggulan dengan bengkel standar industri Daihatsu Kampus 2, membekali siswa keahlian servis berkala, sasis, kelistrikan bodi, dan sistem EFI mobil modern.",
            kompetensiUtama = listOf(
                "Perawatan & perbaikan mesin kendaraan ringan EFI",
                "Sistem pemindah tenaga, rem ABS & suspensi",
                "Diagnosa kerusakan menggunakan OBD-II scanner",
                "Standar K3 & budaya kerja bengkel resmi Daihatsu"
            ),
            prospekKarir = listOf(
                "Mekanik Bengkel Resmi Daihatsu / APM",
                "Service Advisor & Quality Control Otomotif",
                "Wirausaha Bengkel Servis & Spooring Balancing"
            ),
            fasilitas = "Gedung Kampus 2 Daihatsu, Car Lift Hidrolik, Engine Stand EFI & Ruang Uji Emisi",
            sppBulanan = 225_000,
            biayaPraktikTahunan = 450_000,
            gambarRes = R.drawable.img_galeri_tkr_1794921647422
        ),
        ProgramKeahlian(
            id = "tsm",
            kode = "TSM",
            nama = "Teknik Sepeda Motor",
            akreditasi = "Akreditasi A",
            mitraIndustri = "Mitra Bengkel Resmi AHASS",
            ringkasan = "Menguasai perawatan mesin 4-tak & matik injeksi (PGM-FI), kelistrikan sepeda motor, hingga manajemen bengkel motor modern yang siap kerja dan berwirausaha.",
            kompetensiUtama = listOf(
                "Tune-up & overhaul mesin sepeda motor sport & matik",
                "Diagnosa sistem bahan bakar injeksi & sensor FI",
                "Kelistrikan pengapian, starter & sistem pengisian",
                "Manajemen suku cadang & layanan pelanggan bengkel"
            ),
            prospekKarir = listOf(
                "Teknisi Bengkel Resmi Sepeda Motor",
                "Instruktur / Kepala Mekanik Bengkel Mandiri",
                "Wirausaha Bengkel & Modifikasi Motor"
            ),
            fasilitas = "Bengkel Praktik 12 Bike Lift, Diagnostic Tool FI & Unit Praktik Motor Terkini",
            sppBulanan = 215_000,
            biayaPraktikTahunan = 400_000,
            gambarRes = R.drawable.img_gedung_kampus2
        ),
        ProgramKeahlian(
            id = "tkj",
            kode = "TKJ",
            nama = "Teknik Komputer & Jaringan",
            akreditasi = "Akreditasi A",
            mitraIndustri = "MikroTik Academy & ISP Lokal",
            ringkasan = "Fokus pada perakitan komputer, administrasi server Linux/Windows, instalasi jaringan kabel & fiber optik (FTTH), serta konfigurasi router MikroTik bersertifikat.",
            kompetensiUtama = listOf(
                "Instalasi jaringan LAN, WLAN & Fiber Optik (FTTH)",
                "Konfigurasi Router MikroTik & Keamanan Jaringan",
                "Administrasi Server Web, DNS & Cloud Computing",
                "Perakitan & troubleshooting perangkat keras komputer"
            ),
            prospekKarir = listOf(
                "Network Engineer & Teknisi Fiber Optik ISP",
                "IT Support & System Administrator Perusahaan",
                "Konsultan Infrastruktur Jaringan & CCTV"
            ),
            fasilitas = "Lab Jaringan & Server Rack FTTH, Fusion Splicer Fiber Optik & RouterBoard Lab",
            sppBulanan = 225_000,
            biayaPraktikTahunan = 450_000,
            gambarRes = R.drawable.img_galeri_tkj_1794921648057
        ),
        ProgramKeahlian(
            id = "dkv",
            kode = "DKV",
            nama = "Desain Komunikasi Visual",
            akreditasi = "Akreditasi A",
            mitraIndustri = "Creative Studio & Media Yogyakarta",
            ringkasan = "Memadukan kreativitas desain grafis, fotografi komersial, videografi, animasi 2D, dan branding digital untuk kebutuhan industri kreatif modern.",
            kompetensiUtama = listOf(
                "Desain grafis vektor, tipografi & identitas merek",
                "Fotografi studio produk & olah digital profesional",
                "Produksi video, sinematografi & motion graphics",
                "Desain UI/UX & konten media sosial bisnis"
            ),
            prospekKarir = listOf(
                "Graphic Designer & Brand Identity Specialist",
                "Fotografer, Videografer & Video Editor",
                "Content Creator & Freelance Creative Director"
            ),
            fasilitas = "Studio Foto Softbox, PC Desain Dual-Monitor & Kamera DSLR/Mirrorless",
            sppBulanan = 225_000,
            biayaPraktikTahunan = 425_000,
            gambarRes = R.drawable.img_galeri_dkv_1794921664621
        )
    )

    val gelombangList: List<GelombangPpdb> = listOf(
        GelombangPpdb(
            id = "gel1",
            nama = "Gelombang 1 (Inden)",
            periode = "Januari – Maret",
            uangPangkal = 2_400_000,
            seragamDanAtribut = 850_000,
            potonganGelombang = 500_000,
            keterangan = "Diskon uang pangkal Rp 500.000 & prioritas pilihan jurusan."
        ),
        GelombangPpdb(
            id = "gel2",
            nama = "Gelombang 2",
            periode = "April – Mei",
            uangPangkal = 2_400_000,
            seragamDanAtribut = 850_000,
            potonganGelombang = 250_000,
            keterangan = "Diskon uang pangkal Rp 250.000 selama kuota jurusan tersedia."
        ),
        GelombangPpdb(
            id = "gel3",
            nama = "Gelombang Reguler",
            periode = "Juni – Juli",
            uangPangkal = 2_400_000,
            seragamDanAtribut = 850_000,
            potonganGelombang = 0,
            keterangan = "Biaya normal pendaftaran reguler tahun ajaran baru."
        )
    )

    val jalurBeasiswaList: List<JalurBeasiswa> = listOf(
        JalurBeasiswa(
            id = "umum",
            nama = "Jalur Reguler (Umum)",
            potonganUangPangkal = 0,
            syaratSingkat = "Calon siswa lulusan SMP/MTs sederajat."
        ),
        JalurBeasiswa(
            id = "prestasi",
            nama = "Beasiswa Prestasi Akademik / Lomba",
            potonganUangPangkal = 600_000,
            syaratSingkat = "Peringkat 1–5 kelas atau sertifikat juara minimal tingkat kabupaten."
        ),
        JalurBeasiswa(
            id = "kader",
            nama = "Beasiswa Kader / Alumni SMP Muh",
            potonganUangPangkal = 400_000,
            syaratSingkat = "Lulusan SMP/MTs Muhammadiyah atau rekomendasi PCM/PRM."
        )
    )

    val galeriFotoList: List<GaleriFoto> = listOf(
        GaleriFoto(
            id = "kampus2",
            judul = "Gedung Kampus 2 SMK Pusat Keunggulan",
            deskripsi = "Fasad Gedung Kampus 2 SMK Muhammadiyah Pakem sebagai pusat bengkel industri Daihatsu Teknik Kendaraan Ringan.",
            kategori = GaleriKategori.GEDUNG,
            gambarRes = R.drawable.img_gedung_kampus2
        ),
        GaleriFoto(
            id = "bengkel_tkr",
            judul = "Bengkel Praktik Otomotif Standar Industri",
            deskripsi = "Fasilitas car lift hidrolik, alat diagnosa EFI, dan area praktik siswa TKR dengan standar K3 bengkel resmi.",
            kategori = GaleriKategori.BENGKEL,
            gambarRes = R.drawable.img_galeri_tkr_1794921647422
        ),
        GaleriFoto(
            id = "lab_tkj",
            judul = "Lab Komputer, Server & Fiber Optik (FTTH)",
            deskripsi = "Ruang praktik Teknik Komputer & Jaringan lengkap dengan rak server, perangkat router, dan workstation siswa.",
            kategori = GaleriKategori.LAB,
            gambarRes = R.drawable.img_galeri_tkj_1794921648057
        ),
        GaleriFoto(
            id = "studio_dkv",
            judul = "Studio Kreatif & Multimedia DKV",
            deskripsi = "Studio fotografi dengan tata cahaya softbox dan komputer editing ganda untuk pembelajaran Desain Komunikasi Visual.",
            kategori = GaleriKategori.LAB,
            gambarRes = R.drawable.img_galeri_dkv_1794921664621
        )
    )
}
