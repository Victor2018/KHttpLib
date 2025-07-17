package org.victor.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.util.JsonUtils
import org.victor.http.util.InjectorUtils
import org.victor.http.vm.GankGirlVm

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private val gankGirlVm: GankGirlVm by viewModels {
        InjectorUtils.provideGankGirlVm(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    fun initialize () {
        subscribeUi()

        mBtnSend.setOnClickListener(this)
    }

    fun subscribeUi() {
        gankGirlVm.gankGirlData.observe(this, Observer {
            when(it) {
                is HttpResult.Success -> {
                    mTvResponse.text = JsonUtils.toJSONString(it.value)
                }
                is HttpResult.Error -> {
                    mTvResponse.text = it.message.toString()
                }
            }
        })
    }

    private fun sendGankGirlRequest () {
        gankGirlVm.fetchGankGirl()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mBtnSend -> {
                sendGankGirlRequest()
            }
        }
    }
}