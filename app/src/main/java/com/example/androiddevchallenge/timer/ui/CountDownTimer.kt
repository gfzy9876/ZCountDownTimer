package com.example.androiddevchallenge.timer.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.timer.viewmodel.CountDownViewModel

@Composable
fun CountDownTimer(viewModel: CountDownViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize()
    ) {
        val currentSeconds by viewModel.currentCountLiveData.observeAsState()
        Text(text = "$currentSeconds")
        Log.d("GFZY", "viewModel.isCountDownTiming(): ${viewModel.isCountDownTiming()}")
        if (viewModel.isCountDownTiming()) {
            Canvas(
                modifier = Modifier.size(300.dp, 300.dp),
                onDraw = {
                    this.drawCircle(color = Color(0xffababab), style = Stroke(width = 10f))
                    this.rotate(6f * currentSeconds!!) {
                        this.drawLine(
                            Color.Black,
                            start = Offset(size.width / 2f, 5f),
                            end = Offset(size.width / 2f, size.height / 2),
                            strokeWidth = 5f
                        )
                    }
                    this.drawCircle(Color.Black, radius = 10f)
                }
            )

        } else {
            var editedCountDownSeconds by remember(calculation = { mutableStateOf(20) })
            OutlinedTextField(
                value = if (editedCountDownSeconds == -1) {
                    ""
                } else {
                    editedCountDownSeconds.toString()
                },
                onValueChange = {
                    editedCountDownSeconds = it.toIntOrNull() ?: -1
                },
                label = {
                    Text(text = "input count down seconds")
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                viewModel.startCountDownTimer(editedCountDownSeconds)
            }) {
                Text(text = "OK")
            }
        }
    }
}