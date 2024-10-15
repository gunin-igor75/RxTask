package com.github.gunin_igor75.rxtask.example

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


private val compositeDisposable = CompositeDisposable()

fun main() {
    val card = Observable.just("Card1", "Card2","Card3")
    val discount = Observable.just("10%", "20%", "30%", "40%")
    Observable.zip(card, discount){ s: String, l: String -> s to l }
        .subscribe { println("$it") }
        .apply { compositeDisposable.add(this) }

    Thread.sleep(1000)
    clear()
}

private fun clear() {
    compositeDisposable.clear()
}