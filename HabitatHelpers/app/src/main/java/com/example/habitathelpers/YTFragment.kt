package com.example.habitathelpers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.example.habitathelpers.databinding.FragmentYoutubeBinding


class YTFragment : Fragment(), YouTubePlayer.OnInitializedListener {
    private var _binding: FragmentYoutubeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onViewCreated(requireView(), savedInstanceState)

        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val youTubePlayerFragment = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment
        youTubePlayerFragment.initialize(getString(R.string.api), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if(player == null) return
        if(wasRestored) {
            player.play()
        } else {
            player.cueVideo("HzeK7g8cD0Y")
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.d("Youtube Player", "Failed to initialize");
    }


}



/*
class YTFragment(context: Context) : Fragment(), YouTubePlayer.OnInitializedListener{
    val api_key = "AIzaSyBxNrCSkoL1ZyPWSCmasl0Juh6h7-nStMM"
    val ytPlayer = childFragmentManager.findFragmentById(R.id.ytPlayer) as YouTubePlayerFragment

    private var _binding: FragmentYoutubeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ytPlayer.initialize(api_key, object : YouTubePlayer.OnInitializedListener{
    }
    val initList = YouTubePlayer.OnInitializedListener{
            // Implement two methods by clicking on red error bulb
            // inside onInitializationSuccess method
            // add the video link or the
            // playlist link that you want to play
            // In here we also handle the play and pause functionality
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo("HzeK7g8cD0Y")
                player?.play()
            }

            // Inside onInitializationFailure
            // implement the failure functionality
            // Here we will show toast
            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@MainActivity , "Video player Failed" , Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYoutubeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val youTubePlayerFragment = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment
        youTubePlayerFragment.initialize(api_key, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        p2: Boolean
    ) {
        player?.loadVideo("HzeK7g8cD0Y")
        player?.play()
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Toast.makeText(context , "Video player Failed" , Toast.LENGTH_SHORT).show()
    }

}*/
