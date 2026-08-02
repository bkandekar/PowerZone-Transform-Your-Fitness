package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
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

data class MembershipPlan(
    val id: String,
    val title: String, // e.g. "Monthly Power", "Quarterly Shred", "Annual Elite VIP"
    val monthlyPriceText: String, // e.g. "₹2,499 / mo" or "₹1,583 / mo"
    val yearlyPriceText: String, // e.g. "₹18,999 / yr"
    val savingsBadge: String, // e.g. "SAVE 35%"
    val isPopular: Boolean = false,
    val features: List<String>
)

@Composable
fun PricingToggleSection(
    onSelectPlan: (MembershipPlan, Boolean) -> Unit
) {
    var isYearly by remember { mutableStateOf(true) }

    val plans = remember {
        listOf(
            MembershipPlan(
                id = "p1",
                title = "3-Day Trial Pass",
                monthlyPriceText = "FREE",
                yearlyPriceText = "FREE",
                savingsBadge = "100% FREE",
                isPopular = false,
                features = listOf(
                    "3 Consecutive Days Full Gym Access",
                    "1 Free Trainer Consultation & Form Check",
                    "Access to Strength Floor & Cardio Zone",
                    "1 Free Zumba / Yoga Group Session"
                )
            ),
            MembershipPlan(
                id = "p2",
                title = "Power Core Access",
                monthlyPriceText = "₹2,499 / mo",
                yearlyPriceText = "₹1,799 / mo (₹21,588/yr)",
                savingsBadge = "SAVE 28%",
                isPopular = false,
                features = listOf(
                    "Full Access to Gym & Steam Room",
                    "InBody Composition Analysis every month",
                    "Personalized Beginner Workout Routine",
                    "Locker & Shower Amenities Included"
                )
            ),
            MembershipPlan(
                id = "p3",
                title = "Annual Elite VIP",
                monthlyPriceText = "₹2,199 / mo",
                yearlyPriceText = "₹1,499 / mo (₹17,988/yr)",
                savingsBadge = "🔥 SAVE 35%",
                isPopular = true,
                features = listOf(
                    "Unlimited Gym Access + All Group Classes",
                    "Free Customized Indian Diet Chart",
                    "2 Personal Training Intro Sessions",
                    "4 Free Guest Passes for Friends/Family",
                    "Freeze Membership up to 45 Days"
                )
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
                            imageVector = Icons.Default.CardMembership,
                            contentDescription = "Plans",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "MEMBERSHIP PLANS & PRICING",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Transparent Baner Gym Packages with No Hidden Fees",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Monthly vs Yearly Toggle Switch
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { isYearly = false }
                            .testTag("pricing_toggle_monthly"),
                        color = if (!isYearly) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 10.dp)) {
                            Text(
                                text = "Monthly Billing",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (!isYearly) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1.3f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { isYearly = true }
                            .testTag("pricing_toggle_yearly"),
                        color = if (isYearly) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Yearly Pass",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isYearly) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (isYearly) Color.Black else MaterialTheme.colorScheme.secondary
                            ) {
                                Text(
                                    text = "35% OFF",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isYearly) MaterialTheme.colorScheme.secondary else Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Plan Cards List
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                plans.forEach { plan ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = if (plan.isPopular) 2.dp else 1.dp,
                                color = if (plan.isPopular) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .testTag("membership_card_${plan.id}"),
                        shape = RoundedCornerShape(16.dp),
                        color = if (plan.isPopular) MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = plan.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )

                                if (plan.isPopular) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.secondary
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "Popular",
                                                tint = Color.Black,
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "MOST POPULAR",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Black,
                                                    color = Color.Black
                                                )
                                            )
                                        }
                                    }
                                } else {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = plan.savingsBadge,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Price display based on toggle
                            Text(
                                text = if (isYearly) plan.yearlyPriceText else plan.monthlyPriceText,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = if (plan.isPopular) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Features Checklist
                            plan.features.forEach { feature ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Check",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = feature,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = { onSelectPlan(plan, isYearly) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (plan.isPopular) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                                    contentColor = if (plan.isPopular) Color.Black else MaterialTheme.colorScheme.onPrimary
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("btn_select_plan_${plan.id}")
                            ) {
                                Text(
                                    text = if (plan.monthlyPriceText == "FREE") "Claim Free 3-Day Pass" else "Join PowerZone - Select Plan",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
