package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.store.secondlife.R
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnclick()

    }

    fun btnclick(){
        val btn= arrayOf(btn_personal_data, btn_addresses, btn_buy, btn_sales, btn_cards)
        var num =0
        for (b in btn.indices){
            println(btn[b].toString())
            btn[b].setOnClickListener{
                num=b
                val bundle :Bundle= Bundle()
                bundle.putInt("num", num)
                findNavController().navigate(R.id.profileInformationDialogFragment, bundle)
            }
        }
    }
}