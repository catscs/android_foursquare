package es.webandroid.foursquare.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.foursquare.R
import es.webandroid.foursquare.core.extensions.loadFromUrl
import es.webandroid.foursquare.core.platform.BaseFragment
import es.webandroid.foursquare.databinding.FragmentVenueBinding
import es.webandroid.foursquare.domain.model.Venue

@AndroidEntryPoint
class VenueFragment : BaseFragment() {

    private val viewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentVenueBinding? = null
    private val binding get() = _binding!!

    private val args: VenueFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVenueDetail(args.venueId)
        viewModel.event.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun updateUi(event: DetailViewModel.Event) {
        when (event) {
            is DetailViewModel.Event.Content -> updateDetails(event.venue)
        }
    }

    private fun updateDetails(venue: Venue) {
        binding.venueImage.loadFromUrl(venue.photoUrl)
        binding.nameTextView.text = venue.name
        binding.addressTextView.text = venue.address
        binding.categoriesTextView.text = venue.categories

        venue.rating?.let { rating ->
            binding.ratingTextView.apply {
                text = rating.toString()
                visibility = View.VISIBLE
            }
        }

        venue.contactInfo?.let { contactInfo ->
            contactInfo.phone?.let {
                binding.phoneTextView.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }

            contactInfo.twitter?.let {
                showText(binding.twitterTextView, it, R.string.twitter)
            }

            contactInfo.instagram?.let {
                showText(binding.instagramTextView, it, R.string.instagram)
            }

            contactInfo.facebook?.let {
                showText(binding.facebookTextView, it, R.string.facebook)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showText(view: TextView, text: String, @StringRes prefix: Int) {
        view.apply {
            visibility = View.VISIBLE
            this.text = "${resources.getString(prefix)}: $text"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
