package com.example.travel_help.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.DrawableWrapper
import android.os.Bundle
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceScreen
import com.example.travel_help.R


class MypageFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener{
    private var LOGIN: String = "login"
    private var LOGIN_STATE: Boolean = true
    lateinit var prefs: SharedPreferences
    lateinit var loginPreference: Preference



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_setting, rootKey)

//        if(rootKey==null){
//            loginPreference = findPreference("sound_list")
//
//            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//            if (!prefs.getString("sound_list", "").equals("")) {
//                loginPreference?.setSummary(prefs.getString("sound_list", "카톡"));
//            }
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDivider(ColorDrawable(Color.GRAY))
        setDividerHeight(1)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
//        val connectionPref: Preference = findPreference(key!!)
//        if (connectionPref is PreferenceScreen) {
//
//        }
    }


}