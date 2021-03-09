/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.timer.ui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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

            Button(
                onClick = {
                    viewModel.startCountDownTimer(editedCountDownSeconds)
                }
            ) {
                Text(text = "OK")
            }
        }
    }
}
