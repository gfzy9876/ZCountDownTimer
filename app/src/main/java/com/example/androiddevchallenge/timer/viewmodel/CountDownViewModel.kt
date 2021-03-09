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
package com.example.androiddevchallenge.timer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CountDownViewModel : ViewModel() {

    var totalCount: Int = 0
    val isCountTimingData = MutableLiveData(false)
    val currentCountLiveData = MutableLiveData(0)
    private var countDownTimer: Disposable? = null

    fun startCountDownTimer(totalCountSeconds: Int) {
        totalCount = totalCountSeconds
        currentCountLiveData.value = totalCount
        disposeTimer()
        isCountTimingData.value = true
        countDownTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("GFZY", "subscribe: ${currentCountLiveData.value}")
                if (currentCountLiveData.value ?: 0 <= 0) {
                    disposeTimer()
                    isCountTimingData.value = false
                } else {
                    currentCountLiveData.value = currentCountLiveData.value!! - 1
                }
            }
    }

    override fun onCleared() {
        disposeTimer()
    }

    private fun disposeTimer() {
        if (countDownTimer?.isDisposed == false) {
            countDownTimer?.dispose()
        }
    }
}
