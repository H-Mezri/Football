package com.mezri.football.ui.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mezri.football.R

/**
 * Abstract Base Fragment class
 */
abstract class BaseFragment : Fragment() {
    abstract fun getRootView(): View?
    protected fun displaySnackBar(messageId: Int = R.string.unknown_error, duration: Int = Snackbar.LENGTH_LONG) {
        getRootView()?.let { safeView ->
            Snackbar
                .make(safeView, getString(messageId), duration)
                .show()
        }
    }
}