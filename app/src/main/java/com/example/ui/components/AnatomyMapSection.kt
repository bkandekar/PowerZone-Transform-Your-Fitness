package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
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

data class MuscleZone(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val equipmentAvailable: List<String>,
    val targetExercises: List<String>,
    val leadTrainer: String,
    val description: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnatomyMapSection(
    onBookTrialForZone: (String) -> Unit
) {
    val muscleZones = remember {
        listOf(
            MuscleZone(
                id = "chest",
                name = "Chest & Pecs",
                iconEmoji = "🏋️‍♂️",
                equipmentAvailable = listOf("Hammer Strength Incline Press", "Cable Crossover Station", "Olympic Flat Bench", "Dumbbells up to 50kg"),
                targetExercises = listOf("Incline Dumbbell Press", "Low-to-High Cable Flyes", "Weighted Dips"),
                leadTrainer = "Suraj Sharma (Powerlifting Master)",
                description = "Build upper-chest density and outer pec definition with heavy Olympic barbells & plate-loaded isolateral machines."
            ),
            MuscleZone(
                id = "back",
                name = "Back & Lats",
                iconEmoji = "🛡️",
                equipmentAvailable = listOf("Lat Pulldown (Multi-Grip)", "Seated Cable Row", "T-Bar Row Landmine", "Hyperextension Bench"),
                targetExercises = listOf("Wide-Grip Lat Pulldowns", "Meadows Rows", "Deadlifts & Rack Pulls"),
                leadTrainer = "Suraj Sharma & Sameer Joshi",
                description = "Sculpt a V-taper frame using biomechanically optimized cables, landmines, and ergonomic pull handles."
            ),
            MuscleZone(
                id = "legs",
                name = "Legs & Quads",
                iconEmoji = "🍗",
                equipmentAvailable = listOf("45° Leg Press Machine", "Hack Squat Rig", "Prone Leg Curl", "Calf Raise Station"),
                targetExercises = listOf("Barbell Back Squats", "Romanian Deadlifts", "Walking Lunges"),
                leadTrainer = "Priya Kulkarni (Legs & Glutes Specialist)",
                description = "Develop lower body explosive strength and quad mass with smooth linear-bearing leg press and hack squat rigs."
            ),
            MuscleZone(
                id = "core",
                name = "Core & Abs",
                iconEmoji = "🔥",
                equipmentAvailable = listOf("Ab Captain's Chair", "Decline Ab Bench", "Ab Roller Wheels", "TRX Suspension Trainers"),
                targetExercises = listOf("Hanging Leg Raises", "Cable Woodchoppers", "Plank Variations"),
                leadTrainer = "Sameer Joshi (HIIT & Mobility Coach)",
                description = "Tighten waistline and reinforce spinal stability through high-octane rotational core and functional TRX drills."
            ),
            MuscleZone(
                id = "shoulders",
                name = "Shoulders & Delts",
                iconEmoji = "⚡",
                equipmentAvailable = listOf("Seated Military Press Rack", "Dual Cable Lateral Flye", "Reverse Pec Deck Machine"),
                targetExercises = listOf("Overhead Dumbbell Press", "Cable Lateral Raises", "Face Pulls"),
                leadTrainer = "Suraj Sharma",
                description = "Maximize 3D shoulder cap rounding using smooth pulley cables for constant rotational tension."
            ),
            MuscleZone(
                id = "arms",
                name = "Arms & Guns",
                iconEmoji = "💪",
                equipmentAvailable = listOf("Preacher Curl Bench", "Triceps Cable Rope Pulley", "EZ Curl Bar Station"),
                targetExercises = listOf("Preacher Hammer Curls", "Triceps Skullcrushers", "Cable Pushdowns"),
                leadTrainer = "Sameer Joshi",
                description = "Target biceps peak and triceps horseshoe lateral head with strict isolation preacher benches and pulley attachments."
            )
        )
    }

    var selectedZoneId by remember { mutableStateOf("chest") }
    val activeZone = muscleZones.first { it.id == selectedZoneId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
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
                                        MaterialTheme.colorScheme.secondary,
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = "Anatomy",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Target Muscle Equipment Map",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Tap a muscle group to view PowerZone equipment & exercises",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Muscle Zone Selector Pills
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(muscleZones) { zone ->
                    val isSelected = zone.id == selectedZoneId
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedZoneId = zone.id }
                            .testTag("muscle_zone_${zone.id}"),
                        color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(text = zone.iconEmoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = zone.name,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Active Zone Details Container
            AnimatedContent(
                targetState = activeZone,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ZoneDetails"
            ) { zone ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${zone.iconEmoji} ${zone.name} Target Zone",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Trainer",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = zone.leadTrainer,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = zone.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Equipment List
                    Text(
                        text = "🏋️ Gym Equipment Floor Setup:",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        zone.equipmentAvailable.forEach { equip ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Check",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = equip,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Recommended Exercises
                    Text(
                        text = "🎯 PowerZone Recommended Workouts:",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = zone.targetExercises.joinToString(" • "),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onBookTrialForZone("Trial for ${zone.name} Focus Zone") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_zone_trial_${zone.id}"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Book Trial Session for ${zone.name}",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
