package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WhyUsStat
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun StatsBar(viewModel: PowerZoneViewModel) {
    val stats = viewModel.whyUsStats

    var isTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isTriggered = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("stats_bar")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "WHY POWERZONE?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = PowerGold,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "Baner's Most Trusted Fitness Hub",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(FlameOrange.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "LIVE STATS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = FlameOrange
                )
            }
        }

        // Grid of 4 Animated Stats Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // First 2 Stats
            stats.take(2).forEach { stat ->
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedStatCard(stat = stat, isTriggered = isTriggered)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Next 2 Stats
            stats.drop(2).take(2).forEach { stat ->
                Box(modifier = Modifier.weight(1f)) {
                    AnimatedStatCard(stat = stat, isTriggered = isTriggered)
                }
            }
        }
    }
}

@Composable
fun AnimatedStatCard(stat: WhyUsStat, isTriggered: Boolean) {
    val animatedValue = remember { Animatable(0f) }

    LaunchedEffect(isTriggered) {
        if (isTriggered) {
            animatedValue.animateTo(
                targetValue = stat.targetValue.toFloat(),
                animationSpec = tween(
                    durationMillis = 1800,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    val currentValue = animatedValue.value.toInt()
    val formattedNumber = when (stat.id) {
        "stat_members" -> if (currentValue >= 1000) "1,${(currentValue % 1000).toString().padStart(3, '0')}" else currentValue.toString()
        else -> currentValue.toString()
    }

    val icon = when (stat.id) {
        "stat_members" -> Icons.Default.Group
        "stat_years" -> Icons.Default.EmojiEvents
        "stat_trainers" -> Icons.Default.WorkspacePremium
        else -> Icons.Default.Star
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(0.8.dp, DarkCardBorder, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(FlameOrange.copy(alpha = 0.3f), PowerGold.copy(alpha = 0.2f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PowerGold,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = stat.title.uppercase(),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Counter Display Text
            Text(
                text = "${stat.prefix}$formattedNumber${stat.suffix}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = FlameOrange
            )

            Text(
                text = stat.description,
                fontSize = 10.sp,
                color = TextSecondary,
                lineHeight = 13.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
