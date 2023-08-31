package sh4k4w4t.github.io.firebasewithkotlinandroid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainActivityAdapter : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(list: MutableList<User>) {
        this.dataSet = list
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView
        var firstName: TextView
        var lastName: TextView
        var updateImageView: ImageView

        init {
            id = view.findViewById(R.id.riId)
            firstName = view.findViewById(R.id.riFN)
            lastName = view.findViewById(R.id.riLN)
            updateImageView = view.findViewById(R.id.riUpdate)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_items, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.id.text = dataSet[position].id.toString()
        viewHolder.firstName.text = dataSet[position].first_name
        viewHolder.lastName.text = dataSet[position].last_name
        viewHolder.updateImageView.setOnClickListener {
            val dataPosition = dataSet.get(position)
            DataController.instance?.itemClickInterface?.onModelDataClick = dataPosition
        }
    }

    override fun getItemCount() = dataSet.size
}

