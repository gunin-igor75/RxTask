package com.github.gunin_igor75.rxtask.example

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject


private val compositeDisposable = CompositeDisposable()


fun hotSource() {
    val subjectPublish = PublishSubject.create<String>()
    subjectPublish.subscribe { println(it) }.apply { compositeDisposable.add(this) }
    subjectPublish.onNext("1")
    subjectPublish.onNext("2")
    subjectPublish.onNext("3")

    println()

    val subjectReplay = ReplaySubject.create<String>()
    subjectReplay.onNext("1")
    subjectReplay.onNext("2")
    subjectReplay.onNext("3")
    subjectReplay.subscribe { println(it) }.apply { compositeDisposable.add(this) }
}

private fun clear() {
    compositeDisposable.clear()
}