package net.basicmodel

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weeboos.permissionlib.PermissionRequest
import kotlinx.android.synthetic.main.activity_main.*
import net.fragment.AllMessageFragment
import net.fragment.SendMessageFragment
import net.utils.MessageManager
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECEIVE_SMS
    )
    private val title = arrayOf("All Message", "Send Message")
    private var views: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionRequest.getInstance().build(this)
            .requestPermission(object : PermissionRequest.PermissionListener {
                override fun permissionGranted() {
                    initView()
                }

                override fun permissionDenied(permissions: ArrayList<String>?) {
                    finish()
                }

                override fun permissionNeverAsk(permissions: ArrayList<String>?) {

                }

            }, permissions)
    }

    private fun initView() {
        views.add(AllMessageFragment())
        views.add(SendMessageFragment())
        tab.setViewPager(viewpager, title, this, views)
    }
}