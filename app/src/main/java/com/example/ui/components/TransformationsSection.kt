package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TransformationStory(
    val id: String,
    val name: String,
    val ageLocality: String, // "Age 28, Baner"
    val statHighlight: String, // "-14 kg in 4 Months"
    val beforeWeight: String, // "88 kg"
    val afterWeight: String, // "74 kg"
    val duration: String, // "16 Weeks"
    val trainerCoach: String, // "Coached by Rahul Sharma"
    val keyMilestone: String, // "Reduced Body Fat from 28% to 15%"
    val quote: String // "PowerZone's personal trainer created a tailored diet and deadlift progression plan!"
)

@Composable
fun TransformationsSection(
    onStartTransformationClick: () -> Unit
) {
    val stories = remember {
        listOf(
            TransformationStory(
                id = "t1",
                name = "Aakash Kulkarni",
                ageLocality = "Age 29 • Baner, Pune",
                statHighlight = "🔥 -16 kg Fat Loss",
                beforeWeight = "92 kg",
                afterWeight = "76 kg",
                duration = "4 Months",
                trainerCoach = "Rahul Sharma",
                keyMilestone = "Body Fat %: 31% ➔ 16%",
                quote = "Custom diet chart with Indian meals + 4 days weight training circuits brought me back to peak energy!"
            ),
            TransformationStory(
                id = "t2",
                name = "Sneha Kadam",
                ageLocality = "Age 26 • Balewadi",
                statHighlight = "💪 Lean Toning & Core Strength",
                beforeWeight = "68 kg",
                afterWeight = "57 kg",
                duration = "3.5 Months",
                trainerCoach = "Priya Kulkarni",
                keyMilestone = "Inch Loss: 5 Inches off Waist",
                quote = "The 11 AM Ladies Batch gave me an amazing supportive community and sustainable workout habit."
            ),
            TransformationStory(
                id = "t3",
                name = "Siddharth Joshi",
                ageLocality = "Age 34 • Aundh",
                statHighlight = "⚡ Muscle Mass & Hypertrophy",
                beforeWeight = "62 kg",
                afterWeight = "73 kg",
                duration = "5 Months",
                trainerCoach = "Sameer Joshi",
                keyMilestone = "Bench Press: 40kg ➔ 95kg",
                quote = "I was skinny and struggling with energy. Sameer's progressive overload coaching gave me 11 kg pure muscle!"
            ),
            TransformationStory(
                id = "t4",
                name = "Dr. Sameer Patel",
                ageLocality = "Age 41 • Pashan",
                statHighlight = "🩺 Posture & Back Pain Relief",
                beforeWeight = "85 kg",
                afterWeight = "75 kg",
                duration = "3 Months",
                trainerCoach = "Suraj Sharma",
                keyMilestone = "Cured Chronic Lower Back Pain",
                quote = "Mobility drills and deadlift biomechanics coaching completely eliminated my 5-year office lumbar pain."
            )
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = "Transformation",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "BEFORE / AFTER TRANSFORMATION WALL",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Real Baner Pune members, verified kg-lost & duration stats",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Transformation Story Cards Carousel
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(stories) { story ->
                    Surface(
                        modifier = Modifier
                            .width(300.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                RoundedCornerShape(18.dp)
                            )
                            .testTag("transformation_card_${story.id}"),
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Badge Highlight
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = story.statHighlight,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = story.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = story.ageLocality,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Before vs After Stat Comparison Box
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("BEFORE", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                                    Text(story.beforeWeight, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                                }

                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "To",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("AFTER (${story.duration})", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                                    Text(story.afterWeight, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "🎯 ${story.keyMilestone}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "\"${story.quote}\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Coach",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Coach: ${story.trainerCoach}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Start Transformation CTA
            Button(
                onClick = onStartTransformationClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_start_transformation")
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Start Your Transformation Today (Claim Free Pass)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
