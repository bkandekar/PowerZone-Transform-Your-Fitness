package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.viewmodel.PowerZoneViewModel
import kotlin.math.roundToInt

data class FitnessTip(
    val categoryName: String,
    val rangeText: String,
    val color: Color,
    val workoutTip: String,
    val nutritionTip: String,
    val recommendedProgram: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BmiCalculatorModal(
    viewModel: PowerZoneViewModel,
    onDismiss: () -> Unit,
    onBookTrialWithGoal: (String) -> Unit
) {
    var heightCm by remember { mutableFloatStateOf(170f) }
    var weightKg by remember { mutableFloatStateOf(70f) }
    var age by remember { mutableFloatStateOf(26f) }
    var isMale by remember { mutableStateOf(true) }

    // Live Calculations
    val heightM = heightCm / 100f
    val bmi = if (heightM > 0) weightKg / (heightM * heightM) else 0f

    val tipInfo = when {
        bmi < 18.5f -> FitnessTip(
            categoryName = "Underweight",
            rangeText = "< 18.5",
            color = Color(0xFFFFC107), // Gold Warning
            workoutTip = "Focus on hypertrophy strength training with heavy compound lifts (Squats, Deadlifts, Bench Press) 3-4 days a week.",
            nutritionTip = "Consume a healthy caloric surplus (+300-500 kcal/day). Target 1.8g-2.2g protein per kg with dense carbs & healthy fats.",
            recommendedProgram = "Bulking & Lean Mass Gain"
        )
        bmi in 18.5f..24.9f -> FitnessTip(
            categoryName = "Normal / Healthy Weight",
            rangeText = "18.5 - 24.9",
            color = Color(0xFF00E676), // Neon Green Success
            workoutTip = "Maintain body recomposition with 3 days of progressive strength training & 2 days of high-intensity cardio or functional mobility.",
            nutritionTip = "Maintain baseline maintenance calories with balanced macros: 40% carbs, 30% protein, 30% essential fats.",
            recommendedProgram = "Athletic Performance & Core Conditioning"
        )
        bmi in 25.0f..29.9f -> FitnessTip(
            categoryName = "Overweight",
            rangeText = "25.0 - 29.9",
            color = Color(0xFFFF9800), // Orange Warning
            workoutTip = "Combine 4 days of weight training circuits with 20 mins post-workout cardio to maximize calorie burn while preserving muscle mass.",
            nutritionTip = "Create a slight caloric deficit (-400 kcal/day). Prioritize fiber, lean protein (chicken, paneer, tofu) & stay hydrated.",
            recommendedProgram = "Fat Loss & HIIT Shred"
        )
        else -> FitnessTip(
            categoryName = "Obese / High Body Fat",
            rangeText = "≥ 30.0",
            color = Color(0xFFFF1744), // Crimson Danger
            workoutTip = "Start with joint-friendly low-impact exercises (elliptical, swimming, light dumbbell circuits, walking) guided by our certified coaches.",
            nutritionTip = "Structured caloric deficit (-500 kcal/day). Avoid sugary beverages & processed foods. Drink at least 3.5L water daily.",
            recommendedProgram = "Medical Weight Loss & Metabolism Reset"
        )
    }

    // BMR Estimation (Mifflin-St Jeor)
    val bmr = if (isMale) {
        (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5
    } else {
        (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161
    }.roundToInt()

    val recommendedWaterLiters = (weightKg * 0.035f).let { (it * 10).roundToInt() / 10f }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .testTag("modal_bmi_calc")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
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
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "BMI & BODY CALCULATOR",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Text(
                                text = "Instant Body Mass Index & Personalized Fitness Tip",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Gender Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { isMale = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isMale) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (isMale) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("bmi_gender_male")
                    ) {
                        Text("👨 Male", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { isMale = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isMale) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (!isMale) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("bmi_gender_female")
                    ) {
                        Text("👩 Female", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Height Section (+ / - Buttons & Slider)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "HEIGHT",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { if (heightCm > 120f) heightCm -= 1f },
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease Height", modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "${heightCm.toInt()} cm",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = { if (heightCm < 220f) heightCm += 1f },
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase Height", modifier = Modifier.size(16.dp))
                        }
                    }
                }
                Slider(
                    value = heightCm,
                    onValueChange = { heightCm = it },
                    valueRange = 120f..220f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.secondary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.testTag("input_height_slider")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Weight Section (+ / - Buttons & Slider)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "WEIGHT",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { if (weightKg > 30f) weightKg -= 1f },
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease Weight", modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "${weightKg.toInt()} kg",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = { if (weightKg < 180f) weightKg += 1f },
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase Weight", modifier = Modifier.size(16.dp))
                        }
                    }
                }
                Slider(
                    value = weightKg,
                    onValueChange = { weightKg = it },
                    valueRange = 30f..180f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.testTag("input_weight_slider")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Age Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AGE",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${age.toInt()} yrs",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Slider(
                    value = age,
                    onValueChange = { age = it },
                    valueRange = 14f..80f,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.onSurface,
                        activeTrackColor = MaterialTheme.colorScheme.onSurface,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.testTag("input_age_slider")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Live Results Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, tipInfo.color.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CALCULATED BMI",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "%.1f".format(bmi),
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = tipInfo.color
                                    )
                                )
                            }

                            Surface(
                                color = tipInfo.color.copy(alpha = 0.15f),
                                border = BorderStroke(1.dp, tipInfo.color),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = tipInfo.categoryName.uppercase(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = tipInfo.color,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Visual Gauge Bar
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(CircleShape)
                            ) {
                                Box(modifier = Modifier.weight(18.5f).fillMaxHeight().background(Color(0xFFFFC107)))
                                Box(modifier = Modifier.weight(6.4f).fillMaxHeight().background(Color(0xFF00E676)))
                                Box(modifier = Modifier.weight(5f).fillMaxHeight().background(Color(0xFFFF9800)))
                                Box(modifier = Modifier.weight(10f).fillMaxHeight().background(Color(0xFFFF1744)))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("<18.5", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("18.5-24.9", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("25-29.9", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("≥30", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        // BMR & Water Stat Badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FitnessCenter,
                                    contentDescription = "BMR",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text("Est. BMR", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("$bmr kcal/day", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WaterDrop,
                                    contentDescription = "Water Goal",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text("Water Goal", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("$recommendedWaterLiters L/day", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Personalized Fitness & Nutrition Tip Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = tipInfo.color.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, tipInfo.color.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Tip",
                                tint = tipInfo.color,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Personalized Fitness & Nutrition Tip",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = tipInfo.color
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "🏋️ Workout Advice:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = tipInfo.workoutTip,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "🥗 Nutrition Advice:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = tipInfo.nutritionTip,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Text(
                                text = "💡 Recommended PowerZone Batch: ${tipInfo.recommendedProgram}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // CTA Button to Claim Free Pass with calculated Goal
                Button(
                    onClick = {
                        val customGoal = "Target Plan for ${tipInfo.categoryName} (BMI: ${"%.1f".format(bmi)}, BMR: $bmr kcal)"
                        onBookTrialWithGoal(customGoal)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_bmi_book_pass")
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Claim Free ${tipInfo.categoryName} Consultation Pass",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
