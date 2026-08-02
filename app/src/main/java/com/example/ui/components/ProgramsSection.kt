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
import com.example.data.model.Program
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun ProgramsSection(
    viewModel: PowerZoneViewModel,
    onSelectProgram: (Program) -> Unit
) {
    val selectedCategory by viewModel.programCategory.collectAsState()
    val allPrograms = viewModel.allPrograms
    val bookmarks by viewModel.bookmarks.collectAsState()

    val categories = listOf("All", "Training", "Transformation", "Cardio", "Group Class", "Yoga", "Ladies Batch")

    val filteredPrograms = if (selectedCategory == "All") {
        allPrograms
    } else {
        allPrograms.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("programs_section")
    ) {
        // Section Header
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "PROGRAMS OFFERED",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = PowerGold,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Training & Transformation Programs",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Tailored workout regimes designed by expert coaches for every goal.",
                fontSize = 12.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setProgramCategory(cat) },
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
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = DarkCardBorder,
                        selectedBorderColor = FlameOrange
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("chip_program_$cat")
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Program Cards
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            filteredPrograms.forEach { program ->
                val isBookmarked = bookmarks.any { it.programId == program.id }
                ProgramCard(
                    program = program,
                    isBookmarked = isBookmarked,
                    onToggleBookmark = { viewModel.toggleBookmark(program, isBookmarked) },
                    onBookTrial = { onSelectProgram(program) }
                )
            }
        }
    }
}

@Composable
fun ProgramCard(
    program: Program,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onBookTrial: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val categoryIcon = when (program.category) {
        "Training" -> Icons.Default.FitnessCenter
        "Transformation" -> Icons.Default.Bolt
        "Cardio" -> Icons.Default.DirectionsRun
        "Group Class" -> Icons.Default.Groups
        "Yoga" -> Icons.Default.SelfImprovement
        else -> Icons.Default.Female
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(20.dp))
            .testTag("program_card_${program.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category Icon, Badge, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(FlameOrange.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = null,
                            tint = FlameOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Surface(
                        color = PowerGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = program.tagBadge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PowerGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark Program",
                        tint = if (isBookmarked) FlameOrange else TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Program Title
            Text(
                text = program.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
            )

            // Duration & Intensity Row
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "⏱ ${program.duration}",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "🔥 Intensity: ${program.intensity}",
                    fontSize = 11.sp,
                    color = FlameOrange,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = program.description,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Program Tags List
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(program.tags) { tag ->
                    Surface(
                        color = DarkSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 10.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Highlights Checklist (Show 2 items or all if expanded)
            val displayedHighlights = if (expanded) program.highlights else program.highlights.take(2)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                displayedHighlights.forEach { highlight ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = NeonLime,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = highlight,
                            fontSize = 11.sp,
                            color = TextPrimary,
                            lineHeight = 15.sp
                        )
                    }
                }
            }

            if (program.highlights.size > 2) {
                Text(
                    text = if (expanded) "Show Less" else "+ ${program.highlights.size - 2} More Highlights",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .clickable { expanded = !expanded }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onBookTrial,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FlameOrange,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .testTag("btn_book_program_${program.id}")
                ) {
                    Text(
                        text = "Book Trial Pass",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = { expanded = !expanded },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(DarkCardBorder, DarkCardBorder))),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text(
                        text = if (expanded) "Hide Details" else "Schedule & Trainer",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (expanded) {
                Divider(
                    color = DarkCardBorder,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "📅 Schedule: ${program.schedule}",
                        fontSize = 11.sp,
                        color = PowerGold,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "🏋️ Head Trainer: ${program.trainerName}",
                        fontSize = 11.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
