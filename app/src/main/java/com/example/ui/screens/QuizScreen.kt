package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LearningRepository
import com.example.model.QuestionType
import com.example.model.QuizQuestion
import com.example.ui.components.MaritimeTopBar
import com.example.ui.theme.AncientGreen
import com.example.ui.theme.GoldenAmber
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.OceanDeep
import com.example.ui.theme.OceanNavy
import com.example.ui.theme.ParchmentCream
import com.example.ui.theme.Terracotta

@Composable
fun QuizScreen(
  studentName: String,
  userAnswers: Map<Int, Int>,
  isSubmitted: Boolean,
  quizScore: Int,
  onAnswerSelected: (questionId: Int, optionIndex: Int) -> Unit,
  onSubmitQuiz: () -> Unit,
  onResetQuiz: () -> Unit,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  onNextToReflection: () -> Unit
) {
  val questions = LearningRepository.quizQuestions
  var activeQuestionIndex by remember { mutableIntStateOf(0) }
  val currentQuestion = questions[activeQuestionIndex]

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Kuis Interaktif (15 Soal)",
        subtitle = if (isSubmitted) "Skor: $quizScore/100" else "Soal ${activeQuestionIndex + 1} dari ${questions.size}",
        studentName = studentName,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick
      )
    },
    containerColor = ParchmentCream
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      // Question Number Navigation Carousel
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        itemsIndexed(questions) { idx, q ->
          val isAnswered = userAnswers.containsKey(q.id)
          val isActive = activeQuestionIndex == idx
          val isCorrect = isSubmitted && userAnswers[q.id] == q.correctOptionIndex
          val isWrong = isSubmitted && isAnswered && !isCorrect

          val chipColor = when {
            isSubmitted && isCorrect -> AncientGreen
            isSubmitted && isWrong -> Color(0xFFD32F2F)
            isActive -> OceanNavy
            isAnswered -> OceanBlue
            else -> Color(0xFFE2E8F0)
          }

          val textColor = when {
            isActive || isAnswered || isSubmitted -> Color.White
            else -> OceanNavy
          }

          Box(
            modifier = Modifier
              .size(34.dp)
              .background(chipColor, CircleShape)
              .clickable { activeQuestionIndex = idx }
              .testTag("quiz_nav_pill_$idx"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${idx + 1}",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = textColor
            )
          }
        }
      }

      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Result Summary Banner if submitted
        if (isSubmitted) {
          ScoreSummaryCard(
            score = quizScore,
            total = questions.size,
            onReset = onResetQuiz,
            onNext = onNextToReflection
          )
        }

        // Active Question Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          modifier = Modifier.testTag("active_question_card")
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Section Tag & Type badge
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth()
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = OceanNavy.copy(alpha = 0.1f)
              ) {
                Text(
                  text = currentQuestion.materialSection,
                  fontSize = 11.sp,
                  color = OceanNavy,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }

              Spacer(modifier = Modifier.weight(1f))

              val typeLabel = when (currentQuestion.type) {
                QuestionType.MULTIPLE_CHOICE -> "Pilihan Ganda"
                QuestionType.TRUE_FALSE -> "Benar / Salah"
                QuestionType.HISTORICAL_ANALYSIS -> "Analisis Sejarah"
                QuestionType.CAUSE_EFFECT -> "Sebab - Akibat"
              }

              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Terracotta.copy(alpha = 0.12f)
              ) {
                Text(
                  text = typeLabel,
                  fontSize = 10.sp,
                  color = Terracotta,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Question Text
            Text(
              text = "${activeQuestionIndex + 1}. ${currentQuestion.question}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = OceanNavy,
                lineHeight = 22.sp
              )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Option Choices
            currentQuestion.options.forEachIndexed { optIndex, optionText ->
              val isSelected = userAnswers[currentQuestion.id] == optIndex
              val isCorrectOption = optIndex == currentQuestion.correctOptionIndex

              val cardBg = when {
                isSubmitted && isCorrectOption -> Color(0xFFE8F5E9)
                isSubmitted && isSelected && !isCorrectOption -> Color(0xFFFFEBEE)
                isSelected -> Color(0xFFE3F2FD)
                else -> Color(0xFFF8FAFC)
              }

              val borderColor = when {
                isSubmitted && isCorrectOption -> AncientGreen
                isSubmitted && isSelected && !isCorrectOption -> Color(0xFFE53935)
                isSelected -> OceanBlue
                else -> Color(0xFFE2E8F0)
              }

              Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp)
                  .clickable(enabled = !isSubmitted) {
                    onAnswerSelected(currentQuestion.id, optIndex)
                  }
                  .testTag("quiz_option_${currentQuestion.id}_$optIndex")
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  val optionLetter = ('A' + optIndex).toString()
                  Surface(
                    shape = CircleShape,
                    color = if (isSelected) OceanNavy else Color.LightGray,
                    modifier = Modifier.size(26.dp)
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      Text(
                        text = optionLetter,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                      )
                    }
                  }

                  Spacer(modifier = Modifier.width(10.dp))

                  Text(
                    text = optionText,
                    fontSize = 13.sp,
                    color = Color(0xFF2D3748),
                    modifier = Modifier.weight(1f),
                    lineHeight = 18.sp
                  )

                  if (isSubmitted) {
                    if (isCorrectOption) {
                      Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AncientGreen, modifier = Modifier.size(20.dp))
                    } else if (isSelected) {
                      Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
                    }
                  }
                }
              }
            }

            // Real-time Explanation after selection or submission
            val hasAnsweredCurrent = userAnswers.containsKey(currentQuestion.id)
            if (hasAnsweredCurrent) {
              Spacer(modifier = Modifier.height(12.dp))
              val isCorrect = userAnswers[currentQuestion.id] == currentQuestion.correctOptionIndex

              Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isCorrect) Color(0xFFF0FFF4) else Color(0xFFFFF5F5),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isCorrect) AncientGreen else Color(0xFFFEB2B2)
                ),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = if (isCorrect) "✓ Jawabanmu Benar!" else "✗ Jawaban Kurang Tepat",
                      fontWeight = FontWeight.Bold,
                      fontSize = 12.sp,
                      color = if (isCorrect) AncientGreen else Color(0xFFC53030)
                    )
                  }
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = currentQuestion.explanation,
                    fontSize = 12.sp,
                    color = Color(0xFF2D3748),
                    lineHeight = 16.sp
                  )
                }
              }
            }
          }
        }

        // Navigation Prev & Next Controls
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          OutlinedButton(
            onClick = {
              if (activeQuestionIndex > 0) activeQuestionIndex--
            },
            enabled = activeQuestionIndex > 0,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("quiz_prev_button")
          ) {
            Text("← Sebelumnya")
          }

          if (activeQuestionIndex < questions.size - 1) {
            Button(
              onClick = { activeQuestionIndex++ },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
              modifier = Modifier.testTag("quiz_next_button")
            ) {
              Text("Berikutnya →", color = Color.White)
            }
          } else if (!isSubmitted) {
            Button(
              onClick = onSubmitQuiz,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = AncientGreen),
              modifier = Modifier.testTag("quiz_submit_button")
            ) {
              Text("Selesaikan Kuis! ✓", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ScoreSummaryCard(
  score: Int,
  total: Int,
  onReset: () -> Unit,
  onNext: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = OceanNavy),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = Modifier.fillMaxWidth().testTag("quiz_result_summary_card")
  ) {
    Column(
      modifier = Modifier.padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(54.dp)
          .background(GoldenAmber, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.EmojiEvents,
          contentDescription = null,
          tint = OceanNavy,
          modifier = Modifier.size(32.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Hasil Kuis Pembelajaran IPS",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White
      )

      Text(
        text = "Nilai Akhir = (Jawaban Benar ÷ $total) × 100",
        fontSize = 11.sp,
        color = GoldenAmber
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "$score",
        fontWeight = FontWeight.ExtraBold,
        fontSize = 44.sp,
        color = GoldenAmber
      )

      val predicate = when {
        score >= 80 -> "Luar Biasa! Kamu pantas menyandang gelar Cendekiawan Bahari Nusantara! 🏆"
        score >= 60 -> "Bagus! Kamu sudah memahami konsep penting interaksi dan perdagangan kuno. Tetap semangat! 👍"
        else -> "Ayo baca kembali materi di atas dan coba kuis ini sekali lagi untuk hasil terbaik! 💪"
      }

      Text(
        text = predicate,
        fontSize = 12.sp,
        color = Color.White.copy(alpha = 0.9f),
        textAlign = TextAlign.Center,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = onReset,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
          modifier = Modifier
            .weight(1f)
            .testTag("reset_quiz_button")
        ) {
          Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Ulangi Kuis", fontSize = 12.sp)
        }

        Button(
          onClick = onNext,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = GoldenAmber),
          modifier = Modifier
            .weight(1f)
            .testTag("goto_reflection_from_quiz_button")
        ) {
          Text("Refleksi Akhir →", color = OceanDeep, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
      }
    }
  }
}
