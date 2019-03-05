package com.example.littletest.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.littletest.R
import com.example.littletest.databinding.ActivityMainBinding
import com.example.littletest.dto.ColorJson
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    var dataBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()

        dataBinding?.unbind()
        dataBinding = null
    }

    fun loadData() {

        Observable.create(ObservableOnSubscribe<ColorJson> { emitter ->
            val am = getAssets()
            val jsonInputStream = am.open("colors.json")
            val colorJson =
                Gson().fromJson(InputStreamReader(jsonInputStream), ColorJson::class.java)
            emitter.onNext(colorJson)
            emitter.onComplete()
        })
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { colorJson ->
                dataBinding?.colorJson = colorJson
            }
    }
}
