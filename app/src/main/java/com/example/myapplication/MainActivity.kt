package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.BtnJoin
import kotlinx.android.synthetic.main.activity_main.EditID
import kotlinx.android.synthetic.main.activity_main.EditPassword
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("체수분 계산기")

        // 회원가입
        BtnJoin.setOnClickListener {
            val intent = Intent(this, join::class.java)
            startActivity(intent)
        }

        // 로그인
        BtnLogin.setOnClickListener{
            val pref = getSharedPreferences("pref", 0)
            var password = pref.getString(EditID.text.toString(), "")
            // 로그인 성공
            if(password.equals(EditPassword.text.toString())){
                saveIdPassword() // 로그인 성공 시 id, password를 저장(히스토리에서 사용)
                var passwordId = pref.getString("passwordIdCalResult", "0") // 오늘 마셔야할 총 물의 양 key
                var calResult = pref.getString(passwordId, "") // 오늘 마셔야할 총 물의 양
                //저장된 필요 수분량 계산 내역이 없는 경우
                if("".equals(calResult)){
                    val intent = Intent(this, cal::class.java)
                    startActivity(intent)
                }
                // 그 외의 경우
                else {
                    val intent = Intent(this, history::class.java)
                    startActivity(intent)
                }
            }
            //로그인 실패
            else{
                Toast.makeText(this@MainActivity, "로그인에 실패했습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // id, password를 저장
    private fun saveIdPassword() {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit() // 수정모드
        edit.putString("idPasswordHistory", EditID.text.toString() + EditPassword.text.toString())
        edit.putString("passwordIdCalResult", EditPassword.text.toString() + EditID.text.toString())
        edit.apply()
    }

}