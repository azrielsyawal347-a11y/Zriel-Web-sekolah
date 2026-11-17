package com.example.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.NavSection
import com.example.data.SchoolRepository
import com.example.ui.theme.MupaBlueDark
import com.example.ui.theme.MupaGold
import com.example.ui.theme.WhatsAppGreen

fun openWhatsApp(
    context: Context,
    message: String,
    onResultMessage: (String) -> Unit
) {
    val encoded = Uri.encode(message)
    val url = "https://wa.me/${SchoolRepository.WHATSAPP_NUMBER}?text=$encoded"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        onResultMessage("Aplikasi WhatsApp/Browser tidak ditemukan. Hubungi ${SchoolRepository.WHATSAPP_DISPLAY}")
    }
}

fun openGoogleMaps(
    context: Context,
    onResultMessage: (String) -> Unit
) {
    val geoUri = Uri.parse("geo:-7.6656,110.4184?q=${Uri.encode(SchoolRepository.SCHOOL_NAME + " Sleman")}")
    val geoIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(geoIntent)
    } catch (_: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SchoolRepository.MAPS_QUERY_URL)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(webIntent)
        } catch (_: ActivityNotFoundException) {
            onResultMessage("Buka Google Maps gagal. Alamat: ${SchoolRepository.SCHOOL_ADDRESS}")
        }
    }
}

fun navIcon(section: NavSection, selected: Boolean) = when (section) {
    NavSection.BERANDA -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
    NavSection.JURUSAN -> if (selected) Icons.Filled.Build else Icons.Outlined.Build
    NavSection.BIAYA -> if (selected) Icons.Filled.AccountBalanceWallet else Icons.Outlined.AccountBalanceWallet
    NavSection.GALERI -> if (selected) Icons.Filled.Collections else Icons.Outlined.Collections
    NavSection.KONTAK -> if (selected) Icons.Filled.SupportAgent else Icons.Outlined.SupportAgent
}

@Composable
fun SchoolTopNavbar(
    currentSection: NavSection,
    isWideScreen: Boolean,
    onSelectSection: (NavSection) -> Unit,
    onWhatsAppClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // School Logo + Identity
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSelectSection(NavSection.BERANDA) }
                    .padding(end = 8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo_mupa),
                        contentDescription = "Logo SMK Muhammadiyah Pakem",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(3.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = SchoolRepository.SCHOOL_NAME,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = SchoolRepository.SCHOOL_BADGE,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (isWideScreen) {
                // Wide screen / Laptop horizontal Navbar with interactive hover effects
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NavSection.entries.forEach { section ->
                        HoverableNavbarItem(
                            section = section,
                            selected = section == currentSection,
                            onClick = { onSelectSection(section) }
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = onWhatsAppClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("top_whatsapp_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Chat,
                            contentDescription = "Chat WhatsApp PPDB",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("WhatsApp", style = MaterialTheme.typography.labelLarge)
                    }
                }
            } else {
                // Compact smartphone quick WhatsApp pill
                Button(
                    onClick = onWhatsAppClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("top_whatsapp_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = "Chat WhatsApp PPDB",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WA PPDB", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
private fun HoverableNavbarItem(
    section: NavSection,
    selected: Boolean,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    val bgColor by animateColorAsState(
        targetValue = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            isHovered -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
            else -> Color.Transparent
        },
        animationSpec = spring(),
        label = "navBg"
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            selected -> MaterialTheme.colorScheme.primary
            isHovered -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = spring(),
        label = "navContent"
    )
    val indicatorWidth by animateDpAsState(
        targetValue = when {
            selected -> 28.dp
            isHovered -> 20.dp
            else -> 0.dp
        },
        animationSpec = spring(),
        label = "navIndicator"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .pointerInput(section) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            }
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .testTag(section.tag)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = navIcon(section, selected),
                contentDescription = section.label,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = section.label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected || isHovered) FontWeight.Bold else FontWeight.Medium,
                color = contentColor
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(3.dp)
                .clip(CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun SchoolBottomNavBar(
    currentSection: NavSection,
    onSelectSection: (NavSection) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavSection.entries.forEach { section ->
            val selected = section == currentSection
            var isHovered by remember { mutableStateOf(false) }

            NavigationBarItem(
                selected = selected,
                onClick = { onSelectSection(section) },
                icon = {
                    Icon(
                        imageVector = navIcon(section, selected),
                        contentDescription = section.label
                    )
                },
                label = {
                    Text(
                        text = section.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected || isHovered) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = if (isHovered) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = if (isHovered) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier
                    .pointerInput(section) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                when (event.type) {
                                    PointerEventType.Enter -> isHovered = true
                                    PointerEventType.Exit -> isHovered = false
                                }
                            }
                        }
                    }
                    .testTag(section.tag)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SchoolFooter(
    onSelectSection: (NavSection) -> Unit,
    onWhatsAppClick: () -> Unit,
    onMapsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MupaBlueDark,
                        Color(0xFF062C4F)
                    )
                )
            )
            .padding(horizontal = 20.dp, vertical = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 1040.dp)
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(52.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo_mupa),
                        contentDescription = "Logo Footer SMK Muhammadiyah Pakem",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Column {
                    Text(
                        text = SchoolRepository.SCHOOL_NAME,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sekolah Menengah Kejuruan Pusat Keunggulan • Yogyakarta",
                        style = MaterialTheme.typography.bodySmall,
                        color = MupaGold
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FooterInfoRow(icon = Icons.Filled.LocationOn, text = SchoolRepository.SCHOOL_ADDRESS)
                FooterInfoRow(icon = Icons.Filled.Phone, text = "WhatsApp Resmi PPDB: ${SchoolRepository.WHATSAPP_DISPLAY}")
                FooterInfoRow(icon = Icons.Filled.Email, text = "Email: ${SchoolRepository.SCHOOL_EMAIL}")
                FooterInfoRow(icon = Icons.Filled.Schedule, text = "Layanan Informasi: ${SchoolRepository.SERVICE_HOURS}")
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onWhatsAppClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("footer_whatsapp_button")
                ) {
                    Icon(Icons.Filled.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Pesan WhatsApp")
                }

                OutlinedButton(
                    onClick = onMapsClick,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("footer_maps_button")
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Google Maps")
                }

                OutlinedButton(
                    onClick = { onSelectSection(NavSection.KONTAK) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MupaGold),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MupaGold.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("footer_feedback_button")
                ) {
                    Text("Kritik & Pesan")
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.18f))

            Text(
                text = "© 2026 SMK Muhammadiyah Pakem Sleman Yogyakarta. Pusat Informasi Resmi Jurusan, Biaya Pendidikan & PPDB.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.75f)
            )
        }
    }
}

@Composable
private fun FooterInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MupaGold,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.9f)
        )
    }
}
