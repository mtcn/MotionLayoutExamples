package app.layout.motion.motionlayoutexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: MainAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private val mAdapterData: Array<MainAdapter.Demo> = arrayOf(
            MainAdapter.Demo("Basic Collapsing Toolbar", R.layout.collapsing_toolbar),
            MainAdapter.Demo("Basic Collapsing Toolbar 2", R.layout.collapsing_toolbar_2),
            MainAdapter.Demo("Collapsing Toolbar with Cover Example", R.layout.collapsing_toolbar_with_cover_image),
            MainAdapter.Demo("Basic Keyframe Example", R.layout.basic_key_frame_example),
            MainAdapter.Demo("Basic Keyframe Example 2", R.layout.basic_key_frame_example_2),
            MainAdapter.Demo("Notification Center Example", R.layout.notification_center_example),
            MainAdapter.Demo("ViewPager Example", R.layout.viewpager_example, ViewPagerActivity::class.java)

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

    fun start(activity: Class<*>, layoutFileId: Int) {
        val intent = Intent(this, activity).apply {
            putExtra("layoutId", layoutFileId)
        }
        startActivity(intent)
    }
}
