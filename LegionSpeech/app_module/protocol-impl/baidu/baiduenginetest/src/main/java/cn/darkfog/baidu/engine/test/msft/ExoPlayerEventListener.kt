package com.bmwgroup.apinext.china_speech_poc.common.media

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player

class ExoPlayerEventListener(var onAudioDone: (() -> Unit?)? = null) : Player.EventListener {

    override fun onSeekProcessed() {

    }

    override fun onLoadingChanged(isLoading: Boolean) {

    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onRepeatModeChanged(repeatMode: Int) {

    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_ENDED) {
            onAudioDone?.invoke()
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        onAudioDone?.invoke()
    }
}