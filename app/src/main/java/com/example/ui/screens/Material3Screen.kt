package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TempleHindu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.model.HistoricalRelic
import com.example.model.KingdomInfo
import com.example.model.LearningRepository
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
fun Material3Screen(
  studentName: String,
  detectiveSolved: Set<String>,
  onSolveDetectiveRelic: (String) -> Unit,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  onNextToQuiz: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabTitles = listOf("Teori Masuk", "7 Kerajaan", "5 Aspek Hidup", "Detektif Sejarah")

  var inspectedKingdom by remember { mutableStateOf<KingdomInfo?>(null) }
  var inspectedRelic by remember { mutableStateOf<HistoricalRelic?>(null) }

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Materi 3 — Kerajaan Hindu-Buddha",
        subtitle = "Peradaban, Peninggalan & Detektif Sejarah",
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
        contentColor = HeritageGold,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = HeritageGold
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
            modifier = Modifier.testTag("materi3_tab_$index")
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
          0 -> TheoriesOfEntrySection()
          1 -> SevenKingdomsSection(onSelectKingdom = { inspectedKingdom = it })
          2 -> FiveAspectsOfLifeSection()
          3 -> HistoricalDetectiveSection(
            detectiveSolved = detectiveSolved,
            onInspectRelic = { inspectedRelic = it },
            onSolveRelic = onSolveDetectiveRelic,
            onNext = onNextToQuiz
          )
        }
      }
    }
  }

  inspectedKingdom?.let { kingdom ->
    KingdomDetailDialog(kingdom = kingdom, onDismiss = { inspectedKingdom = null })
  }

  inspectedRelic?.let { relic ->
    RelicDetailDialog(
      relic = relic,
      isSolved = detectiveSolved.contains(relic.id),
      onSolve = { onSolveDetectiveRelic(relic.id) },
      onDismiss = { inspectedRelic = null }
    )
  }
}

@Composable
private fun TheoriesOfEntrySection() {
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
            Icon(Icons.Default.TempleHindu, contentDescription = null, tint = OceanNavy)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "A. Proses Masuk & Berkembangnya Pengaruh",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Interpretasi Sejarah & Peran Aktif Lokal",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Masyarakat Nusantara tidak hanya pasif menerima pengaruh dari luar. Para leluhur secara selektif memilih unsur-unsur kebudayaan yang cocok, lalu memadukannya dengan kearifan lokal (Local Genius) sehingga tercipta corak kebudayaan khas Nusantara.",
        fontSize = 13.sp,
        color = Color(0xFF2D3748),
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "4 Teori Utama Masuknya Pengaruh Hindu-Buddha:",
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = OceanNavy
      )

      Spacer(modifier = Modifier.height(6.dp))

      TheoryCardItem(
        name = "1. Teori Brahmana (J.C. van Leur)",
        desc = "Pengaruh dibawa oleh kaum pendeta Brahmana yang diundang khusus oleh kepala suku Nusantara untuk memimpin upacara Vratyastoma (penobatan kasta) dan meruwat raja."
      )

      TheoryCardItem(
        name = "2. Teori Ksatria (C.C. Berg & F.D.K. Bosch)",
        desc = "Dibawa oleh prajurit dan bangsawan India yang kalah perang di negerinya, lalu mengarungi samudra dan mendirikan koloni baru di Nusantara."
      )

      TheoryCardItem(
        name = "3. Teori Waisya (N.J. Krom)",
        desc = "Dibawa oleh para pedagang (Waisya) India yang singgah berbulan-bulan di pelabuhan Nusantara karena menunggu angin muson, lalu menjalin pernikahan dengan warga lokal."
      )

      TheoryCardItem(
        name = "4. Teori Arus Balik (F.D.K. Bosch)",
        desc = "Menekankan peran aktif pemuda Nusantara yang berlayar ke India untuk menimba ilmu agama (seperti di Universitas Nalanda), kemudian pulang untuk mengajarkannya kepada bangsanya sendiri."
      )
    }
  }
}

