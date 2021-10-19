package net.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import net.basicmodel.R
import net.entity.MessageEntity

/**
 * Copyright (C) 2021,2021/10/19, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MessageDetailsAdapter(layoutResId: Int, data: ArrayList<MessageEntity>?) :
    BaseQuickAdapter<MessageEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: MessageEntity) {
        when (item.type) {
            "received" -> {
                holder.setGone(R.id.send_layout, true)
                    .setText(R.id.receive_msg, item.content)
                    .setText(R.id.receive_time, item.date)
            }
            "send" -> {
                holder.setGone(R.id.receive_layout, true)
                    .setText(R.id.send_msg, item.content)
                    .setText(R.id.send_time, item.date)
            }
        }
    }
}