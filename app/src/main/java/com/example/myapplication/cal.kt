package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cal.*

class cal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cal)
        setTitle("체수분 계산기")

        // 계산하기 버튼 클릭 시
        BtnCal.setOnClickListener{
            tvResult.setText("권장 수분 섭취량 : " + Integer.parseInt(EditWeight.text.toString()) * 30 + "ml")
            saveCalResult();
        }

        // 로그아웃 버튼 클릭 시
        BtnLogout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 히스토리 버튼 클릭 시
        BtnHistory.setOnClickListener{
            val pref = getSharedPreferences("pref", 0)
            var passwordId = pref.getString("passwordIdCalResult", "0") // 오늘 마셔야할 총 물의 양 key
            var calResult = pref.getString(passwordId, "") // 오늘 마셔야할 총 물의 양
            //저장된 필요 수분량 계산 내역이 없는 경우
            if("".equals(calResult)){
                Toast.makeText(this@cal, "먼저 필요 수분량 계산이 필요합니다", Toast.LENGTH_SHORT).show()
            }
            // 그 외의 경우
            else {
                val intent = Intent(this, history::class.java)
                startActivity(intent)
            }
        }
    }

    // 권장 수분 섭취량 저장
    private fun saveCalResult() {
        val pref = getSharedPreferences("pref", 0)
        var passwordId = pref.getString("passwordIdCalResult", "0") // 오늘 마셔야할 총 물의 양 key
        val edit = pref.edit() // 수정모드
        edit.putString(passwordId, (Integer.parseInt(EditWeight.text.toString()) * 30).toString())
        edit.apply()
    }
}