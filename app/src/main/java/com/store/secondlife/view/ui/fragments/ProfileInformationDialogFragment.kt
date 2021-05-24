package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.store.secondlife.R
import kotlinx.android.synthetic.main.profile_information_dialog.*


class ProfileInformationDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_information_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnArrow_left.setOnClickListener{
            findNavController().navigate(R.id.navProfileFragment)
        }
        btnhome.setOnClickListener{
            findNavController().navigate(R.id.navHomeFragment)
        }
    }
    //desactiva todos los cardViews
    fun DisabledCardView(){
        cvInformation.visibility=View.GONE
        cvAddress.visibility=View.GONE
        cvBuy.visibility=View.GONE
        cvSales.visibility=View.GONE
        cvCard.visibility=View.GONE
    }
    override fun onStart() {
        super.onStart()
        DisabledCardView()
        val num = arguments?.getSerializable("num")
        //direccionar los CardView
        when(num){
            0 ->{
                cvInformation.visibility=View.VISIBLE
                tv_title_profile.text="Información personal"
            }
            1 ->{
                cvAddress.visibility=View.VISIBLE
                tv_title_profile.text="Mis direcciones"
            }
            2 ->{
                cvBuy.visibility=View.VISIBLE
                tv_title_profile.text="Mis compras"
            }
            3 ->{
                cvSales.visibility=View.VISIBLE
                tv_title_profile.text="Mis ventas"
            }
            4 ->{
                cvCard.visibility=View.VISIBLE
                tv_title_profile.text="Medios de pago"
            }
        }
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}