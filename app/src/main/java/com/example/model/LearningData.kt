package com.example.model

data class MapPoint(
  val id: String,
  val name: String,
  val region: String,
  val category: String, // "Jalur Laut", "Pelabuhan Kuno", "Pusat Kerajaan", "Pusat Rempah"
  val latitudeRatio: Float, // relative to map canvas (0.0 to 1.0)
  val longitudeRatio: Float, // relative to map canvas (0.0 to 1.0)
  val historicalSignificance: String,
  val commoditiesOrRelics: String,
  val sparkQuestion: String, // Pertanyaan pemantik
  val sparkInsight: String
)

data class CommodityItem(
  val id: String,
  val name: String,
  val origin: String,
  val description: String,
  val utilityAndValue: String,
  val tradeRoute: String,
  val iconEmoji: String
)

data class KingdomInfo(
  val id: String,
  val name: String,
  val era: String,
  val location: String,
  val prominentFigures: String,
  val economyAndTrade: String,
  val relicsAndSites: String,
  val societalImpact: String,
  val historicalNote: String
)

data class HistoricalRelic(
  val id: String,
  val name: String,
  val kingdom: String,
  val category: String, // "Prasasti", "Candi", "Karya Sastra", "Arca"
  val discoveryLocation: String,
  val functionAndMeaning: String,
  val historicalDeduction: String, // Informasi yang disimpulkan sejarawan
  val clueForDetective: String
)

data class TradeSimulationResult(
  val commodityChosen: String,
  val targetPort: String,
  val monsoonChosen: String,
  val isSuccessful: Boolean,
  val profitMultiplier: Float,
  val outcomeStory: String,
  val educationalInsight: String
)

enum class QuestionType {
  MULTIPLE_CHOICE,
  TRUE_FALSE,
  HISTORICAL_ANALYSIS,
  CAUSE_EFFECT
}

data class QuizQuestion(
  val id: Int,
  val materialSection: String, // "Materi 1", "Materi 2", "Materi 3"
  val type: QuestionType,
  val question: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val explanation: String
)

data class StudentReflection(
  val newKnowledge: String = "",
  val culturalChangeInsight: String = "",
  val preservationReason: String = "",
  val diversityAppreciation: String = ""
)

object LearningRepository {

