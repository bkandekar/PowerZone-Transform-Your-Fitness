package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Share
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

data class BlogArticle(
    val id: String,
    val title: String,
    val readTime: String, // "4 min read"
    val category: String, // "SEO & Guides", "Nutrition", "Workout Tips"
    val snippet: String,
    val fullContent: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BlogHubSection(
    onReadArticle: (BlogArticle) -> Unit
) {
    val articles = remember {
        listOf(
            BlogArticle(
                id = "b1",
                title = "Why PowerZone is Ranked #1 Gym in Baner Pune (2026 Guide)",
                readTime = "5 min read",
                category = "SEO & Local Guide",
                snippet = "Looking for the top fitness studio in Baner - Balewadi link road? Here is why high-end equipment, certified trainers and hygiene protocols set PowerZone apart.",
                fullContent = """
                    Baner and Balewadi are Pune's fastest growing tech and residential hubs. With long hours sitting in front of laptops, young professionals in IT parks like Cummins, Siemens, and Panchshil Business Park are prioritizing strength & posture.
                    
                    1. Heavy Lifting & Olympic Barbell Zone:
                    Unlike commercial budget gyms that restrict deadlifts, PowerZone features shock-absorbing rubber flooring and 5 dedicated squat racks with bumper plates up to 250kg.
                    
                    2. Certified ACE & K11 Coaches:
                    Our trainers don't just stand around — every member gets active form checks and progressive overload logging.
                    
                    3. Ladies-Only Batches & Private Hygiene:
                    We respect privacy and offer specialized female-only slots with certified women coaches.
                """.trimIndent()
            ),
            BlogArticle(
                id = "b2",
                title = "Home Workout vs Gym Training: Which Gives Faster Fat Loss?",
                readTime = "4 min read",
                category = "Workout Science",
                snippet = "Can bodyweight squats at home match progressive heavy resistance in a gym? We break down caloric burn, EPOC effect and muscle retention.",
                fullContent = """
                    While resistance bands and push-ups at home are great for maintaining baseline activity, progressive overload with dumbbells, cables, and barbells triggers significantly higher EPOC (Excess Post-Exercise Oxygen Consumption).
                    
                    Key Advantages of Gym Resistance:
                    - Mechanical Tension: You can incrementally increase weight from 5kg to 52.5kg.
                    - Metabolic Rate Boost: Building lean muscle mass burns more calories even while resting.
                    - Environment & Focus: Stepping into PowerZone's high-energy music environment eliminates home distractions.
                """.trimIndent()
            ),
            BlogArticle(
                id = "b3",
                title = "High-Protein Indian Vegetarian Diet for Hypertrophy",
                readTime = "6 min read",
                category = "Nutrition Guide",
                snippet = "Struggling to hit 130g+ protein on an Indian vegetarian diet in Pune? Discover meal prep recipes with Paneer, Tofu, Soya chunks, Sprouts & Whey.",
                fullContent = """
                    Hitting macro targets on an Indian vegetarian diet is 100% doable with smart meal planning:
                    
                    Sample 140g Vegetarian Protein Day:
                    - Breakfast: 3 Sprouted Moong Cheela + 200ml Skimmed Milk (25g Protein)
                    - Lunch: 150g Pan-seared Tofu / Paneer + 1 Cup Dal Tadka + Brown Rice (35g Protein)
                    - Post-Workout: 1 Scoop Whey Isolate in Cold Water (25g Protein)
                    - Evening Snack: 50g Roasted Chana + 10 Almonds (12g Protein)
                    - Dinner: 100g Soya Chunks Sabzi + 2 Multigrain Rotis + Curd (43g Protein)
                """.trimIndent()
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
                            imageVector = Icons.Default.Article,
                            contentDescription = "Blog",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "FITNESS & HEALTH CONTENT HUB",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Expert SEO Guides, Local Pune Gym Tips & Nutrition Charts",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Articles Horizontal Carousel
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(articles) { article ->
                    Surface(
                        modifier = Modifier
                            .width(280.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                RoundedCornerShape(18.dp)
                            )
                            .testTag("blog_card_${article.id}"),
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = article.category,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Text(
                                    text = article.readTime,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = article.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                maxLines = 2
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = article.snippet,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { onReadArticle(article) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_read_article_${article.id}")
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Read Full Article", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BlogArticleModal(
    article: BlogArticle,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .testTag("blog_article_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = article.category,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Text(
                    text = "⏱️ ${article.readTime} • Published by PowerZone Fitness Baner",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = article.fullContent,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close Article", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
