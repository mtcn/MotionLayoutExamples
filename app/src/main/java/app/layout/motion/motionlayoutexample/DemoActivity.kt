package app.layout.motion.motionlayoutexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DemoActivity: AppCompatActivity() {
    var motionLayout : View? = null
    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layoutId", R.layout.collapsing_toolbar)
        setContentView(layout)
        motionLayout = findViewById(R.id.motionLayout)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView!!.apply {
            setHasFixedSize(true)
            adapter = DummyListAdapter()
            layoutManager = LinearLayoutManager(this@DemoActivity)
        }


//        val doShowPaths = intent.getBooleanExtra("showPaths", false)
//        (motionLayout as? MotionLayout)?.setShowPaths(doShowPaths)
    }
}