@Composable
private fun TheoryCardItem(name: String, desc: String) {
  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFFBF8F1)),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2D9C5)),
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(text = name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = desc, fontSize = 11.sp, color = Color(0xFF4A5568), lineHeight = 15.sp)
    }
  }
}

@Composable
private fun SevenKingdomsSection(
  onSelectKingdom: (KingdomInfo) -> Unit
) {
  val kingdoms = LearningRepository.kingdoms

  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(Icons.Default.Museum, contentDescription = null, tint = HeritageGold)
      Spacer(modifier = Modifier.width(8.dp))
      Column {
        Text(
          text = "B. 7 Kerajaan Hindu-Buddha Utama",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = OceanNavy
        )
        Text(
          text = "Disusun secara kronologis • Klik untuk melihat profil lengkap",
          fontSize = 12.sp,
          color = Color.Gray
        )
      }
    }

    kingdoms.forEach { item ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onSelectKingdom(item) }
          .testTag("kingdom_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = OceanNavy,
            modifier = Modifier.size(44.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "👑",
                fontSize = 20.sp
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = item.name,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = OceanNavy
            )
            Text(
              text = "⏳ ${item.era} • 📍 ${item.location}",
              fontSize = 11.sp,
              color = Terracotta
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Tokoh: ${item.prominentFigures}",
              fontSize = 11.sp,
              color = Color.Gray,
              maxLines = 1
            )
          }

          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Buka →", fontSize = 11.sp, color = OceanBlue, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun FiveAspectsOfLifeSection() {
  val aspects = listOf(
    Pair("1. Bidang Politik", "Peralihan dari musyawarah kepala suku ke sistem monarki kerajaan mutlak dengan konsep Dewaraja (raja titisan dewa). Hukum dan hierarki birokrasi mulai tersusun rapi."),
    Pair("2. Bidang Ekonomi", "Perpaduan pertanian sawah beririgasi (agraris di pedalaman Jawa) dan pelabuhan niaga maritim (seperti di pesisir Sumatra dan Jawa Timur) yang menarik pedagang dunia."),
    Pair("3. Bidang Sosial", "Pengenalan stratifikasi kasta (Brahmana, Ksatria, Waisya, Sudra). Namun di Nusantara, kasta tidak kaku seperti di India asli karena norma gotong royong lokal tetap hidup."),
    Pair("4. Bidang Kebudayaan", "Seni arsitektur candi megah (perpaduan punden berundak lokal dengan stupa/candi India), wayang kulit, seni relief, serta sastra kakawin Jawa Kuno yang bernilai luhur."),
    Pair("5. Agama & Pendidikan", "Perkembangan asrama pertapaan (mandala) dan universitas biara Buddha tempat mempelajari kitab suci, tata bahasa Sanskerta, filsafat, logika, dan astronomi.")
  )

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
            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = AncientGreen)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "C. Perkembangan 5 Aspek Kehidupan",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Transformasi Struktur Masyarakat Nusantara",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      aspects.forEach { (title, desc) ->
        Column(modifier = Modifier.padding(vertical = 6.dp)) {
          Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OceanNavy)
          Spacer(modifier = Modifier.height(2.dp))
          Text(text = desc, fontSize = 12.sp, color = Color(0xFF4A5568), lineHeight = 16.sp)
        }
      }
    }
  }
}

