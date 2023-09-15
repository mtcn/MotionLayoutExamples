package app.layout.motion.motionlayoutexample

import android.animation.ArgbEvaluator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

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
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    fun getScreenHeight(windowManager: WindowManager?): Int {
        if (windowManager == null) {
            return 0
        }
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = intent.getIntExtra("layoutId", R.layout.collapsing_toolbar)
        exampleType = intent.getIntExtra("type", 0)
        setTheme()
        setContentView(layout)
        initViews()

          // Initialize the bottom sheet behavior
        val bottomSheet: LinearLayout? = findViewById(R.id.bottom_sheet)
        if (null!=bottomSheet) {
        val layoutParams = bottomSheet?.layoutParams
        layoutParams?.height = getScreenHeight(windowManager)
        bottomSheet?.layoutParams = layoutParams

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//        bottomSheetBehavior.isDraggable = false
        // Set the initial state of the bottom sheet
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED

        // Set the peek height (the height of the bottom sheet when it is collapsed)
        bottomSheetBehavior?.peekHeight = 100
        bottomSheetBehavior?.isFitToContents = false
        // Set the half expanded ratio (the ratio of the height of the bottom sheet when it is half expanded)
        bottomSheetBehavior?.halfExpandedRatio = 0.6f

        val shapeAppearanceModel = ShapeAppearanceModel.Builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 36f)
            .setTopRightCorner(CornerFamily.ROUNDED, 36f)
            .build()

        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply{
            fillColor = ColorStateList.valueOf(Color.WHITE) // Set the desired color
        }
        bottomSheet.background = materialShapeDrawable

        // Set the bottom sheet callback to handle changes in state
        bottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Handle state change

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                    bottomSheetBehavior.isDraggable=false
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle slide
            }
        })
        }

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

        // Intercept the recyclerView onTouch event
        recyclerView?.setOnTouchListener { _, event ->
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                // Disable the recyclerView scroll event when the BottomSheet is in Half Expand
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }

        if (layout == R.layout.collapsing_toolbar_2) {
            titleTextView = findViewById(R.id.title)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
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