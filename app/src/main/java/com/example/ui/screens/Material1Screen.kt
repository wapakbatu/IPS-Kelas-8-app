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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.TempleHindu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun Material1Screen(
  studentName: String,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  onNextToMaterial2: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabTitles = listOf("Kondisi Geografis", "Faktor Pendorong", "5 Bentuk Interaksi", "Simulasi Jalur")

  // Interactive Route Simulation State
  var selectedRoute by remember { mutableStateOf("Selat Malaka") }
  var simulationSubmitted by remember { mutableStateOf(false) }

  // Reflection Toggle
  var showReflectionAnalysis by remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Materi 1 — Interaksi Masa Lalu",
        subtitle = "Kondisi Geografis & Dinamika Maritim",
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
      // Sub-Tabs for structured progressive reading
      TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.White,
        contentColor = OceanNavy,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = OceanNavy
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
            modifier = Modifier.testTag("materi1_tab_$index")
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
          0 -> {
            // A. Kondisi Geografis Nusantara
            GeographicConditionSection()
          }
          1 -> {
            // B. Faktor yang Mendorong Interaksi & Angin Muson
            DrivingFactorsSection()
          }
          2 -> {
            // C. 5 Bentuk Interaksi Bangsa Asing
            FormsOfInteractionSection()
          }
          3 -> {
            // D. Simulasi Interaktif & Refleksi
            RouteSimulationSection(
              selectedRoute = selectedRoute,
              onRouteSelected = {
                selectedRoute = it
                simulationSubmitted = true
              },
              simulationSubmitted = simulationSubmitted,
              showReflectionAnalysis = showReflectionAnalysis,
              onToggleReflection = { showReflectionAnalysis = !showReflectionAnalysis },
              onNext = onNextToMaterial2
            )
          }
        }
      }
    }
  }
}

@Composable
private fun GeographicConditionSection() {
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
            Icon(Icons.Default.Public, contentDescription = null, tint = OceanBlue)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "A. Kondisi Geografis Nusantara",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = OceanNavy
          )
          Text(
            text = "Kepulauan, Posisi Silang, & Jembatan Laut",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      InfoBulletItem(
        icon = "🏝️",
        title = "Wilayah Kepulauan Terbesar di Dunia",
        body = "Nusantara terdiri dari ribuan pulau yang dikelilingi laut dangkal (Paparan Sunda dan Paparan Sahul) serta laut dalam. Hal ini melahirkan kebudayaan maritim yang sangat kaya."
      )

      InfoBulletItem(
        icon = "🌐",
        title = "Posisi Silang Strategis Dunia",
        body = "Terletak di persimpangan dua samudera (Hindia dan Pasifik) serta dua benua akbar (Asia dan Australia). Jalur ini merupakan lintasan utama pelayaran pedagang India, Arab, Persia, dan Tiongkok."
      )

      InfoBulletItem(
        icon = "☀️",
        title = "Iklim Tropis & Keanekaragaman Hayati",
        body = "Mendapat sinar matahari dan curah hujan sepanjang tahun, tanah vulkanik Nusantara sangat subur dan menghasilkan tanaman endemik seperti cengkih, pala, lada, dan kayu cendana yang bernilai tinggi."
      )

      InfoBulletItem(
        icon = "⛵",
        title = "Laut Sebagai Jembatan Penghubung",
        body = "Bagi nenek moyang bangsa Indonesia, laut bukanlah pemisah pulau, melainkan jalan bebas hambatan yang menghubungkan perdagangan, perkawinan, bahasa, dan pertukaran gagasan."
      )
    }
  }
}

