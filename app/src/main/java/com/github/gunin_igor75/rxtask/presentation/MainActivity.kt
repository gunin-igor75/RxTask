package com.github.gunin_igor75.rxtask.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.github.gunin_igor75.rxtask.R
import com.github.gunin_igor75.rxtask.data.network.api.ApiFactory
import com.github.gunin_igor75.rxtask.databinding.ActivityMainBinding
import com.github.gunin_igor75.rxtask.presentation.adapter.CaseAdapter
import com.github.gunin_igor75.rxtask.presentation.utils.MarginItemDecorationSimple
import com.github.gunin_igor75.rxtask.presentation.utils.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val activityDisposable = CompositeDisposable()

    private val viewModel: MainViewModel by viewModels<MainViewModel> {
        ViewModelFactory(ApiFactory.apiService)
    }

    private val caseAdapter by lazy {
        CaseAdapter(onClickItem = ::clickItemCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeTimer()
        setupEditText()
        setupRecyclerView()
        observeViewModelCases()
        showToast()
    }

    private fun setupEditText() {
        binding.editText.doOnTextChanged { text, start, before, count ->
            viewModel.changeText(text.toString())
        }
    }

    private fun observeTimer() {
        viewModel.timerSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.textview.text = it.toString()
            }.apply { activityDisposable.add(this) }
    }

    private fun setupRecyclerView() {
        val margin = resources.getDimensionPixelSize(R.dimen.dp_1x)
        val marginDecoration = MarginItemDecorationSimple(margin)
        with(binding.recyclerView) {
            adapter = caseAdapter
            addItemDecoration(marginDecoration)
        }
    }

    private fun observeViewModelCases() {
        viewModel.getCases()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { caseAdapter.items = it },
                { Log.d(TAG, "Error get cases ${it.message}") }
            ).apply { activityDisposable.add(this) }
    }

    private fun clickItemCase(id: String) {
        viewModel.launchToast(id)
    }

    private fun showToast() {
        viewModel.toastSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(
                    this,
                    "Title id : $it",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .apply { activityDisposable.add(this) }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityDisposable.clear()
    }

    private companion object {
        const val TAG = "MainActivity"
    }
}