package di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snowboardexperience.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.example.snowboardexperience.databinding.FragmentYouTubePlayerBinding



class YouTubePlayerFragment : Fragment(), YouTubePlayer.OnInitializedListener {

    companion object {
        private const val ARG_VIDEO_ID = "video_id"

        fun newInstance(videoId: String): YouTubePlayerFragment {
            val fragment = YouTubePlayerFragment()
            val args = Bundle()
            args.putString(ARG_VIDEO_ID, videoId)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var videoId: String
    private lateinit var binding: FragmentYoutubePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoId = it.getString(ARG_VIDEO_ID, "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_you_tube_player, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.youtubePlayerView.initialize("YOUR_YOUTUBE_API_KEY", this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
        if (!wasRestored) {
            player.loadVideo(videoId)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, error: YouTubeInitializationResult) {
        // Handle YouTube Player API initialization errors here
    }
}