@Composable
private fun DrivingFactorsSection() {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = CircleShape,
          color = GoldenAmber.copy(alpha = 0.15f),
          modifier = Modifier.size(40.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Air, contentDescription = null, tint = GoldenAmber)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "B. Faktor Pendorong Interaksi Asing",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = OceanNavy
          )
          Text(
            text = "Mengapa Bangsa Asing Berbondong Datang?",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      InfoBulletItem(
        icon = "✨",
        title = "Kelangkaan Komoditas Endemik",
        body = "Bangsa luar sangat membutuhkan rempah-rempah Maluku dan hasil hutan Sumatra untuk pengobatan, pengawetan makanan musim dingin, ritual keagamaan, dan wewangian."
      )

      InfoBulletItem(
        icon = "🧭",
        title = "Siklus Angin Muson (Monsoon Winds)",
        body = "Pelaut mengandalkan Angin Muson Barat (Oktober–April) untuk berlayar dari barat/utara menuju Nusantara, dan Angin Muson Timur (April–Oktober) untuk kembali. Karena jeda 6 bulan, para pedagang asing wajib menetap berbulan-bulan di pelabuhan Nusantara."
      )

      InfoBulletItem(
        icon = "🛶",
        title = "Teknologi Perahu Cadik & Pelayaran Unggul",
        body = "Masyarakat Nusantara telah memiliki perahu bercadik yang stabil mengarungi ombak besar dan mampu membaca rasi bintang penunjuk arah di laut lepas."
      )

      // Interactive Monsoon Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F7FF)),
        border = androidx.compose.foundation.BorderStroke(1.dp, OceanBlue.copy(alpha = 0.4f)),
        modifier = Modifier.padding(top = 8.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(
            text = "💡 Mengapa Pedagang Asing Menetap Sementara?",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = OceanNavy
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Karena harus menunggu angin muson berbalik arah, pedagang India, Tiongkok, dan Arab menyewa rumah di pesisir. Di sinilah terjalin komunikasi, pernikahan, dan penyebaran agama secara damai!",
            fontSize = 12.sp,
            color = Color(0xFF334E68),
            lineHeight = 16.sp
          )
        }
      }
    }
  }
}

@Composable
private fun FormsOfInteractionSection() {
  val forms = listOf(
    InteractionFormData(
      icon = Icons.Default.LocalAtm,
      title = "1. Interaksi Ekonomi",
      accentColor = OceanBlue,
      desc = "Terjadi pertukaran komoditas rempah, kapur barus, dan emas Nusantara dengan barang impor keramik Tiongkok, kain tenun India, dan manik-manik kaca."
    ),
    InteractionFormData(
      icon = Icons.Default.Diversity3,
      title = "2. Interaksi Sosial",
      accentColor = AncientGreen,
      desc = "Pergaulan erat antarpedagang pendatang dengan penduduk lokal yang melahirkan perkawinan campur, terbentuknya kampung pecinan dan koja (pedagang muslim/India)."
    ),
    InteractionFormData(
      icon = Icons.Default.Language,
      title = "3. Interaksi Budaya",
      accentColor = HeritageGold,
      desc = "Penyerapan kosa kata Sanskerta, pengenalan huruf Pallawa, pertukaran motif seni ukir candi, dan penggabungan tradisi lokal dengan pengaruh luar (akulturasi)."
    ),
    InteractionFormData(
      icon = Icons.Default.TempleHindu,
      title = "4. Interaksi Keagamaan",
      accentColor = Terracotta,
      desc = "Penyebaran dan penerimaan ajaran agama Hindu dan Buddha yang disesuaikan secara harmonis dengan kepercayaan asli pemujaan roh leluhur (animisme/dinamisme)."
    ),
    InteractionFormData(
      icon = Icons.Default.AccountBalance,
      title = "5. Interaksi Politik",
      accentColor = OceanNavy,
      desc = "Perubahan struktur kepemimpinan dari kepala suku (primus inter pares) menjadi sistem kerajaan monarki dengan gelar Raja dan legitimasi dewa (Devaraja)."
    )
  )

  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(
      text = "C. 5 Bentuk Interaksi Antarbangsa",
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = OceanNavy
    )

    forms.forEach { item ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.Top
        ) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = item.accentColor.copy(alpha = 0.15f),
            modifier = Modifier.size(38.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = item.accentColor,
                modifier = Modifier.size(20.dp)
              )
            }
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = item.title,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = OceanNavy
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = item.desc,
              fontSize = 12.sp,
              color = Color(0xFF4A5568),
              lineHeight = 17.sp
            )
          }
        }
      }
    }
  }
}

