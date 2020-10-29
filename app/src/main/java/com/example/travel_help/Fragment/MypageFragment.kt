package com.example.travel_help.Fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.DrawableWrapper
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.travel_help.R


class MypageFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_setting, rootKey)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDivider(ColorDrawable(Color.GRAY))
        setDividerHeight(1)
    }
}