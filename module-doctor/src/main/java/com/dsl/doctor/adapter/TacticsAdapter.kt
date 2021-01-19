package com.dsl.doctor.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dsl.doctor.R
import com.dsl.doctor.bean.TacticsBean
import com.dsl.extend.loadImageExtend
import com.dsl.util.DateTimeUtil
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext

/**
 * 互联网攻略列表适配器
 * @author dsl-abben
 * on 2020/03/03.
 */
class TacticsAdapter(dataList: MutableList<TacticsBean>?) :
    BaseQuickAdapter<TacticsBean, BaseViewHolder>(
        R.layout.doctor_item_tactics, dataList
    ), LoadMoreModule {
    override fun convert(helper: BaseViewHolder, item: TacticsBean) {
        item.run {
            val image = helper.getView<ImageView>(R.id.image)
            image.loadImageExtend(context, top)
            helper.setText(R.id.title, title)
            helper.setText(R.id.create_time, DateTimeUtil.getFormtTime(createTime))
            helper.setText(R.id.read_num, String.format("%d阅读", readNum))
        }
    }
}