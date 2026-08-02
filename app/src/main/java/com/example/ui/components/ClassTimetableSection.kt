package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
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

data class ClassSlot(
    val id: String,
    val day: String, // "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    val className: String, // "Zumba Dance Fitness", "Power Yoga & Stretch", "Functional HIIT Shred", "Crossfit Power Hour", "Ladies Special Fitness", "Spinning & Cardio Surge"
    val timeSlot: String, // "7:00 AM - 8:00 AM"
    val instructor: String, // "Priya Kulkarni", "Sameer Joshi", "Suraj Sharma"
    val roomStudio: String, // "Studio A (Main Floor)", "Studio B (Mind & Body)", "Cardio Deck"
    val totalSeats: Int,
    val openSeats: Int,
    val intensity: String, // "High", "Moderate", "All Levels"
    val isHot: Boolean = false
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClassTimetableSection(
    onReserveSeat: (ClassSlot) -> Unit
) {
    var selectedDay by remember { mutableStateOf("Mon") }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val categoryFilters = listOf("All", "Zumba", "Yoga", "HIIT", "Ladies Batch", "Crossfit")

    val classSchedule = remember {
        listOf(
            ClassSlot("c1", "Mon", "Zumba Dance Fitness", "6:30 AM - 7:30 AM", "Priya Kulkarni", "Studio B (Mind & Body)", 25, 3, "High", true),
            ClassSlot("c2", "Mon", "Functional HIIT Shred", "8:00 AM - 9:00 AM", "Sameer Joshi", "Studio A (Main Floor)", 20, 5, "High", false),
            ClassSlot("c3", "Mon", "Ladies Exclusive Toning", "11:00 AM - 12:00 PM", "Priya Kulkarni", "Studio B (Private)", 15, 2, "Moderate", true),
            ClassSlot("c4", "Mon", "Power Yoga & Vinyasa", "6:00 PM - 7:00 PM", "Suraj Sharma", "Studio B (Mind & Body)", 20, 8, "All Levels", false),
            ClassSlot("c5", "Mon", "Crossfit & Heavy Barbell", "7:30 PM - 8:30 PM", "Suraj Sharma", "Studio A (Main Floor)", 18, 4, "High", true),

            ClassSlot("c6", "Tue", "Power Yoga & Stretch", "6:30 AM - 7:30 AM", "Priya Kulkarni", "Studio B (Mind & Body)", 20, 6, "All Levels", false),
            ClassSlot("c7", "Tue", "Spinning & Cardio Surge", "8:00 AM - 9:00 AM", "Sameer Joshi", "Cardio Deck", 15, 2, "High", true),
            ClassSlot("c8", "Tue", "Zumba Evening Bash", "6:00 PM - 7:00 PM", "Priya Kulkarni", "Studio B (Mind & Body)", 25, 1, "High", true),
            ClassSlot("c9", "Tue", "Core & Calisthenics", "7:30 PM - 8:30 PM", "Sameer Joshi", "Studio A (Main Floor)", 20, 7, "Moderate", false),

            ClassSlot("c10", "Wed", "Zumba Dance Fitness", "6:30 AM - 7:30 AM", "Priya Kulkarni", "Studio B", 25, 4, "High", false),
            ClassSlot("c11", "Wed", "Functional HIIT Shred", "8:00 AM - 9:00 AM", "Sameer Joshi", "Studio A", 20, 3, "High", true),
            ClassSlot("c12", "Wed", "Ladies Special Fitness", "11:00 AM - 12:00 PM", "Priya Kulkarni", "Studio B", 15, 5, "Moderate", false),
            ClassSlot("c13", "Wed", "Crossfit Power Hour", "7:00 PM - 8:00 PM", "Suraj Sharma", "Studio A", 18, 2, "High", true),

            ClassSlot("c14", "Thu", "Power Yoga & Vinyasa", "6:30 AM - 7:30 AM", "Suraj Sharma", "Studio B", 20, 7, "All Levels", false),
            ClassSlot("c15", "Thu", "Zumba Evening Bash", "6:30 PM - 7:30 PM", "Priya Kulkarni", "Studio B", 25, 2, "High", true),
            ClassSlot("c16", "Thu", "Functional HIIT Shred", "7:30 PM - 8:30 PM", "Sameer Joshi", "Studio A", 20, 6, "High", false),

            ClassSlot("c17", "Fri", "Zumba Dance Fitness", "6:30 AM - 7:30 AM", "Priya Kulkarni", "Studio B", 25, 5, "High", false),
            ClassSlot("c18", "Fri", "Crossfit & Heavy Barbell", "8:00 AM - 9:00 AM", "Suraj Sharma", "Studio A", 18, 3, "High", true),
            ClassSlot("c19", "Fri", "Ladies Exclusive Toning", "11:00 AM - 12:00 PM", "Priya Kulkarni", "Studio B", 15, 1, "Moderate", true),
            ClassSlot("c20", "Fri", "Power Yoga & Stretch", "6:00 PM - 7:00 PM", "Suraj Sharma", "Studio B", 20, 9, "All Levels", false),

            ClassSlot("c21", "Sat", "Weekend Warrior HIIT", "7:00 AM - 8:30 AM", "Sameer Joshi & Suraj Sharma", "Studio A & B", 30, 4, "High", true),
            ClassSlot("c22", "Sat", "Zumba Party Blast", "9:00 AM - 10:00 AM", "Priya Kulkarni", "Studio B", 30, 2, "High", true),
            ClassSlot("c23", "Sat", "Power Yoga Detox", "5:00 PM - 6:00 PM", "Priya Kulkarni", "Studio B", 20, 8, "All Levels", false),

            ClassSlot("c24", "Sun", "Sunday Recovery Yoga", "7:30 AM - 8:30 AM", "Suraj Sharma", "Studio B", 25, 12, "All Levels", false),
            ClassSlot("c25", "Sun", "Calisthenics & Mobility", "9:00 AM - 10:15 AM", "Sameer Joshi", "Studio A", 20, 6, "Moderate", false)
        )
    }

    val dayClasses = classSchedule.filter { slot ->
        slot.day == selectedDay && (
            selectedCategoryFilter == "All" ||
            (selectedCategoryFilter == "Zumba" && slot.className.contains("Zumba", ignoreCase = true)) ||
            (selectedCategoryFilter == "Yoga" && slot.className.contains("Yoga", ignoreCase = true)) ||
            (selectedCategoryFilter == "HIIT" && slot.className.contains("HIIT", ignoreCase = true)) ||
            (selectedCategoryFilter == "Ladies Batch" && slot.className.contains("Ladies", ignoreCase = true)) ||
            (selectedCategoryFilter == "Crossfit" && slot.className.contains("Crossfit", ignoreCase = true))
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Timetable",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Live Class Timetable Widget",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Real-time open seat counter for Zumba, Yoga & HIIT",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Day Selector Tabs
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(days) { day ->
                    val isSelected = day == selectedDay
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedDay = day }
                            .testTag("timetable_day_$day"),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = day,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Pills
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categoryFilters) { cat ->
                    val isChosen = cat == selectedCategoryFilter
                    FilterChip(
                        selected = isChosen,
                        onClick = { selectedCategoryFilter = cat },
                        label = {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.secondary,
                            selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Class Cards List
            if (dayClasses.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No $selectedCategoryFilter slots scheduled on $selectedDay. Check other days!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    dayClasses.forEach { slot ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = if (slot.isHot) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .testTag("slot_card_${slot.id}"),
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTime,
                                            contentDescription = "Time",
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = slot.timeSlot,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        )
                                    }

                                    Surface(
                                        shape = CircleShape,
                                        color = if (slot.openSeats <= 3) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.EventSeat,
                                                contentDescription = "Seats",
                                                tint = if (slot.openSeats <= 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = if (slot.openSeats <= 3) "🔥 ${slot.openSeats} Seats Left" else "${slot.openSeats} Seats Open",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (slot.openSeats <= 3) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = slot.className,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "👤 Trainer: ${slot.instructor}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "📍 ${slot.roomStudio}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                        )
                                    }

                                    Button(
                                        onClick = { onReserveSeat(slot) },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        modifier = Modifier.testTag("reserve_slot_${slot.id}")
                                    ) {
                                        Text(text = "Reserve Seat", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
