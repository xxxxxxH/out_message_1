package net.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import net.entity.MessageEntity
import net.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

class MessageReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val bundle = p1!!.extras
        val puds = bundle!!["pdus"] as Array<*>?
            for (item in puds!!) {
                val smsMessage = SmsMessage.createFromPdu(item as ByteArray)
                val phone = smsMessage.originatingAddress
                val content = smsMessage.displayMessageBody
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val d = Date(System.currentTimeMillis())
                val entity = MessageEntity(phone!!, content, dateFormat.format(d), "received")
                EventBus.getDefault().post(MessageEvent("received"))
            }

    }
}