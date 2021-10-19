package net.basicmodel

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_activity_details.*
import kotlinx.android.synthetic.main.layout_title.*
import net.adapter.MessageDetailsAdapter
import net.entity.MessageEntity
import net.event.MessageEvent
import net.utils.KeyboardManager
import net.utils.MessageManager
import org.greenrobot.eventbus.EventBus

/**
 * Copyright (C) 2021,2021/10/19, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MessageDetailsActivity : AppCompatActivity() {
    var data: ArrayList<MessageEntity>? = null
    var phone: String? = null
    var detailsAdapter: MessageDetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_details)
        initData()
    }

    private fun initData() {
        val i = intent
        data = i.getSerializableExtra("data") as ArrayList<MessageEntity>
        phone = data!![0].phone
        initView()
    }

    private fun initView() {
        phoneTv.text = phone
        callTv.setOnClickListener {
            phone?.let { it1 -> MessageManager.get().call(this, it1) }
        }
        detailsAdapter = MessageDetailsAdapter(R.layout.layout_item_details, data)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = detailsAdapter
        send.setOnClickListener {
            if (TextUtils.isEmpty(edit.text.toString()))
                return@setOnClickListener
            MessageManager.get()
                .sendMessage(this, phone!!, edit.text.toString())
            EventBus.getDefault().post(MessageEvent("send"))
            KeyboardManager.get().hideKeyboard(this)
            finish()
        }
    }
}