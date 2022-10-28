package com.akash.currencyconverter.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akash.currencyconverter.databinding.BalanceItemBinding
import com.akash.currencyconverter.domain.model.Balance
import java.text.SimpleDateFormat

class BalancesAdapter : RecyclerView.Adapter<BalancesAdapter.BalanceViewHolder>(){





    private val diffCallback: DiffUtil.ItemCallback<Balance> = object : DiffUtil.ItemCallback<Balance>() {
        override fun areItemsTheSame(oldItem: Balance, newItem: Balance): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Balance, newItem: Balance): Boolean {
            return oldItem.currency == newItem.currency && oldItem.id == newItem.id && oldItem.amount == newItem.amount
        }
    } //define AsyncListDiffer

    private val mDiffer = AsyncListDiffer<Balance>(this, diffCallback)


    inner class BalanceViewHolder(val binding: BalanceItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(balance: Balance){
            binding.currencyName.text = balance.currency
            binding.amount.text = balance.amount.toString()
        }


    }

    fun submitList(data: List<Balance>) {
        mDiffer.submitList(data)
    } //method getItem by position


    fun getItem(position: Int): Balance {
        return mDiffer.currentList[position]
    } //override the method of Adapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val binding = BalanceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BalanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
//        return 10
    }

    fun addFood(food: Balance) {
        val currentList = ArrayList(mDiffer.currentList)
        currentList.add(food)
        submitList(currentList)

    }


}