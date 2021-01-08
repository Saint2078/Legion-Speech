package cn.darkfog.dialog_manager.msft

import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import cn.darkfog.foundation.arch.AppContextLinker.context
import com.bmwgroup.apinext.china_speech_poc.common.media.ExoPlayerEventListener
import com.google.android.exoplayer2.Player.REPEAT_MODE_OFF
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.ExoMediaCrypto
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util


object MediaManager {

    private lateinit var browser: MediaBrowserCompat
    private lateinit var resolveInfoList: List<ResolveInfo>

    private var mediaControllerCompat: MediaControllerCompat? = null
    private var mediaController: MediaControllerCompat.TransportControls? = null
    private var isConnecting = false
    private var connected = false
    private var connectTryCount = 0
    private var playerEventListener = ExoPlayerEventListener()

    private val player = SimpleExoPlayer.Builder(context).build().apply {
        repeatMode = REPEAT_MODE_OFF
        playWhenReady = true

        addListener(playerEventListener)
    }

    private val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
        context,
        null,
        DefaultHttpDataSourceFactory(
            Util.getUserAgent(context, context.packageName),
            null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    )

    fun postAudio(audioList: List<String>, onAudioDone: () -> Unit) {
        playerEventListener.onAudioDone = onAudioDone
        if (audioList.isEmpty()) {
            onAudioDone()
            return
        }
        if (player.isPlaying || player.isLoading) {
            player.stop(true)
        }
        val concatenatedSource = ConcatenatingMediaSource()
        audioList.forEach { concatenatedSource.addMediaSource(createMediaSource(it)) }
        player.prepare(concatenatedSource)
    }

    fun release() {
        browser.disconnect()
        player.removeListener(playerEventListener)
        playerEventListener.onAudioDone = null
        player.release()
    }

    private var drmSessionManager: DrmSessionManager<ExoMediaCrypto> =
        DrmSessionManager.getDummyDrmSessionManager()

    private fun createMediaSource(audioUri: String): MediaSource {
        //判断音频类型（天猫目前返回的有m3u8,目前没有想到好的判断文件类型的方法）
        if (audioUri.contains("m3u8")) {
            return HlsMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(Uri.parse(audioUri))
        }
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(audioUri))

    }
}