@Composable
private fun RouteSimulationSection(
  selectedRoute: String,
  onRouteSelected: (String) -> Unit,
  simulationSubmitted: Boolean,
  showReflectionAnalysis: Boolean,
  onToggleReflection: () -> Unit,
  onNext: () -> Unit
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
            Icon(Icons.Default.Navigation, contentDescription = null, tint = OceanBlue)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Aktivitas: Pilih Jalur Pelayaranmu",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Simulasi Interaksi Maritim Siswa",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Pilih salah satu jalur niaga kuno yang ingin kamu arungi:",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF2D3748)
      )

      Spacer(modifier = Modifier.height(10.dp))

      val routes = listOf(
        Pair("Selat Malaka", "Jalur Utama: Menghubungkan India, Selat Malaka, Laut Natuna, dan Tiongkok."),
        Pair("Samudera Hindia - Selat Sunda", "Jalur Selatan: Melintasi pesisir barat Sumatra (Barus) menuju Selat Sunda dan Laut Jawa."),
        Pair("Jalur Kepulauan Rempah", "Jalur Timur: Dari Laut Jawa menuju Sulawesi Selatan dan Kepulauan Maluku (Banda & Ternate).")
      )

      routes.forEach { (name, desc) ->
        val isChosen = selectedRoute == name
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isChosen) Color(0xFFE8F4FD) else Color(0xFFFAFAFA)
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (isChosen) OceanBlue else Color(0xFFE0E0E0)
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onRouteSelected(name) }
            .testTag("route_option_$name")
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(20.dp)
                .background(if (isChosen) OceanBlue else Color.Transparent, CircleShape)
                .border(2.dp, if (isChosen) OceanBlue else Color.Gray, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              if (isChosen) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isChosen) OceanNavy else Color.DarkGray
              )
              Text(
                text = desc,
                fontSize = 11.sp,
                color = Color(0xFF555555),
                lineHeight = 15.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Simulation Result Card
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF6F8FA),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD0D7DE)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Text(
            text = "Hasil Penjelajahan Jalur: $selectedRoute",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = OceanNavy
          )
          Spacer(modifier = Modifier.height(4.dp))
          val outcomeText = when (selectedRoute) {
            "Selat Malaka" -> "Di jalur ini kamu bertemu ratusan kapal dari dinasti Tiongkok dan kerajaan India! Terjadi pertukaran kain sutra dengan rempah dan penyerapan kosakata bahasa Sanskerta."
            "Samudera Hindia - Selat Sunda" -> "Kamu singgah di Pelabuhan Barus. Kapur barus bermutu tinggi dibeli oleh pedagang Arab dan Mesir untuk bahan balsem wangi dan ritual kerajaan kuno."
            else -> "Kamu tiba di pusat kepulauan cengkih dan pala dunia di Maluku! Masyarakat lokal ramah menyambutmu dengan barter perahu cadik dan hasil panen kebun rempah."
          }
          Text(
            text = outcomeText,
            fontSize = 12.sp,
            color = Color(0xFF24292F),
            lineHeight = 17.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Reflection Question Card
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEA)),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldenAmber)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = GoldenAmber)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Pertanyaan Refleksi Materi 1",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = OceanNavy
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "“Mengapa kondisi geografis Nusantara membuat wilayah ini sangat penting dalam jaringan perdagangan masa lalu?”",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF4A5568)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = onToggleReflection,
            colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.testTag("toggle_materi1_reflection")
          ) {
            Text(
              text = if (showReflectionAnalysis) "Tutup Kunci Analisis" else "Buka Kunci Analisis Sejarah",
              fontSize = 11.sp,
              color = Color.White
            )
          }

          AnimatedVisibility(visible = showReflectionAnalysis) {
            Column(modifier = Modifier.padding(top = 8.dp)) {
              Text(
                text = "Kunci Analisis Guru IPS:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = AncientGreen
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Karena Nusantara terletak di persilangan samudera yang menghubungkan dua kekuatan ekonomi terbesar dunia kuno (India dan Tiongkok), memiliki selat sempit yang wajib dilalui, serta merupakan satu-satunya produsen rempah cengkih dan pala di dunia.",
                fontSize = 12.sp,
                color = Color(0xFF2D3748),
                lineHeight = 16.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Navigate to Next Material Button
      Button(
        onClick = onNext,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("goto_materi2_button"),
        colors = ButtonDefaults.buttonColors(containerColor = Terracotta),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text("Lanjut ke Materi 2: Perdagangan Awal Masehi →", fontWeight = FontWeight.Bold, color = Color.White)
      }
    }
  }
}

@Composable
private fun InfoBulletItem(icon: String, title: String, body: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Text(text = icon, fontSize = 20.sp)
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = body, fontSize = 12.sp, color = Color(0xFF4A5568), lineHeight = 17.sp)
    }
  }
}

private data class InteractionFormData(
  val icon: ImageVector,
  val title: String,
  val accentColor: Color,
  val desc: String
)
