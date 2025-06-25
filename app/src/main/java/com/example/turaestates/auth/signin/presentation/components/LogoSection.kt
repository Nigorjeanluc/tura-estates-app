package com.example.turaestates.auth.signin.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.turaestates.R

@Composable
fun LogoSection(
    title: String,
    subtitle: String
) {
    val darkTheme = isSystemInDarkTheme()
    val logo = if (darkTheme)
        painterResource(id = R.drawable.logolight)
    else
        painterResource(id = R.drawable.logodark)


    Image(
        modifier = Modifier.width(125.dp).height(125.dp),
        contentScale = ContentScale.FillWidth,
        painter = logo,
        contentDescription = "Pager Image"
    )
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = Color(0xFF005C71)
    )
    Text(
        text = subtitle,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleMedium
    )
}