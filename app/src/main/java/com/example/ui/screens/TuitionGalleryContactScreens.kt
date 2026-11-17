package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.GaleriFoto
import com.example.data.GaleriKategori
import com.example.data.SchoolRepository
import com.example.ui.SchoolUiState
import com.example.ui.formatRupiah
import com.example.ui.theme.MapsRed
import com.example.ui.theme.MupaBlueDark
import com.example.ui.theme.MupaGold
import com.example.ui.theme.WhatsAppGreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BiayaSection(
    uiState: SchoolUiState,
    isWideScreen: Boolean,
    onSelectMajor: (String) -> Unit,
    onSelectWave: (String) -> Unit,
    onSelectScholarship: (String) -> Unit,
    onConsultTuitionWa: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Transparansi Biaya & Harga PPDB",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Semua rincian uang pangkal, potongan gelombang, beasiswa, biaya praktik, dan SPP bulanan SMK Muhammadiyah Pakem dalam satu halaman.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Interactive Tuition Calculator Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "1. Pilih Program Keahlian",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SchoolRepository.programKeahlianList.forEach { major ->
                        val selected = major.id == uiState.selectedMajorIdForTuition
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectMajor(major.id) },
                            label = { Text("${major.kode} — ${major.nama}") },
                            leadingIcon = if (selected) {
                                { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            } else null,
                            modifier = Modifier.testTag("tuition_major_${major.id}")
                        )
                    }
                }

                Text(
                    text = "2. Pilih Gelombang Pendaftaran",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SchoolRepository.gelombangList.forEach { wave ->
                        val selected = wave.id == uiState.selectedWaveId
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectWave(wave.id) },
                            label = { Text("${wave.nama} (${wave.periode})") },
                            leadingIcon = if (selected) {
                                { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier.testTag("tuition_wave_${wave.id}")
                        )
                    }
                }

                Text(
                    text = "3. Pilih Jalur Beasiswa / Potongan",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SchoolRepository.jalurBeasiswaList.forEach { scholarship ->
                        val selected = scholarship.id == uiState.selectedScholarshipId
                        FilterChip(
                            selected = selected,
                            onClick = { onSelectScholarship(scholarship.id) },
                            label = { Text(scholarship.nama) },
                            leadingIcon = if (selected) {
                                { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            } else null,
                            modifier = Modifier.testTag("tuition_scholarship_${scholarship.id}")
                        )
                    }
                }
                Text(
                    text = "Syarat: ${uiState.selectedScholarship.syaratSingkat}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Breakdown Result Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MupaBlueDark),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Ringkasan Estimasi Biaya Daftar Ulang",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${uiState.selectedMajorForTuition.nama} • ${uiState.selectedWave.nama}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MupaGold
                )

                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

                TuitionLineRow("Uang Pangkal (SPI Awal)", formatRupiah(uiState.selectedWave.uangPangkal))
                if (uiState.selectedWave.potonganGelombang > 0) {
                    TuitionLineRow(
                        "Potongan ${uiState.selectedWave.nama}",
                        "- ${formatRupiah(uiState.selectedWave.potonganGelombang)}",
                        highlight = MupaGold
                    )
                }
                if (uiState.selectedScholarship.potonganUangPangkal > 0) {
                    TuitionLineRow(
                        "Potongan ${uiState.selectedScholarship.nama}",
                        "- ${formatRupiah(uiState.selectedScholarship.potonganUangPangkal)}",
                        highlight = MupaGold
                    )
                }
                TuitionLineRow("Paket Seragam & Atribut Lengkap", formatRupiah(uiState.selectedWave.seragamDanAtribut))
                TuitionLineRow(
                    "Biaya Praktik Bengkel/Lab (1 Tahun)",
                    formatRupiah(uiState.selectedMajorForTuition.biayaPraktikTahunan)
                )
                TuitionLineRow(
                    "SPP Bulan Pertama (${uiState.selectedMajorForTuition.kode})",
                    formatRupiah(uiState.selectedMajorForTuition.sppBulanan)
                )

                HorizontalDivider(color = Color.White.copy(alpha = 0.25f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Estimasi Total Awal",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Dapat diangsur hingga 3x",
                            style = MaterialTheme.typography.bodySmall,
                            color = MupaGold
                        )
                    }
                    Text(
                        text = formatRupiah(uiState.estimasiTotalDaftarUlang),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        val waMessage = "Halo Admin PPDB SMK Muhammadiyah Pakem, saya tertarik jurusan ${uiState.selectedMajorForTuition.nama} (${uiState.selectedWave.nama}, ${uiState.selectedScholarship.nama}) dengan estimasi daftar ulang ${formatRupiah(uiState.estimasiTotalDaftarUlang)}. Mohon info jadwal pendaftaran & angsuran."
                        onConsultTuitionWa(waMessage)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("tuition_whatsapp_button")
                ) {
                    Icon(Icons.Filled.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tanyakan Skema Angsuran via WhatsApp")
                }
            }
        }

        // Comparison Table Across All Majors
        Text(
            text = "Daftar SPP & Biaya Praktik Semua Jurusan",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        val rows = SchoolRepository.programKeahlianList
        if (isWideScreen) {
            rows.chunked(2).forEach { pair ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pair.forEach { major ->
                        MajorFeeMiniCard(
                            kode = major.kode,
                            nama = major.nama,
                            spp = major.sppBulanan,
                            praktik = major.biayaPraktikTahunan,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        } else {
            rows.forEach { major ->
                MajorFeeMiniCard(
                    kode = major.kode,
                    nama = major.nama,
                    spp = major.sppBulanan,
                    praktik = major.biayaPraktikTahunan,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TuitionLineRow(
    label: String,
    value: String,
    highlight: Color = Color.White
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.88f),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = highlight
        )
    }
}

@Composable
private fun MajorFeeMiniCard(
    kode: String,
    nama: String,
    spp: Int,
    praktik: Int,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = kode,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = nama,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "SPP ${formatRupiah(spp)}/bln",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Praktik ${formatRupiah(praktik)}/thn",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GaleriSection(
    uiState: SchoolUiState,
    isWideScreen: Boolean,
    onSelectCategory: (GaleriKategori) -> Unit,
    onOpenLightbox: (GaleriFoto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Galeri Kampus & Fasilitas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Dokumentasi lingkungan sekolah, Gedung Kampus 2, bengkel praktik otomotif, lab jaringan, dan studio kreatif.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GaleriKategori.entries.forEach { category ->
                val selected = category == uiState.selectedGalleryCategory
                FilterChip(
                    selected = selected,
                    onClick = { onSelectCategory(category) },
                    label = { Text(category.label) },
                    modifier = Modifier.testTag("gallery_filter_${category.name.lowercase()}")
                )
            }
        }

        val photos = uiState.filteredGalleryPhotos
        if (isWideScreen) {
            photos.chunked(2).forEach { pair ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pair.forEach { photo ->
                        GalleryPhotoCard(
                            photo = photo,
                            onClick = { onOpenLightbox(photo) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            photos.forEach { photo ->
                GalleryPhotoCard(
                    photo = photo,
                    onClick = { onOpenLightbox(photo) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun GalleryPhotoCard(
    photo: GaleriFoto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                Image(
                    painter = painterResource(id = photo.gambarRes),
                    contentDescription = photo.judul,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                            )
                        )
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = photo.kategori.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.ZoomIn,
                            contentDescription = "Perbesar Foto",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = photo.judul,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = photo.deskripsi,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun PhotoLightboxDialog(
    photo: GaleriFoto,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = painterResource(id = photo.gambarRes),
                        contentDescription = photo.judul,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .minimumInteractiveComponentSize()
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Tutup foto", tint = Color.White)
                    }
                }
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = photo.kategori.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = photo.judul,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = photo.deskripsi,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KontakKritikSection(
    uiState: SchoolUiState,
    isWideScreen: Boolean,
    onWhatsAppClick: (String) -> Unit,
    onMapsClick: () -> Unit,
    onNamaChange: (String) -> Unit,
    onAsalChange: (String) -> Unit,
    onKontakChange: (String) -> Unit,
    onKategoriChange: (String) -> Unit,
    onPesanChange: (String) -> Unit,
    onSubmitFeedback: () -> Unit
) {
    val kategoriOptions = listOf(
        "Pertanyaan PPDB & Biaya",
        "Informasi Jurusan",
        "Kritik & Saran Layanan",
        "Kunjungan Sekolah"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Layanan Kontak, Maps & Kritik Pesan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tidak perlu datang jauh-jauh hanya untuk menanyakan biaya atau jurusan. Hubungi kami via WhatsApp, cek rute Google Maps, atau kirim pesan & kritik di sini.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Action Cards: WhatsApp & Google Maps
        val actionCards = @Composable {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Konsultasi Cepat WhatsApp",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Terhubung langsung dengan Tim PPDB SMK Muhammadiyah Pakem (${SchoolRepository.WHATSAPP_DISPLAY}).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = {
                            onWhatsAppClick("Halo Admin PPDB SMK Muhammadiyah Pakem, saya ingin konsultasi mengenai pendaftaran siswa baru, jurusan, dan biaya.")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("contact_whatsapp_button")
                    ) {
                        Icon(Icons.Filled.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Chat WhatsApp Sekarang")
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Lokasi Sekolah di Google Maps",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = SchoolRepository.SCHOOL_ADDRESS,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = onMapsClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MapsRed,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("contact_maps_button")
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buka Google Maps")
                    }
                }
            }
        }

        if (isWideScreen) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("Konsultasi Cepat WhatsApp", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("Terhubung langsung dengan Tim PPDB SMK Muhammadiyah Pakem (${SchoolRepository.WHATSAPP_DISPLAY}).", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Button(
                                onClick = { onWhatsAppClick("Halo Admin PPDB SMK Muhammadiyah Pakem, saya ingin konsultasi mengenai pendaftaran siswa baru, jurusan, dan biaya.") },
                                colors = ButtonDefaults.buttonColors(containerColor = WhatsAppGreen, contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize().testTag("contact_whatsapp_button")
                            ) {
                                Icon(Icons.Filled.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Chat WhatsApp Sekarang")
                            }
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("Lokasi Sekolah di Google Maps", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(SchoolRepository.SCHOOL_ADDRESS, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Button(
                                onClick = onMapsClick,
                                colors = ButtonDefaults.buttonColors(containerColor = MapsRed, contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize().testTag("contact_maps_button")
                            ) {
                                Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Buka Google Maps")
                            }
                        }
                    }
                }
            }
        } else {
            actionCards()
        }

        // Kritik & Pesan Form
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Form Kritik, Saran & Pesan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sampaikan pertanyaan seputar PPDB, masukan untuk informasi sekolah, atau permintaan jadwal kunjungan.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = uiState.formNama,
                    onValueChange = onNamaChange,
                    label = { Text("Nama Lengkap *") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_name_input")
                )

                OutlinedTextField(
                    value = uiState.formAsalSekolah,
                    onValueChange = onAsalChange,
                    label = { Text("Asal SMP / Orang Tua") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_school_input")
                )

                OutlinedTextField(
                    value = uiState.formNoKontak,
                    onValueChange = onKontakChange,
                    label = { Text("No. WhatsApp / Email Aktif *") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_contact_input")
                )

                Text(
                    text = "Kategori Pesan",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    kategoriOptions.forEach { kategori ->
                        FilterChip(
                            selected = uiState.formKategori == kategori,
                            onClick = { onKategoriChange(kategori) },
                            label = { Text(kategori) }
                        )
                    }
                }

                OutlinedTextField(
                    value = uiState.formPesan,
                    onValueChange = onPesanChange,
                    label = { Text("Isi Kritik, Saran atau Pesan *") },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feedback_message_input")
                )

                if (uiState.formError != null) {
                    Text(
                        text = uiState.formError,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onSubmitFeedback,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("submit_feedback_button")
                    ) {
                        Icon(Icons.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Kirim Pesan")
                    }

                    OutlinedButton(
                        onClick = {
                            val nama = uiState.formNama.ifBlank { "Calon Siswa" }
                            val pesan = uiState.formPesan.ifBlank { "Saya ingin menyampaikan pertanyaan/saran terkait PPDB." }
                            onWhatsAppClick("[${uiState.formKategori}] Halo Admin SMK Muhammadiyah Pakem, saya $nama. $pesan")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("feedback_send_wa_button")
                    ) {
                        Icon(Icons.Filled.Chat, contentDescription = "Kirim lewat WhatsApp", tint = WhatsAppGreen, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Via WA")
                    }
                }
            }
        }

        // Submitted messages log
        if (uiState.submittedMessages.isNotEmpty()) {
            Text(
                text = "Pesan & Kritik Terkirim (${uiState.submittedMessages.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            uiState.submittedMessages.forEach { msg ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Filled.MarkEmailRead, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Text(msg.namaPengirim, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            }
                            Text(msg.waktuLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("${msg.kategori} • ${msg.asalSekolah}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                        Text(msg.isiPesan, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
