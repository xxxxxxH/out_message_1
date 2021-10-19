package net.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_fragment_send.*
import net.basicmodel.R
import net.event.MessageEvent
import net.utils.KeyboardManager
import net.utils.MessageManager
import org.greenrobot.eventbus.EventBus


class SendMessageFragment : Fragment() {
    var phoneNum: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        send.setOnClickListener {
            if (TextUtils.isEmpty(phone.text.toString()) || TextUtils.isEmpty(content.text.toString()))
                return@setOnClickListener
            MessageManager.get()
                .sendMessage(requireActivity(), phone.text.toString(), content.text.toString())
            EventBus.getDefault().post(MessageEvent("send"))
            KeyboardManager.get().hideKeyboard(requireActivity())
            phone.setText("")
            content.setText("")
        }
        select.setOnClickListener {
            val uri: Uri = ContactsContract.Contacts.CONTENT_URI
            val intent = Intent(Intent.ACTION_PICK, uri)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === 0) {
                val uri: Uri = data!!.data!!
                val contacts: Array<String?>? =
                    activity?.let { MessageManager.get().getPhoneContacts(uri, it) }
                phoneNum = contacts!![1]
                phone.setText(phoneNum)
            }
        }

    }

}