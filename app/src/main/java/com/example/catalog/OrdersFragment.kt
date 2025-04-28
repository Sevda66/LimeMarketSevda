package com.example.catalog

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrdersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_orders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_orders)
        val btnClear = view.findViewById<Button>(R.id.btn_clear_history)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = OrdersAdapter(OrdersManager.getOrders())

        btnClear.setOnClickListener {
            OrdersManager.clearOrders()
            recyclerView.adapter = OrdersAdapter(OrdersManager.getOrders())
        }
    }
}
