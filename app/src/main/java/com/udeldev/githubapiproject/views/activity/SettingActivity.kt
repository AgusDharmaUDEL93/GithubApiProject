package com.udeldev.githubapiproject.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.udeldev.githubapiproject.R
import com.udeldev.githubapiproject.controllers.view_models.SettingViewModel
import com.udeldev.githubapiproject.helper.SettingPreferences
import com.udeldev.githubapiproject.helper.ViewModelFactorySettingPreferences
import com.udeldev.githubapiproject.helper.dataStore


class SettingActivity : AppCompatActivity() {

    private lateinit var switchTheme: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initComponent()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelFactorySettingPreferences(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
        
    }

    private fun initComponent() {
        switchTheme = findViewById(R.id.switch_theme)
    }
}