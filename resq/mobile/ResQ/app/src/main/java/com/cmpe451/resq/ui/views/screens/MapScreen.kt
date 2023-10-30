package com.cmpe451.resq.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MapScreen(
    navController: NavController ,
    // Viewmodel
){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .size(64.dp)
            )
            Text(text = "🤡")
        }
    }

}
