package net.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import kotlinx.android.synthetic.main.layout_fragment_all.*
import net.adapter.MessageAdapter
import net.basicmodel.R
import net.entity.MessageEntity
import net.event.MessageEvent
import net.utils.MessageManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AllMessageFragment : Fragment(), OnItemClickListener{
    var msgAdapter: MessageAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initView()
    }

    private fun initView() {
        val data = activity?.let { MessageManager.get().getMessage(it) }
        msgAdapter = MessageAdapter(R.layout.layout_item_msg, data)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = msgAdapter
        msgAdapter!!.setOnItemClickListener(this)
    }

    private fun detailsDialog(entity: MessageEntity): AlertDialog {
        val dlg = AlertDialog.Builder(activity).create()
        val view = LayoutInflater.from(activity).inflate(R.layout.layout_dialog_details, null)
        dlg.setView(view)
        view.findViewById<TextView>(R.id.phone).text = entity.phone
        view.findViewById<TextView>(R.id.date).text = entity.date
        view.findViewById<TextView>(R.id.content).text = entity.content
        view.findViewById<TextView>(R.id.close).setOnClickListener {
            dlg.dismiss()
        }
        return dlg
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val entity = adapter.data[position] as MessageEntity
        detailsDialog(entity).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        val e = event.getMessage()
        if (!TextUtils.isEmpty(e[0].toString())) {
            Thread.sleep(1000)
            val data = activity?.let { MessageManager.get().getMessage(it) }
            msgAdapter!!.setNewInstance(data)
            recycler.smoothScrollToPosition(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}