@Composable
private fun HistoricalDetectiveSection(
  detectiveSolved: Set<String>,
  onInspectRelic: (HistoricalRelic) -> Unit,
  onSolveRelic: (String) -> Unit,
  onNext: () -> Unit
) {
  val relics = LearningRepository.historicalRelics

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
            Icon(Icons.Default.Search, contentDescription = null, tint = OceanBlue)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Aktivitas: Detektif Sejarah 🔍",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Pecahkan teka-teki peninggalan purba (${detectiveSolved.size}/${relics.size} Terpecahkan)",
            fontSize = 12.sp,
            color = AncientGreen,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Sejarawan bekerja seperti detektif: meneliti prasasti, candi, dan naskah kuno untuk merekonstruksi cara hidup masyarakat masa lampau. Klik peninggalan di bawah dan pecahkan petunjuknya!",
        fontSize = 12.sp,
        color = Color(0xFF4A5568),
        lineHeight = 17.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      relics.forEach { relic ->
        val isSolved = detectiveSolved.contains(relic.id)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onInspectRelic(relic) }
            .testTag("relic_item_${relic.id}"),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isSolved) Color(0xFFF0FFF4) else Color(0xFFFAFAFA)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSolved) AncientGreen else Color(0xFFDCE2E6)
          )
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .background(if (isSolved) AncientGreen else OceanBlue, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              if (isSolved) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
              } else {
                Text(text = "📜", fontSize = 16.sp)
              }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = relic.name,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = OceanNavy
              )
              Text(
                text = "${relic.category} • ${relic.kingdom}",
                fontSize = 11.sp,
                color = Color.Gray
              )
            }

            Text(
              text = if (isSolved) "Terpecahkan ✓" else "Analisis →",
              fontSize = 11.sp,
              color = if (isSolved) AncientGreen else OceanBlue,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onNext,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("goto_quiz_button"),
        colors = ButtonDefaults.buttonColors(containerColor = GoldenAmber),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text("Siap Uji Pemahaman: Menuju Kuis 15 Soal! 🏆", fontWeight = FontWeight.Bold, color = OceanDeep)
      }
    }
  }
}

@Composable
private fun KingdomDetailDialog(
  kingdom: KingdomInfo,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = Color.White,
    title = {
      Column {
        Text(
          text = kingdom.name,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = OceanNavy
          )
        )
        Text(text = "⏳ Periode: ${kingdom.era}", fontSize = 12.sp, color = Terracotta)
        Text(text = "📍 Wilayah: ${kingdom.location}", fontSize = 12.sp, color = Color.Gray)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        DetailSectionBlock("Tokoh Penting", kingdom.prominentFigures)
        DetailSectionBlock("Perekonomian & Perdagangan", kingdom.economyAndTrade)
        DetailSectionBlock("Peninggalan Sejarah", kingdom.relicsAndSites)
        DetailSectionBlock("Pengaruh Terhadap Kehidupan", kingdom.societalImpact)
        DetailSectionBlock("Catatan Kehati-hatian Sejarah", kingdom.historicalNote)
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
        shape = RoundedCornerShape(10.dp)
      ) {
        Text("Tutup", color = Color.White)
      }
    }
  )
}

@Composable
private fun RelicDetailDialog(
  relic: HistoricalRelic,
  isSolved: Boolean,
  onSolve: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = Color.White,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "🔍", fontSize = 24.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = relic.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = OceanNavy
            )
          )
          Text(text = "${relic.category} • ${relic.kingdom}", fontSize = 11.sp, color = Terracotta)
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        DetailSectionBlock("Petunjuk Detektif", relic.clueForDetective)
        DetailSectionBlock("Lokasi Penemuan", relic.discoveryLocation)
        DetailSectionBlock("Fungsi & Makna Peninggalan", relic.functionAndMeaning)
        DetailSectionBlock("Informasi yang Disimpulkan Sejarawan", relic.historicalDeduction)
      }
    },
    confirmButton = {
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (!isSolved) {
          Button(
            onClick = {
              onSolve()
              onDismiss()
            },
            colors = ButtonDefaults.buttonColors(containerColor = AncientGreen),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("solve_relic_button")
          ) {
            Text("Pecahkan Misteri! ✓", color = Color.White)
          }
        }
        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Tutup", color = Color.White)
        }
      }
    }
  )
}

@Composable
private fun DetailSectionBlock(label: String, content: String) {
  Column {
    Text(text = "$label:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = OceanNavy)
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = content, fontSize = 12.sp, color = Color(0xFF2D3748), lineHeight = 16.sp)
  }
}
