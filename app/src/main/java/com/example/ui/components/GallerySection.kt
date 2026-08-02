package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GalleryItem
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun GallerySection(
    viewModel: PowerZoneViewModel,
    onBookTrialForZone: (String) -> Unit
) {
    val selectedCategory by viewModel.galleryCategory.collectAsState()
    val selectedTag by viewModel.galleryTag.collectAsState()
    val allGalleryItems = viewModel.allGalleryItems

    val categories = listOf("All", "Gym Floor", "Cardio Zone", "Personal Training", "Group Class Studio", "Transformation Wall", "Steam Room", "Yoga Studio", "Ladies Batch")

    val filteredItems = allGalleryItems.filter { item ->
        val matchesCategory = (selectedCategory == "All") || item.category.equals(selectedCategory, ignoreCase = true)
        val matchesTag = selectedTag == null || item.tags.contains(selectedTag)
        matchesCategory && matchesTag
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("gallery_section")
    ) {
        // Section Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "STUDIO GALLERY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = PowerGold,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Explore PowerZone Facilities & Zones",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Tap any facility card or tag to view high-res floor tour and details.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setGalleryCategory(cat) },
                    label = {
                        Text(
                            text = cat,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = FlameOrange,
                        selectedLabelColor = Color.White,
                        containerColor = DarkSurfaceVariant,
                        labelColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("chip_gallery_$cat")
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Grid/List of Gallery Cards with Image Tags
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            filteredItems.forEach { item ->
                GalleryCard(
                    item = item,
                    selectedTag = selectedTag,
                    onTagClick = { tag -> viewModel.setGalleryTag(tag) },
                    onItemClick = { viewModel.openGalleryItem(item) }
                )
            }
        }
    }
}

@Composable
fun GalleryCard(
    item: GalleryItem,
    selectedTag: String?,
    onTagClick: (String) -> Unit,
    onItemClick: () -> Unit
) {
    val (gradientColors, icon) = when (item.category) {
        "Gym Floor" -> listOf(Color(0xFF2C1810), Color(0xFF1A120B)) to Icons.Default.FitnessCenter
        "Cardio Zone" -> listOf(Color(0xFF1E2818), Color(0xFF0F150B)) to Icons.Default.DirectionsRun
        "Personal Training" -> listOf(Color(0xFF2B1A28), Color(0xFF140D13)) to Icons.Default.AccessibilityNew
        "Group Class Studio" -> listOf(Color(0xFF12242B), Color(0xFF081216)) to Icons.Default.Groups
        "Transformation Wall" -> listOf(Color(0xFF2B2412), Color(0xFF141007)) to Icons.Default.EmojiEvents
        "Steam Room" -> listOf(Color(0xFF122228), Color(0xFF091216)) to Icons.Default.HotTub
        "Yoga Studio" -> listOf(Color(0xFF1A261E), Color(0xFF0C130E)) to Icons.Default.SelfImprovement
        else -> listOf(Color(0xFF2B1621), Color(0xFF150A10)) to Icons.Default.Female
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(20.dp))
            .clickable { onItemClick() }
            .testTag("gallery_card_${item.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // High-Impact Graphic Header Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Brush.verticalGradient(gradientColors)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PowerGold,
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.category.uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        letterSpacing = 1.sp
                    )
                }

                // Top Right Badge
                Surface(
                    color = FlameOrange,
                    shape = RoundedCornerShape(topEnd = 0.dp, bottomStart = 12.dp, topStart = 12.dp, bottomEnd = 0.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = item.highlightBadge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Card Body & Image Tags
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                )

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Image Tags Display (ADD IMAGE TAGS)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Tag,
                        contentDescription = "Image Tags",
                        tint = PowerGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Image Tags:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PowerGold
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(item.tags) { tag ->
                        val isTagSelected = tag == selectedTag
                        Surface(
                            color = if (isTagSelected) FlameOrange else DarkSurfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.clickable { onTagClick(tag) }
                        ) {
                            Text(
                                text = tag,
                                fontSize = 10.sp,
                                fontWeight = if (isTagSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isTagSelected) Color.White else TextPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tap to View Full Tour & Book",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = FlameOrange
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = FlameOrange,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
