package com.kto.employenexa.ui.servicem

import android.app.Dialog
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
import com.kto.employenexa.databinding.FragmentServicemBinding
import com.kto.employenexa.domain.models.ServicemModel
import com.kto.employenexa.ui.servicem.adapter.ServicemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServicemFragment : Fragment() {
    private val servicemViewModel by viewModels<ServicemViewModel>()
    private lateinit var servicemAdapter: ServicemAdapter

    private var _binding: FragmentServicemBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        servicemViewModel.getServicemList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        servicemViewModel.getServicemList()
        initUI()
    }

    private fun initUI() {
        initListService()
        initUIState()
        initListener()
    }

    private fun initListener() {
        binding.fbAddServicem.setOnClickListener { starOperation(null) }
    }

    private fun starOperation(id: String?) {
        findNavController().navigate(
            ServicemFragmentDirections.actionServicemFragmentToCreUpServiceActivity(id)
        )
    }

    private fun deleteServicem(data: ServicemModel) {
        val dialog = Dialog(binding.pbSer.context)
        dialog.setContentView(R.layout.dialog_delete)
        val tvDeleteId: TextView = dialog.findViewById(R.id.tvDeleteId)
        val btnDelete: Button = dialog.findViewById(R.id.btnDelete)
        tvDeleteId.text = data.service
        btnDelete.setOnClickListener {
            servicemViewModel.deleteServicemIds(data.id.toString());
            dialog.hide()
        }
        dialog.show()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                servicemViewModel.state.collect {
                    when (it) {
                        is ServicemState.Error -> errorState()
                        ServicemState.Loading -> loadingState()
                        is ServicemState.Success -> succesState(it)
                    }
                }
            }
        }
    }

    private fun succesState(it: ServicemState.Success) {
        binding.pbSer.isVisible = false
        servicemAdapter.udateServicemList(it.data)
    }

    private fun loadingState() {
        binding.pbSer.isVisible = true
    }

    private fun errorState() {
        binding.pbSer.isVisible = false
    }

    private fun initListService() {
        servicemAdapter = ServicemAdapter(updateServicemId = { starOperation(it) },
            deleteServicemId = { deleteServicem(it) })
        binding.rvServicem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = servicemAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}