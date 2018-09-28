package me.toptas.monitto.ui.main


import kotlinx.android.synthetic.main.row_site.view.*
import me.toptas.monitto.BR
import me.toptas.monitto.R
import me.toptas.monitto.base.BaseListAdapter
import me.toptas.monitto.databinding.RowSiteBinding
import me.toptas.monitto.model.Site

class SiteListAdapter(private val listener: OnSiteClickListener) : BaseListAdapter<Site, RowSiteBinding>() {

    override fun layoutResource() = R.layout.row_site

    override fun bindingVariableId() = BR.site

    override fun bind(holder: BaseViewHolder<Site>, item: Site?) {
        holder.itemView.btnCheck.setOnClickListener {
            item?.apply {
                listener.onSiteCheck(this)
            }
        }
    }


}

interface OnSiteClickListener {
    fun onSiteCheck(site: Site)
}