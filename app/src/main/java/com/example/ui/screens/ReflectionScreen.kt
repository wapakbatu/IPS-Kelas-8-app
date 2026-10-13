package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LearningRepository
import com.example.model.StudentReflection
import com.example.ui.components.MaritimeTopBar
import com.example.ui.theme.AncientGreen
import com.example.ui.theme.GoldenAmber
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanBlue
import com.example.ui.theme.OceanNavy
import com.example.ui.theme.ParchmentCream
import com.example.ui.theme.Terracotta

@Composable
fun ReflectionScreen(
  studentName: String,
  reflection: StudentReflection,
  onSaveReflection: (newKnowledge: String, culturalChange: String, preservation: String, diversity: String) -> Unit,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabTitles = listOf("Sintesis Hubungan", "Refleksi Pribadi", "Ringkasan Materi", "Daftar Pustaka")

  var newKnowledgeInput by remember { mutableStateOf(reflection.newKnowledge) }
  var culturalChangeInput by remember { mutableStateOf(reflection.culturalChangeInsight) }
  var preservationInput by remember { mutableStateOf(reflection.preservationReason) }
  var diversityInput by remember { mutableStateOf(reflection.diversityAppreciation) }

  var savedSuccess by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Refleksi & Evaluasi Akhir",
        subtitle = "Sintesis Pengetahuan & Makna Keberagaman",
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
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.White,
        contentColor = AncientGreen,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = AncientGreen
          )
        }
      ) {
        tabTitles.forEachIndexed { index, title ->
          Tab(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            text = {
              Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                maxLines = 1
              )
            },
            modifier = Modifier.testTag("refleksi_tab_$index")
          )
        }
      }

      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        when (selectedTab) {
          0 -> SynthesisDiagramSection()
          1 -> {
            StudentReflectionFormSection(
              newKnowledge = newKnowledgeInput,
              culturalChange = culturalChangeInput,
              preservation = preservationInput,
              diversity = diversityInput,
              onNewKnowledgeChange = { newKnowledgeInput = it },
              onCulturalChangeChange = { culturalChangeInput = it },
              onPreservationChange = { preservationInput = it },
              onDiversityChange = { diversityInput = it },
              onSave = {
                onSaveReflection(
                  newKnowledgeInput,
                  culturalChangeInput,
                  preservationInput,
                  diversityInput
                )
                savedSuccess = true
              },
              savedSuccess = savedSuccess
            )
          }
          2 -> KeySummarySection()
          3 -> BibliographySection()
        }
      }
    }
  }
}

@Composable
private fun SynthesisDiagramSection() {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = CircleShape,
          color = AncientGreen.copy(alpha = 0.15f),
          modifier = Modifier.size(40.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AncientGreen)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Sintesis Hubungan Sebab-Akibat",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Bagaimana Sejarah Nusantara Saling Terhubung?",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      SynthesisStep(
        step = "1",
        title = "Kondisi Geografis Kepulauan",
        desc = "Letak silang strategis di antara 2 benua dan 2 samudera menjadikan kepulauan Nusantara sebagai urat nadi pelayaran internasional, didukung kesuburan tanah tropis yang menghasilkan rempah-rempah bernilai tinggi."
      )

      ArrowDownDivider()

      SynthesisStep(
        step = "2",
        title = "Perkembangan Perdagangan Maritim",
        desc = "Angin muson memicu kedatangan kapal dagang asing dari India, Tiongkok, dan Arab yang berlabuh di pelabuhan-pelabuhan strategis seperti Selat Malaka, Barus, dan Sunda."
      )

      ArrowDownDivider()

      SynthesisStep(
        step = "3",
        title = "Interaksi & Masuknya Pengaruh Luar",
        desc = "Pedagang yang menetap berbulan-bulan berinteraksi secara damai dengan penduduk lokal, membawa pengaruh bahasa Sanskerta, aksara Pallawa, dan ajaran Hindu-Buddha."
      )

      ArrowDownDivider()

      SynthesisStep(
        step = "4",
        title = "Perkembangan Kerajaan & Peradaban",
        desc = "Masyarakat Nusantara menyerap dan mengadaptasi pengaruh tersebut untuk membangun kerajaan-kerajaan besar (Kutai, Tarumanegara, Sriwijaya, Medang, hingga Majapahit) dengan seni arsitektur dan hukum tata negara yang maju."
      )
    }
  }
}

@Composable
private fun SynthesisStep(step: String, title: String, desc: String) {
  Row(verticalAlignment = Alignment.Top) {
    Surface(
      shape = CircleShape,
      color = OceanNavy,
      modifier = Modifier.size(26.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(text = step, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = desc, fontSize = 12.sp, color = Color(0xFF4A5568), lineHeight = 16.sp)
    }
  }
}

@Composable
private fun ArrowDownDivider() {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.Center
  ) {
    Text(text = "↓", fontSize = 18.sp, color = Terracotta, fontWeight = FontWeight.Bold)
  }
}

