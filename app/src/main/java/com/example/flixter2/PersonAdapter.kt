package com.example.flixter2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


const val PERSON_EXTRA = "PERSON_EXTRA"
private const val TAG = "PersonAdapter"

class PersonAdapter(private val people: List<PopularPerson>) :
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        ViewCompat.setTransitionName(holder.itemView.findViewById(R.id.profile_image), person.name)
        holder.bind(person)
    }

    override fun getItemCount() = people.size

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val name = itemView.findViewById(R.id.name) as TextView
        private val profileImage = itemView.findViewById(R.id.profile_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(person: PopularPerson) {
            name.text = person.name

            val radius = 30
            Glide.with(itemView.context)
                .load(("https://image.tmdb.org/t/p/w500/"+person.profile_path))
                .centerInside()
                .transform(RoundedCorners(radius))
                .into(profileImage)
        }

        override fun toString(): String {
            return name.toString()
        }

        override fun onClick(v: View?) {
            val person = people[absoluteAdapterPosition]


            val intent = Intent(itemView.context, PersonActivity::class.java)
            intent.putExtra(PERSON_EXTRA, person)

            itemView.context.startActivity(intent)
        }
    }
}