package com.example.littletest.main

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.littletest.R
import com.example.littletest.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    var dataBinding: ActivityMainBinding? = null
    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding?.viewModel = viewModel

        viewModel.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.unbind()
        dataBinding = null
    }
}