  val mapPoints = listOf(
    MapPoint(
      id = "malaka",
      name = "Selat Malaka",
      region = "Sumatra & Semenanjung Melayu",
      category = "Jalur Laut & Gerbang Niaga",
      latitudeRatio = 0.35f,
      longitudeRatio = 0.18f,
      historicalSignificance = "Selat Malaka adalah urat nadi perdagangan maritim dunia purba. Menjadi jalur terpendek penghubung antara India (Asia Selatan) dan Tiongkok (Asia Timur). Kapal-kapal dagang wajib melintasi selat ini dan menunggu pergantian angin muson.",
      commoditiesOrRelics = "Tempat persinggahan kapal muatan rempah, sutra, keramik, dan wewangian.",
      sparkQuestion = "Mengapa Selat Malaka sering diperebutkan oleh kerajaan-kerajaan besar di masa lalu?",
      sparkInsight = "Penguasa Selat Malaka dapat menarik bea cukai (upeti), menjamin keamanan dari perompak, dan memonopoli arus keluar-masuk komoditas internasional."
    ),
    MapPoint(
      id = "barus",
      name = "Pelabuhan Barus (Fansur)",
      region = "Pesisir Barat Sumatra Utara",
      category = "Pelabuhan Kuno",
      latitudeRatio = 0.28f,
      longitudeRatio = 0.12f,
      historicalSignificance = "Salah satu bandar pelabuhan tertua di Nusantara yang tercatat dalam manuskrip Yunani kuno oleh Claudius Ptolemaeus (abad ke-2 M) dan catatan pengembara Timur Tengah.",
      commoditiesOrRelics = "Kapur Barus (kamfer) kualitas tertinggi di dunia dan getah kemenyan untuk bahan obat serta ritual.",
      sparkQuestion = "Apa yang menyebabkan pelabuhan sekecil Barus dikenal hingga ke Mesir dan Timur Tengah pada awal Masehi?",
      sparkInsight = "Kapur Barus memiliki aroma wangi alami yang langka dan sangat bernilai tinggi sebagai bahan pembalseman mumi serta pengobatan tradisional bangsawan kuno."
    ),
    MapPoint(
      id = "sriwijaya",
      name = "Kedatuan Sriwijaya",
      region = "Palembang / Sungai Musi (Sumatra Selatan)",
      category = "Pusat Kerajaan Maritim",
      latitudeRatio = 0.52f,
      longitudeRatio = 0.24f,
      historicalSignificance = "Kerajaan maritim adidaya bercorak Buddha abad ke-7 hingga ke-12 M. Memiliki armada laut tangguh yang mengontrol Selat Malaka dan Selat Sunda serta menjadi pusat studi agama Buddha internasional tempat biksu I-Tsing belajar bahasa Sanskerta.",
      commoditiesOrRelics = "Prasasti Kedukan Bukit, Prasasti Telaga Batu, Candi Muara Takus, komoditas emas, gading, dan timah.",
      sparkQuestion = "Bagaimana Sriwijaya memanfaatkan kondisi geografisnya untuk menjadi pusat pendidikan Buddha internasional?",
      sparkInsight = "Lokasinya yang menjadi transit wajib kapal dagang antara India dan Tiongkok membuat para biksu asing singgah berminggu-minggu hingga berbulan-bulan sembari menunggu angin musim."
    ),
    MapPoint(
      id = "tarumanegara",
      name = "Tarumanegara & Sunda Kelapa",
      region = "Jawa Barat (Bogor, Bekasi, Jakarta)",
      category = "Pusat Kerajaan & Pelabuhan",
      latitudeRatio = 0.65f,
      longitudeRatio = 0.32f,
      historicalSignificance = "Kerajaan Hindu tertua di Pulau Jawa (abad ke-5 M) di bawah pimpinan Raja Purnawarman. Terkenal dengan pembangunan saluran air (Sungai Gomati dan Chandrabhaga) untuk irigasi sawah dan mencegah banjir.",
      commoditiesOrRelics = "Prasasti Ciaruteun (jejak telapak kaki Raja Purnawarman), Prasasti Tugu, Prasasti Kebon Kopi.",
      sparkQuestion = "Mengapa Raja Purnawarman memahat jejak telapak kakinya pada batu prasasti?",
      sparkInsight = "Jejak kaki disamakan dengan jejak Dewa Wisnu sebagai simbol keagungan raja, penaklukan wilayah kekuasaan, dan pengayom rakyat."
    ),
    MapPoint(
      id = "mataram",
      name = "Mataram Kuno (Medang)",
      region = "Jawa Tengah & Jawa Timur",
      category = "Pusat Kerajaan Agraris-Maritim",
      latitudeRatio = 0.68f,
      longitudeRatio = 0.44f,
      historicalSignificance = "Berkembang abad ke-8 hingga ke-10 M di tanah vulkanik subur Jawa Tengah. Diperintah oleh Dinasti Sanjaya (Hindu Siwa) dan Dinasti Syailendra (Buddha Mahayana) yang hidup berdampingan secara toleran.",
      commoditiesOrRelics = "Mahakarya dunia Candi Borobudur, Candi Prambanan, Kalasan, Sewu, dan Prasasti Canggal.",
      sparkQuestion = "Apa bukti sejarah adanya toleransi dan perpaduan budaya di Mataram Kuno?",
      sparkInsight = "Pernikahan politik antara Rakai Pikatan (Hindu) dan Pramodhawardhani (Buddha) melahirkan pembangunan candi-candi megah yang berdampingan harmonis."
    ),
    MapPoint(
      id = "kutai",
      name = "Kutai Martadipura",
      region = "Sungai Mahakam, Kalimantan Timur",
      category = "Pusat Kerajaan Tertua",
      latitudeRatio = 0.42f,
      longitudeRatio = 0.58f,
      historicalSignificance = "Kerajaan bercorak Hindu tertua di kepulauan Nusantara (± 400 M). Dipimpin oleh Raja Mulawarman, cucu Kudungga. Terletak di tepi Sungai Mahakam yang menjadi jalur transportasi air utama Kalimantan.",
      commoditiesOrRelics = "7 buah Prasasti Yupa bertuliskan huruf Pallawa dan bahasa Sanskerta.",
      sparkQuestion = "Informasi penting apa yang tercatat dalam Prasasti Yupa bagi sejarah Indonesia?",
      sparkInsight = "Menandai berakhirnya zaman praaksara di Indonesia dan dimulainya zaman aksara (sejarah tertulis), serta mencatat sedekah 20.000 ekor sapi kepada kaum Brahmana."
    ),
    MapPoint(
      id = "majapahit",
      name = "Majapahit & Pelabuhan Tuban",
      region = "Trowulan & Pesisir Jawa Timur",
      category = "Pusat Kemaharajaan Maritim",
      latitudeRatio = 0.70f,
      longitudeRatio = 0.52f,
      historicalSignificance = "Kemaharajaan terbesar di Nusantara (1293–1527 M). Mencapai masa kejayaan di bawah Raja Hayam Wuruk dan Mahapatih Gajah Mada dengan Sumpah Palapa untuk menyatukan kepulauan Nusantara.",
      commoditiesOrRelics = "Kitab Negarakretagama, Kitab Sutasoma (asal semboyan 'Bhinneka Tunggal Ika'), Candi Penataran, Candi Bajang Ratu.",
      sparkQuestion = "Mengapa semboyan negara kita diambil dari karya sastra zaman Majapahit?",
      sparkInsight = "Konsep 'Bhinneka Tunggal Ika tan Hana Dharma Mangrwa' menggambarkan masyarakat Majapahit yang majemuk (pemeluk Siwa dan Buddha) namun tetap rukun bersatu dalam harmoni."
    ),
    MapPoint(
      id = "maluku",
      name = "Kepulauan Maluku (Banda, Ternate, Tidore)",
      region = "Kepulauan Maluku",
      category = "Pusat Rempah-Rempah Dunia",
      latitudeRatio = 0.48f,
      longitudeRatio = 0.82f,
      historicalSignificance = "Dikenal di seluruh dunia kuno sebagai 'The Spice Islands'. Satu-satunya wilayah di muka bumi pada masa itu yang menghasilkan cengkih dan pala secara alami.",
      commoditiesOrRelics = "Cengkih (Syzygium aromaticum), Pala (Myristica fragrans), dan fuli (bunga pala).",
      sparkQuestion = "Mengapa rempah-rempah Maluku bernilai seharga emas di pasar Eropa dan Timur Tengah?",
      sparkInsight = "Rempah-rempah sangat dibutuhkan untuk mengawetkan daging saat musim dingin, obat-obatan, dan wewangian, sementara rute distribusinya sangat jauh dan berisiko tinggi."
    ),
    MapPoint(
      id = "selat_sunda",
      name = "Selat Sunda",
      region = "Antara Sumatra dan Jawa",
      category = "Jalur Pelayaran Strategis",
      latitudeRatio = 0.62f,
      longitudeRatio = 0.26f,
      historicalSignificance = "Pintu gerbang laut selatan yang menghubungkan Samudera Hindia dengan Laut Jawa. Menjadi rute alternatif kapal dagang yang menghindari Selat Malaka.",
      commoditiesOrRelics = "Lada Sumatra Selatan, garam Jawa, dan hasil bumi.",
      sparkQuestion = "Kapan para pelaut lebih memilih Selat Sunda dibanding Selat Malaka?",
      sparkInsight = "Ketika cuaca di Selat Malaka buruk, terjadi konflik perang antarbandar di utara, atau kapal datang langsung melintasi Samudera Hindia dari barat daya."
    )
  )

