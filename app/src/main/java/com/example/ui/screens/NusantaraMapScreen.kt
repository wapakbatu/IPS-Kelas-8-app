package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LearningRepository
import com.example.model.MapPoint
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
fun NusantaraMapScreen(
  studentName: String,
  visitedPoints: Set<String>,
  onPointVisited: (String) -> Unit,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit
) {
  val allPoints = LearningRepository.mapPoints
  var selectedCategory by remember { mutableStateOf("Semua") }
  var selectedPoint by remember { mutableStateOf<MapPoint?>(null) }
  var showInsight by remember { mutableStateOf(false) }

  val categories = listOf("Semua", "Jalur Laut", "Pelabuhan Kuno", "Pusat Kerajaan", "Pusat Rempah")

  val filteredPoints = if (selectedCategory == "Semua") {
    allPoints
  } else {
    allPoints.filter { it.category.contains(selectedCategory, ignoreCase = true) }
  }

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Peta Petualangan Nusantara",
        subtitle = "Eksplorasi Jalur Sutra Maritim Kuno",
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
      // Category filter chips
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(categories) { cat ->
          FilterChip(
            selected = selectedCategory == cat,
            onClick = { selectedCategory = cat },
            label = { Text(cat, fontSize = 12.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = OceanNavy,
              selectedLabelColor = Color.White
            ),
            modifier = Modifier.testTag("filter_chip_$cat")
          )
        }
      }

      // Map instruction banner
      Surface(
        color = OceanBlue.copy(alpha = 0.12f),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Explore,
            contentDescription = null,
            tint = OceanNavy,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Klik salah satu titik emas pada peta di bawah untuk mengungkap rahasia sejarah!",
            fontSize = 12.sp,
            color = OceanNavy,
            fontWeight = FontWeight.Medium
          )
        }
      }

      // Interactive Map Canvas Area
      BoxWithConstraints(
        modifier = Modifier
          .fillMaxWidth()
          .height(290.dp)
          .background(Color(0xFF0F3254))
          .testTag("interactive_map_canvas")
      ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // Canvas for historical sea, trade routes, and island silhouettes
        Canvas(modifier = Modifier.fillMaxSize()) {
          // Sea coordinate grid lines
          val gridStroke = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
          drawLine(
            color = Color(0x22FFFFFF),
            start = Offset(0f, height * 0.5f),
            end = Offset(width, height * 0.5f),
            strokeWidth = 1f
          )
          drawLine(
            color = Color(0x22FFFFFF),
            start = Offset(width * 0.5f, 0f),
            end = Offset(width * 0.5f, height),
            strokeWidth = 1f
          )

          // Sumatra Island Silhouette
          val sumatra = Path().apply {
            moveTo(width * 0.08f, height * 0.15f)
            lineTo(width * 0.26f, height * 0.58f)
            lineTo(width * 0.22f, height * 0.65f)
            lineTo(width * 0.06f, height * 0.22f)
            close()
          }
          drawPath(sumatra, color = Color(0xFF285943))

          // Java Island Silhouette
          val java = Path().apply {
            moveTo(width * 0.25f, height * 0.68f)
            lineTo(width * 0.56f, height * 0.72f)
            lineTo(width * 0.55f, height * 0.77f)
            lineTo(width * 0.25f, height * 0.73f)
            close()
          }
          drawPath(java, color = Color(0xFF2E654C))

          // Kalimantan Island Silhouette
          val borneo = Path().apply {
            moveTo(width * 0.38f, height * 0.25f)
            lineTo(width * 0.55f, height * 0.32f)
            lineTo(width * 0.58f, height * 0.52f)
            lineTo(width * 0.40f, height * 0.56f)
            lineTo(width * 0.34f, height * 0.42f)
            close()
          }
          drawPath(borneo, color = Color(0xFF285943))

          // Sulawesi Island Silhouette (K-shaped)
          val sulawesi = Path().apply {
            moveTo(width * 0.62f, height * 0.26f)
            lineTo(width * 0.69f, height * 0.32f)
            lineTo(width * 0.65f, height * 0.48f)
            lineTo(width * 0.72f, height * 0.58f)
            lineTo(width * 0.68f, height * 0.66f)
            lineTo(width * 0.63f, height * 0.52f)
            close()
          }
          drawPath(sulawesi, color = Color(0xFF23533E))

          // Maluku Islands cluster (The Spice Islands)
          drawCircle(color = Color(0xFF2E654C), radius = 12f, center = Offset(width * 0.80f, height * 0.42f))
          drawCircle(color = Color(0xFF2E654C), radius = 10f, center = Offset(width * 0.83f, height * 0.54f))
          drawCircle(color = Color(0xFFE5A93B), radius = 4f, center = Offset(width * 0.82f, height * 0.48f)) // Spice glow

          // Papua Island Silhouette
          val papua = Path().apply {
            moveTo(width * 0.88f, height * 0.42f)
            lineTo(width * 0.98f, height * 0.54f)
            lineTo(width * 0.95f, height * 0.72f)
            lineTo(width * 0.86f, height * 0.58f)
            close()
          }
          drawPath(papua, color = Color(0xFF214C38))

          // Ancient Maritime Silk Road Trade Routes (Dashed Lines)
          val tradePath = Path().apply {
            // India to Malaka
            moveTo(0f, height * 0.22f)
            quadraticTo(width * 0.12f, height * 0.28f, width * 0.18f, height * 0.35f)
            // Malaka to Java Sea
            lineTo(width * 0.30f, height * 0.62f)
            // Java Sea to Spice Islands (Maluku)
            quadraticTo(width * 0.55f, height * 0.66f, width * 0.81f, height * 0.48f)
            // Branch to China
            moveTo(width * 0.18f, height * 0.35f)
            quadraticTo(width * 0.35f, height * 0.15f, width * 0.45f, 0f)
          }

          drawPath(
            tradePath,
            color = Color(0xAAFFD166),
            style = Stroke(
              width = 3.5f,
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
            )
          )
        }

        // Overlay Interactive Point Markers
        filteredPoints.forEach { point ->
          val isVisited = visitedPoints.contains(point.id)
          val pointX = (width * point.longitudeRatio) - 20
          val pointY = (height * point.latitudeRatio) - 20

          Box(
            modifier = Modifier
              .offset(
                x = (maxWidth * point.longitudeRatio) - 18.dp,
                y = (maxHeight * point.latitudeRatio) - 18.dp
              )
              .size(36.dp)
              .testTag("map_marker_${point.id}")
              .clickable {
                selectedPoint = point
                showInsight = false
                onPointVisited(point.id)
              },
            contentAlignment = Alignment.Center
          ) {
            // Pulsing background beacon
            Box(
              modifier = Modifier
                .size(if (isVisited) 28.dp else 32.dp)
                .background(
                  color = if (isVisited) AncientGreen.copy(alpha = 0.35f) else GoldenAmber.copy(alpha = 0.4f),
                  shape = CircleShape
                )
            )

            // Center Pin icon
            Surface(
              shape = CircleShape,
              color = if (isVisited) AncientGreen else HeritageGold,
              shadowElevation = 4.dp,
              border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
              modifier = Modifier.size(22.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                if (isVisited) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                  )
                } else {
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .background(OceanNavy, CircleShape)
                  )
                }
              }
            }
          }
        }
      }

      // Legend & Quick Info
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFE2EBF3))
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(modifier = Modifier.size(10.dp).background(HeritageGold, CircleShape))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Belum Dikunjungi", fontSize = 11.sp, color = OceanDeep)
          Spacer(modifier = Modifier.width(12.dp))
          Box(modifier = Modifier.size(10.dp).background(AncientGreen, CircleShape))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Sudah Dikunjungi", fontSize = 11.sp, color = OceanDeep)
        }
        Text(
          text = "${visitedPoints.size}/${allPoints.size} Titik",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = OceanNavy
        )
      }

      // List of Points below for easy reading and accessibility
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, bottom = 10.dp),
        contentPadding = PaddingValues(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(filteredPoints) { item ->
          val isVisited = visitedPoints.contains(item.id)
          Card(
            modifier = Modifier
              .width(220.dp)
              .clickable {
                selectedPoint = item
                showInsight = false
                onPointVisited(item.id)
              }
              .testTag("map_card_${item.id}"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isVisited) AncientGreen else Color(0xFFCFD8DC)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                  shape = RoundedCornerShape(6.dp),
                  color = OceanNavy.copy(alpha = 0.1f)
                ) {
                  Text(
                    text = item.category,
                    fontSize = 10.sp,
                    color = OceanNavy,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (isVisited) {
                  Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = AncientGreen,
                    modifier = Modifier.size(16.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = OceanNavy
              )
              Text(
                text = item.region,
                fontSize = 11.sp,
                color = Color.Gray
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Klik untuk lihat sejarah & pemantik →",
                fontSize = 11.sp,
                color = OceanBlue,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }
  }

  // Point Detail Dialog
  selectedPoint?.let { point ->
    AlertDialog(
      onDismissRequest = { selectedPoint = null },
      shape = RoundedCornerShape(20.dp),
      containerColor = Color.White,
      title = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = OceanNavy.copy(alpha = 0.1f)
            ) {
              Text(
                text = point.category,
                fontSize = 11.sp,
                color = OceanNavy,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
              )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = point.name,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = OceanNavy
              )
            )
            Text(
              text = "📍 ${point.region}",
              fontSize = 12.sp,
              color = Color.Gray
            )
          }
          IconButton(
            onClick = { selectedPoint = null }
          ) {
            Icon(Icons.Default.Close, contentDescription = "Tutup")
          }
        }
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = "Signifikansi Historis:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = OceanNavy
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = point.historicalSignificance,
            fontSize = 13.sp,
            color = Color(0xFF2D3748),
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Komoditas / Bukti Peninggalan:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = OceanNavy
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = point.commoditiesOrRelics,
            fontSize = 13.sp,
            color = Terracotta,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Spark Question Card (Pertanyaan Pemantik)
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldenAmber)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Lightbulb,
                  contentDescription = null,
                  tint = GoldenAmber,
                  modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Ayo Berpikir Kritis!",
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = OceanNavy
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = point.sparkQuestion,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A5568)
              )

              Spacer(modifier = Modifier.height(8.dp))

              Button(
                onClick = { showInsight = !showInsight },
                colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("toggle_spark_insight_button")
              ) {
                Text(
                  text = if (showInsight) "Sembunyikan Wawasan" else "Buka Wawasan Sejarah",
                  fontSize = 11.sp,
                  color = Color.White
                )
              }

              AnimatedVisibility(visible = showInsight) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                  Text(
                    text = "Jawaban & Wawasan Sejarah:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = AncientGreen
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = point.sparkInsight,
                    fontSize = 12.sp,
                    color = Color(0xFF2D3748),
                    lineHeight = 16.sp
                  )
                }
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { selectedPoint = null },
          colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Tutup", color = Color.White)
        }
      }
    )
  }
}
