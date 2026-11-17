package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.NavSection
import com.example.ui.SchoolViewModel
import com.example.ui.components.SchoolBottomNavBar
import com.example.ui.components.SchoolFooter
import com.example.ui.components.SchoolTopNavbar
import com.example.ui.components.openGoogleMaps
import com.example.ui.components.openWhatsApp
import com.example.ui.screens.BerandaSection
import com.example.ui.screens.BiayaSection
import com.example.ui.screens.GaleriSection
import com.example.ui.screens.JurusanSection
import com.example.ui.screens.KontakKritikSection
import com.example.ui.screens.MajorDetailDialog
import com.example.ui.screens.PhotoLightboxDialog
import com.example.ui.theme.MupaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MupaTheme {
                SmkMupaApp()
            }
        }
    }
}

@Composable
fun SmkMupaApp(
    viewModel: SchoolViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarEvent) {
        uiState.snackbarEvent?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearSnackbar()
        }
    }

    LaunchedEffect(uiState.currentSection) {
        scrollState.scrollTo(0)
    }

    BackHandler(enabled = uiState.currentSection != NavSection.BERANDA) {
        viewModel.selectSection(NavSection.BERANDA)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth >= 600.dp

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                SchoolTopNavbar(
                    currentSection = uiState.currentSection,
                    isWideScreen = isWideScreen,
                    onSelectSection = { section -> viewModel.selectSection(section) },
                    onWhatsAppClick = {
                        openWhatsApp(
                            context = context,
                            message = "Halo Admin PPDB SMK Muhammadiyah Pakem, saya ingin bertanya informasi jurusan dan biaya pendaftaran."
                        ) { fallback -> viewModel.showSnackbar(fallback) }
                    }
                )
            },
            bottomBar = {
                if (!isWideScreen) {
                    SchoolBottomNavBar(
                        currentSection = uiState.currentSection,
                        onSelectSection = { section -> viewModel.selectSection(section) }
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 1040.dp)
                        .fillMaxWidth()
                ) {
                    AnimatedContent(
                        targetState = uiState.currentSection,
                        label = "sectionTransition"
                    ) { section ->
                        when (section) {
                            NavSection.BERANDA -> BerandaSection(
                                isWideScreen = isWideScreen,
                                onNavigate = { target -> viewModel.selectSection(target) },
                                onSelectMajor = { major -> viewModel.openMajorDetail(major) },
                                onWhatsAppClick = { msg ->
                                    openWhatsApp(context, msg) { fallback -> viewModel.showSnackbar(fallback) }
                                },
                                onMapsClick = {
                                    openGoogleMaps(context) { fallback -> viewModel.showSnackbar(fallback) }
                                }
                            )

                            NavSection.JURUSAN -> JurusanSection(
                                isWideScreen = isWideScreen,
                                onSelectMajor = { major -> viewModel.openMajorDetail(major) },
                                onWhatsAppMajor = { major ->
                                    openWhatsApp(
                                        context,
                                        "Halo Admin PPDB SMK Muhammadiyah Pakem, saya ingin bertanya detail jurusan ${major.nama} (${major.kode})."
                                    ) { fallback -> viewModel.showSnackbar(fallback) }
                                }
                            )

                            NavSection.BIAYA -> BiayaSection(
                                uiState = uiState,
                                isWideScreen = isWideScreen,
                                onSelectMajor = viewModel::selectMajorForTuition,
                                onSelectWave = viewModel::selectWave,
                                onSelectScholarship = viewModel::selectScholarship,
                                onConsultTuitionWa = { msg ->
                                    openWhatsApp(context, msg) { fallback -> viewModel.showSnackbar(fallback) }
                                }
                            )

                            NavSection.GALERI -> GaleriSection(
                                uiState = uiState,
                                isWideScreen = isWideScreen,
                                onSelectCategory = viewModel::selectGalleryCategory,
                                onOpenLightbox = viewModel::openPhotoLightbox
                            )

                            NavSection.KONTAK -> KontakKritikSection(
                                uiState = uiState,
                                isWideScreen = isWideScreen,
                                onWhatsAppClick = { msg ->
                                    openWhatsApp(context, msg) { fallback -> viewModel.showSnackbar(fallback) }
                                },
                                onMapsClick = {
                                    openGoogleMaps(context) { fallback -> viewModel.showSnackbar(fallback) }
                                },
                                onNamaChange = viewModel::updateFormNama,
                                onAsalChange = viewModel::updateFormAsalSekolah,
                                onKontakChange = viewModel::updateFormNoKontak,
                                onKategoriChange = viewModel::updateFormKategori,
                                onPesanChange = viewModel::updateFormPesan,
                                onSubmitFeedback = {
                                    viewModel.submitFeedback()
                                }
                            )
                        }
                    }
                }

                SchoolFooter(
                    onSelectSection = { target ->
                        viewModel.selectSection(target)
                        scope.launch { scrollState.animateScrollTo(0) }
                    },
                    onWhatsAppClick = {
                        openWhatsApp(
                            context,
                            "Halo Admin PPDB SMK Muhammadiyah Pakem, saya ingin bertanya seputar pendaftaran siswa baru."
                        ) { fallback -> viewModel.showSnackbar(fallback) }
                    },
                    onMapsClick = {
                        openGoogleMaps(context) { fallback -> viewModel.showSnackbar(fallback) }
                    }
                )
            }
        }
    }

    uiState.selectedMajorForDetail?.let { major ->
        MajorDetailDialog(
            major = major,
            onDismiss = { viewModel.closeMajorDetail() },
            onWhatsAppMajor = { selected ->
                openWhatsApp(
                    context,
                    "Halo Admin PPDB SMK Muhammadiyah Pakem, saya tertarik jurusan ${selected.nama} (${selected.kode}). Mohon info kuota & pendaftarannya."
                ) { fallback -> viewModel.showSnackbar(fallback) }
            }
        )
    }

    uiState.selectedPhotoForLightbox?.let { photo ->
        PhotoLightboxDialog(
            photo = photo,
            onDismiss = { viewModel.closePhotoLightbox() }
        )
    }
}
