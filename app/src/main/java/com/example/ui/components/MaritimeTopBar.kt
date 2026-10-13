package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldenAmber
import com.example.ui.theme.HeritageGold
import com.example.ui.theme.OceanDeep
import com.example.ui.theme.OceanNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaritimeTopBar(
  title: String,
  subtitle: String? = null,
  studentName: String = "",
  showBackButton: Boolean = true,
  showHomeButton: Boolean = true,
  onBackClick: () -> Unit = {},
  onHomeClick: () -> Unit = {},
  onInfoClick: (() -> Unit)? = null,
  badgeCount: Int = 0
) {
  Surface(
    shadowElevation = 4.dp,
    color = OceanNavy
  ) {
    TopAppBar(
      modifier = Modifier
        .statusBarsPadding()
        .testTag("maritime_top_bar"),
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = Color.White,
        navigationIconContentColor = Color.White,
        actionIconContentColor = Color.White
      ),
      title = {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Box(
            modifier = Modifier.weight(1f)
          ) {
            androidx.compose.foundation.layout.Column {
              Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 17.sp,
                  color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
              if (!subtitle.isNullOrBlank()) {
                Text(
                  text = subtitle,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = GoldenAmber
                  ),
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }
          }

          if (studentName.isNotBlank()) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = OceanDeep.copy(alpha = 0.6f),
              border = androidx.compose.foundation.BorderStroke(1.dp, HeritageGold.copy(alpha = 0.6f)),
              modifier = Modifier.padding(end = 4.dp)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "⚓ $studentName",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                  ),
                  maxLines = 1
                )
                if (badgeCount > 0) {
                  Spacer(modifier = Modifier.width(4.dp))
                  Box(
                    modifier = Modifier
                      .size(16.dp)
                      .background(HeritageGold, CircleShape),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "$badgeCount",
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = OceanDeep
                      )
                    )
                  }
                }
              }
            }
          }
        }
      },
      navigationIcon = {
        if (showBackButton) {
          IconButton(
            onClick = onBackClick,
            modifier = Modifier.testTag("nav_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Kembali",
              tint = Color.White
            )
          }
        }
      },
      actions = {
        if (showHomeButton) {
          IconButton(
            onClick = onHomeClick,
            modifier = Modifier.testTag("nav_home_button")
          ) {
            Icon(
              imageVector = Icons.Default.Home,
              contentDescription = "Menu Utama",
              tint = GoldenAmber
            )
          }
        }
        if (onInfoClick != null) {
          IconButton(
            onClick = onInfoClick,
            modifier = Modifier.testTag("nav_info_button")
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = "Petunjuk",
              tint = Color.White
            )
          }
        }
      }
    )
  }
}
