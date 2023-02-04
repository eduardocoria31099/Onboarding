package com.example.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

object ExtendedFunctions {
    fun Activity.nextActivity(activity: Activity) {
        Intent(this, activity::class.java).apply {
            startActivity(this)
            finish()
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

    fun Context.materialAlertDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("accept", null).setCancelable(false)
            .show()
    }

    fun Context.materialAlertDialogWhitClick(
        title: String,
        message: String,
        onClickDialog: OnClickDialog
    ) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Cancelar") { dialog, which ->
                onClickDialog.clickCancel()
            }
            .setPositiveButton("Aceptar") { dialog, which ->
                onClickDialog.clickAccept()
            }
            .setCancelable(true)
            .show()
    }

    interface OnClickDialog {
        fun clickAccept()
        fun clickCancel()
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.beginTransaction().apply {
            replace(frameId, fragment)
            commit()
        }
    }

    fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    action.invoke(it)
                }
            }
        }
    }

    //Bitmap to string
    fun Bitmap.bitmapToString(): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}