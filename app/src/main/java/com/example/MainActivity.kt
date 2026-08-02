package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.example.ui.components.*
import com.example.ui.theme.PowerZoneTheme
import com.example.ui.viewmodel.PowerZoneViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PowerZoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appTheme by viewModel.appTheme.collectAsState()
            PowerZoneTheme(appTheme = appTheme) {
                PowerZoneAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PowerZoneAppScreen(viewModel: PowerZoneViewModel) {
    val context = LocalContext.current
    val currentTheme by viewModel.appTheme.collectAsState()
    val isBookingOpen by viewModel.isBookingModalOpen.collectAsState()
    val isBmiOpen by viewModel.isBmiModalOpen.collectAsState()
    val isMyBookingsOpen by viewModel.isMyBookingsOpen.collectAsState()
    val isThemeModalOpen by viewModel.isThemeModalOpen.collectAsState()
    val isReferralOpen by viewModel.isReferralModalOpen.collectAsState()
    val selectedBlogTitle by viewModel.selectedBlogArticleTitle.collectAsState()
    val selectedGalleryItem by viewModel.selectedGalleryItem.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopBar(
                viewModel = viewModel,
                onOpenBmi = { viewModel.openBmiModal() },
                onOpenBookings = { viewModel.openMyBookings() },
                onOpenTrialBooking = { viewModel.openBookingModal() },
                onOpenThemeSelector = { viewModel.openThemeModal() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .testTag("powerzone_root")
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Hero Banner
                HeroBanner(
                    viewModel = viewModel,
                    onBookTrial = { viewModel.openBookingModal() },
                    onOpenBmi = { viewModel.openBmiModal() }
                )

                // Why-Us Stats Bar
                StatsBar(viewModel = viewModel)

                // PHASE 2 HIGH PRIORITY: Live Class Timetable Widget
                ClassTimetableSection(
                    onReserveSeat = { slot ->
                        viewModel.launchWhatsAppRouting(
                            context = context,
                            locality = "Baner / Pune",
                            fitnessGoal = "Class Seat Reservation: ${slot.className}",
                            preferredSlot = slot.timeSlot,
                            programTitle = "${slot.className} (${slot.day})"
                        )
                    }
                )

                // 4-Step Process Flow
                ProcessFlow(
                    viewModel = viewModel,
                    onBookTrial = { viewModel.openBookingModal() },
                    onOpenBmi = { viewModel.openBmiModal() }
                )

                // Master Trainers Spotlight
                TrainersSection(
                    trainers = viewModel.trainers,
                    onBookTrainer = { trainer ->
                        viewModel.launchWhatsAppRouting(
                            context = context,
                            locality = "Baner / Balewadi",
                            fitnessGoal = "1-on-1 Personal Training",
                            programTitle = "Coach ${trainer.name} (${trainer.title})"
                        )
                    }
                )

                // PHASE 2 HIGH PRIORITY: Google Reviews Rating Embed
                GoogleReviewsSection(
                    onBookTrial = { viewModel.openBookingModal() }
                )

                // Programs Offered
                ProgramsSection(
                    viewModel = viewModel,
                    onSelectProgram = { program ->
                        viewModel.openBookingModal(program)
                    }
                )

                // PHASE 2 HIGH PRIORITY: Before/After Transformation Wall
                TransformationsSection(
                    onStartTransformationClick = { viewModel.openBookingModal() }
                )

                // PHASE 2 MEDIUM PRIORITY: Monthly vs Yearly Pricing Plans
                PricingToggleSection(
                    onSelectPlan = { plan, isYearly ->
                        viewModel.openBookingModal()
                    }
                )

                // Interactive Weekly Routine & Calorie Planner
                WorkoutPlannerSection(
                    onBookConsultation = { customPlanSummary ->
                        viewModel.launchWhatsAppRouting(
                            context = context,
                            locality = "Baner / Pashan",
                            fitnessGoal = "Custom Routine Review",
                            programTitle = customPlanSummary
                        )
                    }
                )

                // Interactive Muscle Target & Equipment Map
                AnatomyMapSection(
                    onBookTrialForZone = { zoneTitle ->
                        viewModel.openBookingModal()
                    }
                )

                // PHASE 2 MEDIUM PRIORITY: Blog / Content Hub
                BlogHubSection(
                    onReadArticle = { article ->
                        viewModel.openBlogArticle(article.title)
                    }
                )

                // Member Testimonials
                TestimonialsSection(
                    viewModel = viewModel,
                    onBookTrial = { viewModel.openBookingModal() }
                )

                // PHASE 2 MEDIUM PRIORITY: FAQ Accordion
                FaqAccordionSection(
                    onAskQuestionClick = {
                        viewModel.launchWhatsAppRouting(
                            context = context,
                            locality = "Baner",
                            fitnessGoal = "FAQ Inquiry / Support",
                            programTitle = "Custom Help Question"
                        )
                    }
                )

                // PHASE 2: Virtual 360° Facility Tour & Video Section
                VirtualTourVideoSection(
                    onPlayVideo = { video ->
                        viewModel.launchWhatsAppRouting(
                            context = context,
                            locality = "Baner",
                            fitnessGoal = "Request Full Video Tour Link",
                            programTitle = video.title
                        )
                    }
                )

                // Studio Gallery with Tags
                GallerySection(
                    viewModel = viewModel,
                    onBookTrialForZone = { zoneTitle ->
                        viewModel.openBookingModal()
                    }
                )

                // Final CTA Conversion Banner
                CtaBanner(
                    viewModel = viewModel,
                    onBookTrial = { viewModel.openBookingModal() }
                )

                // Comprehensive Footer
                FooterSection(viewModel = viewModel)
            }

            // PHASE 2 HIGH PRIORITY: Floating WhatsApp Chat Bubble (Persistent on bottom right)
            FloatingWhatsAppBubble(viewModel = viewModel)

            // Dialog Modals
            if (isThemeModalOpen) {
                ThemeSelectorModal(
                    currentTheme = currentTheme,
                    onSelectTheme = { selected ->
                        viewModel.setAppTheme(selected)
                    },
                    onDismiss = { viewModel.closeThemeModal() }
                )
            }

            if (isBookingOpen) {
                TrialBookingModal(
                    viewModel = viewModel,
                    onDismiss = { viewModel.closeBookingModal() }
                )
            }

            if (isBmiOpen) {
                BmiCalculatorModal(
                    viewModel = viewModel,
                    onDismiss = { viewModel.closeBmiModal() },
                    onBookTrialWithGoal = { customGoal ->
                        viewModel.openBookingModal()
                    }
                )
            }

            if (isMyBookingsOpen) {
                MyBookingsDialog(
                    viewModel = viewModel,
                    onDismiss = { viewModel.closeMyBookings() }
                )
            }

            if (isReferralOpen) {
                ReferralProgramModal(
                    viewModel = viewModel,
                    onDismiss = { viewModel.closeReferralModal() }
                )
            }

            selectedBlogTitle?.let { title ->
                BlogArticleModal(
                    article = BlogArticle(
                        id = "modal_b1",
                        title = title,
                        readTime = "5 min read",
                        category = "PowerZone Fitness Baner Guide",
                        snippet = "Detailed breakdown of training principles, diet plans, and facility amenities.",
                        fullContent = """
                            Welcome to PowerZone Fitness Studio's official knowledge guide!
                            
                            1. Heavy Resistance & Biomechanics:
                            Our gym features Olympic barbells, dumbells up to 50kg, and isolateral lever selectorized machines designed for maximum muscle hyper-trophy with minimal joint shear stress.
                            
                            2. Customized Indian Nutrition:
                            We combine macro-nutrient targets with traditional Indian food choices (Paneer, Tofu, Soya, Sprouts, Dal, Eggs) so you can hit your fitness goals without giving up local meals.
                            
                            3. Certified Coaches & Progress Tracking:
                            Every month, our ACE & K11 certified trainers conduct InBody composition scans to track muscle gain, body fat percentage and visceral fat.
                        """.trimIndent()
                    ),
                    onDismiss = { viewModel.closeBlogArticle() }
                )
            }

            selectedGalleryItem?.let { galleryItem ->
                GalleryLightboxModal(
                    item = galleryItem,
                    viewModel = viewModel,
                    onDismiss = { viewModel.openGalleryItem(null) }
                )
            }
        }
    }
}

