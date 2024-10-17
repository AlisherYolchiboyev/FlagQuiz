package com.example.flagquiz

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.flagquiz.madels.Flag
import java.util.Random
import kotlin.collections.ArrayList
class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var flagArrayList: ArrayList<Flag>
    var count = 0
    var countryName = ""
    var buttonArrayList = ArrayList<Button>()

    lateinit var linMatn:LinearLayout
    lateinit var linBtn1:LinearLayout
    lateinit var linBtn2:LinearLayout
    lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linMatn = findViewById(R.id.lin_1_matn)
        linBtn1 = findViewById(R.id.lin_2_btn_1)
        linBtn2 = findViewById(R.id.lin_3_btn_2)
        image = findViewById(R.id.img_1)

        obyektYAratish()
        btnJoylaCount()
    }

    private fun obyektYAratish() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("uzbekistan", R.drawable.img))
        flagArrayList.add(Flag("amerika", R.drawable.img_1))
        flagArrayList.add(Flag("rassiya", R.drawable.img_2))
        flagArrayList.add(Flag("turkiya", R.drawable.img_3))
        flagArrayList.add(Flag("braziliya", R.drawable.img_4))
        flagArrayList.add(Flag("prtugaliya", R.drawable.img_5))
    }

    fun btnJoylaCount(){
        image.setImageResource(flagArrayList[count].image!!)
        linMatn.removeAllViews()
        linBtn1.removeAllViews()
        linBtn2.removeAllViews()
        countryName=""
        btnJoyla(flagArrayList[count].name)
    }

    private fun btnJoyla(countryName: String?) {
        val btnArray:ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5){
            linBtn1.addView(btnArray[i])
        }
        for (i in 6 .. 11){
            linBtn2.addView(btnArray[i])
        }

    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()

        for (c in countryName!!) {
            arrayText.add(c.toString())
        }
        if (arrayText.size < 12){
            val str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            while (arrayText.size <12 ) {
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()

            for (i in 0 until arrayText.size ) {
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
                button.text = arrayText[i]
                button.setOnClickListener(this)
                array.add(button)
            }
        return array
    }
    override fun onClick(v: View?) {
        val button1 = v as Button
        if (buttonArrayList.contains(button1)) {
            linMatn.removeView(button1)
            buttonArrayList.remove(button1)
            var hasC = false

            // Check in both button lists
            for (button in linBtn1.children) {
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (countryName.isNotEmpty()) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                    hasC = true
                    break
                }
            }

            for (button in linBtn2.children) {
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    if (!hasC && countryName.isNotEmpty()) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                    break
                }
            }

        } else {
            button1.visibility = View.INVISIBLE
            countryName += button1.text.toString().uppercase()
            val button2 = Button(this)
            button2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            button2.text = button1.text
            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            linMatn.addView(button2)
            matnTogri()
        }
    }

    private fun matnTogri() {
        if (countryName.uppercase() == flagArrayList[count].name?.uppercase()){
            Log.d("FlagGame", "Correct answer for: ${flagArrayList[count].name}")
            Toast.makeText(this, "Correct ✅ ", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size -1){
                count=0
            }else{
                count++
            }
            btnJoylaCount()
        }else{
            if (countryName.length == flagArrayList[count].name?.length){
                Log.d("FlagGame", "Wrong answer for: ${flagArrayList[count].name}")
                Toast.makeText(this, "Wrong ❌ ", Toast.LENGTH_SHORT).show()
                linMatn.removeAllViews()
                linBtn1.removeAllViews()
                linBtn2.removeAllViews()
                btnJoyla(flagArrayList[count].name)
                countryName = ""

            }
        }
    }

}