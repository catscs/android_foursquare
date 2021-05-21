package es.webandroid.foursquare.core.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider.NetworkState
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider.NetworkState.ConnectedState


fun NetworkState.hasInternet(): Boolean = (this as? ConnectedState)?.hasInternet == true

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Activity.hideKeyboard() {
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    val currentFocus = currentFocus
    if (currentFocus != null && inputManager != null) {
        inputManager.hideSoftInputFromWindow(
            currentFocus.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Fragment.hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    val currentFocus = activity.currentFocus
    if (currentFocus != null && inputManager != null) {
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun ImageView.loadFromUrl(url: String?) = Picasso.get().load(url).into(this)


inline fun <reified T : ViewModel> Fragment.obtainViewModel() =
    ViewModelProvider(this, this.defaultViewModelProviderFactory)[T::class.java]
