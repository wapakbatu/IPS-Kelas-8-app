package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.InstructionsDialog
import com.example.ui.theme.GoldenAmber
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.OceanDeep
import com.example.ui.theme.OceanNavy
import com.example.ui.theme.ParchmentCream
import com.example.ui.theme.WarmSand

@Composable
fun WelcomeScreen(
  initialName: String = "",
  onStartAdventure: (String) -> Unit
) {
  var studentNameInput by remember { mutableStateOf(initialName) }
  var showInstructions by remember { mutableStateOf(false) }
  var inputError by remember { mutableStateOf(false) }

  if (showInstructions) {
    InstructionsDialog(onDismiss = { showInstructions = false })
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(OceanDeep, OceanNavy, Color(0xFF133B5C))
        )
      )
  ) {
    // Decorative maritime canvas
    Canvas(
      modifier = Modifier
        .fillMaxSize()
    ) {
      val w = size.width
      val h = size.height

      // Gentle ocean waves in background
      val wavePath1 = Path().apply {
        moveTo(0f, h * 0.45f)
        cubicTo(w * 0.25f, h * 0.42f, w * 0.5f, h * 0.48f, w * 0.75f, h * 0.44f)
        cubicTo(w * 0.9f, h * 0.42f, w, h * 0.46f, w, h * 0.46f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
      }
      drawPath(wavePath1, color = Color(0x153A86FF))

      val wavePath2 = Path().apply {
        moveTo(0f, h * 0.48f)
        cubicTo(w * 0.3f, h * 0.52f, w * 0.6f, h * 0.46f, w * 0.85f, h * 0.50f)
        lineTo(w, h * 0.49f)
        lineTo(w, h)
        lineTo(0f, h)
        close()
      }
      drawPath(wavePath2, color = Color(0x221B6CA8))

      // Ancient compass rose star
      drawCircle(
        color = Color(0x1AF4B41A),
        radius = 120f,
        center = Offset(w * 0.85f, h * 0.18f)
      )
    }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp, vertical = 32.dp)
        .imePadding(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Spacer(modifier = Modifier.height(20.dp))

      // Emblem Header
      Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0x33FFFFFF),
        border = androidx.compose.foundation.BorderStroke(1.dp, HeritageGold.copy(alpha = 0.5f)),
        modifier = Modifier.padding(bottom = 16.dp)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "🇮🇩", fontSize = 18.sp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "MEDIA PEMBELAJARAN INTERAKTIF IPS KELAS VIII",
            color = GoldenAmber,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }
      }

      // Title & Subtitle
      Text(
        text = "Jelajah Nusantara",
        style = MaterialTheme.typography.headlineLarge.copy(
          fontWeight = FontWeight.ExtraBold,
          fontSize = 34.sp,
          color = Color.White
        ),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Kondisi Geografis dan Interaksi dengan Bangsa Asing",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Medium,
          fontSize = 15.sp,
          color = ParchmentCream.copy(alpha = 0.9f)
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier.widthIn(max = 480.dp)
      )

      Spacer(modifier = Modifier.height(24.dp))

      // Interactive Sailing Ship Card Illustration
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 440.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x350A192F)),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x40D4AF37))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(
              imageVector = Icons.Default.DirectionsBoat,
              contentDescription = "Kapal Jung",
              tint = HeritageGold,
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Kurikulum Merdeka • Kemdikbud 2021",
                color = GoldenAmber,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
              Text(
                text = "Eksplorasi Maritim, Perdagangan, & Kerajaan Kuno",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 12.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Student Name Form Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 440.dp)
          .testTag("welcome_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Siapa Nama Penjelajahmu?",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = OceanNavy,
              fontSize = 18.sp
            )
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "Masukkan nama lengkap atau panggilanmu untuk memulai ekspedisi belajar IPS.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = Color.Gray,
              textAlign = TextAlign.Center
            )
          )

          Spacer(modifier = Modifier.height(20.dp))

          OutlinedTextField(
            value = studentNameInput,
            onValueChange = {
              studentNameInput = it
              if (it.isNotBlank()) inputError = false
            },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("student_name_input"),
            label = { Text("Nama Siswa") },
            placeholder = { Text("Contoh: Budi Santoso / Siti Rahma") },
            singleLine = true,
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Ikon Siswa",
                tint = OceanBlue
              )
            },
            isError = inputError,
            supportingText = {
              if (inputError) {
                Text(
                  text = "Silakan masukkan nama terlebih dahulu ya!",
                  color = MaterialTheme.colorScheme.error,
                  fontSize = 12.sp
                )
              }
            },
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OceanNavy,
              unfocusedBorderColor = Color(0xFFCFD8DC),
              focusedLabelColor = OceanNavy
            ),
            shape = RoundedCornerShape(14.dp)
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Primary Action: Mulai Petualangan
          Button(
            onClick = {
              if (studentNameInput.isBlank()) {
                inputError = true
              } else {
                onStartAdventure(studentNameInput.trim())
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("start_adventure_button"),
            colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
            shape = RoundedCornerShape(14.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "Mulai Petualangan",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = GoldenAmber
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Secondary Action: Petunjuk Penggunaan
          OutlinedButton(
            onClick = { showInstructions = true },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("instructions_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = OceanNavy)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = OceanBlue,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Petunjuk Penggunaan",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = OceanNavy
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Dirancang untuk Pembelajaran Mandiri & Pendampingan Guru IPS",
        style = MaterialTheme.typography.labelSmall.copy(
          color = Color.White.copy(alpha = 0.6f),
          fontSize = 11.sp
        )
      )
    }
  }
}
