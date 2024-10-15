package com.github.gunin_igor75.rxtask.example

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private val compositeDisposable = CompositeDisposable()

fun taskScheduler() {
    Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
        // он работает если источник не привязан к определенному Scheduler, в данном случае не работает
        .subscribeOn(Schedulers.io())
        // map  работает на Schedulers.newThread()
        .map {
            Log.d("HAHAHA", "mapThread = ${Thread.currentThread().name}")
        }
        // данный метод выполняется в текущем потоке
        .doOnSubscribe {
            Log.d("HAHAHA", "onSubscribeThread = ${Thread.currentThread().name}")
        }
        // это оператор переопределяет Scheduler для doOnSubscribe
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.single())
        // flatMap выполняется на Schedulers.single()
        .flatMap {
            Log.d("HAHAHA", "flatMapThread = ${Thread.currentThread().name}")
            Observable.just(it)
                .subscribeOn(Schedulers.io())
        }
        // так как flatMap возвращет Observable созданный на Schedulers.io(), то и подписка буде идти на нем
        .subscribe {
            Log.d("HAHAHA", "subscribeThread = ${Thread.currentThread().name}")
        }.apply { compositeDisposable.add(this) }
}

private fun clear() {
    compositeDisposable.clear()
}

fun main() {
    taskScheduler()
    Thread.sleep(5000)
    clear()
}