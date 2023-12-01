package com.crepass.emergencyrescue

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.crepass.emergencyrescue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.floatingActionButton2.setOnClickListener {
            val intent= Intent(this,EditActivity::class.java)//intent end포인트 찍기

            startActivity(intent)
        }
        binding.deleteActionButton2.setOnClickListener {
            delete()
        }
        binding.layer.setOnClickListener {
            call()
        }
    }

    private fun call() {
        val intent=with(Intent(Intent.ACTION_VIEW)){
            val phone = binding.helpValue.text.toString()
                .replace("-","")
            data=Uri.parse("tel:$$phone")
            startActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()

        getDataAndUi()

    }
    fun getDataAndUi(){//원래는 함수 하나에 기능이 하나여야됨
        with(getSharedPreferences(USER_INFO,Context.MODE_PRIVATE)){
            binding.nameValue.text=getString(NAME,"미정입니다")
            binding.birthValue.text=getString(BIRTHDAY,"미정입니다")
            binding.bloodValue.text=getString(BLOODTYPE,"미정입니다")
            binding.helpValue.text=getString(EMERGENCY,"미정입니다")
            val warring=getString(WARRING,"")
            binding.warringText.isVisible=warring.isNullOrEmpty().not()
            binding.warringValue.isVisible=warring.isNullOrEmpty().not()
            if(!warring.isNullOrEmpty()){
                binding.warringValue.text=warring
            }

        }
    }
    fun delete(){
        with(getSharedPreferences(USER_INFO, MODE_PRIVATE).edit()){
            clear()
            apply()
            getDataAndUi()
        }
        Toast.makeText(this,"초기화 했습니다",Toast.LENGTH_SHORT).show()
    }

}