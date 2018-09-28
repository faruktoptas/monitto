package me.toptas.monitto.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseListAdapter<T, in DB : ViewDataBinding> : RecyclerView.Adapter<BaseListAdapter.BaseViewHolder<T>>() {

    var items: List<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract fun layoutResource(): Int

    abstract fun bindingVariableId(): Int


    open fun bind(holder: BaseViewHolder<T>, item: T?) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DB>(layoutInflater, layoutResource(), parent, false)

        return BaseViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = if (position < items!!.size) items!![position] else null
        holder.bind(bindingVariableId(), item)
        bind(holder, item)
    }

    override fun getItemCount() = items?.size ?: 0


    class BaseViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bindingVariableId: Int, item: T?) {
            binding.setVariable(bindingVariableId, item)
            binding.executePendingBindings()
        }
    }


}