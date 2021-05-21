package es.webandroid.foursquare.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider
import es.webandroid.foursquare.databinding.ActivityMainBinding
import es.webandroid.foursquare.core.extensions.hasInternet
import es.webandroid.foursquare.core.extensions.toast
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    ConnectivityProvider.ConnectivityStateListener  {

    @Inject
    lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        if (!state.hasInternet()) {
            toast(getString(es.webandroid.foursquare.R.string.not_internet_available))
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityProvider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityProvider.removeListener(this)
    }
}