  val commodities = listOf(
    CommodityItem(
      id = "cengkih",
      name = "Cengkih Nusantara",
      origin = "Kepulauan Maluku (Ternate, Tidore, Motir, Makian, Bacan)",
      description = "Kuncup bunga kering beraroma harum pedas yang tumbuh di kepulauan vulkanik Maluku Utara.",
      utilityAndValue = "Bahan pengawet alami makanan, obat antiseptik, pereda sakit gigi, dan dupa ritual keagamaan.",
      tradeRoute = "Diangkut perahu kora-kora ke pelabuhan Jawa dan Sumatra, lalu diteruskan ke India, Arab, dan Eropa.",
      iconEmoji = "🌿"
    ),
    CommodityItem(
      id = "pala",
      name = "Pala & Fuli (Bunga Pala)",
      origin = "Kepulauan Banda, Maluku Selatan",
      description = "Biji pohon Myristica fragrans bersama selubung merahnya (fuli) yang eksotis dan aromatik.",
      utilityAndValue = "Penyedap masakan bangsawan, bumbu daging, penghangat tubuh, serta komoditas paling mahal per gram.",
      tradeRoute = "Jalur Rempah Timur menuju Tuban dan Gresik sebelum didistribusikan ke Tiongkok dan Romawi.",
      iconEmoji = "🌰"
    ),
    CommodityItem(
      id = "kapur_barus",
      name = "Kapur Barus (Kamfer)",
      origin = "Hutan pedalaman Barus, Sumatra Barat/Utara",
      description = "Kristal bening alami dari getah pohon Dryobalanops aromatica di pedalaman hutan tropis Sumatra.",
      utilityAndValue = "Wewangian kelas bangsawan, balsem mumifikasi Mesir kuno, dan pengobatan asma serta demam.",
      tradeRoute = "Pelabuhan Barus langsung melintasi Samudera Hindia ke India, Teluk Persia, dan Laut Merah.",
      iconEmoji = "💎"
    ),
    CommodityItem(
      id = "cendana",
      name = "Kayu Cendana & Gaharu",
      origin = "Nusa Tenggara (Timor, Sumba) dan Kalimantan",
      description = "Kayu aromatik bernilai tinggi yang mengeluarkan wangi dupa alami saat dibakar.",
      utilityAndValue = "Dupa persembahan kuil di India dan Tiongkok, minyak wangi kosmetik, dan bahan ukiran berharga.",
      tradeRoute = "Jalur laut kepulauan Nusa Tenggara menuju pusat pelabuhan Jawa Timur lalu diekspor.",
      iconEmoji = "🪵"
    ),
    CommodityItem(
      id = "keramik_sutra",
      name = "Keramik & Kain Sutra Asing",
      origin = "Tiongkok (Dinasti Han, Tang, Song)",
      description = "Barang mewah impor dari Tiongkok berupa piring porselen, mangkuk seladon, dan kain sutra halus.",
      utilityAndValue = "Simbol status sosial para raja dan bangsawan Nusantara, serta bekal kubur upacara.",
      tradeRoute = "Jalur Sutra Maritim dari Guangzhou dan Quanzhou menuju pelabuhan Sriwijaya dan Jawa.",
      iconEmoji = "🏺"
    ),
    CommodityItem(
      id = "manik_patola",
      name = "Manik-Manik Kaca & Kain Tenun India",
      origin = "India (Gujarat, Pantai Koromandel)",
      description = "Manik-manik batu karnelian, manik kaca warna-warni, serta kain katun dan patola sutra bermotif.",
      utilityAndValue = "Alat barter berharga tinggi, pakaian upacara adat kepala suku, dan perhiasan penanda kehormatan.",
      tradeRoute = "Melintasi Teluk Benggala menuju Selat Malaka dan pelabuhan pesisir barat Sumatra.",
      iconEmoji = "📿"
    )
  )

