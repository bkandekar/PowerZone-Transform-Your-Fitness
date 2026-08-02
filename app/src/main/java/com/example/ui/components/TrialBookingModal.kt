package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.*
import com.example.ui.viewmodel.PowerZoneViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrialBookingModal(
    viewModel: PowerZoneViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val selectedProgram by viewModel.selectedProgramForTrial.collectAsState()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedLocality by remember { mutableStateOf("Baner") }
    var selectedGoal by remember { mutableStateOf("Weight Loss & Fat Burn") }
    var selectedSlot by remember { mutableStateOf("Morning Batch (7:00 AM - 8:30 AM)") }

    val localities = listOf("Baner", "Balewadi", "Aundh", "Pashan", "Wakad", "Bavdhan")
    val goals = listOf(
        "Weight Loss & Fat Burn",
        "90-Day Fat-to-Fit Transformation",
        "Muscle Building & Powerlifting",
        "1-on-1 Personal Training",
        "Ladies Special Batch",
        "Yoga & Core Flexibility"
    )
    val slots = listOf(
        "Morning Batch (6:00 AM - 9:00 AM)",
        "Mid-day Batch (11:00 AM - 1:00 PM)",
        "Evening Batch (5:00 PM - 9:30 PM)"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = DarkSurface,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .border(1.dp, FlameOrange.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .testTag("modal_trial_booking")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Modal Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(FlameOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "CLAIM 3-DAY FREE PASS",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            Text(
                                text = "PowerZone Studio • Baner Pune",
                                fontSize = 11.sp,
                                color = PowerGold,
                                fontWeight = FontWeight.Medium
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
                            tint = TextSecondary
                        )
                    }
                }

                if (selectedProgram != null) {
                    Surface(
                        color = FlameOrange.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = "📌 Selected Program: ${selectedProgram?.title}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FlameOrange,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Input Fields
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Full Name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FlameOrange,
                        unfocusedBorderColor = DarkCardBorder,
                        focusedLabelColor = FlameOrange,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_booking_name")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (WhatsApp)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FlameOrange,
                        unfocusedBorderColor = DarkCardBorder,
                        focusedLabelColor = FlameOrange,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_booking_phone")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Locality Picker
                Text(
                    text = "SELECT YOUR LOCALITY (PUNE)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    localities.take(3).forEach { loc ->
                        val isSelected = loc == selectedLocality
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedLocality = loc },
                            label = { Text(loc, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FlameOrange,
                                selectedLabelColor = Color.White,
                                containerColor = DarkSurfaceVariant,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    localities.drop(3).forEach { loc ->
                        val isSelected = loc == selectedLocality
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedLocality = loc },
                            label = { Text(loc, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FlameOrange,
                                selectedLabelColor = Color.White,
                                containerColor = DarkSurfaceVariant,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Goal Selection
                Text(
                    text = "YOUR PRIMARY FITNESS GOAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold
                )

                Column(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    goals.take(3).forEach { goal ->
                        val isSelected = goal == selectedGoal
                        Surface(
                            color = if (isSelected) PowerGold.copy(alpha = 0.2f) else DarkSurfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(if (isSelected) PowerGold else DarkCardBorder, if (isSelected) PowerGold else DarkCardBorder))),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedGoal = goal },
                                    colors = RadioButtonDefaults.colors(selectedColor = PowerGold)
                                )
                                Text(
                                    text = goal,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Preferred Slot
                Text(
                    text = "PREFERRED BATCH SLOT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PowerGold
                )

                Column(
                    modifier = Modifier.padding(top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    slots.forEach { slot ->
                        val isSelected = slot == selectedSlot
                        Surface(
                            color = if (isSelected) FlameOrange.copy(alpha = 0.2f) else DarkSurfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedSlot = slot },
                                    colors = RadioButtonDefaults.colors(selectedColor = FlameOrange)
                                )
                                Text(
                                    text = slot,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Submit & WhatsApp Routing Button
                Button(
                    onClick = {
                        viewModel.submitTrialBooking(
                            context = context,
                            name = name,
                            phone = phone,
                            locality = selectedLocality,
                            fitnessGoal = selectedGoal,
                            preferredSlot = selectedSlot,
                            programTitle = selectedProgram?.title ?: ""
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsappGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_submit_booking_whatsapp")
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Claim Free Pass via WhatsApp (918329931123)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
