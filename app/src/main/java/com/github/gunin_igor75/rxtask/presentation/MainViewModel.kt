package com.github.gunin_igor75.rxtask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.gunin_igor75.rxtask.data.network.api.ApiService
import com.github.gunin_igor75.rxtask.presentation.mappers.dataDtoCasesToCases
import com.github.gunin_igor75.rxtask.presentation.model.UiCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val apiService: ApiService,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val editTextSubject = PublishSubject.create<String>()

    private val defaultCases = listOf(
        UiCase("fe85a416-2f0c-470a-b3ee-c750248d81a3", "Title default One"),
        UiCase("3f122e34-6081-44c8-806f-29eeadd9bd00", "Title default Two"),
        UiCase("c33029c0-85ff-4d5b-ad2d-ba2b67e5d970", "Title default Three"),
    )

    val toastSubject = PublishSubject.create<String>()

    val timerSubject = BehaviorSubject.create<Long>()

    init {
        timer()
        observeEditTextSubject()
    }


    fun changeText(world: String) {
        editTextSubject.onNext(world)
    }

    fun getCases(): Single<List<UiCase>> = apiService.getCases()
        .subscribeOn(Schedulers.io())
        .map { it.dataCasesDto.dataDtoCasesToCases() }
        .onErrorReturnItem(defaultCases)

    fun launchToast(id: String) {
        toastSubject.onNext(id)
    }

    private fun timer() {
        val value = timerSubject.value ?: 0L
        Observable.intervalRange(
            value,
            Long.MAX_VALUE,
            0L,
            1,
            TimeUnit.SECONDS
        )
            .subscribe {
                timerSubject.onNext(it)
            }.apply {
                compositeDisposable.add(this)
            }
    }

    private fun observeEditTextSubject() {
        editTextSubject
            .debounce(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "World : $it")
            }.apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private companion object {
        const val TAG = "MainViewModel"
    }
}