  val kingdoms = listOf(
    KingdomInfo(
      id = "kutai",
      name = "Kerajaan Kutai Martadipura",
      era = "± Abad ke-4 Masehi (400 M)",
      location = "Muara Kaman, Sungai Mahakam, Kalimantan Timur",
      prominentFigures = "Kudungga (Kepala suku lokal), Asmawarman (Pendiri dinasti), Mulawarman (Raja kejayaan)",
      economyAndTrade = "Perekonomian berbasis peternakan sapi, pertanian, dan perdagangan air melalui aliran Sungai Mahakam.",
      relicsAndSites = "7 tiang batu tertulis (Prasasti Yupa) berhuruf Pallawa dan berbahasa Sanskerta.",
      societalImpact = "Menandai peralihan peradaban Nusantara dari masa praaksara ke masa sejarah tertulis; masuknya pengaruh kasta dan ritual kurban Hindu.",
      historicalNote = "Kudungga merupakan nama asli Nusantara, sedangkan nama cucunya 'Mulawarman' memakai akhiran 'warman' khas bangsawan India Selatan."
    ),
    KingdomInfo(
      id = "tarumanegara",
      name = "Kerajaan Tarumanegara",
      era = "Abad ke-5 Masehi (± 450 M)",
      location = "Lembah Sungai Citarum, Jawa Barat (Bogor, Bekasi, Jakarta)",
      prominentFigures = "Raja Purnawarman (Raja yang gagah berani dan memperhatikan kemakmuran rakyat)",
      economyAndTrade = "Pertanian sawah intensif dengan saluran irigasi buatan serta perdagangan maritim melalui pesisir utara Sunda.",
      relicsAndSites = "Prasasti Ciaruteun, Prasasti Tugu, Prasasti Kebon Kopi, Prasasti Jambu, Prasasti Pasir Awi, Prasasti Muara Cianten, Prasasti Cidanghiang.",
      societalImpact = "Keahlian teknik sipil hidro-teknologi awal di Nusantara; pembuatan saluran air sepanjang 6.122 busur tombak (± 11 km) untuk irigasi.",
      historicalNote = "Prasasti Tugu mencatat penggalian Sungai Gomati dan Chandrabhaga yang diselesaikan dalam waktu 21 hari disertai sedekah 1.000 ekor sapi."
    ),
    KingdomInfo(
      id = "sriwijaya",
      name = "Kedatuan Sriwijaya",
      era = "Abad ke-7 sampai ke-12 Masehi",
      location = "Palembang / Sungai Musi, Sumatra Selatan hingga Selat Malaka",
      prominentFigures = "Dapunta Hyang Sri Jayanasa, Balaputradewa, Sanggrama Wijayatunggawarman",
      economyAndTrade = "Negara maritim murni berbasis armada laut kuat; pengontrol monopoli perdagangan Selat Malaka, bea cukai pelabuhan, timah, gading, dan kapur barus.",
      relicsAndSites = "Prasasti Kedukan Bukit, Prasasti Telaga Batu, Prasasti Kota Kapur, Candi Muara Takus, Prasasti Nalanda (di India).",
      societalImpact = "Menjadi pusat pengajaran bahasa Sanskerta dan teologi Buddha Mahayana terbesar di Asia Tenggara sebelum murid melanjutkannya ke India.",
      historicalNote = "Biksu Tiongkok I-Tsing pada tahun 671 M menyarankan para biksu yang ingin ke India untuk tinggal 1–2 tahun di Sriwijaya guna memperdalam tata bahasa Sanskerta."
    ),
    KingdomInfo(
      id = "mataram",
      name = "Kerajaan Mataram Kuno (Medang)",
      era = "Abad ke-8 sampai ke-10 Masehi",
      location = "Bumi Mataram (Jawa Tengah) lalu pindah ke Jawa Timur (Lembah Sungai Brantas)",
      prominentFigures = "Raja Sanjaya, Rakai Panangkaran, Rakai Pikatan, Pramodhawardhani, Mpu Sindok",
      economyAndTrade = "Perpaduan agraris (lumbung padi di pedalaman) dan pelabuhan dagang sungai (Bengawan Solo dan Sungai Brantas).",
      relicsAndSites = "Candi Borobudur, Candi Prambanan, Candi Kalasan, Candi Mendut, Candi Plaosan, Prasasti Canggal, Prasasti Mantyasih.",
      societalImpact = "Puncak kemegahan seni arsitektur candi batu andesit; bukti kerukunan hidup antara pemeluk agama Hindu Siwa dan Buddha Mahayana.",
      historicalNote = "Mpu Sindok memindahkan pusat kerajaan dari Jawa Tengah ke Jawa Timur pada 929 M diduga akibat letusan Gunung Merapi dahsyat dan ancaman serangan Sriwijaya."
    ),
    KingdomInfo(
      id = "kediri",
      name = "Kerajaan Kediri (Panjalu)",
      era = "Abad ke-11 sampai ke-12 Masehi (1042–1222 M)",
      location = "Daha, Lembah Sungai Brantas, Jawa Timur",
      prominentFigures = "Raja Airlangga (peletak dasar), Raja Bameswara, Raja Jayabaya, Kertajaya",
      economyAndTrade = "Pertanian maju di tepi Brantas dan perdagangan emas, perak, kayu cendana, serta beras ke kepulauan timur.",
      relicsAndSites = "Prasasti Hantang, Kitab Bharatayuddha (Mpu Sedah & Mpu Panuluh), Kitab Smaradahana (Mpu Dharmaja), Arca Airlangga Wisnu.",
      societalImpact = "Zaman keemasan sastra Jawa Kuno (Kakawin); ramalan Jayabaya yang melegenda dalam kebudayaan masyarakat Jawa.",
      historicalNote = "Karya sastra Kakawin Bharatayuddha digubah pada masa Raja Jayabaya untuk mengobarkan semangat persatuan setelah perang saudara Panjalu dan Janggala."
    ),
    KingdomInfo(
      id = "singhasari",
      name = "Kerajaan Singhasari",
      era = "Abad ke-13 Masehi (1222–1292 M)",
      location = "Tumapel (Malang), Jawa Timur",
      prominentFigures = "Ken Arok (Pendiri dinasti Rajasa), Anusapati, Tohjaya, Ranggawuni, Kertanegara",
      economyAndTrade = "Pertanian dan perdagangan sungai, memperluas wibawa maritim keluar Pulau Jawa menandingi hegemoni Sriwijaya.",
      relicsAndSites = "Candi Singhasari, Candi Kidal, Candi Jago, Arca Prajnaparamita (perwujudan Ken Dedes), Prasasti Mula Malurung.",
      societalImpact = "Gagasan penyatuan Nusantara awal melalui politik luar negeri Ekspedisi Pamalayu (1275 M) untuk membendung ekspansi Kaisar Kubilai Khan dari Mongol.",
      historicalNote = "Raja Kertanegara menolak tunduk pada utusan Kaisar Mongol Meng Chi dan melukai telinganya, yang memicu pengiriman armada tentara Tartar ke Jawa."
    ),
    KingdomInfo(
      id = "majapahit",
      name = "Kerajaan Majapahit",
      era = "Abad ke-13 sampai ke-16 Masehi (1293–1527 M)",
      location = "Trowulan (Mojokerto), Jawa Timur",
      prominentFigures = "Raden Wijaya, Tribhuwana Tunggadewi, Hayam Wuruk, Mahapatih Gajah Mada",
      economyAndTrade = "Negara agraris-maritim terintegrasi; pelabuhan internasional Tuban, Gresik, dan Canggu memfasilitasi pedagang dari Tiongkok, India, Arab, dan Champa.",
      relicsAndSites = "Kitab Negarakretagama (Mpu Prapanca), Kitab Sutasoma (Mpu Tantular), Candi Penataran, Candi Bajang Ratu, Candi Tikus.",
      societalImpact = "Puncak kejayaan integrasi kepulauan Nusantara; lahirnya semboyan persatuan 'Bhinneka Tunggal Ika' serta sistem hukum perundang-undangan Kutaramanawa.",
      historicalNote = "Mahapatih Gajah Mada mengikrarkan 'Sumpah Palapa' pada tahun 1336 M, berjanji tidak akan menikmati istirahat atau kemewahan sebelum menyatukan wilayah-wilayah Nusantara."
    )
  )

