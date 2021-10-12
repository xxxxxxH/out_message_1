package net.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import net.basicmodel.R
import net.entity.MessageEntity

class MessageAdapter(layoutResId: Int, data: ArrayList<MessageEntity>?) :
    BaseQuickAdapter<MessageEntity, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: MessageEntity) {
        holder.setText(R.id.phone, item.phone)
            .setText(R.id.date, item.date)
            .setText(R.id.content, item.content)
        val imageView = holder.getView<ImageView>(R.id.type)
        when (item.type) {
            "received" -> imageView.setBackgroundResource(R.mipmap.received)
            "send" ->imageView.setBackgroundResource(R.mipmap.send)
            else -> imageView.setBackgroundResource(R.mipmap.unknown)
        }
    }
}