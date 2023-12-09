package com.kto.employenexa.ui.operationser

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.kto.employenexa.databinding.ActivityCreUpServiceBinding
import com.kto.employenexa.domain.models.ServicemModel
import com.kto.employenexa.ui.home.MainActivity
import com.kto.employenexa.ui.operation.CreateUpdateActivityArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreUpServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreUpServiceBinding
    private val creUpServiceViewModel: CreUpServiceViewModel by viewModels()

    private val args: CreateUpdateActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreUpServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initUIState()
        initListeners()
        initUpdateServicem()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                creUpServiceViewModel.state.collect {
                    when (it) {
                        is CreUServiceState.Error -> errorState()
                        CreUServiceState.Init -> initState()
                        CreUServiceState.Loading -> loadingState()
                        is CreUServiceState.Success -> succesState(it)
                    }
                }
            }
        }
    }

    private fun succesState(service: CreUServiceState.Success) {
        if (args.id == null) {
            Toast.makeText(this, "Service ${service.data.service} create", Toast.LENGTH_LONG).show()
        } else {
            binding.tvIdInfo.text = service.data.id.toString()
            binding.edService.setText(service.data.service)

            binding.edPrice.setText(service.data.price.toString())
            binding.edBase.setText(service.data.base)
            binding.btnOpeService.text = "update"
        }
    }

    private fun loadingState() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initState() {

    }

    private fun errorState() {

    }

    private fun validLong(price: Editable) :Long{
        return try {
            price.toString().toLong()
        } catch (e: Exception) {
            0
        }
    }

    private fun initListeners() {
        binding.imvBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnOpeService.setOnClickListener {
            val servicem = ServicemModel(
                validId(binding.tvIdInfo),
                binding.edService.text.toString(),
                validLong(binding.edPrice.text),
                binding.edBase.text.toString()
            )
            if (args.id != null) {
                creUpServiceViewModel.updateServicemIds(servicem, args.id!!)
            } else {
                creUpServiceViewModel.createServicems(servicem)
            }

        }
    }

    private fun initUpdateServicem() {
        if (args.id != null) {
            creUpServiceViewModel.searchServicemIds(args.id!!)
        }
    }

    private fun validId(edId: TextView): Int {
        return try {
            Integer.parseInt(edId.text.toString())
        } catch (e: Exception) {
            0
        }
    }
}