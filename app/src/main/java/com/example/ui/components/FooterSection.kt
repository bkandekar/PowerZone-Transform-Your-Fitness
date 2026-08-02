package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun FooterSection(viewModel: PowerZoneViewModel) {
    val contact = viewModel.contactInfo
    val context = LocalContext.current

    Surface(
        color = Color(0xFF0B0D10),
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .testTag("footer_section")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Brand & Owner Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "POWER",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "ZONE",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = FlameOrange
                        )
                    }
                    Text(
                        text = "FITNESS STUDIO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PowerGold,
                        letterSpacing = 1.sp
                    )
                }

                Surface(
                    color = DarkSurfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "BANER • PUNE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = DarkCardBorder)
            Spacer(modifier = Modifier.height(16.dp))

            // Contact Block
            Text(
                text = "POWERZONE CONTACT DETAILS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = PowerGold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Owner / Head Coach
            FooterContactItem(
                icon = Icons.Default.Person,
                title = "Founder & Head Coach",
                value = "${contact.ownerName} (${contact.ownerTitle})"
            )

            // Phone & WhatsApp
            FooterContactItem(
                icon = Icons.Default.Phone,
                title = "Phone / WhatsApp Direct",
                value = contact.phone,
                onClick = { viewModel.launchWhatsAppRouting(context) }
            )

            // Email
            FooterContactItem(
                icon = Icons.Default.Email,
                title = "Email Address",
                value = contact.email,
                onClick = {
                    try {
                        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${contact.email}"))
                        context.startActivity(emailIntent)
                    } catch (_: Exception) {}
                }
            )

            // Address
            FooterContactItem(
                icon = Icons.Default.LocationOn,
                title = "Gym Studio Address",
                value = contact.address,
                onClick = {
                    try {
                        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${Uri.encode(contact.address)}"))
                        context.startActivity(mapIntent)
                    } catch (_: Exception) {}
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Working Hours Block
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = PowerGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "WORKING HOURS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PowerGold
                        )
                    }
                    Text(
                        text = contact.workingHours,
                        fontSize = 12.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Service Areas Tag Chips
            Text(
                text = "SERVICE AREAS IN PUNE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = TextSecondary,
                letterSpacing = 0.8.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                contact.serviceAreas.forEach { area ->
                    Surface(
                        color = DarkSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "📍 $area",
                            fontSize = 10.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Social Links Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialButton("Instagram", Icons.Default.Share) {
                    openUrl(context, "https://${contact.socialInstagram}")
                }
                Spacer(modifier = Modifier.width(12.dp))
                SocialButton("Facebook", Icons.Default.Public) {
                    openUrl(context, "https://${contact.socialFacebook}")
                }
                Spacer(modifier = Modifier.width(12.dp))
                SocialButton("WhatsApp 918329931123", Icons.Default.Phone) {
                    viewModel.launchWhatsAppRouting(context)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = DarkCardBorder)
            Spacer(modifier = Modifier.height(14.dp))

            // Mandatory Credit Line & Copyright
            Text(
                text = contact.creditLine,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = PowerGold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("credit_line")
            )

            Text(
                text = "© 2026 PowerZone Fitness Studio. All Rights Reserved.",
                fontSize = 10.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun FooterContactItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(DarkSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FlameOrange,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = title,
                fontSize = 10.sp,
                color = TextSecondary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 12.sp,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SocialButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        color = DarkSurfaceVariant,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = PowerGold,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

private fun openUrl(context: android.content.Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (_: Exception) {}
}
