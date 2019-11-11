package jp.ac.asojuku.kadai1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    val tora = 0;val bba = 1;val busi = 2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("MY_HAND",0);
        val myHand:Int;
        myHand = when(id){
            R.id.tora -> {myHandImage.setImageResource(R.drawable.animal_tora);tora}
            R.id.bba -> {myHandImage.setImageResource(R.drawable.koshi_magari_smile_obaasan);bba}
            R.id.busi -> {myHandImage.setImageResource(R.drawable.bushi_yoroikabuto);busi}
            else -> tora;
        }
        val comHand = (Math.random() * 3).toInt()
        when(comHand){
            tora -> comHandImage.setImageResource(R.drawable.animal_tora);
            bba -> comHandImage.setImageResource(R.drawable.koshi_magari_smile_obaasan);
            busi -> comHandImage.setImageResource(R.drawable.bushi_yoroikabuto);
        }
        val gameResult = (comHand - myHand + 3) % 3
        when(gameResult){
            0 -> resultLabel.setText("あいこです")
            1 -> resultLabel.setText("あなたの勝ちです")
            2 -> resultLabel.setText("あなたの負けです")
        }
        backButton.setOnClickListener { finish() }
        saveData(myHand,comHand,gameResult)
    }
    private fun saveData(myHand: Int, comHand: Int,gameResult: Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNIG_STREAK_COUNT",0)
        val lastComHand = pref.getInt("LAST_COM_HAND",0)
        val lastGameResult = pref.getInt("GAME_RESULT",-1)

        val edtWinningStreakCount = when{
            (lastGameResult == 2 && gameResult == 2) -> (winningStreakCount + 1)
            else -> 0
        }
        val  editor = pref.edit()
        editor.putInt("GAME_COUNT",gameCount + 1)
            .putInt("WINNING_STREAK_COUNT",edtWinningStreakCount)
            .putInt("LAST_MY_HAND",myHand)
            .putInt("LAST_COM_HAND",comHand)
            .putInt("BEFORE_LAST_COM_HAND",lastComHand)
            .putInt("GAME_RESULT",gameResult)
            .apply()

    }


    private fun getHand():Int{
        var hand = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT",0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT",0)
        val lastMyHand = pref.getInt("LAST_MY_HAND",0)
        val lastComHand = pref.getInt("LAST_COM_HAND",0)
        val beforeLastComHand = pref.getInt("BEFORE_LAST_COM_HAND",0)
        val gameResult = pref.getInt("GAME_RESULT",-1)

        if(gameCount == 1){
            if(gameResult == 2){
                //前回の勝負が１回目で、コンピュータが勝った場合、
                //コンピュータは次に出す手を考える
                while (lastComHand == hand){
                    hand = (Math.random() * 3).toInt()
                }
            }else if (gameResult == 1){
                //前回の勝負が１回目で、コンピュータが負けた場合、
                //相手の出した手に勝つ手を出す
                hand = (lastMyHand - 1 + 3) % 3
            }
        }else if(winningStreakCount > 0){
            if(beforeLastComHand == lastComHand){
                //同じ手で連勝した場合は手を帰る
                while (lastComHand == hand){
                    hand = (Math.random() * 3).toInt()
                }
            }
        }
        return hand

    }



}
