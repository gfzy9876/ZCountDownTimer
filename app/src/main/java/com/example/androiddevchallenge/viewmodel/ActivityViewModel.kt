package com.example.androiddevchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
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