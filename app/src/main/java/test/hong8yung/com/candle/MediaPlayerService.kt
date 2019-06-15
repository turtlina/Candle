package test.hong8yung.com.candle

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.MediaSessionManager
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class MediaPlayerService : Service(), AudioManager.OnAudioFocusChangeListener, Player.EventListener{

    private var audioManager: AudioManager? = null
    private var notificationManager: PlayerNotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //notificationManager = PlayerNotificationManager(context.ser)

    }
    override fun onAudioFocusChange(focusChange: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private var mSession : MediaSessionCompat? = null
    public final val ACTION_PLAY : String = "action_play"
    public final val ACTION_PAUSE : String = "action_pause"
    public final val ACTION_REWIND : String = "action_rewind"
    public final val ACTION_FAST_FORWARD : String = "action_fast_forward"
    public final val ACTION_NEXT : String = "action_next"
    public final val ACTION_PREVIOUS : String = "action_previous"
    public final val ACTION_STOP : String = "action_stop"

    private var mMediaPlayer : SimpleExoPlayer? = null
    private var nManager : MediaSessionManager? = null
    private var mController : MediaController? = null

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if(mSession == null){
        }

        return super.onUnbind(intent)
    }

}