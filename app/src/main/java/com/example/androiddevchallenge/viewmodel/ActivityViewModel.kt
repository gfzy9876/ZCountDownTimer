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
package com.example.androiddevchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.Calendar
import java.util.concurrent.TimeUnit

class ActivityViewModel : ViewModel() {
    private val _countDownData = MutableLiveData(0L)
    val countDownData = _countDownData

    private var mTimer: Subscription? = null

    fun startCountDownTimer() {
        val instance = Calendar.getInstance()
        instance.set(2021, 2, 9, 23, 59, 59)
        val endMills = instance.timeInMillis
        mTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val diff = endMills - System.currentTimeMillis()
                countDownData.value = diff
            }
    }

    override fun onCleared() {
        super.onCleared()
        if (mTimer?.isUnsubscribed == false) {
            mTimer?.unsubscribe()
        }
    }
}
