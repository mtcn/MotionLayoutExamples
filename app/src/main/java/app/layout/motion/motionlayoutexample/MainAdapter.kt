package app.layout.motion.motionlayoutexample

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val data: Array<MainAdapter.Demo>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    data class Demo(val title: String, val type: ExampleTypes, val layout: Int = 0, val activity: Class<*> = DemoActivity::class.java) {
        constructor(title: String, type: ExampleTypes, activity: Class<*> = DemoActivity::class.java) : this(title, type, 0, activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layoutFileId = data[position].layout
        holder.activity = data[position].activity
        holder.exampleType = data[position].type
        holder.title.text = data[position].title
//        holder.rootLayout.background =
    }

    class ViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout) {
        var title = layout.findViewById(R.id.titleTextView) as TextView
        var rootLayout = layout.findViewById(R.id.rootLayout) as ConstraintLayout
        var layoutFileId = 0
        var activity: Class<*>? = null
        var exampleType: ExampleTypes? = null

        init {
            layout.setOnClickListener {
                val context = it?.context as MainActivity
                activity?.let {
                    context.start(it, layoutFileId, exampleType, layoutPosition)
                }

            }
        }
    }

}