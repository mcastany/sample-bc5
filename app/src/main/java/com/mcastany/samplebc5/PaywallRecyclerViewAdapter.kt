package com.mcastany.samplebc5

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.mcastany.samplebc5.databinding.PaywallItemBinding
import com.revenuecat.purchases.models.PurchaseOption

class Offering(
    val productName: String,
    val purchaseOption: PurchaseOption,
    val productId: String,
    val subscriptionPeriod: String?
){
    val productNameUpperCase: String
        get() = this.productName.uppercase()
    val price: String
        get() = this.purchaseOption.pricingPhases.last().formattedPrice
    val formattedPeriod: String
        get() = when (this.subscriptionPeriod) {
            "P1M" -> "MONTHLY"
            "P1Y" -> "YEARLY"
            else -> {
                "NA"
            }
        }
}

class PaywallRecyclerViewAdapter(
    private val values: List<Offering>
) : RecyclerView.Adapter<PaywallRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PaywallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.priceView.text = item.price
        holder.durationView.text = item.formattedPeriod
        holder.productView.text = item.productNameUpperCase
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: PaywallItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val priceView: TextView = binding.price
        val durationView: TextView = binding.duration
        val productView: TextView = binding.product

        override fun toString(): String {
            return super.toString() + " '" + productView.text+ " '" + durationView.text + " '" + priceView.text
        }
    }

}