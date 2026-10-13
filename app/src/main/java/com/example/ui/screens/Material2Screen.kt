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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CommodityItem
import com.example.model.LearningRepository
import com.example.model.TradeSimulationResult
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
fun Material2Screen(
  studentName: String,
  simulationResult: TradeSimulationResult?,
  onRunSimulation: (commodity: String, destination: String, monsoon: String) -> Unit,
  onBackClick: () -> Unit,
  onHomeClick: () -> Unit,
  onNextToMaterial3: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabTitles = listOf("Awal Masehi", "Kartu Komoditas", "Peran Pelabuhan", "Jadi Pedagang")

  var inspectedCommodity by remember { mutableStateOf<CommodityItem?>(null) }

  Scaffold(
    topBar = {
      MaritimeTopBar(
        title = "Materi 2 — Perdagangan Awal Masehi",
        subtitle = "Jalur Sutra Laut & Komoditas Rempah",
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
        contentColor = Terracotta,
        indicator = { tabPositions ->
          TabRowDefaults.SecondaryIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
            color = Terracotta
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
            modifier = Modifier.testTag("materi2_tab_$index")
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
          0 -> EarlyCenturyTimelineSection()
          1 -> CommoditiesCardsSection(onSelectCommodity = { inspectedCommodity = it })
          2 -> AncientPortRolesSection()
          3 -> TraderSimulationGameSection(
            simulationResult = simulationResult,
            onRunSimulation = onRunSimulation,
            onNext = onNextToMaterial3
          )
        }
      }
    }
  }

  inspectedCommodity?.let { item ->
    CommodityDetailDialog(commodity = item, onDismiss = { inspectedCommodity = null })
  }
}

@Composable
private fun EarlyCenturyTimelineSection() {
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
            Icon(Icons.Default.Timeline, contentDescription = null, tint = Terracotta)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "A. Perdagangan pada Awal Masehi",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = OceanNavy
          )
          Text(
            text = "Garis Waktu & Jaringan Pelayaran Maritim",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Pada abad-abad awal Masehi (abad ke-1 hingga ke-5 M), jalur sutra darat yang melintasi Asia Tengah sering terganggu oleh konflik suku-suku nomaden. Hal ini mendorong perpindahan rute ke 'Jalur Sutra Maritim' (Maritime Silk Road) yang melintasi kepulauan Nusantara.",
        fontSize = 13.sp,
        color = Color(0xFF2D3748),
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      TimelineEventItem(
        era = "Abad ke-1 M",
        title = "Kontak Niaga Awal dengan India",
        detail = "Pedagang India mulai melintasi Teluk Benggala mencari emas (Suvarnabhumi) dan rempah-rempah, memperkenalkan tradisi tulisan dan kosmologi ke Nusantara."
      )

      TimelineEventItem(
        era = "Abad ke-2 M",
        title = "Catatan Geografi Claudius Ptolemaeus",
        detail = "Ahli geografi Yunani-Mesir mencatat pulau 'Iabadiou' (Pulau Jelai/Jawa) dan bandar 'Barousai' (Barus, Sumatra) yang termasyhur akan kapur barus aromatiknya."
      )

      TimelineEventItem(
        era = "Abad ke-3 – 4 M",
        title = "Hubungan Diplomatik dengan Dinasti Tiongkok",
        detail = "Utusan kerajaan kuno Nusantara mulai tercatat mengirim upeti komoditas gaharu, sarang burung walet, dan cengkih ke istana Dinasti Jin dan Liu Song."
      )

      TimelineEventItem(
        era = "Abad ke-5 M",
        title = "Munculnya Kerajaan Aksara Pertama",
        detail = "Bukti tulisan pertama di Nusantara lahir: Prasasti Yupa di Kutai (Kaltim) dan Prasasti Ciaruteun di Tarumanegara (Jabar)."
      )

      Spacer(modifier = Modifier.height(10.dp))

      Surface(
        color = Color(0xFFFFF8E7),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldenAmber)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Info, contentDescription = null, tint = GoldenAmber)
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "Penting: Peta jalur pelayaran bukan jalur tunggal yang kaku, melainkan jejaring dinamis yang intensitasnya berubah sesuai cuaca, musim, dan kebijakan kerajaan.",
            fontSize = 11.sp,
            color = Color(0xFF6B4E16),
            lineHeight = 15.sp
          )
        }
      }
    }
  }
}

