package app.layout.motion.motionlayoutexample

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DemoActivity : AppCompatActivity(), MotionLayout.TransitionListener {

    var motionLayout: MotionLayout? = null
    var recyclerView: RecyclerView? = null
    var layout = R.layout.collapsing_toolbar
    var exampleType = 0
    var titleTextView: TextView? = null
    var lastProgress = 0f
    private var mArgbEvaluator = ArgbEvaluator()
    private var startColor = Color.parseColor("#f48930")
    private var endColor = Color.parseColor("#b55fa3")
    private var currentColor = Color.parseColor("#b55fa3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = intent.getIntExtra("layoutId", R.layout.collapsing_toolbar)
        exampleType = intent.getIntExtra("type", 0)
        setTheme()
        setContentView(layout)
        initViews()
        recyclerView!!.apply {
            setHasFixedSize(true)
            adapter = DummyListAdapter()
            layoutManager = LinearLayoutManager(this@DemoActivity)
        }

        // workaround for wrong state after view being clicked/touched on some points
        // https://issuetracker.google.com/issues/11280555
        motionLayout!!.transitionToEnd()
        Handler().postDelayed({motionLayout!!.apply{
            transitionToStart()
            setTransitionListener(this@DemoActivity)
        }}, 50)
    }

    private fun setTheme() {
        if (exampleType == ExampleTypes.FULLSCREEN.ordinal && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //todo hide navigation bar too.
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                navigationBarColor = ContextCompat.getColor(this@DemoActivity, R.color.black)
                decorView.systemUiVisibility = 0
            }
        } else
            setTheme(R.style.LightTheme)
    }

    private fun initViews() {
        motionLayout = findViewById(R.id.motionLayout)
        recyclerView = findViewById(R.id.recyclerView)

        if (layout == R.layout.collapsing_toolbar_2) {
            titleTextView = findViewById(R.id.title)
        }
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progress: Float) {
        if (p0 == null)
            return

        if (layout == R.layout.collapsing_toolbar_2) {
            currentColor = mArgbEvaluator.evaluate(progress, startColor, endColor) as Int
            titleTextView?.setTextColor(currentColor)
        }else if (layout == R.layout.collapsing_toolbar_with_cover){
            if (progress - lastProgress > 0 && Math.abs(progress - 1f) < 0.1f) {
                // from start to end
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (progress < 0.8f) {
                // from end to start
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    window?.decorView?.systemUiVisibility = 0
            }
            lastProgress = progress
        }


    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }
    
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }
}