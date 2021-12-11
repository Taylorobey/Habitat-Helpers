package com.example.habitathelpers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.fragment_learn.*



class LearnFragment(private val con: Context, private val position: Int) : androidx.fragment.app.Fragment(),
    YouTubePlayer.OnInitializedListener {
    private val petInfo = arrayListOf(R.string.pythonInfo, R.string.leopardGeckoInfo, R.string.crestedGeckoInfo, R.string.beardedDragonInfo)
    private val videoList = arrayListOf("IQxve7yPHCY", "4g_vMdx25LM", "ToTqJ3oqQgU", "jyt7o-jmPNM")

    //ignore errors, youtube API incompatibility with android studio automatic error checker
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentYoutubeView: View = inflater.inflate(R.layout.fragment_learn, container, false)
        val mYoutubePlayerFragment = YouTubePlayerSupportFragment()
        mYoutubePlayerFragment.initialize("AIzaSyBxNrCSkoL1ZyPWSCmasl0Juh6h7-nStMM", this)
        val fragmentManager = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.youtube_fragment, mYoutubePlayerFragment)
        fragmentTransaction.commit()
        return fragmentYoutubeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = resources.getString(petInfo[position])
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {
            player.cueVideo(videoList[position])
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        result: YouTubeInitializationResult
    ) {
        if (result.isUserRecoverableError) {
            result.getErrorDialog(this.activity, 1).show()
        } else {
            Toast.makeText(
                this.activity,
                "YouTubePlayer.onInitializationFailure(): $result",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        fun newInstance(con: Context, pos: Int) : LearnFragment{
            return LearnFragment(con, pos)
        }
    }
}

