package ru.jdeveloperapps.konturtest.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.jdeveloperapps.konturtest.R

class DetailFragment : Fragment(R.layout.fragment_detail) {

    val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userItem = args.user
        name.text = userItem.name
        phone.text = userItem.phone
        temperament.text = userItem.temperament
        val startDate = userItem.educationPeriod.start.substringBefore("T")
        val endDate = userItem.educationPeriod.end.substringBefore("T")
        educationPeriod.text = "$startDate - $endDate"
        biography.text = userItem.biography

        phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${userItem.phone}")
            startActivity(intent)
        }
    }
}