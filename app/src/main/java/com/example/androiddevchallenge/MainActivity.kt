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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.ActivityViewModel

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
        viewModel.startCountDownTimer()
    }

    @Composable
    fun MyApp() {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(20.dp),
                    elevation = 10.dp,
                ) {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.mipmap.bg),
                        contentDescription = null
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "before the end of the game",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        CountDownText(diff = viewModel.countDownData.observeAsState().value ?: 0L)
                    }
                }
            }
        }
    }

    @Composable
    fun CountDownText(diff: Long) {
        var tempDiff = diff
        val day = tempDiff / 60 / 60 / 24 / 1000
        tempDiff -= day * 60 * 60 * 24 * 1000
        val hour = tempDiff / 60 / 60 / 1000
        tempDiff -= hour * 60 * 60 * 1000
        val min = tempDiff / 60 / 1000
        tempDiff -= min * 60 * 1000
        val second = tempDiff / 1000
        Text(
            text = getString(
                R.string.before_the_end_of_the_game,
                day,
                hour,
                min,
                second
            ),
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
