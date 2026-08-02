package com.example.data.model

data class Program(
    val id: String,
    val title: String,
    val category: String, // e.g. "Training", "Transformation", "Cardio", "Group Class", "Yoga", "Ladies Batch"
    val tags: List<String>,
    val tagBadge: String, // e.g. "Popular", "High Intensity", "Women Only", "Custom Plan"
    val description: String,
    val duration: String, // e.g. "12 Weeks", "Ongoing", "45 Mins/Session"
    val intensity: String, // e.g. "High", "Moderate - High", "All Levels"
    val highlights: List<String>,
    val schedule: String,
    val trainerName: String,
    val iconName: String
)

data class WhyUsStat(
    val id: String,
    val title: String,
    val targetValue: Int,
    val prefix: String = "",
    val suffix: String = "",
    val label: String,
    val description: String
)

data class ProcessStep(
    val stepNumber: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val actionText: String
)

data class Testimonial(
    val id: String,
    val name: String,
    val locality: String, // "Baner", "Balewadi", "Aundh", "Pashan"
    val duration: String, // e.g., "6 Months Member"
    val achievement: String, // e.g. "Lost 14 kg & gained core strength"
    val rating: Float = 5.0f,
    val reviewText: String,
    val avatarBgColor: Long
)

data class GalleryItem(
    val id: String,
    val title: String,
    val category: String, // "Gym Floor", "Cardio Zone", "Personal Training", "Group Class Studio", "Transformation Wall", "Steam Room", "Yoga Studio", "Ladies Batch"
    val tags: List<String>,
    val description: String,
    val highlightBadge: String
)

data class Trainer(
    val id: String,
    val name: String,
    val title: String, // e.g. "Master Fitness Coach & Founder"
    val experience: String, // "10+ Years"
    val certifications: List<String>,
    val bio: String
)

data class ContactInfo(
    val gymName: String = "PowerZone Fitness Studio",
    val ownerName: String = "Rahul Sharma",
    val ownerTitle: String = "Founder & Master Fitness Coach",
    val phone: String = "+91 83299 31123",
    val rawPhone: String = "918329931123",
    val email: String = "info@powerzonefitness.in",
    val address: String = "High Street Baner - Balewadi Link Road, Baner, Pune, Maharashtra 411045",
    val serviceAreas: List<String> = listOf("Baner", "Balewadi", "Aundh", "Pashan", "Wakad", "Bavdhan"),
    val workingHours: String = "Mon - Sat: 5:30 AM – 10:00 PM | Sun: 6:00 AM – 1:00 PM",
    val socialInstagram: String = "instagram.com/powerzone.fitness",
    val socialFacebook: String = "facebook.com/powerzonepune",
    val creditLine: String = "Website by ebookcharm Web Services"
)
