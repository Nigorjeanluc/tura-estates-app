package com.example.turaestates.auth.signin.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.turaestates.R
import com.example.turaestates.util.components.SocialLoginButton

@Composable
fun SocialLoginButtons(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialLoginButton(
            text = "Google",
            icon = painterResource(id = R.drawable.google), // Your Google icon
            backgroundColor = Color.White,
            textColor = Color.Black,
            borderColor = Color.LightGray,
            onClick = onGoogleClick
        )

        SocialLoginButton(
            text = "Facebook",
            icon = painterResource(id = R.drawable.facebook), // Your Facebook icon
            backgroundColor = Color(0xFF1877F2),
            textColor = Color.White,
            borderColor = Color.Transparent,
            onClick = onFacebookClick
        )
    }
}
