package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.LearningRepository
import com.example.model.StudentReflection
import com.example.model.TradeSimulationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AppScreen {
  WELCOME,
  DASHBOARD,
  MAP_EXPLORATION,
  MATERIAL_1,
  MATERIAL_2,
  MATERIAL_3,
  QUIZ,
  REFLECTION,
  INSTRUCTIONS
}

data class BadgeInfo(
  val id: String,
  val title: String,
  val description: String,
  val iconEmoji: String
)

class JelajahViewModel : ViewModel() {

  private val _studentName = MutableStateFlow("")
  val studentName: StateFlow<String> = _studentName.asStateFlow()

  private val _screenStack = MutableStateFlow(listOf(AppScreen.WELCOME))
  val currentScreen: StateFlow<AppScreen> = MutableStateFlow(AppScreen.WELCOME)

  // Explicit current screen flow for Compose observing
  private val _currentScreenState = MutableStateFlow(AppScreen.WELCOME)
  val currentScreenState: StateFlow<AppScreen> = _currentScreenState.asStateFlow()

  private val _completedSections = MutableStateFlow(setOf<String>())
  val completedSections: StateFlow<Set<String>> = _completedSections.asStateFlow()

  private val _visitedMapPoints = MutableStateFlow(setOf<String>())
  val visitedMapPoints: StateFlow<Set<String>> = _visitedMapPoints.asStateFlow()

  // Quiz state
  private val _quizAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
  val quizAnswers: StateFlow<Map<Int, Int>> = _quizAnswers.asStateFlow()

  private val _quizSubmitted = MutableStateFlow(false)
  val quizSubmitted: StateFlow<Boolean> = _quizSubmitted.asStateFlow()

  private val _quizScore = MutableStateFlow(0)
  val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

  // Badges
  val allBadges = listOf(
    BadgeInfo("penjelajah", "Penjelajah Nusantara", "Menjelajahi peta pelayaran dan pelabuhan kuno", "🧭"),
    BadgeInfo("pedagang", "Pedagang Cerdas", "Berhasil menjalankan simulasi niaga antarpulau", "⛵"),
    BadgeInfo("detektif", "Detektif Sejarah", "Menganalisis sumber prasasti dan peninggalan candi", "🔍"),
    BadgeInfo("cendekiawan", "Cendekiawan Bahari", "Meraih nilai kuis 80 atau lebih", "🏆"),
    BadgeInfo("pembelajar", "Refleksionis Teladan", "Menyelesaikan lembar refleksi pembelajaran", "📜")
  )

  private val _earnedBadges = MutableStateFlow(setOf<String>())
  val earnedBadges: StateFlow<Set<String>> = _earnedBadges.asStateFlow()

  // Material 2 Simulation state
  private val _tradeSimulation = MutableStateFlow<TradeSimulationResult?>(null)
  val tradeSimulation: StateFlow<TradeSimulationResult?> = _tradeSimulation.asStateFlow()

  // Material 3 Detective state
  private val _detectiveSolved = MutableStateFlow(setOf<String>())
  val detectiveSolved: StateFlow<Set<String>> = _detectiveSolved.asStateFlow()

  // Reflection state
  private val _reflection = MutableStateFlow(StudentReflection())
  val reflection: StateFlow<StudentReflection> = _reflection.asStateFlow()

  fun setStudentName(name: String) {
    _studentName.value = name.trim()
  }

  fun navigateTo(screen: AppScreen) {
    _screenStack.update { it + screen }
    _currentScreenState.value = screen
    markSectionCompleted(screen)
  }

  fun navigateBack(): Boolean {
    val current = _screenStack.value
    if (current.size > 1) {
      val newStack = current.dropLast(1)
      _screenStack.value = newStack
      _currentScreenState.value = newStack.last()
      return true
    }
    return false
  }

  fun markMapPointVisited(pointId: String) {
    _visitedMapPoints.update { it + pointId }
    if (_visitedMapPoints.value.size >= 4) {
      awardBadge("penjelajah")
      _completedSections.update { it + "map" }
    }
  }

  private fun markSectionCompleted(screen: AppScreen) {
    when (screen) {
      AppScreen.MATERIAL_1 -> _completedSections.update { it + "materi1" }
      AppScreen.MATERIAL_2 -> _completedSections.update { it + "materi2" }
      AppScreen.MATERIAL_3 -> _completedSections.update { it + "materi3" }
      AppScreen.QUIZ -> _completedSections.update { it + "kuis" }
      AppScreen.REFLECTION -> _completedSections.update { it + "refleksi" }
      else -> {}
    }
  }

