package app.layout.motion.motionlayoutexample;

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

public class CustomBottomSheetBehavior<V : View>(context: Context, attrs: AttributeSet) : BottomSheetBehavior<V>(context, attrs) {
    private val TAG = "CustomBottomSheetBehavior"

    @SuppressLint("LongLogTag")
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        Log.i(TAG, "onInterceptTouchEvent, child: " + child + " MotionEvent:" + event)

        if (state == BottomSheetBehavior.STATE_EXPANDED) {
            return false
        }

        if (child is MotionLayout) {
            val shouldIntercept = child.progress < 1
            isDraggable = shouldIntercept
            return if (shouldIntercept) {
                super.onInterceptTouchEvent(parent, child, event)
            } else {
                false
            }
        }

        return super.onInterceptTouchEvent(parent, child, event)
    }


}