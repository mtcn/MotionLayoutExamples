package app.layout.motion.motionlayoutexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DummyListAdapter : RecyclerView.Adapter<DummyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.dummy_list_item, parent, false) as ConstraintLayout)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: DummyListAdapter.ViewHolder, position: Int) {

    }

    class ViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout)

}