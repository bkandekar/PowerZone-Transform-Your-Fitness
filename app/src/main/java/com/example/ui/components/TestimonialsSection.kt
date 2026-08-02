package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Testimonial
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun TestimonialsSection(viewModel: PowerZoneViewModel, onBookTrial: () -> Unit) {
    val selectedLocality by viewModel.testimonialLocality.collectAsState()
    val allTestimonials = viewModel.allTestimonials

    val localities = listOf("All", "Baner", "Balewadi", "Aundh", "Pashan")

    val filteredTestimonials = if (selectedLocality == "All") {
        allTestimonials
    } else {
        allTestimonials.filter { it.locality.equals(selectedLocality, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("testimonials_section")
    ) {
        // Section Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "MEMBER REVIEWS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = FlameOrange,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Transformation Stories Across Pune",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Real results from members in Baner, Balewadi, Aundh & Pashan.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Locality Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(localities) { loc ->
                val isSelected = loc == selectedLocality
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setTestimonialLocality(loc) },
                    label = {
                        Text(
                            text = if (loc == "All") "All Localities" else "📍 $loc",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PowerGold,
                        selectedLabelColor = TextDark,
                        containerColor = DarkSurfaceVariant,
                        labelColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("chip_locality_$loc")
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Reviews List
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            filteredTestimonials.forEach { review ->
                TestimonialCard(review = review, onBookTrial = onBookTrial)
            }
        }
    }
}

@Composable
fun TestimonialCard(review: Testimonial, onBookTrial: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(20.dp))
            .testTag("review_card_${review.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Avatar, Name, Locality Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(review.avatarBgColor)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = review.name.take(1).uppercase(),
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = review.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = review.duration,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                Surface(
                    color = FlameOrange.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = FlameOrange,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${review.locality}, Pune",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = FlameOrange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Achievement Callout Badge
            Surface(
                color = PowerGold.copy(alpha = 0.15f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🏆 Achievement: ${review.achievement}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 5-Star Rating Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = PowerGold,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "5.0 Verified Member",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quote Text
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = TextSecondary.copy(alpha = 0.4f),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = review.reviewText,
                    fontSize = 12.sp,
                    color = TextPrimary,
                    lineHeight = 17.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
