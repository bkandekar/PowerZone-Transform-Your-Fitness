package com.example.data.repository

import com.example.data.local.BookmarkEntity
import com.example.data.local.PowerZoneDao
import com.example.data.local.TrialBookingEntity
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class PowerZoneRepository(private val dao: PowerZoneDao) {

    // Room DB Operations
    val allBookings: Flow<List<TrialBookingEntity>> = dao.getAllTrialBookings()
    val allBookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()

    suspend fun saveTrialBooking(booking: TrialBookingEntity) = dao.insertTrialBooking(booking)
    suspend fun deleteTrialBooking(id: Int) = dao.deleteTrialBooking(id)
    suspend fun toggleBookmark(program: Program, isBookmarked: Boolean) {
        if (isBookmarked) {
            dao.removeBookmark(program.id)
        } else {
            dao.insertBookmark(
                BookmarkEntity(
                    programId = program.id,
                    programTitle = program.title,
                    category = program.category
                )
            )
        }
    }

    fun isBookmarked(programId: String): Flow<Boolean> = dao.isBookmarked(programId)

    // Static Data
    fun getContactInfo(): ContactInfo = ContactInfo()

    fun getWhyUsStats(): List<WhyUsStat> {
        return listOf(
            WhyUsStat(
                id = "stat_members",
                title = "Active Members",
                targetValue = 1200,
                suffix = "+",
                label = "1,200+ Members",
                description = "Fitness enthusiasts transformed across Baner & Balewadi"
            ),
            WhyUsStat(
                id = "stat_years",
                title = "Years Experience",
                targetValue = 5,
                suffix = "+ Years",
                label = "5+ Years",
                description = "Proven excellence in strength & body transformation"
            ),
            WhyUsStat(
                id = "stat_trainers",
                title = "Certified Coaches",
                targetValue = 10,
                suffix = "+ Trainers",
                label = "10+ Trainers",
                description = "K10, ACE & ACSM certified elite fitness coaches"
            ),
            WhyUsStat(
                id = "stat_success",
                title = "Success Rate",
                targetValue = 98,
                suffix = "%",
                label = "98% Success Rate",
                description = "Guaranteed weight loss & muscle transformation results"
            )
        )
    }

    fun getProcessSteps(): List<ProcessStep> {
        return listOf(
            ProcessStep(
                stepNumber = 1,
                title = "Book Trial",
                subtitle = "Claim 3-Day Free Pass",
                description = "Connect on WhatsApp (918329931123) or register via app to claim your complimentary 3-day access.",
                actionText = "Book Trial Pass"
            ),
            ProcessStep(
                stepNumber = 2,
                title = "Fitness Assessment",
                subtitle = "InBody Body Composition Analysis",
                description = "Our expert coaches evaluate your BMR, muscle balance, body fat %, and medical history.",
                actionText = "Get Assessment"
            ),
            ProcessStep(
                stepNumber = 3,
                title = "Custom Plan",
                subtitle = "Tailored Nutrition & Training",
                description = "Receive a personalized workout schedule and custom diet chart suited to your Pune lifestyle.",
                actionText = "View Sample Plan"
            ),
            ProcessStep(
                stepNumber = 4,
                title = "Train & Transform",
                subtitle = "Achieve Guaranteed Results",
                description = "Execute guided workouts under elite supervision with weekly progress tracking and body photos.",
                actionText = "Start Transformation"
            )
        )
    }

    fun getPrograms(): List<Program> {
        return listOf(
            Program(
                id = "prog_personal_training",
                title = "1-on-1 Personal Training",
                category = "Training",
                tags = listOf("#PersonalCoaching", "#CustomWorkouts", "#PostureFix", "#ResultsGuaranteed"),
                tagBadge = "Most Popular",
                description = "Dedicated 1-on-1 coaching designed around your body type, goals, and schedule. Includes body composition tracking and custom meal plans.",
                duration = "Monthly / Quarterly",
                intensity = "High",
                highlights = listOf(
                    "Dedicated ACSM/ACE Certified Personal Coach",
                    "Customized progressive overload workout strategy",
                    "Weekly body fat % & girth measurements",
                    "Custom macro-counted meal plans with local Pune food choices"
                ),
                schedule = "6:00 AM - 9:30 PM (Flexible Slot)",
                trainerName = "Rahul Sharma (Master Coach)",
                iconName = "fitness_center"
            ),
            Program(
                id = "prog_body_transformation",
                title = "90-Day Fat-to-Fit Transformation",
                category = "Transformation",
                tags = listOf("#90DaysChallenge", "#FatLoss", "#MuscleGain", "#BodyRecomp"),
                tagBadge = "High Conversion",
                description = "Our signature 90-day transformation blueprint engineered for dramatic fat loss, muscle tone, and sustainable healthy habits.",
                duration = "90 Days (12 Weeks)",
                intensity = "High Intensity",
                highlights = listOf(
                    "Guaranteed target weight loss or muscle building goal",
                    "Full body composition analysis before & after",
                    "Daily nutrition guidance & macro tracking on WhatsApp",
                    "Transformation hall of fame entry upon completion"
                ),
                schedule = "Morning & Evening Special Batches",
                trainerName = "Transformation Squad",
                iconName = "bolt"
            ),
            Program(
                id = "prog_cardio_endurance",
                title = "Cardio & Endurance Zone",
                category = "Cardio",
                tags = listOf("#CalorieBurn", "#Spinning", "#Stamina", "#HeartHealth"),
                tagBadge = "Fat Burn",
                description = "State-of-the-art commercial treadmills, stairmasters, elliptical cross-trainers, and indoor cycling bikes designed for maximum caloric burn.",
                duration = "Ongoing Membership",
                intensity = "Moderate - High",
                highlights = listOf(
                    "Commercial grade Matrix & Jerai cardio gear",
                    "Sled push turf zone & battle ropes for HIIT",
                    "Heart-rate zone monitored cardio sessions",
                    "Burn up to 700+ calories per 45-min workout"
                ),
                schedule = "All Day Access (5:30 AM - 10:00 PM)",
                trainerName = "Cardio Specialists",
                iconName = "directions_run"
            ),
            Program(
                id = "prog_group_classes",
                title = "High Energy Group Classes",
                category = "Group Class",
                tags = listOf("#Zumba", "#Aerobics", "#CrossFit", "#FunFitness"),
                tagBadge = "Community Favorite",
                description = "Pump up your energy in studio group workouts featuring Zumba, Aerobics, Cross-Functional Circuit, and HIIT body pump.",
                duration = "Daily 45 Min Batches",
                intensity = "All Fitness Levels",
                highlights = listOf(
                    "Licensed Zumba & Aerobics instructors",
                    "Acoustic wooden dance flooring & dynamic club lighting",
                    "High calorie burning group motivation",
                    "Special weekend fitness bootcamps"
                ),
                schedule = "Mon/Wed/Fri: 7:00 AM & 6:30 PM",
                trainerName = "Priya & Group Instructors",
                iconName = "groups"
            ),
            Program(
                id = "prog_yoga_studio",
                title = "Yoga & Mobility Restoration",
                category = "Yoga",
                tags = listOf("#HathaYoga", "#Flexibility", "#Pranayama", "#Mindfulness"),
                tagBadge = "Holistic Wellness",
                description = "Restore mental peace, spinal health, posture, and deep core flexibility with traditional Power Yoga & Hatha Yoga sessions.",
                duration = "1 Hour Sessions",
                intensity = "Light - Moderate",
                highlights = listOf(
                    "Guided Pranayama & deep stress relief meditation",
                    "Spinal decompression & desk-job posture correction",
                    "Increased joint range of motion & core stability",
                    "Peaceful air-conditioned studio environment"
                ),
                schedule = "Tue/Thu/Sat: 6:30 AM & 7:00 PM",
                trainerName = "Guruji Yogesh",
                iconName = "self_improvement"
            ),
            Program(
                id = "prog_ladies_batch",
                title = "Ladies Special Fitness Batch",
                category = "Ladies Batch",
                tags = listOf("#WomenOnly", "#FemaleTrainers", "#PostnatalFitness", "#SafeSpace"),
                tagBadge = "Women Special",
                description = "Dedicated female-only workout sessions supervised by certified women trainers. Focus on toning, fat burn, and hormonal balance.",
                duration = "Ongoing",
                intensity = "Tailored",
                highlights = listOf(
                    "Exclusive female-only studio hours & female trainers",
                    "PCOS/PCOD friendly workout & diet customization",
                    "Post-pregnancy core recovery & pelvic floor strength",
                    "Comfortable, empowering & privacy-focused atmosphere"
                ),
                schedule = "Mon to Sat: 11:00 AM - 1:00 PM & 4:00 PM - 5:30 PM",
                trainerName = "Coach Snehal & Coach Ananya",
                iconName = "female"
            )
        )
    }

    fun getTestimonials(): List<Testimonial> {
        return listOf(
            Testimonial(
                id = "rev_baner_1",
                name = "Amit Kulkarni",
                locality = "Baner",
                duration = "Member for 8 Months",
                achievement = "Lost 16 kg & Reduced Body Fat by 12%",
                reviewText = "PowerZone is hands down the best gym in Baner! Rahul Sir's personal coaching and 90-day transformation strategy completely transformed my fitness. The equipment is top class and super clean.",
                avatarBgColor = 0xFFE53935
            ),
            Testimonial(
                id = "rev_balewadi_1",
                name = "Sneha Joshi",
                locality = "Balewadi",
                duration = "Member for 1 Year",
                achievement = "Toned 8 kg Post-Pregnancy & Rebuilt Core",
                reviewText = "I join the Ladies Special Batch in Balewadi Link Road branch. Coach Snehal is so encouraging. The hygiene, steam room, and group Zumba classes are incredible!",
                avatarBgColor = 0xFF8E24AA
            ),
            Testimonial(
                id = "rev_aundh_1",
                name = "Rajesh Deshmukh",
                locality = "Aundh",
                duration = "Member for 1.5 Years",
                achievement = "Gained 6 kg Lean Muscle Mass",
                reviewText = "I travel from Aundh every morning because PowerZone's strength equipment and turf zone are unmatched. Great community vibe and highly professional certified trainers.",
                avatarBgColor = 0xFF1E88E5
            ),
            Testimonial(
                id = "rev_pashan_1",
                name = "Priyanka Patil",
                locality = "Pashan",
                duration = "Member for 6 Months",
                achievement = "Reversed Cervical Pain & Improved Stamina",
                reviewText = "The Yoga & posture restoration sessions helped resolve my desk-job back pain. The staff is warm and the trial booking on WhatsApp was instant!",
                avatarBgColor = 0xFF43A047
            )
        )
    }

    fun getGalleryItems(): List<GalleryItem> {
        return listOf(
            GalleryItem(
                id = "gal_gym_floor",
                title = "Main Gym Floor & Strength Arena",
                category = "Gym Floor",
                tags = listOf("#GymFloor", "#StrengthZone", "#FreeWeights", "#JeraiFitness", "#PowerLifting"),
                description = "Fully air-conditioned 4,000+ sq.ft main gym floor equipped with heavy dumbbells (up to 50kg), power racks, cable crossover stations, and Olympic lifting platforms.",
                highlightBadge = "4,000+ Sq.Ft Space"
            ),
            GalleryItem(
                id = "gal_cardio_zone",
                title = "High-Calorie Cardio & Turf Zone",
                category = "Cardio Zone",
                tags = listOf("#CardioZone", "#Treadmills", "#SpinningBikes", "#HIITTurf", "#SledPush"),
                description = "Commercial grade treadmills, stairmasters, elliptical trainers, and synthetic grass sled push turf for intense fat burning HIIT workouts.",
                highlightBadge = "Imported Equipment"
            ),
            GalleryItem(
                id = "gal_personal_training",
                title = "1-on-1 Personal Training Suite",
                category = "Personal Training",
                tags = listOf("#PersonalTraining", "#1on1Coaching", "#InBodyScanner", "#PostureFix"),
                description = "Dedicated personal training bay with InBody body composition analyzer, posture alignment grid, and personalized accessory weights.",
                highlightBadge = "Certified Master Coaches"
            ),
            GalleryItem(
                id = "gal_group_studio",
                title = "Acoustic Group Class Studio",
                category = "Group Class Studio",
                tags = listOf("#GroupClassStudio", "#ZumbaDance", "#Aerobics", "#CrossFitCircuit", "#SoundSystem"),
                description = "Spacious group exercise studio featuring cushioned wooden flooring, JBL surround audio system, and party lights for Zumba and Aerobics.",
                highlightBadge = "Sound & Light Ambience"
            ),
            GalleryItem(
                id = "gal_transformation_wall",
                title = "PowerZone Transformation Hall of Fame",
                category = "Transformation Wall",
                tags = listOf("#TransformationWall", "#HallOfFame", "#FatLossResults", "#Inspiration", "#PuneFitness"),
                description = "Real before-and-after photos of our 1,200+ Pune members who successfully transformed their physique and lives at PowerZone Studio.",
                highlightBadge = "1,200+ Success Stories"
            ),
            GalleryItem(
                id = "gal_steam_room",
                title = "Luxury Spa & Detox Steam Room",
                category = "Steam Room",
                tags = listOf("#SteamRoom", "#MuscleRecovery", "#Detox", "#LuxuryShowers", "#CleanHygiene"),
                description = "Relaxing eucalyptus-infused steam sauna rooms, separate male & female changing rooms, lockable lockers, and hot showers.",
                highlightBadge = "Daily Sanitized"
            ),
            GalleryItem(
                id = "gal_yoga_studio",
                title = "Serene Yoga & Mindful Studio",
                category = "Yoga Studio",
                tags = listOf("#YogaStudio", "#HathaYoga", "#Pranayama", "#Flexibility", "#Meditation"),
                description = "Peaceful studio space designed for holistic yoga, core stabilization, stretching routines, and deep breathing meditation.",
                highlightBadge = "Calm Ambiance"
            ),
            GalleryItem(
                id = "gal_ladies_batch",
                title = "Exclusive Ladies Fitness Area",
                category = "Ladies Batch",
                tags = listOf("#LadiesBatch", "#WomenOnly", "#FemaleCoaches", "#SafeAndPrivate", "#ToneUp"),
                description = "Privacy-focused female batch section with woman-friendly weights, resistance bands, and female certified fitness trainers.",
                highlightBadge = "100% Privacy & Safety"
            )
        )
    }

    private fun tagBadgeText() = "1-on-1 Zone"

    fun getTrainers(): List<Trainer> {
        return listOf(
            Trainer(
                id = "tr_rahul",
                name = "Rahul Sharma",
                title = "Founder & Head Strength Coach",
                experience = "10+ Years",
                certifications = listOf("K10 Certified Master Trainer", "ACSM Exercise Specialist", "Sports Nutritionist"),
                bio = "Over a decade of expertise in natural body transformation, strength conditioning, and lifestyle coaching in Baner, Pune."
            ),
            Trainer(
                id = "tr_snehal",
                name = "Snehal Patil",
                title = "Senior Women's Fitness & Pilates Coach",
                experience = "6+ Years",
                certifications = listOf("ACE Certified Personal Trainer", "Pre & Postnatal Fitness Specialist"),
                bio = "Specializes in female hormone health, weight management, postnatal rehabilitation, and core toning."
            ),
            Trainer(
                id = "tr_vikram",
                name = "Vikram Deshmukh",
                title = "HIIT & Cross-Functional Specialist",
                experience = "7+ Years",
                certifications = listOf("CrossFit Level 2 Coach", "Functional Movement Screen (FMS)"),
                bio = "Passionate about athletic endurance, kettlebell conditioning, and explosive fat loss training."
            )
        )
    }
}
