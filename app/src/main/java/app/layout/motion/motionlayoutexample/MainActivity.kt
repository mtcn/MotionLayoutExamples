package app.layout.motion.motionlayoutexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MainAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private val mAdapterData: Array<MainAdapter.Demo> = arrayOf(
        MainAdapter.Demo(
            "Basic Collapsing Toolbar",
            ExampleTypes.DEFAULT,
            R.layout.collapsing_toolbar
        ),
        MainAdapter.Demo(
            "Collapsing Toolbar w/ Text Interpolation",
            ExampleTypes.DEFAULT,
            R.layout.collapsing_toolbar_2
        ),
        MainAdapter.Demo(
            "Collapsing Toolbar w/ Cover Example",
            ExampleTypes.FULLSCREEN,
            R.layout.collapsing_toolbar_with_cover
        ),
        MainAdapter.Demo(
            "Collapsing Toolbar with BottomSheet " +
                    "Example",
            ExampleTypes.FULLSCREEN,
            R.layout.collapsing_toolbar_with_cover_bottomsheet
        ),
        MainAdapter.Demo(
            "Complex Animation Example",
            ExampleTypes.WITHOUT_RECYCLER_VIEW,
            R.layout.complex_animation_example,
            ComplexAnimationActivity::class.java
        ),
        MainAdapter.Demo(
            "Multiple Animation Example",
            ExampleTypes.FULLSCREEN,
            R.layout.multiple_animation_example,
            ComplexAnimationActivity::class.java
        ),
        MainAdapter.Demo(
            "Basic Keyframe Example",
            ExampleTypes.WITHOUT_RECYCLER_VIEW,
            R.layout.basic_key_frame_example
        ),
        MainAdapter.Demo(
            "Basic Keyframe Example 2",
            ExampleTypes.WITHOUT_RECYCLER_VIEW,
            R.layout.basic_key_frame_example_2
        ),
        MainAdapter.Demo(
            "Notification Center Example",
            ExampleTypes.DEFAULT,
            R.layout.notification_center_example
        ),
        MainAdapter.Demo(
            "ViewPager Example",
            ExampleTypes.VIEW_PAGER,
            R.layout.viewpager_example,
            ViewPagerActivity::class.java
        ),
        MainAdapter.Demo(
            "Youtube Fragment Transition Example",
            ExampleTypes.FRAGMENT,
            R.layout.youtube_fragment_transition_example,
            FragmentActivity::class.java
        )


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerViewProperties()
    }

    private fun initRecyclerViewProperties() {
        mAdapter = MainAdapter(mAdapterData)
        mLayoutManager = LinearLayoutManager(this)
        recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            setHasFixedSize(true)
        }

    }

    fun start(activity: Class<*>, layoutFileId: Int, types: ExampleTypes?, position: Int) {
        if (position > 4) {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, activity).apply {
                putExtra("layoutId", layoutFileId)
                putExtra("type", types?.ordinal ?: ExampleTypes.DEFAULT.ordinal)
            }
            startActivity(intent)
        }
    }
}