  fun answerQuiz(questionId: Int, optionIndex: Int) {
    if (!_quizSubmitted.value) {
      _quizAnswers.update { it + (questionId to optionIndex) }
    }
  }

  fun submitQuiz() {
    val answers = _quizAnswers.value
    val questions = LearningRepository.quizQuestions
    var correctCount = 0
    questions.forEach { q ->
      if (answers[q.id] == q.correctOptionIndex) {
        correctCount++
      }
    }
    val score = ((correctCount.toFloat() / questions.size) * 100).toInt()
    _quizScore.value = score
    _quizSubmitted.value = true
    _completedSections.update { it + "kuis" }

    if (score >= 80) {
      awardBadge("cendekiawan")
    }
  }

  fun resetQuiz() {
    _quizAnswers.value = emptyMap()
    _quizSubmitted.value = false
    _quizScore.value = 0
  }

  fun runTradeSimulation(commodityId: String, portId: String, monsoon: String) {
    // Logic based on historical monsoon and commodities:
    // Muson Barat (Okt - April): Angin bertiup dari Asia ke Nusantara / Australia (cocok berlayar ke selatan/timur)
    // Muson Timur (April - Okt): Angin bertiup dari Australia ke Asia (cocok kembali ke barat/utara)
    val isFavorable = (monsoon == "Muson Barat" && (portId == "Jawa" || portId == "Maluku")) ||
        (monsoon == "Muson Timur" && (portId == "Selat Malaka" || portId == "Tiongkok"))

    val result = if (isFavorable) {
      TradeSimulationResult(
        commodityChosen = commodityId,
        targetPort = portId,
        monsoonChosen = monsoon,
        isSuccessful = true,
        profitMultiplier = 2.8f,
        outcomeStory = "Kapal Jung layarmu meluncur mulus terbawa tiupan angin $monsoon! Barang dagangan $commodityId laku keras di pelabuhan $portId dengan keuntungan berlipat.",
        educationalInsight = "Para pelaut kuno menguasai ilmu astronomi dan siklus angin muson. Kesalahan membaca arah angin dapat menunda pelayaran hingga 6 bulan penuh!"
      )
    } else {
      TradeSimulationResult(
        commodityChosen = commodityId,
        targetPort = portId,
        monsoonChosen = monsoon,
        isSuccessful = true, // Still completes with lesson
        profitMultiplier = 1.3f,
        outcomeStory = "Kapalmu menghadapi arus laut dan angin $monsoon yang berlawanan. Terpaksa singgah di bandar pesisir selama beberapa pekan sebelum mencapai $portId.",
        educationalInsight = "Persinggahan tak terduga seperti ini justru memicu terjadinya interaksi sosial, pertukaran bahasa, dan penyebaran agama di pelabuhan-pelabuhan singgah Nusantara!"
      )
    }
    _tradeSimulation.value = result
    awardBadge("pedagang")
  }

  fun solveDetectiveRelic(relicId: String) {
    _detectiveSolved.update { it + relicId }
    if (_detectiveSolved.value.size >= 3) {
      awardBadge("detektif")
    }
  }

  fun updateReflection(
    newKnowledge: String? = null,
    culturalChange: String? = null,
    preservation: String? = null,
    diversity: String? = null
  ) {
    _reflection.update { curr ->
      curr.copy(
        newKnowledge = newKnowledge ?: curr.newKnowledge,
        culturalChangeInsight = culturalChange ?: curr.culturalChangeInsight,
        preservationReason = preservation ?: curr.preservationReason,
        diversityAppreciation = diversity ?: curr.diversityAppreciation
      )
    }
    val ref = _reflection.value
    if (ref.newKnowledge.isNotBlank() && ref.culturalChangeInsight.isNotBlank()) {
      awardBadge("pembelajar")
      _completedSections.update { it + "refleksi" }
    }
  }

  private fun awardBadge(badgeId: String) {
    _earnedBadges.update { it + badgeId }
  }

  fun calculateOverallProgress(): Float {
    // Total sections: map, materi1, materi2, materi3, kuis, refleksi (6 sections)
    val total = 6f
    val completed = _completedSections.value.size.coerceAtMost(6)
    return (completed / total)
  }
}
