package com.instant.commonproj.ui.indicatorbottomnav

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.instant.commonproj.R

class IndicatorBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val onNavigationItemSelectedListeners = mutableListOf<OnNavigationItemSelectedListener>()

    init {
        super.setOnNavigationItemSelectedListener(this)
    }

    override fun setOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener?) {
        if (listener != null) addOnNavigationItemSelectedListener(listener)
    }

    fun addOnNavigationItemSelectedListener(listener: OnNavigationItemSelectedListener) {
        onNavigationItemSelectedListeners.add(listener)
    }

    fun addOnNavigationItemSelectedListener(listener: (Int) -> Unit) {
        addOnNavigationItemSelectedListener(OnNavigationItemSelectedListener {
            for (i in 0 until menu.size()) if (menu.getItem(i) == it) listener(i)
            false
        })
    }

    override fun onNavigationItemSelected(item: MenuItem) = onNavigationItemSelectedListeners
        .map { it.onNavigationItemSelected(item) }
        .fold(false) { acc, it -> acc || it }

}