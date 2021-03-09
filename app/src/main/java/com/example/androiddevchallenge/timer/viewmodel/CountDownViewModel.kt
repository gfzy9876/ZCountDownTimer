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
    val currentCountLiveData = MutableLiveData(0)
    private var countDownTimer: Disposable? = null

    fun startCountDownTimer(totalCountSeconds: Int) {
        totalCount = totalCountSeconds
        currentCountLiveData.value = totalCount
        disposeTimer()
        countDownTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("GFZY", "subscribe: ${currentCountLiveData.value}")
                if (currentCountLiveData.value ?: 0 <= 0) {
                    disposeTimer()
                } else {
                    currentCountLiveData.value = currentCountLiveData.value!! - 1
                }
            }
    }

    fun isCountDownTiming(): Boolean = countDownTimer?.isDisposed == false

    override fun onCleared() {
        disposeTimer()
    }

    private fun disposeTimer() {
        if (countDownTimer?.isDisposed == false) {
            countDownTimer?.dispose()
        }
    }
}