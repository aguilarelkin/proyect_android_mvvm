package com.kto.employenexa.ui.employe

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kto.employenexa.R
import com.kto.employenexa.databinding.FragmentEmployeBinding
import com.kto.employenexa.domain.models.EmployeModel
import com.kto.employenexa.ui.employe.adapter.EmployeAdapter
import com.kto.employenexa.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeFragment : Fragment() {

    private val employeViewModel by viewModels<EmployeViewModel>()//conectar frag con el viewModel

    private lateinit var employeAdapter: EmployeAdapter //conectar para imprimir data

    private var _binding: FragmentEmployeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        employeViewModel.getEmployes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//PARA CONFIGURACIONES
        super.onViewCreated(view, savedInstanceState)
        employeViewModel.getEmployes()//cargando employes
        initUI()
    }

    private fun initUI() {
        initList()
        initUIState()
        initListeners()
    }

    private fun initListeners() {
        binding.fbAddEmploye.setOnClickListener { startOperation(null) }
        binding.fbAddLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startOperation(id: String?) {
        findNavController().navigate(
            EmployeFragmentDirections.actionEmployeFragmentToCreateUpdateActivity(id)
        )
    }

    private fun deleteEmploye(data: EmployeModel) {
        val dialog = Dialog(binding.pbEm.context)
        dialog.setContentView(R.layout.dialog_delete)
        val tvDeleteId: TextView = dialog.findViewById(R.id.tvDeleteId)
        val btnDelete: Button = dialog.findViewById(R.id.btnDelete)
        tvDeleteId.text = data.firstname
        btnDelete.setOnClickListener {
            employeViewModel.deleteEmployeId(data.id.toString());
            dialog.hide()
        }
        dialog.show()
    }

    private fun initList() {//init adapter y recicle
        employeAdapter = EmployeAdapter(
            updateEmployeId = {
                startOperation(it)
            },
            deleteEmployeId = {
                deleteEmploye(it)
            })
        binding.rvEmployeAdmin.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = employeAdapter
        }
    }

    private fun initUIState() {//enganchar con el stateFlow en viewModel
        /*        lifecycleScope.launch { //corutina para fragment
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        employeViewModel.employe.collect {
                            //al hacer cambios en employe.value se ejecuta el siguiente código
                            //cambios en employe
                            Toast.makeText(context, "it $it", Toast.LENGTH_LONG).show()
                            employeAdapter.updateList(it)
                        }
                    }
                }*/ // sin estados
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeViewModel.state.collect {
                    when (it) {
                        is EmployeState.Error -> errorState()
                        EmployeState.Loading -> loadingState()
                        is EmployeState.Success -> succeessState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.pbEm.isVisible = true
    }

    private fun errorState() {
        binding.pbEm.isVisible = false
    }

    private fun succeessState(state: EmployeState.Success) {
        binding.pbEm.isVisible = false
        employeAdapter.updateList(state.data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}