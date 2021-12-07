package com.example.habitathelpers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*

import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : androidx.fragment.app.Fragment() {

    private var mListener: MainFragment.OnMainInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set listener for create button
        button_new_hab.setOnClickListener{
            onCreatePressed(it)
        }
        //set listener for load button
        button_load_hab.setOnClickListener{
            onLoadPressed(it)
        }
    }

    fun onCreatePressed(uri: View) {
        if (mListener != null) {
            mListener?.onCreateInteraction(uri)
        }
    }

    fun onLoadPressed(uri: View) {
        if (mListener != null) {
            mListener?.onLoadInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainInteractionListener) {
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
        fun newInstance() : LoginFragment{
            return LoginFragment()
        }
    }

    interface OnMainInteractionListener {
        fun onCreateInteraction(uri: View)
        fun onLoadInteraction(uri: View)
    }
}
