package test.hong8yung.com.candle

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if(intent.hasExtra("nameKey")){
            textview.text = intent.getStringExtra("nameKey")
        }else{
            Toast.makeText(this, "전달된 이름 없음음", Toast.LENGTH_SHORT).show()
        }
        Log.d("TAG", "this is subActivity")
        val textView = findViewById<TextView>(R.id.textview).apply {
            text = "test for flow test..."
        }
        textview.text = "test for flow test..../and this is second"
    }
}