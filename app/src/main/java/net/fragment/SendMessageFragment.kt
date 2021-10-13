package net.fragment

import android.os.Bundle
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
    }

}