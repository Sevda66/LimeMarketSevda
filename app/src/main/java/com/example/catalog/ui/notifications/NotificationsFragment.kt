package com.example.catalog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bonusTextView = view.findViewById<TextView>(R.id.tv_bonus)
        bonusTextView.text = "Ваши бонусы: ${BonusManager.getBonus()}"

        view.findViewById<Button>(R.id.btn_call).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+79521170326")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btn_whatsapp).setOnClickListener {
            val uri = Uri.parse("https://t.me/79521170326")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("UserSession", android.content.Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()

            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
