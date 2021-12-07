package com.example.habitathelpers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : androidx.fragment.app.Fragment() {

    private var mListener: OnLoginInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set listener for login button
        button_login.setOnClickListener{
            onLoginPressed(it)
        }
        //set listener for register button
        button_reg.setOnClickListener{
            onRegisterPressed(it)
        }
    }

    fun onLoginPressed(uri: View) {
        if (mListener != null) {
            mListener?.onLoginInteraction(uri)
        }
    }

    fun onRegisterPressed(uri: View) {
        if (mListener != null) {
            mListener?.onRegisterInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginInteractionListener) {
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

    interface OnLoginInteractionListener {
        fun onLoginInteraction(uri: View)
        fun onRegisterInteraction(uri: View)
    }

}
