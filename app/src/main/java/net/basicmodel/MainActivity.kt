package net.basicmodel

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weeboos.permissionlib.PermissionRequest
import kotlinx.android.synthetic.main.activity_main.*
import net.fragment.AllMessageFragment
import net.fragment.SendMessageFragment
import net.receiver.MessageReceiver
import java.util.*

class MainActivity : AppCompatActivity() {
    private var permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CONTACTS,
    )
    private val title = arrayOf("All Message", "Send Message")
    private var views: ArrayList<Fragment> = ArrayList()
    private val receiver = MessageReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultMessageApp()
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

    private fun setDefaultMessageApp(){
        var default = ""
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
             default = Telephony.Sms.getDefaultSmsPackage(this)
        }
        if (!TextUtils.equals(default,this.packageName)){
            val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, BuildConfig.APPLICATION_ID)
            startActivity(intent)
        }
    }

}