package com.mcastany.samplebc5

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mcastany.samplebc5.databinding.PaywallItemBinding
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.Store
import com.revenuecat.purchases.models.GooglePurchaseOption
import com.revenuecat.purchases.models.PurchaseOption
import com.revenuecat.purchases.models.StoreProduct
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.purchasePackageOptionWith

class Offering {
    private var product: StoreProduct
    public val purchaseOption: PurchaseOption?
    public val rcpackage: Package

    constructor(rcPackage: Package) {
        this.purchaseOption = rcPackage.product.purchaseOptions.get(0)
        this.product = rcPackage.product
        this.rcpackage = rcPackage
    }

    val productNameUpperCase: String
        get() = this.product.productId.uppercase()
    val price: String
        get() = this.purchaseOption?.pricingPhases?.last()?.formattedPrice!!
    val formattedPeriod: String
        get() = when (this.product.subscriptionPeriod) {
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
        holder.button.setOnClickListener{
            clickHandler(item)
        }
    }

    fun clickHandler(item: Offering){
        Log.i("info", String.format("Purchased Package %s at %s", item.productNameUpperCase, item.price))
    }

   override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: PaywallItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val priceView: TextView = binding.price
        val durationView: TextView = binding.duration
        val productView: TextView = binding.product
        val button: TextView = binding.button

        override fun toString(): String {
            return super.toString() + " '" + productView.text+ " '" + durationView.text + " '" + priceView.text
        }
    }

}