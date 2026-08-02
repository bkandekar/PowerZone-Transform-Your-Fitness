package com.example.ui.components

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.WhatsappGreen
import com.example.ui.viewmodel.PowerZoneViewModel

@Composable
fun FloatingWhatsAppBubble(
    viewModel: PowerZoneViewModel
) {
    val context = LocalContext.current

    // Pulsating animation scale
    val infiniteTransition = rememberInfiniteTransition(label = "WhatsAppPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp, end = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                viewModel.launchWhatsAppRouting(
                    context = context,
                    locality = "Baner / Pune",
                    fitnessGoal = "General Inquiry / Direct Chat",
                    programTitle = "Floating Chat Bubble"
                )
            },
            containerColor = WhatsappGreen,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .scale(scale)
                .size(58.dp)
                .testTag("floating_whatsapp_chat")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp Live Chat",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
