package net.utils

import android.content.Context
import android.net.Uri
import net.entity.MessageEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageManager {
    companion object {
        private var i: MessageManager? = null
            get() {
                field ?: run {
                    field = MessageManager()
                }
                return field
            }

        @Synchronized
        fun get(): MessageManager {
            return i!!
        }
    }

    fun getMessage(context: Context): ArrayList<MessageEntity> {
        val result = ArrayList<MessageEntity>()
        val SMS_URI_ALL = "content://sms/"
        try {
            val cr = context.contentResolver
            val projection = arrayOf(
                "_id", "address", "person",
                "body", "date", "type"
            )
            val uri = Uri.parse(SMS_URI_ALL)
            val cur = cr.query(uri, projection, null, null, "date desc")
            if (cur!!.moveToFirst()) {
                val nameColumn = cur.getColumnIndex("person")
                val phoneNumberColumn = cur.getColumnIndex("address")
                val smsbodyColumn = cur.getColumnIndex("body")
                val dateColumn = cur.getColumnIndex("date")
                val typeColumn = cur.getColumnIndex("type")

                do {
                    val phone = cur.getString(phoneNumberColumn)
                    val content = cur.getString(smsbodyColumn)
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    val d = Date(cur.getString(dateColumn).toLong())
                    val date = dateFormat.format(d)
                    val typeId = cur.getInt(typeColumn)
                    var type = ""
                    type = when (typeId) {
                        1 -> {
                            "received"
                        }
                        2 -> {
                            "send"
                        }
                        else -> {
                            ""
                        }
                    }
                    val entity = MessageEntity(
                        phone = phone,
                        content = content,
                        date = date,
                        type = type
                    )
                    result.add(entity)
                } while (cur.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}