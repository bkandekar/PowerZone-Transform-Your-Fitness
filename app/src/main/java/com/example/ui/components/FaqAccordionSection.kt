package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.QuestionAnswer
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

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String,
    val category: String // "Trial & Pass", "Pricing & Freeze", "Hygiene & Batches"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FaqAccordionSection(
    onAskQuestionClick: () -> Unit
) {
    var expandedFaqId by remember { mutableStateOf<String?>("faq1") }

    val faqs = remember {
        listOf(
            FaqItem(
                id = "faq1",
                question = "How does the 3-Day Free Trial Pass work at PowerZone Baner?",
                answer = "Your 3-day pass grants 100% free access to our gym floor, cardio deck, and 1 group class (Zumba/Yoga). No credit card or commitment required. Simply request your pass on this app & show the WhatsApp pass code at reception!",
                category = "Trial & Pass"
            ),
            FaqItem(
                id = "faq2",
                question = "Can I freeze my annual gym membership if I travel or fall sick?",
                answer = "Yes! Annual Elite members can pause or freeze their membership for up to 45 days at no extra fee. Quarterly members can pause for up to 14 days upon submitting a travel itinerary or medical note.",
                category = "Pricing & Freeze"
            ),
            FaqItem(
                id = "faq3",
                question = "Are there dedicated Women-Only batches at PowerZone?",
                answer = "Yes! We run exclusive Ladies Batches every Mon-Sat from 11:00 AM to 12:00 PM and 4:00 PM to 5:00 PM, coached by certified female fitness specialist Priya Kulkarni with full privacy.",
                category = "Hygiene & Batches"
            ),
            FaqItem(
                id = "faq4",
                question = "What hygiene and sanitization protocols are followed in the gym?",
                answer = "Our staff sanitizes all dumbell handles, barbell grips, and cardio screens every 2 hours. Hand sanitizer stations and clean microfiber towel dispensers are placed throughout the floor.",
                category = "Hygiene & Batches"
            ),
            FaqItem(
                id = "faq5",
                question = "Is personal training included or available separately?",
                answer = "Every member receives a complimentary baseline fitness assessment and diet chart. For 1-on-1 dedicated coaching, we offer customized 12-week Transformation Packages with ACE/K11 certified trainers.",
                category = "Pricing & Freeze"
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
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "FAQ",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "FREQUENTLY ASKED QUESTIONS",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Answers to Pricing, Trial Rules & Hygiene Protocols",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Accordion Items
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                faqs.forEach { faq ->
                    val isExpanded = expandedFaqId == faq.id

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                1.dp,
                                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                RoundedCornerShape(14.dp)
                            )
                            .clickable { expandedFaqId = if (isExpanded) null else faq.id }
                            .testTag("faq_item_${faq.id}"),
                        shape = RoundedCornerShape(14.dp),
                        color = if (isExpanded) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = faq.question,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    ),
                                    modifier = Modifier.weight(1f)
                                )

                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand Toggle",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            AnimatedVisibility(
                                visible = isExpanded,
                                enter = expandVertically(),
                                exit = shrinkVertically()
                            ) {
                                Column(modifier = Modifier.padding(top = 10.dp)) {
                                    HorizontalDivider(
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                        modifier = Modifier.padding(bottom = 10.dp)
                                    )
                                    Text(
                                        text = faq.answer,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ask Custom Question Button
            OutlinedButton(
                onClick = onAskQuestionClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("btn_ask_faq_whatsapp")
            ) {
                Icon(
                    imageVector = Icons.Default.QuestionAnswer,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Have a Different Question? Ask Us on WhatsApp", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}
