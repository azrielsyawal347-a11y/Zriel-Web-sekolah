package com.example.ui

import com.example.data.GaleriFoto
import com.example.data.GaleriKategori
import com.example.data.GelombangPpdb
import com.example.data.JalurBeasiswa
import com.example.data.KritikPesanItem
import com.example.data.NavSection
import com.example.data.ProgramKeahlian
import com.example.data.SchoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

data class SchoolUiState(
    val currentSection: NavSection = NavSection.BERANDA,
    val selectedMajorForDetail: ProgramKeahlian? = null,
    val selectedMajorIdForTuition: String = SchoolRepository.programKeahlianList.first().id,
    val selectedWaveId: String = SchoolRepository.gelombangList.first().id,
    val selectedScholarshipId: String = SchoolRepository.jalurBeasiswaList.first().id,
    val selectedGalleryCategory: GaleriKategori = GaleriKategori.SEMUA,
    val selectedPhotoForLightbox: GaleriFoto? = null,
    val formNama: String = "",
    val formAsalSekolah: String = "",
    val formNoKontak: String = "",
    val formKategori: String = "Pertanyaan PPDB & Biaya",
    val formPesan: String = "",
    val formError: String? = null,
    val submittedMessages: List<KritikPesanItem> = emptyList(),
    val snackbarEvent: String? = null
) {
    val selectedMajorForTuition: ProgramKeahlian
        get() = SchoolRepository.programKeahlianList.find { it.id == selectedMajorIdForTuition }
            ?: SchoolRepository.programKeahlianList.first()

    val selectedWave: GelombangPpdb
        get() = SchoolRepository.gelombangList.find { it.id == selectedWaveId }
            ?: SchoolRepository.gelombangList.first()

    val selectedScholarship: JalurBeasiswa
        get() = SchoolRepository.jalurBeasiswaList.find { it.id == selectedScholarshipId }
            ?: SchoolRepository.jalurBeasiswaList.first()

    val estimasiUangPangkalNet: Int
        get() = (selectedWave.uangPangkal - selectedWave.potonganGelombang - selectedScholarship.potonganUangPangkal)
            .coerceAtLeast(0)

    val estimasiTotalDaftarUlang: Int
        get() = estimasiUangPangkalNet +
            selectedWave.seragamDanAtribut +
            selectedMajorForTuition.biayaPraktikTahunan +
            selectedMajorForTuition.sppBulanan

    val filteredGalleryPhotos: List<GaleriFoto>
        get() = if (selectedGalleryCategory == GaleriKategori.SEMUA) {
            SchoolRepository.galeriFotoList
        } else {
            SchoolRepository.galeriFotoList.filter { it.kategori == selectedGalleryCategory }
        }
}

class SchoolViewModel : androidx.lifecycle.ViewModel() {
    private val _uiState = MutableStateFlow(SchoolUiState())
    val uiState: StateFlow<SchoolUiState> = _uiState.asStateFlow()

    fun selectSection(section: NavSection) {
        _uiState.update { it.copy(currentSection = section) }
    }

    fun openMajorDetail(major: ProgramKeahlian) {
        _uiState.update { it.copy(selectedMajorForDetail = major) }
    }

    fun closeMajorDetail() {
        _uiState.update { it.copy(selectedMajorForDetail = null) }
    }

    fun selectMajorForTuition(majorId: String) {
        _uiState.update { it.copy(selectedMajorIdForTuition = majorId) }
    }

    fun selectWave(waveId: String) {
        _uiState.update { it.copy(selectedWaveId = waveId) }
    }

    fun selectScholarship(scholarshipId: String) {
        _uiState.update { it.copy(selectedScholarshipId = scholarshipId) }
    }

    fun selectGalleryCategory(category: GaleriKategori) {
        _uiState.update { it.copy(selectedGalleryCategory = category) }
    }

    fun openPhotoLightbox(photo: GaleriFoto) {
        _uiState.update { it.copy(selectedPhotoForLightbox = photo) }
    }

    fun closePhotoLightbox() {
        _uiState.update { it.copy(selectedPhotoForLightbox = null) }
    }

    fun updateFormNama(value: String) {
        _uiState.update { it.copy(formNama = value, formError = null) }
    }

    fun updateFormAsalSekolah(value: String) {
        _uiState.update { it.copy(formAsalSekolah = value, formError = null) }
    }

    fun updateFormNoKontak(value: String) {
        _uiState.update { it.copy(formNoKontak = value, formError = null) }
    }

    fun updateFormKategori(value: String) {
        _uiState.update { it.copy(formKategori = value) }
    }

    fun updateFormPesan(value: String) {
        _uiState.update { it.copy(formPesan = value, formError = null) }
    }

    fun submitFeedback(): KritikPesanItem? {
        val current = _uiState.value
        val nama = current.formNama.trim()
        val asal = current.formAsalSekolah.trim().ifEmpty { "Calon Siswa / Orang Tua" }
        val kontak = current.formNoKontak.trim()
        val pesan = current.formPesan.trim()

        if (nama.length < 2) {
            _uiState.update { it.copy(formError = "Mohon isi nama lengkap minimal 2 karakter.") }
            return null
        }
        if (kontak.length < 6) {
            _uiState.update { it.copy(formError = "Mohon isi nomor WhatsApp atau email yang aktif.") }
            return null
        }
        if (pesan.length < 8) {
            _uiState.update { it.copy(formError = "Mohon tulis kritik, saran, atau pertanyaan minimal 8 karakter.") }
            return null
        }

        val item = KritikPesanItem(
            id = UUID.randomUUID().toString(),
            namaPengirim = nama,
            asalSekolah = asal,
            noKontak = kontak,
            kategori = current.formKategori,
            isiPesan = pesan,
            waktuLabel = "Baru saja terkirim"
        )

        _uiState.update {
            it.copy(
                formNama = "",
                formAsalSekolah = "",
                formNoKontak = "",
                formPesan = "",
                formError = null,
                submittedMessages = listOf(item) + it.submittedMessages,
                snackbarEvent = "Terima kasih, ${item.namaPengirim}! Pesan & kritik Anda telah dicatat."
            )
        }
        return item
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarEvent = message) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarEvent = null) }
    }
}

fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return "Rp ${formatter.format(amount)}"
}
