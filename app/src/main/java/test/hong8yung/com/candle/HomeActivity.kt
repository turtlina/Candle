package test.hong8yung.com.candle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val topMenuFragment = TopMenuFragment()
//        val playerFragment = PlayerFragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
//        transaction.replace(R.id.fragment_container, playerFragment)
//        transaction.addToBackStack(null)

//        transaction.commit()
    }
}
