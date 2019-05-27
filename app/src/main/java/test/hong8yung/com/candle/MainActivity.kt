package test.hong8yung.com.candle

import android.content.Context
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import android.os.StrictMode
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer:MediaPlayer
    private lateinit var runnable:Runnable
    private var handler:Handler = Handler()
    private var pause:Boolean = false

    val wifiManager = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiLock: WifiManager.WifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the media player
        playBtn.setOnClickListener{
            if(pause){
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(this,"media playing", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "this is media playing msg")
            }else{
//                mediaPlayer = MediaPlayer.create(applicationContext, R.raw.sample)
//                mediaPlayer.start()
                if(!wifiLock.isHeld()) wifiLock.acquire()
                Toast.makeText(this,"media playing", Toast.LENGTH_SHORT).show()
                Log.d("Tag", "this is else position")
                mediaPlayer = MediaPlayer()
                mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
                mediaPlayer.setOnPreparedListener { mp ->
                    mp.start()
                    Log.d("TAG", "[+] OnPrepared!")
                }
                try{
                    mediaPlayer.setDataSource("http://urban180.com/wp-content/uploads/2017/09/olaide-Bambi_urban180.com_-1.mp3")

                    mediaPlayer.prepare() // prepareasync()
                }catch (e:IOException){
                    Log.d("TAG","The file does not exist"+e)
                    Toast.makeText(this,"Thei file does not exist", Toast.LENGTH_SHORT).show()
                }
            }
            initiallizeSeekBar()
            playBtn.isEnabled = false
            pauseBtn.isEnabled = true
            stopBtn.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                Toast.makeText(this, "end", Toast.LENGTH_SHORT).show()
            }
        }

        // Pause the media player
        pauseBtn.setOnClickListener{
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                pause = true
                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = true
                Toast.makeText(this, "media pause", Toast.LENGTH_SHORT).show()
            }
        }

        // Stop the media player
        stopBtn.setOnClickListener{
            if(mediaPlayer.isPlaying || pause.equals(true)){
                pause = false
                seek_bar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                playBtn.isEnabled = true
                pauseBtn.isEnabled = false
                stopBtn.isEnabled = false
                tv_pass.text = ""
                tv_due.text = ""
                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }
            if(wifiLock.isHeld()) wifiLock.release()
        }

        // Seek bar change listener
        seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    // Method to initiallize seek bar and audio stats
    private fun initiallizeSeekBar(){
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

}

// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds:Int
    get(){
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get(){
        return this.currentPosition / 1000
    }