@Composable
private fun TimelineEventItem(era: String, title: String, detail: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Surface(
      shape = RoundedCornerShape(8.dp),
      color = Terracotta,
      modifier = Modifier.width(82.dp)
    ) {
      Text(
        text = era,
        color = Color.White,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        modifier = Modifier.padding(vertical = 4.dp)
      )
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = detail, fontSize = 12.sp, color = Color(0xFF4A5568), lineHeight = 16.sp)
    }
  }
}

@Composable
private fun CommoditiesCardsSection(
  onSelectCommodity: (CommodityItem) -> Unit
) {
  val commodities = LearningRepository.commodities

  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = Terracotta)
      Spacer(modifier = Modifier.width(8.dp))
      Column {
        Text(
          text = "B. Kartu Komoditas Perdagangan",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = OceanNavy
        )
        Text(
          text = "Klik kartu untuk melihat asal daerah, nilai, dan kegunaannya!",
          fontSize = 12.sp,
          color = Color.Gray
        )
      }
    }

    commodities.forEach { item ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onSelectCommodity(item) }
          .testTag("commodity_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(46.dp)
              .background(Terracotta.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Text(text = item.iconEmoji, fontSize = 24.sp)
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = item.name,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = OceanNavy
            )
            Text(
              text = "📍 Asal: ${item.origin}",
              fontSize = 11.sp,
              color = Terracotta,
              fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = item.description,
              fontSize = 12.sp,
              color = Color(0xFF555555),
              maxLines = 2
            )
          }

          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Rincian →", fontSize = 11.sp, color = OceanBlue, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
private fun AncientPortRolesSection() {
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
            Icon(Icons.Default.Anchor, contentDescription = null, tint = OceanBlue)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "D. Peran Pelabuhan Kuno",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = OceanNavy
          )
          Text(
            text = "Pusat Bongkar Muat & Pertemuan Budaya Dunia",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      PortRoleBullet(
        number = "1",
        title = "Pusat Bongkar Muat & Pergudangan Logistik",
        body = "Tempat kapal menurunkan muatan rempah, memperbaiki cadik/layar yang rusak, serta mengisi perbekalan air tawar dan beras untuk perjalanan ribuan mil."
      )

      PortRoleBullet(
        number = "2",
        title = "Pusat Pertemuan & Pasar Antarbangsa",
        body = "Pedagang dari India, Tiongkok, Arab, Champa, dan kepulauan Nusantara saling bertukar komoditas secara barter maupun menggunakan mata uang logam kuno."
      )

      PortRoleBullet(
        number = "3",
        title = "Ruang Pertukaran Informasi & Ilmu Pengetahuan",
        body = "Menjadi tempat bertukarnya peta navigasi samudra, teknologi perkapalan, pengetahuan astronomi bintang, serta penyalinan teks sastra dan ajaran filsafat."
      )

      PortRoleBullet(
        number = "4",
        title = "Pemicu Lahirnya Kota & Pusat Kerajaan",
        body = "Keuntungan dari pajak pelabuhan dan upeti kapal niaga membiayai pembentukan tentara maritim yang kuat, yang mendasari berdirinya kerajaan besar seperti Sriwijaya dan Majapahit."
      )
    }
  }
}

@Composable
private fun PortRoleBullet(number: String, title: String, body: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Surface(
      shape = CircleShape,
      color = OceanNavy,
      modifier = Modifier.size(24.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(text = number, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = OceanNavy)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = body, fontSize = 12.sp, color = Color(0xFF4A5568), lineHeight = 16.sp)
    }
  }
}

