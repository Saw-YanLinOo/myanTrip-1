package com.vmyan.myantrip.ui.adapter.hotel

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.hotel.BankCard
import kotlinx.android.synthetic.main.payment_item.view.*

class BankCardAdapter(private val listener: ItemClickListener, private val items: MutableList<BankCard>): RecyclerView.Adapter<BankCardAdapter.BankCardViewHolder>(){
    interface ItemClickListener{
        fun onItemClick(id: String)
    }
    fun setItems(items: List<BankCard>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BankCardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.payment_item,parent,false)
        return  BankCardViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BankCardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class BankCardViewHolder(private val view: View, private val listener: ItemClickListener):RecyclerView.ViewHolder(view),
        View.OnClickListener{
        private lateinit var bankCard: BankCard
        init {
            view.setOnClickListener ( this )
        }

        fun bind(item : BankCard){
            this.bankCard=item
            view.txtBankName.text=item.bankName
            if (item.bankName=="KBZ"){
                view.cardBank.setCardBackgroundColor(Color.BLUE)
            }else if(item.bankName=="CB")
            {
                view.cardBank.setCardBackgroundColor(Color.YELLOW)
            }

            Glide.with(view)
                .load(item.bankCardType)
                .into(view.cardType)
            Glide.with(view)
                .load(item.bankLogo)
                .into(view.imgBankLogo)



        }


        override fun onClick(p0: View?) {
           listener.onItemClick(bankCard.bankId)

        }

    }
}