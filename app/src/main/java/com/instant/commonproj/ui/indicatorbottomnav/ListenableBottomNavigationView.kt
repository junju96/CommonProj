package com.instant.commonproj.ui.indicatorbottomnav

import android.content.Context
import android.util.AttributeSet
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class ListenableBottomNavigationView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr), NavigationBarView.OnItemSelectedListener {

    private val onNavigationItemSelectedListeners = mutableListOf<NavigationBarView.OnItemSelectedListener>()

    init {
        super.setOnItemSelectedListener(this)
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        if (listener != null) addOnNavigationItemSelectedListener(listener)
    }

    fun addOnNavigationItemSelectedListener(listener: NavigationBarView.OnItemSelectedListener) {
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
