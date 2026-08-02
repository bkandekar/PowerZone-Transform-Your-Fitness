package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookmarkEntity
import com.example.data.local.PowerZoneDatabase
import com.example.data.local.TrialBookingEntity
import com.example.data.model.*
import com.example.data.repository.PowerZoneRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PowerZoneViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PowerZoneRepository

    init {
        val dao = PowerZoneDatabase.getDatabase(application).powerZoneDao()
        repository = PowerZoneRepository(dao)
    }

    val contactInfo: ContactInfo = repository.getContactInfo()
    val whyUsStats: List<WhyUsStat> = repository.getWhyUsStats()
    val processSteps: List<ProcessStep> = repository.getProcessSteps()
    val allPrograms: List<Program> = repository.getPrograms()
    val allTestimonials: List<Testimonial> = repository.getTestimonials()
    val allGalleryItems: List<GalleryItem> = repository.getGalleryItems()
    val trainers: List<Trainer> = repository.getTrainers()

    // App Design Theme State ("FlameGold", "CyberNeon", "ElectricCobalt", "CrimsonVIP")
    private val _appTheme = MutableStateFlow("FlameGold")
    val appTheme: StateFlow<String> = _appTheme.asStateFlow()

    private val _isThemeModalOpen = MutableStateFlow(false)
    val isThemeModalOpen: StateFlow<Boolean> = _isThemeModalOpen.asStateFlow()

    // Filters
    private val _programCategory = MutableStateFlow("All")
    val programCategory: StateFlow<String> = _programCategory.asStateFlow()

    private val _testimonialLocality = MutableStateFlow("All")
    val testimonialLocality: StateFlow<String> = _testimonialLocality.asStateFlow()

    private val _galleryCategory = MutableStateFlow("All")
    val galleryCategory: StateFlow<String> = _galleryCategory.asStateFlow()

    private val _galleryTag = MutableStateFlow<String?>(null)
    val galleryTag: StateFlow<String?> = _galleryTag.asStateFlow()

    // Modals & Selected Items
    private val _selectedGalleryItem = MutableStateFlow<GalleryItem?>(null)
    val selectedGalleryItem: StateFlow<GalleryItem?> = _selectedGalleryItem.asStateFlow()

    private val _selectedProgramForTrial = MutableStateFlow<Program?>(null)
    val selectedProgramForTrial: StateFlow<Program?> = _selectedProgramForTrial.asStateFlow()

    private val _isBookingModalOpen = MutableStateFlow(false)
    val isBookingModalOpen: StateFlow<Boolean> = _isBookingModalOpen.asStateFlow()

    private val _isBmiModalOpen = MutableStateFlow(false)
    val isBmiModalOpen: StateFlow<Boolean> = _isBmiModalOpen.asStateFlow()

    private val _isMyBookingsOpen = MutableStateFlow(false)
    val isMyBookingsOpen: StateFlow<Boolean> = _isMyBookingsOpen.asStateFlow()

    private val _isReferralModalOpen = MutableStateFlow(false)
    val isReferralModalOpen: StateFlow<Boolean> = _isReferralModalOpen.asStateFlow()

    private val _selectedBlogArticleTitle = MutableStateFlow<String?>(null)
    val selectedBlogArticleTitle: StateFlow<String?> = _selectedBlogArticleTitle.asStateFlow()

    // Local DB State
    val savedBookings: StateFlow<List<TrialBookingEntity>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Actions
    fun setProgramCategory(cat: String) {
        _programCategory.value = cat
    }

    fun setTestimonialLocality(loc: String) {
        _testimonialLocality.value = loc
    }

    fun setGalleryCategory(cat: String) {
        _galleryCategory.value = cat
        _galleryTag.value = null
    }

    fun setGalleryTag(tag: String?) {
        _galleryTag.value = if (_galleryTag.value == tag) null else tag
    }

    fun openGalleryItem(item: GalleryItem?) {
        _selectedGalleryItem.value = item
    }

    fun openBookingModal(program: Program? = null) {
        _selectedProgramForTrial.value = program
        _isBookingModalOpen.value = true
    }

    fun closeBookingModal() {
        _isBookingModalOpen.value = false
        _selectedProgramForTrial.value = null
    }

    fun openBmiModal() {
        _isBmiModalOpen.value = true
    }

    fun closeBmiModal() {
        _isBmiModalOpen.value = false
    }

    fun openMyBookings() {
        _isMyBookingsOpen.value = true
    }

    fun closeMyBookings() {
        _isMyBookingsOpen.value = false
    }

    fun openReferralModal() {
        _isReferralModalOpen.value = true
    }

    fun closeReferralModal() {
        _isReferralModalOpen.value = false
    }

    fun openBlogArticle(title: String?) {
        _selectedBlogArticleTitle.value = title
    }

    fun closeBlogArticle() {
        _selectedBlogArticleTitle.value = null
    }

    fun setAppTheme(themeName: String) {
        _appTheme.value = themeName
    }

    fun openThemeModal() {
        _isThemeModalOpen.value = true
    }

    fun closeThemeModal() {
        _isThemeModalOpen.value = false
    }

    fun toggleBookmark(program: Program, currentlyBookmarked: Boolean) {
        viewModelScope.launch {
            repository.toggleBookmark(program, currentlyBookmarked)
        }
    }

    fun submitTrialBooking(
        context: Context,
        name: String,
        phone: String,
        locality: String,
        fitnessGoal: String,
        preferredSlot: String,
        programTitle: String
    ) {
        viewModelScope.launch {
            val booking = TrialBookingEntity(
                name = name,
                phone = phone,
                locality = locality,
                fitnessGoal = fitnessGoal,
                preferredSlot = preferredSlot,
                programTitle = programTitle.ifEmpty { "General 3-Day Pass" }
            )
            repository.saveTrialBooking(booking)

            // Direct WhatsApp Routing
            launchWhatsAppRouting(
                context = context,
                name = name,
                phone = phone,
                locality = locality,
                fitnessGoal = fitnessGoal,
                preferredSlot = preferredSlot,
                programTitle = programTitle
            )

            closeBookingModal()
        }
    }

    fun launchWhatsAppRouting(
        context: Context,
        name: String = "",
        phone: String = "",
        locality: String = "Baner",
        fitnessGoal: String = "3-Day Free Pass Trial",
        preferredSlot: String = "Morning Batch",
        programTitle: String = "PowerZone Pass"
    ) {
        val targetPhone = contactInfo.rawPhone // 918329931123
        val formattedMsg = StringBuilder().apply {
            append("🏋️ *PowerZone Fitness Studio - Trial Booking Request*\n\n")
            if (name.isNotBlank()) append("👤 *Name:* $name\n")
            if (phone.isNotBlank()) append("📞 *Phone:* $phone\n")
            append("📍 *Locality:* $locality (Pune)\n")
            append("🎯 *Goal:* $fitnessGoal\n")
            append("⏰ *Preferred Slot:* $preferredSlot\n")
            if (programTitle.isNotBlank()) append("📌 *Program:* $programTitle\n")
            append("\n💬 *Requested via PowerZone Android App*")
        }.toString()

        val url = "https://wa.me/$targetPhone?text=${Uri.encode(formattedMsg)}"
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to dialer if WhatsApp unavailable
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+$targetPhone")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(dialIntent)
        }
    }
}
