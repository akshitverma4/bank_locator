package com.wednesday.banklocator.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wednesday.banklocator.R
import com.wednesday.banklocator.model.IfscResponse
import com.wednesday.banklocator.viewmodel.BankViewModel
import kotlinx.android.synthetic.main.partial_banks_list.view.bankAddress_tv
import kotlinx.android.synthetic.main.partial_banks_list.view.bankName_tv
import kotlinx.android.synthetic.main.partial_saved_banks_list.view.*

class LocalDatabaseAdapter(var bankDetails: List<IfscResponse>, private val viewModel: BankViewModel) :
    RecyclerView.Adapter<LocalDatabaseAdapter.bankDetailsViewHolder>(
    ) {
    inner class bankDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bankDetailsViewHolder {
        return bankDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.partial_saved_banks_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: bankDetailsViewHolder, position: Int) {
        val model = bankDetails[position]
        holder.itemView.apply {
            bankNameTextView.text = model.BRANCH
            bankAddressTextView.text = model.ADDRESS
            deleteIconImageView.setOnClickListener {
                val toast = Toast.makeText(
                    context,
                    model.BRANCH + " deleted from Local Database",
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                toast.show()
                viewModel.delete(model)
            }


            setOnClickListener {
                onItemClickListener?.let { it(model) }
            }
        }
    }


    override fun getItemCount(): Int {

        return bankDetails.size
    }

    private var onItemClickListener: ((IfscResponse) -> Unit)? = null
}