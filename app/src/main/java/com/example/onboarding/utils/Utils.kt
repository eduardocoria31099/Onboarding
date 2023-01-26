package com.example.onboarding.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Utils {

    fun Activity.nextActivity(activity: Activity) {
        Intent(this, activity::class.java).apply {
            startActivity(this)
        }
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    fun View.show(): View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    //Hide view
    fun View.hide(): View {
        if (visibility != View.INVISIBLE) {
            visibility = View.INVISIBLE
        }
        return this
    }

    //Hide gone
    fun View.gone(): View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        this.requestFocus()
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun Context.materialAlertDialogBuilder(title: Int, message: Int) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("accept", null)
            .setCancelable(false)
            .show()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(frameId, fragment)
        fragmentTransaction.commit()
    }

}