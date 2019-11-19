package app.layout.motion.motionlayoutexample

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout


class ComplexAnimationActivity: AppCompatActivity(), MotionLayout.TransitionListener {

    private val motionLayout by lazy {
        findViewById<MotionLayout>(R.id.motionLayout)
    }
    private val favImage by lazy {
        findViewById<ImageView>(R.id.favImage)
    }

    private val seenImage by lazy {
        findViewById<ImageView>(R.id.seenImage)
    }
    private val shareImage by lazy {
        findViewById<ImageView>(R.id.shareImage)
    }

    private val mainImage2 by lazy {
        findViewById<ImageView>(R.id.mainImage2)
    }

    var layoutId = 0

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        if(p0 == null)
            return
        if(layoutId == R.layout.complex_animation_example && p0.currentState != -1)
            doBounceAnimation(mainImage2)

        if(layoutId == R.layout.multiple_animation_example && p0.currentState != -1){
            doBounceAnimation(favImage)
            doBounceAnimation(shareImage)
            doBounceAnimation(seenImage)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId = intent.getIntExtra("layoutId", R.layout.complex_animation_example)
        setContentView(layoutId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
        }
        motionLayout.setTransitionListener(this)
    }

    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationY", 0f, -10f, 0f)
        animator.interpolator = BounceInterpolator()
        animator.duration = 1000
        animator.start()
    }
}