@Composable
private fun StudentReflectionFormSection(
  newKnowledge: String,
  culturalChange: String,
  preservation: String,
  diversity: String,
  onNewKnowledgeChange: (String) -> Unit,
  onCulturalChangeChange: (String) -> Unit,
  onPreservationChange: (String) -> Unit,
  onDiversityChange: (String) -> Unit,
  onSave: () -> Unit,
  savedSuccess: Boolean
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = CircleShape,
          color = OceanBlue.copy(alpha = 0.15f),
          modifier = Modifier.size(40.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.EditNote, contentDescription = null, tint = OceanBlue)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Lembar Refleksi Diri Siswa",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Tuliskan pemikiranmu setelah belajar modul ini",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      ReflectionInputField(
        number = "1",
        label = "Pengetahuan baru apa yang paling berkesan bagimu?",
        placeholder = "Contoh: Saya baru tahu bahwa pedagang asing tinggal berbulan-bulan di Indonesia karena menunggu siklus angin muson...",
        value = newKnowledge,
        onValueChange = onNewKnowledgeChange,
        tag = "reflection_input_1"
      )

      ReflectionInputField(
        number = "2",
        label = "Bagaimana perdagangan dapat membawa perubahan budaya?",
        placeholder = "Contoh: Melalui pergaulan sehari-hari di pasar, perkawinan campur, dan penyerapan bahasa asing ke bahasa Melayu kuno...",
        value = culturalChange,
        onValueChange = onCulturalChangeChange,
        tag = "reflection_input_2"
      )

      ReflectionInputField(
        number = "3",
        label = "Mengapa peninggalan sejarah (prasasti/candi) perlu kita jaga?",
        placeholder = "Contoh: Agar generasi penerus dapat mempelajari asal-usul bangsa dan tidak melupakan kejayaan maritim masa lalu...",
        value = preservation,
        onValueChange = onPreservationChange,
        tag = "reflection_input_3"
      )

      ReflectionInputField(
        number = "4",
        label = "Bagaimana kita dapat menghargai keberagaman budaya di Indonesia?",
        placeholder = "Contoh: Mengamalkan semboyan Bhinneka Tunggal Ika dari zaman Majapahit dengan saling menghormati perbedaan suku dan agama...",
        value = diversity,
        onValueChange = onDiversityChange,
        tag = "reflection_input_4"
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = onSave,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("save_reflection_button"),
        colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
        shape = RoundedCornerShape(12.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Check, contentDescription = null, tint = GoldenAmber)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Simpan Lembar Refleksi 📜", fontWeight = FontWeight.Bold, color = Color.White)
        }
      }

      AnimatedVisibility(visible = savedSuccess) {
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFF0FFF4),
          border = androidx.compose.foundation.BorderStroke(1.dp, AncientGreen),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AncientGreen, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Refleksi berhasil disimpan! Kamu mendapatkan lencana Refleksionis Teladan! 🎉",
              color = AncientGreen,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ReflectionInputField(
  number: String,
  label: String,
  placeholder: String,
  value: String,
  onValueChange: (String) -> Unit,
  tag: String
) {
  Column(modifier = Modifier.padding(vertical = 6.dp)) {
    Text(
      text = "$number. $label",
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
      color = OceanNavy
    )
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = Modifier
        .fillMaxWidth()
        .testTag(tag),
      placeholder = { Text(placeholder, fontSize = 11.sp, color = Color.LightGray) },
      minLines = 2,
      shape = RoundedCornerShape(10.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = OceanNavy,
        unfocusedBorderColor = Color(0xFFCFD8DC)
      )
    )
  }
}

@Composable
private fun KeySummarySection() {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = CircleShape,
          color = HeritageGold.copy(alpha = 0.2f),
          modifier = Modifier.size(40.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.AutoStories, contentDescription = null, tint = OceanNavy)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Ringkasan Materi Esensial",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Catatan Intisari Belajar IPS Kelas VIII",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      SummaryCardItem("1. Kepulauan & Laut Pemersatu", "Laut Nusantara berfungsi sebagai penghubung komunikasi dan jalur niaga, bukan dinding pemisah.")
      SummaryCardItem("2. Angin Muson", "Pergantian Angin Muson Barat dan Muson Timur setiap 6 bulan menjadi pengatur ritme pelayaran samudra kuno.")
      SummaryCardItem("3. Rempah-Rempah Endemik", "Cengkih dan pala hanya tumbuh di Kepulauan Maluku, menjadikannya komoditas bernilai tinggi seharga emas.")
      SummaryCardItem("4. Teori Masuknya Hindu-Buddha", "Meliputi Teori Brahmana, Ksatria, Waisya, dan Arus Balik. Masyarakat lokal berperan aktif mengadaptasi pengaruh sesuai kondisi setempat (akulturasi).")
      SummaryCardItem("5. Kerajaan Bersejarah", "Dari Kutai (Kaltim, abad ke-4 M), Tarumanegara (Jabar), Sriwijaya (maritim Sumatra), Mataram Kuno (Borobudur & Prambanan), hingga Majapahit dengan semboyan persatuan Bhinneka Tunggal Ika.")
    }
  }
}

@Composable
private fun SummaryCardItem(title: String, body: String) {
  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBFD)),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = body, fontSize = 11.sp, color = Color(0xFF4A5568), lineHeight = 15.sp)
    }
  }
}

@Composable
private fun BibliographySection() {
  val bibliography = LearningRepository.bibliography

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = CircleShape,
          color = Terracotta.copy(alpha = 0.15f),
          modifier = Modifier.size(40.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Terracotta)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Daftar Pustaka & Referensi",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Rujukan Kurikulum Kemdikbud 2021 & Sejarah Kredibel",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Sumber Utama Pembelajaran:",
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = OceanNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = bibliography.first(),
        fontSize = 12.sp,
        color = Color(0xFF1B4332),
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium
      )

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Sumber Tambahan & Pengayaan Sejarah Maritim:",
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = OceanNavy
      )
      Spacer(modifier = Modifier.height(4.dp))

      bibliography.drop(1).forEachIndexed { idx, ref ->
        Text(
          text = "${idx + 1}. $ref",
          fontSize = 11.sp,
          color = Color(0xFF4A5568),
          lineHeight = 15.sp,
          modifier = Modifier.padding(vertical = 3.dp)
        )
      }
    }
  }
}
