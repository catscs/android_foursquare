package es.webandroid.foursquare.core.platform

import androidx.fragment.app.Fragment
import es.webandroid.foursquare.R
import es.webandroid.foursquare.core.error_handling.Failure
import es.webandroid.foursquare.core.error_handling.Failure.DBError
import es.webandroid.foursquare.core.error_handling.Failure.NetworkError.*
import es.webandroid.foursquare.core.extensions.toast

abstract class BaseFragment: Fragment() {

    fun handleFailure(failure: Failure?) {
        when (failure) {
            is Fatal -> toast(getString(R.string.failure_fatal_error))
            is Recoverable -> toast(getString(R.string.failure_recoverable_error))
            NoConnection -> toast(getString(R.string.failure_no_connection))
            DBError -> toast(getString(R.string.info_no_data_available))
        }
    }
}
