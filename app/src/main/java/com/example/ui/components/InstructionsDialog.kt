package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.OceanNavy
import com.example.ui.theme.ParchmentCream

@Composable
fun InstructionsDialog(
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = Color.White,
    icon = {
      Icon(
        imageVector = Icons.Default.Info,
        contentDescription = "Petunjuk",
        tint = OceanNavy,
        modifier = Modifier.size(36.dp)
      )
    },
    title = {
      Text(
        text = "Petunjuk Penggunaan MPI",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = OceanNavy
        )
      )
    },
    text = {
      Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
          .fillMaxWidth()
      ) {
        Text(
          text = "Selamat datang di Media Pembelajaran Interaktif (MPI) IPS Kelas VIII 'Jelajah Nusantara'!",
          style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
        )
        Spacer(modifier = Modifier.height(12.dp))

        InstructionStepItem(
          number = "1",
          title = "Peta Petualangan Nusantara",
          description = "Klik titik-titik pelabuhan kuno, selat niaga, dan kerajaan pada peta untuk membaca kisah sejarah dan memecahkan pertanyaan pemantik."
        )
        InstructionStepItem(
          number = "2",
          title = "Materi & Simulasi Interaktif",
          description = "Jelajahi 3 modul materi: pelajari pengaruh geografis, siklus angin muson, balikkan kartu komoditas rempah, dan ikuti simulasi pelayaran dagang."
        )
        InstructionStepItem(
          number = "3",
          title = "Detektif Sejarah",
          description = "Analisis peninggalan prasasti, candi, dan karya sastra kuno untuk menafsirkan kehidupan peradaban masa lalu."
        )
        InstructionStepItem(
          number = "4",
          title = "Kuis & Lencana",
          description = "Uji pemahamanmu lewat kuis 15 soal interaktif dan kumpulkan seluruh lencana pencapaian (skor minimal 80 untuk Cendekiawan Bahari)!"
        )
        InstructionStepItem(
          number = "5",
          title = "Refleksi Akhir",
          description = "Tuliskan refleksi belajarmu dan simpulkan keterkaitan antara letak geografis dengan kemajuan bangsa Indonesia."
        )
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.testTag("close_instructions_dialog")
      ) {
        Text("Saya Mengerti", color = Color.White)
      }
    }
  )
}

@Composable
private fun InstructionStepItem(
  number: String,
  title: String,
  description: String
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    colors = CardDefaults.cardColors(containerColor = ParchmentCream),
    shape = RoundedCornerShape(10.dp)
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.Top
    ) {
      Surface(
        color = OceanBlue,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(24.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = number,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
          )
        }
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = OceanNavy
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = description,
          fontSize = 12.sp,
          color = Color(0xFF4A5568),
          lineHeight = 16.sp
        )
      }
    }
  }
}
