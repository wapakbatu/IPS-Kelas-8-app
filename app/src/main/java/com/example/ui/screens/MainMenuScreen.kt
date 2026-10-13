package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.InstructionsDialog
import com.example.ui.components.MaritimeTopBar
import com.example.ui.theme.AncientGreen
import com.example.ui.theme.GoldenAmber
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.OceanDeep
import com.example.ui.theme.OceanNavy
import com.example.ui.theme.ParchmentCream
import com.example.ui.theme.Terracotta
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BadgeInfo
import com.example.viewmodel.JelajahViewModel

data class MenuItemData(
  val id: String,
  val targetScreen: AppScreen,
  val title: String,
  val subtitle: String,
  val icon: ImageVector,
  val accentColor: Color,
  val tag: String
)

@Composable
fun MainMenuScreen(
  viewModel: JelajahViewModel,
  studentName: String,
  completedSections: Set<String>,
  quizScore: Int,
  quizSubmitted: Boolean,
  earnedBadges: Set<String>,
  allBadges: List<BadgeInfo>,
  progressRatio: Float,
  onNavigate: (AppScreen) -> Unit
) {
  var showInstructions by remember { mutableStateOf(false) }

  if (showInstructions) {
    InstructionsDialog(onDismiss = { showInstructions = false })
  }

  val menuList = listOf(
    MenuItemData(
      id = "map",
      targetScreen = AppScreen.MAP_EXPLORATION,
      title = "Peta Petualangan Nusantara",
      subtitle = "Eksplorasi selat, pelabuhan, bandar niaga, dan kerajaan maritim",
      icon = Icons.Default.Explore,
      accentColor = OceanBlue,
      tag = "menu_item_map"
    ),
    MenuItemData(
      id = "materi1",
      targetScreen = AppScreen.MATERIAL_1,
      title = "Materi 1 — Interaksi Bangsa Asing",
      subtitle = "Kondisi geografis kepulauan, angin muson, dan 5 bentuk interaksi",
      icon = Icons.Default.AutoStories,
      accentColor = OceanNavy,
      tag = "menu_item_materi1"
    ),
    MenuItemData(
      id = "materi2",
      targetScreen = AppScreen.MATERIAL_2,
      title = "Materi 2 — Perdagangan Awal Masehi",
      subtitle = "Jalur sutra laut, komoditas rempah Maluku, dan simulasi niaga",
      icon = Icons.Default.ShoppingBag,
      accentColor = Terracotta,
      tag = "menu_item_materi2"
    ),
    MenuItemData(
      id = "materi3",
      targetScreen = AppScreen.MATERIAL_3,
      title = "Materi 3 — Kerajaan Hindu-Buddha",
      subtitle = "Teori masuknya pengaruh, 7 kerajaan besar, dan Detektif Sejarah",
      icon = Icons.Default.HistoryEdu,
      accentColor = HeritageGold,
      tag = "menu_item_materi3"
    ),
    MenuItemData(
      id = "kuis",
      targetScreen = AppScreen.QUIZ,
      title = "Kuis Interaktif (15 Soal)",
      subtitle = if (quizSubmitted) "Nilai Terakhir: $quizScore/100 • Uji ulang pemahaman" else "Uji pemahaman sebab-akibat & analisis sejarah",
      icon = Icons.Default.Quiz,
      accentColor = GoldenAmber,
      tag = "menu_item_quiz"
    ),
    MenuItemData(
      id = "refleksi",
      targetScreen = AppScreen.REFLECTION,
      title = "Refleksi & Evaluasi Akhir",
      subtitle = "Sintesis hubungan geografis, catatan refleksi, dan kepustakaan",
      icon = Icons.Default.Lightbulb,
      accentColor = AncientGreen,
      tag = "menu_item_reflection"
    )
  )

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Jelajah Nusantara",
        subtitle = "Menu Petualangan IPS Kelas VIII",
        studentName = studentName,
        showBackButton = false,
        showHomeButton = false,
        onInfoClick = { showInstructions = true },
        badgeCount = earnedBadges.size
      )
    },
    containerColor = ParchmentCream
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Welcome Greeting & Progress Card
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("dashboard_header_card"),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = OceanNavy),
          elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
          Column(
            modifier = Modifier.padding(20.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .background(GoldenAmber, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Text(text = "🧭", fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.width(14.dp))
              Column {
                Text(
                  text = "Halo, Penjelajah $studentName!",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp
                  )
                )
                Text(
                  text = "Mari jelajahi peradaban maritim dan sejarah Nusantara",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = ParchmentCream.copy(alpha = 0.85f),
                    fontSize = 12.sp
                  )
                )
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Progress bar
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Progres Petualangan",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = GoldenAmber,
                  fontWeight = FontWeight.SemiBold
                )
              )
              Text(
                text = "${(progressRatio * 100).toInt()}% Selesai",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = Color.White,
                  fontWeight = FontWeight.Bold
                )
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
              progress = { progressRatio },
              modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
              color = GoldenAmber,
              trackColor = Color(0x33FFFFFF),
              drawStopIndicator = {}
            )

            if (quizSubmitted) {
              Spacer(modifier = Modifier.height(12.dp))
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0x25F4B41A),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = GoldenAmber,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "Skor Kuis Tertinggi: $quizScore/100",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            }
          }
        }
      }

      // Badges Carousel Section
      item {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Lencana Penghargaan (${earnedBadges.size}/${allBadges.size})",
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = OceanNavy,
                fontSize = 15.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(allBadges) { badge ->
              val isEarned = earnedBadges.contains(badge.id)
              Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                  containerColor = if (isEarned) Color.White else Color(0xFFECEFF1)
                ),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isEarned) HeritageGold else Color(0xFFCFD8DC)
                ),
                modifier = Modifier.width(150.dp)
              ) {
                Column(
                  modifier = Modifier.padding(10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Box(
                    modifier = Modifier
                      .size(36.dp)
                      .background(
                        if (isEarned) GoldenAmber.copy(alpha = 0.2f) else Color(0xFFE0E0E0),
                        CircleShape
                      ),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = if (isEarned) badge.iconEmoji else "🔒",
                      fontSize = 18.sp
                    )
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(
                    text = badge.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = if (isEarned) OceanNavy else Color.Gray,
                    maxLines = 1
                  )
                  Text(
                    text = if (isEarned) "Tercapai!" else "Belum diraih",
                    fontSize = 10.sp,
                    color = if (isEarned) AncientGreen else Color.Gray
                  )
                }
              }
            }
          }
        }
      }

      // 6 Main Learning Modules
      item {
        Text(
          text = "Modul & Aktivitas Pembelajaran",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = OceanNavy,
            fontSize = 16.sp
          ),
          modifier = Modifier.padding(top = 8.dp)
        )
      }

      items(menuList) { item ->
        val isCompleted = completedSections.contains(item.id)

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(item.targetScreen) }
            .testTag(item.tag),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = item.accentColor.copy(alpha = 0.15f),
              modifier = Modifier.size(52.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = item.icon,
                  contentDescription = item.title,
                  tint = item.accentColor,
                  modifier = Modifier.size(28.dp)
                )
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
              modifier = Modifier.weight(1f)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = item.title,
                  style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = OceanNavy,
                    fontSize = 15.sp
                  ),
                  modifier = Modifier.weight(1f)
                )
                if (isCompleted) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selesai",
                    tint = AncientGreen,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = Color(0xFF5A6A80),
                  fontSize = 12.sp,
                  lineHeight = 16.sp
                )
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
              imageVector = Icons.Default.ChevronRight,
              contentDescription = "Buka",
              tint = Color.LightGray
            )
          }
        }
      }

      item {
        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}
