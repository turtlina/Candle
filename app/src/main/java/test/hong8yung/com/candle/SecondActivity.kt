package test.hong8yung.com.candle

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity :AppCompatActivity(){
    private var exoplayer: SimpleExoPlayer? = null
    private var playbackStateBuilder :PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initializePlayer()
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(baseContext, trackSelector)
        player_view_tmp.player = exoplayer

        val userAgent = Util.getUserAgent(baseContext, "Exo")
        val mediaUri = Uri.parse("http://urban180.com/wp-content/uploads/2017/09/olaide-Bambi_urban180.com_-1.mp3")
        val mediaSource = ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(baseContext, userAgent),DefaultExtractorsFactory(), null, null)

        exoplayer?.prepare(mediaSource)

        val componentName = ComponentName(baseContext, "Exo")
        mediaSession = MediaSessionCompat(baseContext, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer(){
        if(exoplayer != null){
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }
}
