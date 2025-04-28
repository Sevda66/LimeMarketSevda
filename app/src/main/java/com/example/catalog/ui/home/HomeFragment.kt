package com.example.catalog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categories = mapOf(
            R.id.btn_grains to GrainsActivity::class.java,
            R.id.btn_dairy to DairyActivity::class.java,
            R.id.btn_meat to MeatActivity::class.java,
            R.id.btn_vegetables to VegetablesActivity::class.java,
            R.id.btn_fruits to FruitsActivity::class.java,
            R.id.btn_bakery to BakeryActivity::class.java,
            R.id.btn_canned to CannedActivity::class.java,
            R.id.btn_sweets to SweetsActivity::class.java,
            R.id.btn_drinks to DrinksActivity::class.java
        )

        for ((id, activityClass) in categories) {
            view.findViewById<ImageButton>(id)?.setOnClickListener {
                val intent = Intent(requireContext(), activityClass)
                startActivity(intent)
            }
        }

        val bonusButton = view.findViewById<Button>(R.id.btn_bonus_card)
        bonusButton.setOnClickListener {
            val intent = Intent(requireContext(), BonusCardActivity::class.java)
            startActivity(intent)
        }
    }
}