  val historicalRelics = listOf(
    HistoricalRelic(
      id = "yupa",
      name = "Prasasti Yupa",
      kingdom = "Kerajaan Kutai Martadipura (Kalimantan Timur)",
      category = "Prasasti Batu",
      discoveryLocation = "Muara Kaman, Sungai Mahakam",
      functionAndMeaning = "Tiang batu peringatan kurban persembahan sapi dan pencatat silsilah raja.",
      historicalDeduction = "Membuktikan bahwa masyarakat lokal telah mengenal tulisan huruf Pallawa dari India Selatan, mengadopsi agama Hindu, tetapi tetap mempertahankan tradisi batu megalitikum (menhir) lokal.",
      clueForDetective = "Prasasti ini berbentuk tugu batu kasar mirip menhir tempat menambatkan hewan kurban sedekah raja."
    ),
    HistoricalRelic(
      id = "ciaruteun",
      name = "Prasasti Ciaruteun",
      kingdom = "Kerajaan Tarumanegara (Jawa Barat)",
      category = "Prasasti Batu Bertapak Kaki",
      discoveryLocation = "Tepi Sungai Ciaruteun, Bogor",
      functionAndMeaning = "Memuat pahatan sepasang telapak kaki manusia dan puisi sanjungan kepemimpinan.",
      historicalDeduction = "Menunjukkan kepercayaan Hindu Waisnawa di mana raja diposisikan sebagai titisan Dewa Wisnu yang melindungi dunia dan menundukkan musuh.",
      clueForDetective = "Perhatikan lekukan pahatan dua telapak kaki di atas batu besar di tepi sungai alami."
    ),
    HistoricalRelic(
      id = "borobudur",
      name = "Candi Borobudur",
      kingdom = "Mataram Kuno (Dinasti Syailendra)",
      category = "Candi Buddha Mahayana",
      discoveryLocation = "Magelang, Jawa Tengah",
      functionAndMeaning = "Monumen suci stupa bertingkat yang melambangkan tingkatan kosmologi Buddha: Kamadhatu, Rupadhatu, dan Arupadhatu.",
      historicalDeduction = "Membuktikan puncak kecakapan matematika, arsitektur batu andesit, dan perpaduan konsep punden berundak asli Nusantara dengan filsafat Buddha India.",
      clueForDetective = "Memiliki ribuan panel relief yang juga menggambarkan kapal bercadik layar Nusantara yang sedang mengarungi samudra purba."
    ),
    HistoricalRelic(
      id = "kedukan_bukit",
      name = "Prasasti Kedukan Bukit (683 M)",
      kingdom = "Kedatuan Sriwijaya (Sumatra Selatan)",
      category = "Prasasti Perjalanan Militer",
      discoveryLocation = "Tepi Sungai Tatang, Palembang",
      functionAndMeaning = "Menceritakan perjalanan suci (Siddhayatra) Dapunta Hyang membawa 20.000 bala tentara naik perahu untuk mendirikan perkampungan makmur.",
      historicalDeduction = "Bukti awal kekuatan militer maritim Sriwijaya dan penyebaran kekuasaannya melalui penguasaan sungai dan muara laut.",
      clueForDetective = "Batu bulat bertuliskan huruf Pallawa berbahasa Melayu Kuno tertua di Nusantara."
    ),
    HistoricalRelic(
      id = "sutasoma",
      name = "Kakawin Sutasoma",
      kingdom = "Kerajaan Majapahit (Mpu Tantular)",
      category = "Karya Sastra Daun Lontar",
      discoveryLocation = "Jawa Timur",
      functionAndMeaning = "Mengisahkan perjalanan Pangeran Sutasoma yang mengajarkan pengorbanan suci dan welas asih.",
      historicalDeduction = "Memuat pupuh 139 bait 5 yang berbunyi 'Bhinneka Tunggal Ika tan Hana Dharma Mangrwa', membuktikan toleransi mendalam antara penganut Siwa dan Buddha di Majapahit.",
      clueForDetective = "Karya sastra lontar yang menjadi inspirasi lambang Garuda Pancasila negara Republik Indonesia."
    )
  )

