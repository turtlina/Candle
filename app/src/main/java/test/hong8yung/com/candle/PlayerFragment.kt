package test.hong8yung.com.candle

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment(){
    private var exoplayer:SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    private val trackSelectorFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory()
    private val trackSelector:TrackSelector = DefaultTrackSelector(trackSelectorFactory)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Get the custom view for this fragment layout
        val view = inflater!!.inflate(R.layout.fragment_player, container, false)

        //initializePlayer()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
    }
    private fun initializePlayer() {
        exoplayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector)
        Log.d("TAG", exoplayer.toString())
        player_view.player = exoplayer

        val userAgent = Util.getUserAgent(activity, "Exo")
        val mediaUri = Uri.parse("http://urban180.com/wp-content/uploads/2017/09/olaide-Bambi_urban180.com_-1.mp3")
        val mediaSource = ExtractorMediaSource(
            mediaUri,
            DefaultDataSourceFactory(activity, userAgent),
            DefaultExtractorsFactory(),
            null,
            null)

        exoplayer?.prepare(mediaSource)

        val componentName = ComponentName(activity, "Exo")
        mediaSession = MediaSessionCompat(activity, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        if(exoplayer != null){
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
