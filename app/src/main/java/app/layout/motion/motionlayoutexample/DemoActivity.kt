package app.layout.motion.motionlayoutexample

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DemoActivity : AppCompatActivity(), MotionLayout.TransitionListener {
    var motionLayout: MotionLayout? = null
    var recyclerView: RecyclerView? = null
    var layout = R.layout.collapsing_toolbar
    var titleTextView: TextView? = null
    private var mArgbEvaluator = ArgbEvaluator()
    private var startColor = Color.parseColor("#f48930")
    private var endColor = Color.parseColor("#b55fa3")
    private var currentColor = Color.parseColor("#b55fa3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = intent.getIntExtra("layoutId", R.layout.collapsing_toolbar)
        setTheme()
        setContentView(layout)
        initViews()
        recyclerView!!.apply {
            setHasFixedSize(true)
            adapter = DummyListAdapter()
            layoutManager = LinearLayoutManager(this@DemoActivity)
        }

        motionLayout!!.setTransitionListener(this)

//        val doShowPaths = intent.getBooleanExtra("showPaths", false)
//        (motionLayout as? MotionLayout)?.setShowPaths(doShowPaths)
    }

    private fun setTheme(){
        //todo fullscreen
        setTheme(R.style.LightTheme)
    }
    private fun initViews() {
        motionLayout = findViewById(R.id.motionLayout)
        recyclerView = findViewById(R.id.recyclerView)

        if (layout == R.layout.collapsing_toolbar_2) {
            titleTextView = findViewById(R.id.title)
        }
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        if (p0 == null)
            return

        if (layout == R.layout.collapsing_toolbar_2) {
            currentColor = mArgbEvaluator.evaluate(p3, startColor, endColor) as Int
            titleTextView?.setTextColor(currentColor)
        }


    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

}