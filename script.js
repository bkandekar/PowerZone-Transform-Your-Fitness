/* ==========================================================================
   POWERZONE FITNESS STUDIO - FINAL JAVASCRIPT (script.js)
   ========================================================================== */

document.addEventListener("DOMContentLoaded", () => {
  // ---------- Timetable Data ----------
  const timetableData = {
    "Mon-Wed-Fri": [
      { time: "06:30 AM - 07:30 AM", title: "Sunrise Strength & Hypertrophy", trainer: "Rahul Sharma (ACE Lead)", slots: "4 Seats Left" },
      { time: "08:30 AM - 09:30 AM", title: "CrossFit HIIT & Fat Loss Circuit", trainer: "Vikram Patil", slots: "6 Seats Left" },
      { time: "11:00 AM - 12:00 PM", title: "Exclusive Ladies Fitness & Toning", trainer: "Priya Kulkarni", slots: "3 Seats Left" },
      { time: "06:00 PM - 07:00 PM", title: "Heavy Barbell & Powerlifting Deck", trainer: "Amit Deshmukh", slots: "2 Seats Left" },
      { time: "07:30 PM - 08:30 PM", title: "Zumba Dance Fitness Beat Party", trainer: "Neha Verma", slots: "5 Seats Left" }
    ],
    "Tue-Thu-Sat": [
      { time: "07:00 AM - 08:00 AM", title: "Power Vinyasa Yoga & Core Conditioning", trainer: "Ananya Joshi", slots: "5 Seats Left" },
      { time: "09:00 AM - 10:00 AM", title: "Kettlebell & Functional Mobility", trainer: "Rahul Sharma", slots: "4 Seats Left" },
      { time: "04:00 PM - 05:00 PM", title: "Ladies Exclusive Weight Loss Batch", trainer: "Priya Kulkarni", slots: "2 Seats Left" },
      { time: "07:00 PM - 08:00 PM", title: "Extreme Fat Loss Calisthenics", trainer: "Vikram Patil", slots: "6 Seats Left" }
    ],
    "Sunday": [
      { time: "08:00 AM - 09:30 AM", title: "Sunday Olympic Deadlift & Squat Clinic", trainer: "Master Coach Rahul", slots: "Only 8 Seats" },
      { time: "10:00 AM - 11:30 AM", title: "Sound Bath & Recovery Yoga Session", trainer: "Ananya Joshi", slots: "10 Seats" }
    ]
  };

  // ---------- Render Timetable ----------
  const timetableGrid = document.getElementById("timetableGrid");
  const tabButtons = document.querySelectorAll(".tab-btn");

  function renderTimetable(dayCategory) {
    if (!timetableGrid) return;
    timetableGrid.innerHTML = "";
    const slots = timetableData[dayCategory] || [];

    slots.forEach(slot => {
      const card = document.createElement("div");
      card.className = "slot-card";
      card.innerHTML = `
        <div>
          <div class="slot-time">${slot.time}</div>
          <div class="slot-title">${slot.title}</div>
          <div class="slot-trainer">🎙️ Coach: \( {slot.trainer} • <span style="color:#CCFF00"> \){slot.slots}</span></div>
        </div>
        <button type="button" class="btn btn-sm btn-outline btn-reserve-slot" data-slot="\( {slot.title} ( \){slot.time})">Reserve Seat on WhatsApp</button>
      `;
      timetableGrid.appendChild(card);
    });

    document.querySelectorAll(".btn-reserve-slot").forEach(btn => {
      btn.addEventListener("click", (e) => {
        const slotInfo = e.currentTarget.getAttribute("data-slot");
        openWhatsAppMessage(`Hi PowerZone Baner! I want to reserve a seat for the batch: ${slotInfo}`);
      });
    });
  }

  renderTimetable("Mon-Wed-Fri");

  tabButtons.forEach(btn => {
    btn.addEventListener("click", () => {
      tabButtons.forEach(b => b.classList.remove("active"));
      btn.classList.add("active");
      renderTimetable(btn.getAttribute("data-day"));
    });
  });

  // ---------- BMI Calculator ----------
  const btnCalculateBmi = document.getElementById("btnCalculateBmi");
  const bmiHeight = document.getElementById("bmiHeight");
  const bmiWeight = document.getElementById("bmiWeight");
  const bmiGoal = document.getElementById("bmiGoal");
  const bmiValue = document.getElementById("bmiValue");
  const bmiStatus = document.getElementById("bmiStatus");
  const proteinTarget = document.getElementById("proteinTarget");
  const bmiRecommendation = document.getElementById("bmiRecommendation");
  const btnBmiWhatsApp = document.getElementById("btnBmiWhatsApp");

  function calculateBMI() {
    const h = parseFloat(bmiHeight.value) / 100;
    const w = parseFloat(bmiWeight.value);
    if (h > 0 && w > 0) {
      const bmi = (w / (h * h)).toFixed(1);
      const bmiNum = parseFloat(bmi);
      bmiValue.innerText = bmi;

      let statusText = "Normal Weight";
      let recommendation = "Maintain lean mass & progressive resistance overload.";

      if (bmiNum < 18.5) {
        statusText = "Underweight";
        recommendation = "Focus on calorie surplus + strength training to build healthy mass.";
      } else if (bmiNum >= 25 && bmiNum < 30) {
        statusText = "Overweight";
        recommendation = "Prioritize fat loss with calorie deficit + resistance training.";
      } else if (bmiNum >= 30) {
        statusText = "Obese Range";
        recommendation = "Start with guided fat-loss program and gradual progressive training.";
      }

      const goal = bmiGoal ? bmiGoal.value : "";
      if (goal === "Muscle Hypertrophy") {
        recommendation = "Eat in mild surplus, prioritize progressive overload and 1.8–2.2g protein/kg.";
      } else if (goal === "Fat Loss & Toning") {
        recommendation = "Maintain slight calorie deficit, high protein and strength training 4–5 days/week.";
      }

      bmiStatus.innerText = statusText;
      if (bmiRecommendation) bmiRecommendation.innerText = recommendation;

      const minProtein = Math.round(w * 1.6);
      const maxProtein = Math.round(w * 2.2);
      proteinTarget.innerText = `${minProtein}g - ${maxProtein}g / day`;
    }
  }

  if (btnCalculateBmi) btnCalculateBmi.addEventListener("click", calculateBMI);
  calculateBMI();

  if (btnBmiWhatsApp) {
    btnBmiWhatsApp.addEventListener("click", () => {
      const h = bmiHeight.value;
      const w = bmiWeight.value;
      const bmi = bmiValue.innerText;
      const goal = bmiGoal ? bmiGoal.value : "General Fitness";
      openWhatsAppMessage(`Hi PowerZone Baner! My Height is ${h}cm, Weight is ${w}kg, BMI is ${bmi}. Goal: ${goal}. Please share a custom Indian Diet Chart & Training Routine for Baner.`);
    });
  }

  // ---------- FAQ ----------
  document.querySelectorAll(".faq-item").forEach(item => {
    const question = item.querySelector(".faq-question");
    if (question) {
      question.addEventListener("click", () => {
        const isOpen = item.classList.contains("open");
        document.querySelectorAll(".faq-item").forEach(i => i.classList.remove("open"));
        if (!isOpen) item.classList.add("open");
      });
    }
  });

  // ---------- Pricing Toggle ----------
  const pricingToggle = document.getElementById("pricingToggle");
  const priceQuarterly = document.getElementById("priceQuarterly");
  const priceYearly = document.getElementById("priceYearly");

  if (pricingToggle) {
    pricingToggle.addEventListener("change", () => {
      if (pricingToggle.checked) {
        if (priceQuarterly) priceQuarterly.innerHTML = "₹ 5,999 <span>/ 3 Months (Annual Rate)</span>";
        if (priceYearly) priceYearly.innerHTML = "₹ 14,999 <span>/ 12 Months (Save 35%)</span>";
      } else {
        if (priceQuarterly) priceQuarterly.innerHTML = "₹ 6,499 <span>/ 3 Months</span>";
        if (priceYearly) priceYearly.innerHTML = "₹ 14,999 <span>/ 12 Months</span>";
      }
    });
  }

  // ---------- Modals ----------
  const bookingModal = document.getElementById("bookingModal");
  const referralModal = document.getElementById("referralModal");
  const modalClose = document.getElementById("modalClose");
  const refModalClose = document.getElementById("refModalClose");
  const btnHeroTrial = document.getElementById("btnHeroTrial");
  const btnBookTrialNav = document.getElementById("btnBookTrialNav");
  const btnReferral = document.getElementById("btnReferral");

  function openModal(modal) {
    if (modal) {
      modal.classList.add("active");
      document.body.style.overflow = "hidden";
    }
  }

  function closeModal(modal) {
    if (modal) {
      modal.classList.remove("active");
      document.body.style.overflow = "";
    }
  }

  if (btnHeroTrial) btnHeroTrial.addEventListener("click", () => openModal(bookingModal));
  if (btnBookTrialNav) btnBookTrialNav.addEventListener("click", () => openModal(bookingModal));
  if (btnReferral) btnReferral.addEventListener("click", () => openModal(referralModal));
  if (modalClose) modalClose.addEventListener("click", () => closeModal(bookingModal));
  if (refModalClose) refModalClose.addEventListener("click", () => closeModal(referralModal));

  [bookingModal, referralModal].forEach(modal => {
    if (modal) {
      modal.addEventListener("click", (e) => {
        if (e.target === modal) closeModal(modal);
      });
    }
  });

  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      closeModal(bookingModal);
      closeModal(referralModal);
    }
  });

  // ---------- Program & Plan buttons ----------
  document.querySelectorAll(".btn-program-inquire").forEach(btn => {
    btn.addEventListener("click", (e) => {
      const programName = e.currentTarget.getAttribute("data-program");
      openWhatsAppMessage(`Hi PowerZone Baner! I want to inquire and book a 3-Day Free Trial for the ${programName} program.`);
    });
  });

  document.querySelectorAll(".btn-select-plan").forEach(btn => {
    btn.addEventListener("click", (e) => {
      const planName = e.currentTarget.getAttribute("data-plan");
      openWhatsAppMessage(`Hi PowerZone Baner! I am interested in joining the ${planName} membership plan.`);
    });
  });

  // ---------- Booking Form ----------
  const bookingForm = document.getElementById("bookingForm");
  if (bookingForm) {
    bookingForm.addEventListener("submit", (e) => {
      e.preventDefault();
      const name = document.getElementById("memberName").value.trim();
      const phone = document.getElementById("memberPhone").value.trim();
      const locality = document.getElementById("memberLocality").value;
      if (!name || !phone) return;
      closeModal(bookingModal);
      openWhatsAppMessage(`Hi PowerZone Baner! My name is \( {name} ( \){phone}) from ${locality}. Please activate my 3-Day Free Trial Pass & send location pin!`);
    });
  }

  // ---------- Referral ----------
  const btnShareReferral = document.getElementById("btnShareReferral");
  if (btnShareReferral) {
    btnShareReferral.addEventListener("click", () => {
      openWhatsAppMessage("🏋️ Hey! Join me at PowerZone Fitness Studio in Baner Pune! Use my code *POWER-VIP-BANER* for a 3-Day FREE Trial Pass + 10% off membership!");
    });
  }

  // ---------- Mobile Hamburger ----------
  const hamburgerBtn = document.getElementById("hamburgerBtn");
  const navMenu = document.getElementById("navMenu");
  if (hamburgerBtn && navMenu) {
    hamburgerBtn.addEventListener("click", () => navMenu.classList.toggle("open"));
    navMenu.querySelectorAll("a").forEach(link => {
      link.addEventListener("click", () => navMenu.classList.remove("open"));
    });
  }

  // ---------- WhatsApp Helper ----------
  function openWhatsAppMessage(text) {
    const encodedText = encodeURIComponent(text);
    window.open(`https://wa.me/918329931123?text=${encodedText}`, "_blank");
  }
});
