package com.ocean.mouher.ui.profile.address

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.ocean.mouher.R
import com.ocean.mouher.shared.General.saveComesFromAddress

class AddressParentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)
        setContentView(R.layout.activity_address_parent)

        if (savedInstanceState == null) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(R.id.frame, AddressFragment(), "").commit()
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        saveComesFromAddress(true)
        finish()
    }
}