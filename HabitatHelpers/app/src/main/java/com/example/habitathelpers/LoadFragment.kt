package com.example.habitathelpers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_load.*

class LoadFragment : androidx.fragment.app.Fragment() {

    // TODO: user interface to choose saved habitat and load it into editor

    private var mListener: OnLoadInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoadInteractionListener) {
            mListener = context
            Log.i("onAttach: ", "context = " + context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {
        fun newInstance() : LoadFragment{
            return LoadFragment()
        }
    }

    interface OnLoadInteractionListener {
    }

}
