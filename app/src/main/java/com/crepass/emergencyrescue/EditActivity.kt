package com.crepass.emergencyrescue

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.location.GnssAntennaInfo.Listener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.crepass.emergencyrescue.databinding.ActivityEditBinding
import com.crepass.emergencyrescue.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //adapter 중요!!!!!!!!!!!
        binding.bloodSpinner.adapter=ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthlayer.setOnClickListener {
            var listener=OnDateSetListener{_,year,month,dayOfMonth->
                binding.birthEdit.text="$year-${month.inc()}-$dayOfMonth"

            }
            DatePickerDialog(
                this,
                listener,
                2000,
                1,
                1

            ).show()//쇼를 안하면 그냥 만들기만 한거임
        }
        binding.warringCheck.setOnCheckedChangeListener{_,isCheck->
            binding.warringEdit.isVisible=isCheck

        }
        binding.warringEdit.isVisible=binding.warringCheck.isChecked
        binding.saveActionButton2.setOnClickListener {
            saveData()
            finish()
        }

    }


    fun saveData(){
        //스콧펑션
        with( getSharedPreferences("UserInformation", Context.MODE_PRIVATE).edit()){
            putString(NAME,binding.nameEdit.text.toString())
            putString(BLOODTYPE,getBlood())
            putString(EMERGENCY,binding.helpEdit.text.toString())
            putString(BIRTHDAY,binding.birthEdit.text.toString())
            putString(WARRING,getWarring())
            apply()//commit같은 경우엔 데이터 저장에서 사용자의 동작을 막게 됨, apply는 비동기 방식이라 다른 스레드를 열어줌
        }


        Toast.makeText(this,"저장을 완료했습니다",Toast.LENGTH_SHORT).show()
    }

    fun getBlood():String{
        val bloodAl=binding.bloodSpinner.selectedItem.toString()//실시간 값을 받을 수 있음.
        val bloodSign=if(binding.RhP.isChecked) "+" else "-"
        return "$bloodSign$bloodAl"
    }
    fun getWarring():String{
       return  if(binding.warringCheck.isChecked) binding.warringEdit.text.toString() else ""
    }
}