@Composable
private fun TraderSimulationGameSection(
  simulationResult: TradeSimulationResult?,
  onRunSimulation: (commodity: String, destination: String, monsoon: String) -> Unit,
  onNext: () -> Unit
) {
  var selectedCommodity by remember { mutableStateOf("Cengkih Maluku") }
  var selectedMonsoon by remember { mutableStateOf("Muson Barat") }
  var selectedDestination by remember { mutableStateOf("Jawa") }

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
            Icon(Icons.Default.DirectionsBoat, contentDescription = null, tint = OceanNavy)
          }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = "Game: Jadi Pedagang Nusantara",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = OceanNavy
          )
          Text(
            text = "Uji Kecakapan Pelaut & Negosiasi Niaga",
            fontSize = 12.sp,
            color = Color.Gray
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Text(
        text = "Langkah 1: Pilih Komoditas Muatan Kapalmu",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = OceanNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      val commodities = listOf("Cengkih Maluku", "Kapur Barus Sumatra", "Kayu Cendana")
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        commodities.forEach { item ->
          val isSel = selectedCommodity == item
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) OceanNavy else Color(0xFFF0F4F8),
            modifier = Modifier
              .clickable { selectedCommodity = item }
              .testTag("sim_commodity_$item")
          ) {
            Text(
              text = item,
              color = if (isSel) Color.White else OceanNavy,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Langkah 2: Pilih Waktu Pelayaran (Angin Muson)",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = OceanNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      val monsoons = listOf("Muson Barat", "Muson Timur")
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        monsoons.forEach { m ->
          val isSel = selectedMonsoon == m
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) Terracotta else Color(0xFFF0F4F8),
            modifier = Modifier
              .clickable { selectedMonsoon = m }
              .testTag("sim_monsoon_$m")
          ) {
            Text(
              text = m,
              color = if (isSel) Color.White else Terracotta,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Langkah 3: Pilih Pelabuhan Tujuan",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = OceanNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      val ports = listOf("Jawa", "Selat Malaka", "Maluku", "Tiongkok")
      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        ports.forEach { p ->
          val isSel = selectedDestination == p
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isSel) OceanBlue else Color(0xFFF0F4F8),
            modifier = Modifier
              .clickable { selectedDestination = p }
              .testTag("sim_port_$p")
          ) {
            Text(
              text = p,
              color = if (isSel) Color.White else OceanBlue,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = {
          onRunSimulation(selectedCommodity, selectedDestination, selectedMonsoon)
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("run_trade_simulation_button"),
        colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text("Berlayar Sekarang! ⛵", fontWeight = FontWeight.Bold, color = Color.White)
      }

      // Result of Trade Simulation
      simulationResult?.let { res ->
        Spacer(modifier = Modifier.height(14.dp))
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF4)),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, AncientGreen),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "🎉", fontSize = 20.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Laporan Ekspedisi Pelayaran",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = AncientGreen
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = res.outcomeStory,
              fontSize = 12.sp,
              color = Color(0xFF1B4332),
              lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color.White,
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(
                  text = "💡 Pelajaran Berharga Guru IPS:",
                  fontWeight = FontWeight.Bold,
                  fontSize = 11.sp,
                  color = OceanNavy
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = res.educationalInsight,
                  fontSize = 11.sp,
                  color = Color(0xFF4A5568),
                  lineHeight = 15.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Button(
        onClick = onNext,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("goto_materi3_button"),
        colors = ButtonDefaults.buttonColors(containerColor = HeritageGold),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text("Lanjut ke Materi 3: Kerajaan Hindu-Buddha →", fontWeight = FontWeight.Bold, color = OceanDeep)
      }
    }
  }
}

@Composable
private fun CommodityDetailDialog(
  commodity: CommodityItem,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    shape = RoundedCornerShape(20.dp),
    containerColor = Color.White,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = commodity.iconEmoji, fontSize = 28.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = commodity.name,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = OceanNavy
            )
          )
          Text(text = "📍 Asal: ${commodity.origin}", fontSize = 11.sp, color = Terracotta)
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
        Text(
          text = "Deskripsi:",
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          color = OceanNavy
        )
        Text(text = commodity.description, fontSize = 12.sp, color = Color(0xFF2D3748))

        Text(
          text = "Kegunaan & Nilai Ekonomis:",
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          color = OceanNavy
        )
        Text(text = commodity.utilityAndValue, fontSize = 12.sp, color = Color(0xFF2D3748))

        Text(
          text = "Jalur Distribusi Purba:",
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          color = OceanNavy
        )
        Text(text = commodity.tradeRoute, fontSize = 12.sp, color = Color(0xFF2D3748))
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = OceanNavy),
        shape = RoundedCornerShape(10.dp)
      ) {
        Text("Tutup Kartu", color = Color.White)
      }
    }
  )
}
