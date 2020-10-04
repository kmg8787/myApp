package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.EditID
import java.util.*

class history : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setTitle("체수분 계산기")

        val pref = getSharedPreferences("pref", 0)
        var passwordId = pref.getString("passwordIdCalResult", "0") // 오늘 마셔야할 총 물의 양 key
        var idPassword = pref.getString("idPasswordHistory", "") // 물 마신 양 히스토리 내역 key

        var calResult = pref.getString(passwordId, "0") // 오늘 마셔야할 총 물의 양
        var compare = Integer.parseInt(calResult) // 계산을 위해 int형으로 변환
        var historyStr = pref.getString(idPassword, "") // 물 마신 양 히스토리 내역
        var drinkWaterList = mutableListOf("")
        if(historyStr == null){
            drinkWaterList = mutableListOf()
        }else{
            var arrDrinkWater = historyStr.replace("[","").replace("]","").split(",") // id,password 가져오기
            drinkWaterList = mutableListOf()
            val cal = Calendar.getInstance()
            var today = cal.get(Calendar.YEAR).toString() + "-" + cal.get(Calendar.MONTH).toString() + "-" + cal.get(Calendar.DATE).toString()
            for(drinkWater in arrDrinkWater){
                if(!(drinkWater == null || drinkWater.equals(""))){
                    //editDrinkWater.setText(today)
                    if(drinkWater.contains(today)) {
                        var tempArr = drinkWater.split("ml")
                        var compare2 = tempArr[0].replace(" ", "")
                        if (!(compare2 == null || compare2.equals(""))) {
                            //editDrinkWater.setText(compare2)
                            compare = compare - Integer.parseInt(compare2)
                        }
                    }
                    }
            }
        }

        if(compare > 0){
            tvGuid.setText("앞으로" + compare + "ml 더 섭취하셔야 합니다")
        }
        else if(compare == 0){
            tvGuid.setText("알맞게 섭취하셨습니다")
        }
        else if(compare < 0){
            tvGuid.setText("권장량보다 많이 섭취하셨습니다")
        }
        else{
            tvGuid.setText("오류가 있으므로 관리자에게 문의해주세요")
        }

        //리스트 보여주기
        getDrinkWaterList()

        // 로그아웃 버튼 클릭 시
        BtnLogout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 다시 계산하기 버튼 클릭 시
        BtnCalAgain.setOnClickListener{
            val intent = Intent(this, cal::class.java)
            startActivity(intent)
        }

        // 오늘 마신 물의 양 저장하기 버튼 클릭 시
        BtnSaveDrinkWater.setOnClickListener{
            saveDrinkWater()
        }
    }

    // 오늘 마신 물의 양 저장
    private fun saveDrinkWater() {
        val pref = getSharedPreferences("pref", 0)
        var idPassword = pref.getString("idPasswordHistory", "")  // 물 마신 양 히스토리 내역 key

        var historyStr = pref.getString(idPassword, "") // 물 마신 양 히스토리 내역
        var drinkWaterList = mutableListOf("")
        if(historyStr == null){
            drinkWaterList = mutableListOf()
        }else{
            var arrDrinkWater = historyStr.replace("[","").replace("]","").split(",") // id,password 가져오기
            drinkWaterList = mutableListOf()
            for(drinkWater in arrDrinkWater){
                drinkWaterList.add(0, drinkWater)
            }
        }

        val edit = pref.edit() // 수정모드
        val cal = Calendar.getInstance()
        drinkWaterList.add(0,editDrinkWater.text.toString() + "ml (" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE) + ")")

        // 리스트 저장
        drinkWaterList.remove("")
        drinkWaterList.remove(" ")
        edit.putString(idPassword, drinkWaterList.toString().replace(" ",""))
        edit.apply()

        val intent = Intent(this, history::class.java)
        startActivity(intent)
    }

    // 물 마신 양 리스트 가져오기
    private fun getDrinkWaterList() {
        val pref = getSharedPreferences("pref", 0)
        var idPassword = pref.getString("idPasswordHistory", "") // 물 마신 양 히스토리 내역 key
        var historyStr = pref.getString(idPassword, "") // 물 마신 양 히스토리 내역
        var drinkWaterList = mutableListOf("")
        if(historyStr == null){
            drinkWaterList = mutableListOf()
        }else{
            var arrDrinkWater = historyStr.replace("[","").replace("]","").split(",") // id,password 가져오기
            drinkWaterList = mutableListOf()
            for(drinkWater in arrDrinkWater){
                drinkWaterList.add(0, drinkWater)
            }
        }
        historyList.adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, drinkWaterList)
    }
}