package net.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.layout_fragment_all.*
import net.adapter.MessageAdapter
import net.basicmodel.R
import net.utils.MessageManager

class AllMessageFragment : Fragment() ,OnItemClickListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val data = activity?.let { MessageManager.get().getMessage(it) }
        val msgAdapter = MessageAdapter(R.layout.layout_item_msg, data)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = msgAdapter
        msgAdapter.setOnItemClickListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}