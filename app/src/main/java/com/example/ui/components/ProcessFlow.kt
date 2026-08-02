package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProcessStep
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun ProcessFlow(
    viewModel: PowerZoneViewModel,
    onBookTrial: () -> Unit,
    onOpenBmi: () -> Unit
) {
    val steps = viewModel.processSteps

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("process_flow")
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "HOW IT WORKS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = FlameOrange,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "Your 4-Step Transformation Path",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Surface(
                color = DarkSurfaceVariant,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "SIMPLE & PROVEN",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Steps Column with visual connector lines
        steps.forEachIndexed { index, step ->
            ProcessStepCard(
                step = step,
                isLast = index == steps.size - 1,
                onStepClick = {
                    when (step.stepNumber) {
                        1 -> onBookTrial()
                        2 -> onOpenBmi()
                        3 -> onBookTrial()
                        4 -> onBookTrial()
                    }
                }
            )
            if (index < steps.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ProcessStepCard(
    step: ProcessStep,
    isLast: Boolean,
    onStepClick: () -> Unit
) {
    val icon = when (step.stepNumber) {
        1 -> Icons.Default.EventAvailable
        2 -> Icons.Default.Analytics
        3 -> Icons.Default.Assignment
        else -> Icons.Default.FitnessCenter
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(16.dp))
            .clickable { onStepClick() }
            .testTag("step_card_${step.stepNumber}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Step Number Badge
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(FlameOrange, FlameOrangeDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "0${step.stepNumber}",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                if (!isLast) {
                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .width(2.dp)
                            .height(24.dp)
                            .background(FlameOrange.copy(alpha = 0.4f))
                    )
                }
            }

            // Step Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = step.title.uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = TextPrimary
                    )

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PowerGold,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = step.subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PowerGold,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = step.description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = step.actionText,
                        fontSize = 12.sp,
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
