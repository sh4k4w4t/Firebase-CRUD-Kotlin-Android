package sh4k4w4t.github.io.firebasewithkotlinandroid

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainActivityAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    private var dataSet = mutableListOf<User>()
    private var context: Context? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSet(list: MutableList<User>, context: Context) {
        this.dataSet = list
        this.context = context
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView
        var firstName: TextView
        var lastName: TextView
        var updateUserData: ImageView
        var fullRow: LinearLayout
        var deleteUserData: ImageView

        init {
            id = view.findViewById(R.id.riId)
            firstName = view.findViewById(R.id.riFN)
            lastName = view.findViewById(R.id.riLN)
            updateUserData = view.findViewById(R.id.riUpdate)
            deleteUserData = view.findViewById(R.id.riDelete)
            fullRow = view.findViewById(R.id.fullRow)
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
        viewHolder.fullRow.setOnClickListener {
            listener.onItemClickForUpdate(dataSet[position])
        }
        viewHolder.updateUserData.setOnClickListener {
            listener.onItemClickForUpdate(dataSet[position])
        }
        viewHolder.deleteUserData.setOnClickListener {
            listener.onItemClickForDelete(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size
}