  val quizQuestions = listOf(
    // MATERI 1 (Soal 1-5)
    QuizQuestion(
      id = 1,
      materialSection = "Materi 1 — Interaksi Masa Lalu",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Kondisi geografis Nusantara yang berupa kepulauan dan terletak di antara dua benua serta dua samudera menyebabkan wilayah ini...",
      options = listOf(
        "Menjadi daerah terisolasi dari peradaban luar",
        "Menjadi jalur transit strategis pelayaran dan perdagangan internasional",
        "Hanya dapat dikunjungi oleh bangsa yang memiliki teknologi modern",
        "Mengalami kekeringan ekstrem sepanjang tahun"
      ),
      correctOptionIndex = 1,
      explanation = "Posisi silang strategis antara Samudera Hindia dan Pasifik serta Benua Asia dan Australia menjadikan Nusantara jalur lintas wajib kapal dagang dunia."
    ),
    QuizQuestion(
      id = 2,
      materialSection = "Materi 1 — Interaksi Masa Lalu",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Faktor alamiah manakah yang sangat menentukan jadwal pelayaran kapal pedagang asing di Nusantara pada masa lampau?",
      options = listOf(
        "Gelombang tsunami tahunan",
        "Pola pergantian Angin Muson Barat dan Angin Muson Timur",
        "Arus sungai bawah tanah",
        "Ketinggian pasang surut air danau"
      ),
      correctOptionIndex = 1,
      explanation = "Angin Muson bertiup bergantian setiap 6 bulan sekali. Pedagang memanfaatkan angin ini untuk berlayar dan terpaksa singgah berbulan-bulan di pelabuhan Nusantara sembari menunggu angin balik."
    ),
    QuizQuestion(
      id = 3,
      materialSection = "Materi 1 — Interaksi Masa Lalu",
      type = QuestionType.TRUE_FALSE,
      question = "Benar atau Salah: Laut di kepulauan Nusantara pada masa lalu dipandang sebagai dinding pemisah yang menghambat hubungan antarpulau.",
      options = listOf(
        "Benar, karena perahu kuno tidak mampu melintasi laut",
        "Salah, laut justru berfungsi sebagai jembatan penghubung transportasi, komunikasi, dan pertukaran budaya"
      ),
      correctOptionIndex = 1,
      explanation = "Salah. Dalam konsep maritim Nusantara, laut bukan pemisah melainkan pemersatu yang menghubungkan pulau-pulau melalui jalur pelayaran perahu cadik."
    ),
    QuizQuestion(
      id = 4,
      materialSection = "Materi 1 — Interaksi Masa Lalu",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Hubungan antara pedagang asing dengan penduduk setempat yang berkembang menjadi ikatan pernikahan dan asimilasi kebiasaan merupakan contoh interaksi dalam bidang...",
      options = listOf(
        "Interaksi Militer",
        "Interaksi Sosial dan Budaya",
        "Interaksi Monopoli Fiskal",
        "Interaksi Agraris Murni"
      ),
      correctOptionIndex = 1,
      explanation = "Ketika pedagang tinggal berbulan-bulan di pelabuhan Nusantara, terjadilah pergaulan sosial, pernikahan campur, adopsi bahasa, dan akulturasi budaya setempat."
    ),
    QuizQuestion(
      id = 5,
      materialSection = "Materi 1 — Interaksi Masa Lalu",
      type = QuestionType.CAUSE_EFFECT,
      question = "Mengapa iklim tropis dengan tanah vulkanik yang subur di Nusantara menjadi faktor penarik kedatangan pedagang asing?",
      options = listOf(
        "Menghasilkan rempah-rempah dan hasil bumi bernilai tinggi yang tidak tumbuh di daerah subtropis",
        "Menjadikan penduduk Nusantara tidak perlu bekerja",
        "Menghalangi tumbuhnya pepohonan kayu",
        "Membuat bangsa asing ingin membangun pangkalan militer modern"
      ),
      correctOptionIndex = 0,
      explanation = "Kondisi tanah dan iklim tropis mendukung tumbuhnya komoditas langka bernilai tinggi seperti cengkih, pala, lada, dan kayu cendana yang sangat dicari dunia."
    ),

    // MATERI 2 (Soal 6-10)
    QuizQuestion(
      id = 6,
      materialSection = "Materi 2 — Perdagangan Awal Masehi",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Komoditas asli dari Kepulauan Maluku yang pada awal Masehi menjadi rebutan di pasar internasional karena aromanya dan kegunaannya sebagai pengawet makanan adalah...",
      options = listOf(
        "Kain sutra dan seladon",
        "Cengkih dan Pala",
        "Batu giok dan perak",
        "Tembakau dan kopi"
      ),
      correctOptionIndex = 1,
      explanation = "Cengkih dan pala adalah komoditas endemik Maluku (The Spice Islands) yang tidak tumbuh di belahan bumi lain pada masa awal Masehi."
    ),
    QuizQuestion(
      id = 7,
      materialSection = "Materi 2 — Perdagangan Awal Masehi",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Pelabuhan Barus (Fansur) di pesisir barat Sumatra menjadi sangat terkenal di dunia kuno terutama karena mengekspor komoditas...",
      options = listOf(
        "Kapur barus (kamfer) dan kemenyan",
        "Beras dan gandum",
        "Keramik porselen biru",
        "Biji kopi arabika"
      ),
      correctOptionIndex = 0,
      explanation = "Kapur Barus dari pohon kamfer hutan Sumatra sangat masyhur hingga tercatat oleh penjelajah Yunani, Romawi, Arab, dan Tiongkok sebagai wewangian serta obat pembalsem."
    ),
    QuizQuestion(
      id = 8,
      materialSection = "Materi 2 — Perdagangan Awal Masehi",
      type = QuestionType.TRUE_FALSE,
      question = "Benar atau Salah: Semua wilayah Nusantara pada awal Masehi memperdagangkan komoditas dalam jumlah dan intensitas yang persis sama ke India dan Tiongkok.",
      options = listOf(
        "Benar, seluruh pulau memiliki pelabuhan internasional yang setara",
        "Salah, intensitas perdagangan berbeda-beda menurut letak geografis, periode sejarah, dan potensi komoditas tiap daerah"
      ),
      correctOptionIndex = 1,
      explanation = "Salah. Setiap wilayah memiliki kekhasan: Maluku sebagai produsen rempah, Sumatra dan Jawa sebagai bandar perantara dan lumbung beras, dengan intensitas berbeda antarzaman."
    ),
    QuizQuestion(
      id = 9,
      materialSection = "Materi 2 — Perdagangan Awal Masehi",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Selain tempat bongkar muat barang dagangan, apakah fungsi penting pelabuhan kuno di Nusantara bagi perkembangan peradaban?",
      options = listOf(
        "Hanya sebagai tempat penyimpanan kapal rusak",
        "Pusat pertukaran informasi, bahasa, pengetahuan, serta cikal bakal tumbuhnya perkotaan dan kerajaan",
        "Tempat isolasi bagi warga yang tidak boleh keluar pulau",
        "Lokasi latihan perang tertutup tanpa interaksi luar"
      ),
      correctOptionIndex = 1,
      explanation = "Pelabuhan kuno merupakan melting pot (ruang perjumpaan) tempat masyarakat lokal menyerap teknologi perkapalan, aksara, agama, dan sistem pemerintahan baru."
    ),
    QuizQuestion(
      id = 10,
      materialSection = "Materi 2 — Perdagangan Awal Masehi",
      type = QuestionType.HISTORICAL_ANALYSIS,
      question = "Barang-barang mewah dari luar negeri yang dibawa pedagang asing ke Nusantara dan dijadikan simbol kehormatan oleh bangsawan lokal antara lain...",
      options = listOf(
        "Keramik porselen Dinasti Tiongkok, kain sutra, dan manik-manik kaca India",
        "Pakaian wol musim salju dan sepatu bot besi",
        "Mesin cetak listrik dan kompas magnetik digital",
        "Mobil kayu dan senapan mesiu otomatis"
      ),
      correctOptionIndex = 0,
      explanation = "Keramik Dinasti Han/Tang/Song dari Tiongkok serta kain tenun patola dan manik-manik dari India menjadi barang gengsi bernilai tinggi bagi kaum penguasa lokal."
    ),

    // MATERI 3 (Soal 11-15)
    QuizQuestion(
      id = 11,
      materialSection = "Materi 3 — Kerajaan Hindu-Buddha",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Bukti tertulis paling awal mengenai keberadaan kerajaan bercorak Hindu di kepulauan Indonesia ditemukan pada tugu batu Prasasti Yupa di...",
      options = listOf(
        "Kerajaan Tarumanegara, Jawa Barat",
        "Kerajaan Kutai Martadipura, Kalimantan Timur",
        "Kerajaan Sriwijaya, Sumatra Selatan",
        "Kerajaan Majapahit, Jawa Timur"
      ),
      correctOptionIndex = 1,
      explanation = "Prasasti Yupa di Muara Kaman, Sungai Mahakam (Kaltim) berasal dari sekitar tahun 400 M, mencatat kedermawanan Raja Mulawarman."
    ),
    QuizQuestion(
      id = 12,
      materialSection = "Materi 3 — Kerajaan Hindu-Buddha",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Teori yang menyatakan bahwa masyarakat Nusantara sendiri yang secara aktif berlayar menuntut ilmu agama ke India lalu kembali untuk mengajarkannya dikenal sebagai...",
      options = listOf(
        "Teori Brahmana",
        "Teori Ksatria",
        "Teori Waisya",
        "Teori Arus Balik (Nasionalis)"
      ),
      correctOptionIndex = 3,
      explanation = "Teori Arus Balik (dikemukakan oleh F.D.K. Bosch) menekankan peran aktif pemuda Nusantara yang belajar ke pusat-pusat keilmuan di India seperti Universitas Nalanda."
    ),
    QuizQuestion(
      id = 13,
      materialSection = "Materi 3 — Kerajaan Hindu-Buddha",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Pembangunan saluran air Sungai Gomati sepanjang 11 km yang dicatat dalam Prasasti Tugu membuktikan bahwa Kerajaan Tarumanegara memiliki perhatian besar pada...",
      options = listOf(
        "Pencegahan banjir dan irigasi pertanian rakyat",
        "Pembuatan arena pacuan kuda istana",
        "Pencarian harta karun emas bawah tanah",
        "Penutupan jalur masuk bagi perahu dagang luar"
      ),
      correctOptionIndex = 0,
      explanation = "Raja Purnawarman memimpin penggalian Sungai Gomati dan Chandrabhaga demi mengairi persawahan rakyat dan mencegah bahaya luapan banjir musiman."
    ),
    QuizQuestion(
      id = 14,
      materialSection = "Materi 3 — Kerajaan Hindu-Buddha",
      type = QuestionType.HISTORICAL_ANALYSIS,
      question = "Mengapa Kedatuan Sriwijaya disebut sebagai kerajaan bercorak 'Thalassokrasi' atau negara maritim?",
      options = listOf(
        "Karena tidak memiliki lahan daratan sama sekali",
        "Karena kekuatan politik dan ekonominya bertumpu pada penguasaan armada laut, selat dagang, dan monopoli pelabuhan internasional",
        "Karena hanya menghasilkan ikan asin untuk konsumsi lokal",
        "Karena rajanya melarang rakyat bertani padi di darat"
      ),
      correctOptionIndex = 1,
      explanation = "Thalassokrasi adalah sistem kekuasaan yang berbasis dominasi wilayah perairan dan jalur niaga laut (maritim), seperti Sriwijaya yang menguasai Selat Malaka."
    ),
    QuizQuestion(
      id = 15,
      materialSection = "Materi 3 — Kerajaan Hindu-Buddha",
      type = QuestionType.MULTIPLE_CHOICE,
      question = "Semboyan persatuan bangsa Indonesia 'Bhinneka Tunggal Ika' berasal dari kitab karya sastra masa Kerajaan Majapahit, yaitu...",
      options = listOf(
        "Kitab Negarakretagama karangan Mpu Prapanca",
        "Kitab Sutasoma karangan Mpu Tantular",
        "Kitab Bharatayuddha karangan Mpu Sedah",
        "Kitab Arjunawiwaha karangan Mpu Kanwa"
      ),
      correctOptionIndex = 1,
      explanation = "Semboyan 'Bhinneka Tunggal Ika tan Hana Dharma Mangrwa' ditulis oleh Mpu Tantular dalam Kitab Sutasoma pada masa kejayaan Majapahit untuk menjaga keharmonisan pemeluk Hindu dan Buddha."
    )
  )

  val bibliography = listOf(
    "Kemdikbudristek. (2021). Ilmu Pengetahuan Sosial untuk SMP Kelas VIII. Pusat Kurikulum dan Perbukuan, Badan Standar, Kurikulum, dan Asesmen Pendidikan, Jakarta.",
    "Poesponegoro, M. D., & Notosusanto, N. (2019). Sejarah Nasional Indonesia Jilid II: Zaman Kuno. Balai Pustaka, Jakarta.",
    "Hall, K. R. (2011). A History of Early Southeast Asia: Maritime Trade and Societal Development, 100-1500. Rowman & Littlefield Publishers.",
    "Manguin, P. Y., Mani, A., & Geoff, W. (2011). Early Interactions between South and Southeast Asia: Reflections on Cross-Cultural Exchange. ISEAS Publishing.",
    "Reid, A. (2014). Asia Tenggara dalam Kurun Niaga 1450-1680: Jilid 2 Jaringan Perdagangan Global. Yayasan Pustaka Obor Indonesia."
  )
}
