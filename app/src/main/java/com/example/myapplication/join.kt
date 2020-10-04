package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_join.*

class join : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setTitle("체수분 계산기")

        // 회원가입
        BtnJoin.setOnClickListener {
            saveAccount()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@join, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveAccount() {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit() // 수정모드
        edit.putString(EditID.text.toString(), EditPassword.text.toString())
        edit.apply()
    }
}