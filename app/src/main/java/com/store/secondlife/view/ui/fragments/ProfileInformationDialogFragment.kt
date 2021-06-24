package com.store.secondlife.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.secondlife.R
import com.store.secondlife.model.Direccion
import com.store.secondlife.model.Producto
import com.store.secondlife.model.Usuario
import com.store.secondlife.model11.Tarjeta
import com.store.secondlife.network.FirestoreService
import com.store.secondlife.view.adapter.AddressAdapter
import com.store.secondlife.view.adapter.AddressListener
import com.store.secondlife.view.adapter.CardAdapter
import com.store.secondlife.view.adapter.CardListener
import com.store.secondlife.viewmodel.AddressViewModel
import com.store.secondlife.viewmodel.CardViewModel
import com.store.secondlife.viewmodel.ProductViewModel
import com.store.secondlife.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.profile_information_dialog.*


class ProfileInformationDialogFragment : DialogFragment(), AddressListener, CardListener {

    /*---------------variables para visualizar datos---------------------*/
    private lateinit var userViewModel:UserViewModel

    private lateinit var addressAdapter:AddressAdapter
    private lateinit var addressViewModel:AddressViewModel

    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardViewModel: CardViewModel

    private var  firebase: FirestoreService = FirestoreService()
    /*-------------------------------*/
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

        val user:String="JnxUjOJY3hBUa6FYcgZd"

        btnArrow_left.setOnClickListener{
            findNavController().navigate(R.id.navProfileFragment)
        }
        btnhome.setOnClickListener{
            findNavController().navigate(R.id.navHomeFragment)
        }
        //informarion personal
        getUser(view, user)
        //mis direecciones
        getAddress(view, user)
        //mis tarjetas
        getCard(view, user)
    }

    //mostrar datos del usuario
    private fun getUser(view:View, user:String) {
        val name=view.findViewById<EditText>(R.id.et_Name)
        val lastname=view.findViewById<EditText>(R.id.et_LastName)
        val dni=view.findViewById<EditText>(R.id.et_DNI)
        val date=view.findViewById<EditText>(R.id.et_date)
        val email=view.findViewById<TextView>(R.id.et_email)
        val telephone=view.findViewById<EditText>(R.id.et_telephone)
        val user_name=view.findViewById<EditText>(R.id.et_user)
        val pass=view.findViewById<EditText>(R.id.et_password)
        var key: String= ""

        var u: Usuario= Usuario()

        userViewModel=ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.refresh(user)
        userViewModel.usuario.observe(viewLifecycleOwner, Observer<Usuario>{
            usuario->
            u=usuario as Usuario
            key=usuario.key
            name.setText(usuario.nombre.toString())
            name.setSelection(et_Name.getText().length);
            lastname.setText(usuario.apellido.toString())
            dni.setText(usuario.dni.toString())
            date.setText(usuario.fec_nacimiento.toString())
            email.setText(usuario.email.toString())
            telephone.setText(usuario.telefono.toString())
            user_name.setText(usuario.usuario.toString())
            pass.setText("falta")
        })


        btnSave.setOnClickListener {

            u.key=key
            u.nombre=name.toString()
            u.apellido=lastname.toString()
            firebase.saveInformationUser(u)
        }
    }

    //mis dirrecciones
    private fun getAddress(view: View, user: String) {
        addressViewModel=ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel.refresh(user)
        addressAdapter= AddressAdapter(user, this)

        rvAddress.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter=addressAdapter
        }

        addressViewModel.listaAddress.observe(viewLifecycleOwner,
            Observer<List<Direccion>> { address ->
                addressAdapter.updateData(address)
            })
    }

    //mis tarjetas
    private fun getCard(view: View, user: String) {
        cardViewModel=ViewModelProviders.of(this).get(CardViewModel::class.java)
        cardViewModel.refresh(user)
        cardAdapter= CardAdapter(user, this)

        rvCard.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter=cardAdapter
        }

        cardViewModel.listaTarjeta.observe(viewLifecycleOwner,
            Observer<List<Tarjeta>> { card ->
                cardAdapter.updateData(card)
            })
    }


    //desactiva todos los cardViews
    private fun DisabledCardView(){
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

    /*------------metodos para clickear los item del RecyclerView---------------------*/
    override fun onAddressClicked(address: Direccion, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardClicked(address: Tarjeta, position: Int) {
        TODO("Not yet implemented")
    }

}