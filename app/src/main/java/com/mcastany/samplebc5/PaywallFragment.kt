package com.mcastany.samplebc5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revenuecat.purchases.*
import com.revenuecat.purchases.models.PurchaseOption
import com.revenuecat.purchases.models.StoreProduct

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
        recyclerView.adapter = PaywallRecyclerViewAdapter(listOf(), this::purchasePackage)

        Purchases.sharedInstance.getOfferingsWith(::showError, ::populateOfferings)

        return view
    }

    public fun purchasePackage(product: StoreProduct, purchaseOption: PurchaseOption ){
        Purchases.sharedInstance.purchaseProductOptionWith(
            requireActivity(),
            product,
            purchaseOption!!,
            { error, userCancelled ->
                if (!userCancelled) {
                    Log.e("Error", error.message)
                }
            },
            { storeTransaction, _ ->
                Log.i("Successfull Purchase", storeTransaction.orderId!!)
            })
    }

    private fun populateOfferings(offerings: Offerings) {
        val list = offerings.current?.availablePackages?.map {
            Offering(it)
        }
        recyclerView.adapter = PaywallRecyclerViewAdapter(list!!, this::purchasePackage)
    }

    private fun showError(message: PurchasesError) {
        Log.e("Error", message.message)
    }
}