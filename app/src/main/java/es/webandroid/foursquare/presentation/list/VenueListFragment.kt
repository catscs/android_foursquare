package es.webandroid.foursquare.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.webandroid.foursquare.R
import es.webandroid.foursquare.core.extensions.hideKeyboard
import es.webandroid.foursquare.core.extensions.toast
import es.webandroid.foursquare.core.platform.BaseFragment
import es.webandroid.foursquare.databinding.FragmentVenueListBinding
import es.webandroid.foursquare.presentation.list.VenueListViewModel.Event


@AndroidEntryPoint
class VenueListFragment : BaseFragment() {

    private val viewModel by viewModels<VenueListViewModel>()
    private lateinit var adapter: VenueListAdapter

    private var _binding: FragmentVenueListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVenueListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFragmentViewCreated()
        configureView()
        viewModel.event.observe(viewLifecycleOwner, Observer(::updateUi))
        viewModel.failure.observe(viewLifecycleOwner, Observer(::handleFailure))
    }

    private fun configureView() {
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            isQueryRefinementEnabled = true
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchVenues(query)
                    hideKeyboard(requireActivity())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        adapter = VenueListAdapter(viewModel::onVenueClicked)
        binding.recycler.adapter = adapter
    }

    private fun updateUi(event: Event) {
        binding.progress.visibility = if (event is Event.Loading) View.VISIBLE else View.GONE

        when (event) {
            is Event.Content -> {
                adapter.submitList(event.venues)
            }
            is Event.Detail -> {
                val action = VenueListFragmentDirections.navigateToVenue(event.venueId)
                view?.findNavController()?.navigate(action)
            }
        }
    }
}
