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
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.example.androiddevchallenge.ui.theme.MyTheme
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private val mCountDownData = MutableLiveData(0L)
    private var mTimer: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
        val instance = Calendar.getInstance()
        instance.set(2021, 2, 9, 23, 59, 59)
        val endMills = instance.timeInMillis

        mTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val diff = endMills - System.currentTimeMillis()
                mCountDownData.value = diff
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTimer?.isUnsubscribed == false) {
            mTimer?.unsubscribe()
        }
    }

    @Composable
    fun MyApp() {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column {
                var diff = mCountDownData.observeAsState().value ?: 0L
                val day = diff / 60 / 60 / 24 / 1000
                diff -= day * 60 * 60 * 24 * 1000
                val hour = diff / 60 / 60 / 1000
                diff -= hour * 60 * 60 * 1000
                val min = diff / 60 / 1000
                diff -= min * 60 * 1000
                val second = diff / 1000
                Text(text = "距离比赛结束还有: ${day}天 ${hour}小时 ${min}分 ${second}秒")
            }
        }
    }
}

//
//@Preview("Light Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun LightPreview() {
//    MyTheme {
//        MyApp()
//    }
//}
//
//@Preview("Dark Theme", widthDp = 360, heightDp = 640)
//@Composable
//fun DarkPreview() {
//    MyTheme(darkTheme = true) {
//        MyApp()
//    }
//}
