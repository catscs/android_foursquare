package es.webandroid.foursquare.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.webandroid.foursquare.databinding.VenueRowBinding
import es.webandroid.foursquare.domain.model.Venue

class VenueListAdapter(private val clickListener: (String) -> Unit) : ListAdapter<Venue, VenueListAdapter.ViewHolder>(VenueDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: VenueRowBinding = VenueRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = getItem(position)
        holder.bindTo(venue)
        holder.itemView.setOnClickListener { clickListener(venue.id) }
    }

    class VenueDiffCallback : DiffUtil.ItemCallback<Venue>() {

        override fun areItemsTheSame(oldItem: Venue, newItem: Venue) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue) = oldItem == newItem
    }

    inner class ViewHolder(private val binding: VenueRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(venue: Venue) {
            binding.tvName.text = venue.name
            binding.tvAddress.text = venue.address
        }
    }
}
