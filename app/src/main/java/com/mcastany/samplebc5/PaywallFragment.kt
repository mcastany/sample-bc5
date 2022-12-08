package com.mcastany.samplebc5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.revenuecat.purchases.Offerings
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.getOfferingsWith

/**
 * A fragment representing a list of Items.
 */
class PaywallFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaywallRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.paywall_item_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        recyclerView.adapter = PaywallRecyclerViewAdapter(listOf())

        Purchases.sharedInstance.getOfferingsWith(::showError, ::populateOfferings)

        return view
    }

    private fun populateOfferings(offerings: Offerings) {
        val list = offerings.current?.availablePackages?.map {
            Offering(
                it.product.productId,
                it.product.purchaseOptions.get(0),
                it.product.description,
                it.product.subscriptionPeriod,
            )
        }
        recyclerView.adapter = list?.let { PaywallRecyclerViewAdapter(it) }!!
    }

    private fun showError(message: PurchasesError) {
        Log.e("Error", message.message)
    }
}