package jp.ac.asojuku.kadai1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit { clear() }
    }
    override fun onResume() {
        super.onResume()

        tora.setOnClickListener { onJankenButtonTapped(it); }
        bba.setOnClickListener { onJankenButtonTapped(it); }
        busi.setOnClickListener { onJankenButtonTapped(it); }


    }
    fun onJankenButtonTapped(view: View?){
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("MY_HAND",view?.id)
        startActivity(intent)
    }
}
