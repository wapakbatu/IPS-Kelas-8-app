package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.MainMenuScreen
import com.example.ui.screens.Material1Screen
import com.example.ui.screens.Material2Screen
import com.example.ui.screens.Material3Screen
import com.example.ui.screens.NusantaraMapScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReflectionScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ParchmentCream
import com.example.viewmodel.AppScreen
import com.example.viewmodel.JelajahViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = ParchmentCream
        ) {
          JelajahNusantaraApp()
        }
      }
    }
  }
}

@Composable
fun JelajahNusantaraApp(
  viewModel: JelajahViewModel = viewModel()
) {
  val currentScreen by viewModel.currentScreenState.collectAsState()
  val studentName by viewModel.studentName.collectAsState()
  val completedSections by viewModel.completedSections.collectAsState()
  val visitedPoints by viewModel.visitedMapPoints.collectAsState()
  val quizAnswers by viewModel.quizAnswers.collectAsState()
  val quizSubmitted by viewModel.quizSubmitted.collectAsState()
  val quizScore by viewModel.quizScore.collectAsState()
  val earnedBadges by viewModel.earnedBadges.collectAsState()
  val tradeSimulation by viewModel.tradeSimulation.collectAsState()
  val detectiveSolved by viewModel.detectiveSolved.collectAsState()
  val reflection by viewModel.reflection.collectAsState()

  // Handle hardware Back button to return gracefully
  BackHandler(enabled = currentScreen != AppScreen.WELCOME && currentScreen != AppScreen.DASHBOARD) {
    viewModel.navigateBack()
  }

  Crossfade(targetState = currentScreen, label = "screen_transition") { screen ->
    when (screen) {
      AppScreen.WELCOME -> {
        WelcomeScreen(
          initialName = studentName,
          onStartAdventure = { name ->
            viewModel.setStudentName(name)
            viewModel.navigateTo(AppScreen.DASHBOARD)
          }
        )
      }

      AppScreen.DASHBOARD -> {
        MainMenuScreen(
          viewModel = viewModel,
          studentName = studentName,
          completedSections = completedSections,
          quizScore = quizScore,
          quizSubmitted = quizSubmitted,
          earnedBadges = earnedBadges,
          allBadges = viewModel.allBadges,
          progressRatio = viewModel.calculateOverallProgress(),
          onNavigate = { target -> viewModel.navigateTo(target) }
        )
      }

      AppScreen.MAP_EXPLORATION -> {
        NusantaraMapScreen(
          studentName = studentName,
          visitedPoints = visitedPoints,
          onPointVisited = { pointId -> viewModel.markMapPointVisited(pointId) },
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) }
        )
      }

      AppScreen.MATERIAL_1 -> {
        Material1Screen(
          studentName = studentName,
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          onNextToMaterial2 = { viewModel.navigateTo(AppScreen.MATERIAL_2) }
        )
      }

      AppScreen.MATERIAL_2 -> {
        Material2Screen(
          studentName = studentName,
          simulationResult = tradeSimulation,
          onRunSimulation = { comm, dest, monsoon ->
            viewModel.runTradeSimulation(comm, dest, monsoon)
          },
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          onNextToMaterial3 = { viewModel.navigateTo(AppScreen.MATERIAL_3) }
        )
      }

      AppScreen.MATERIAL_3 -> {
        Material3Screen(
          studentName = studentName,
          detectiveSolved = detectiveSolved,
          onSolveDetectiveRelic = { relicId -> viewModel.solveDetectiveRelic(relicId) },
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          onNextToQuiz = { viewModel.navigateTo(AppScreen.QUIZ) }
        )
      }

      AppScreen.QUIZ -> {
        QuizScreen(
          studentName = studentName,
          userAnswers = quizAnswers,
          isSubmitted = quizSubmitted,
          quizScore = quizScore,
          onAnswerSelected = { qId, optIdx -> viewModel.answerQuiz(qId, optIdx) },
          onSubmitQuiz = { viewModel.submitQuiz() },
          onResetQuiz = { viewModel.resetQuiz() },
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) },
          onNextToReflection = { viewModel.navigateTo(AppScreen.REFLECTION) }
        )
      }

      AppScreen.REFLECTION -> {
        ReflectionScreen(
          studentName = studentName,
          reflection = reflection,
          onSaveReflection = { newKnowledge, cultural, preservation, diversity ->
            viewModel.updateReflection(newKnowledge, cultural, preservation, diversity)
          },
          onBackClick = { viewModel.navigateBack() },
          onHomeClick = { viewModel.navigateTo(AppScreen.DASHBOARD) }
        )
      }

      AppScreen.INSTRUCTIONS -> {
        // Fallback to Dashboard if directly navigated
        MainMenuScreen(
          viewModel = viewModel,
          studentName = studentName,
          completedSections = completedSections,
          quizScore = quizScore,
          quizSubmitted = quizSubmitted,
          earnedBadges = earnedBadges,
          allBadges = viewModel.allBadges,
          progressRatio = viewModel.calculateOverallProgress(),
          onNavigate = { target -> viewModel.navigateTo(target) }
        )
      }
    }
  }
}
