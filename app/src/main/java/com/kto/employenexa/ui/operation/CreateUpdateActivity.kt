package com.kto.employenexa.ui.operation

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.kto.employenexa.R
import com.kto.employenexa.databinding.ActivityCreateUpdateBinding
import com.kto.employenexa.domain.models.EmployeModel
import com.kto.employenexa.domain.models.Role
import com.kto.employenexa.domain.models.RoleModel
import com.kto.employenexa.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUpdateBinding
    private val createUpdateViewModel: CreateUpdateViewModel by viewModels()

    private val args: CreateUpdateActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initUIState()
        initListeners()
        initUpdateEmploye()
    }

    private fun initUpdateEmploye() {
        if (args.id != null) {
            createUpdateViewModel.searchEmployeId(args.id!!)
        }
    }

    private fun validId(edId: TextView): Int {
        return try {
            Integer.parseInt(edId.text.toString())
        } catch (e: Exception) {
            0
        }
    }

    private fun roleIdModel(text: CharSequence): RoleModel {
        return when (text) {
            "ADMIN" -> RoleModel(4, Role.ROLE_ADMIN.toString())
            "USER" -> RoleModel(3, Role.ROLE_USER.toString())
            else -> RoleModel(11, Role.ROLE_INVITE.toString())
        }
    }

    private fun initListeners() {
        binding.btnOperation.setOnClickListener {
            if (args.id != null) {
                createUpdateViewModel.updateEmployesId(
                    EmployeModel(
                        validId(binding.edId),
                        binding.edFirstname.text.toString(),
                        binding.edLastname.text.toString(),
                        binding.edPassword.text.toString(),
                        binding.edMail.text.toString(),
                        binding.edAvatar.text.toString(),
                        binding.edBase.text.toString(),
                        listOf(roleModelName()),
                        binding.edTelephone.text.toString()
                    ),
                    args.id!!
                )
            } else {
                createUpdateViewModel.createEmployes(
                    EmployeModel(
                        validId(binding.edId),
                        binding.edFirstname.text.toString(),
                        binding.edLastname.text.toString(),
                        binding.edPassword.text.toString(),
                        binding.edMail.text.toString(),
                        binding.edAvatar.text.toString(),
                        binding.edBase.text.toString(),
                        listOf(roleModelName()),
                        binding.edTelephone.text.toString()
                    )
                )
            }
        }
        binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun roleModelName(): RoleModel {
        val selectId = binding.rgRolesAdd.checkedRadioButtonId
        val selectRadioButton: RadioButton = binding.rgRolesAdd.findViewById(selectId)
        return roleIdModel(selectRadioButton.text)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                createUpdateViewModel.state.collect {
                    when (it) {
                        is CreateUpdateState.Error -> errorState()
                        CreateUpdateState.Loading -> loadingState()
                        is CreateUpdateState.Success -> successState(it)
                        CreateUpdateState.Init -> initState()
                    }
                }
            }
        }
    }

    private fun initState() {

    }

    private fun errorState() {

    }

    private fun loadingState() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun successState(employe: CreateUpdateState.Success) {
        if (args.id == null) {
            Toast.makeText(this, "Employe ${employe.data.firstname} create", Toast.LENGTH_LONG)
                .show()
        } else {
            var role = ""
            employe.data.role.map { role += " ${it.name}" }

            binding.edId.text = employe.data.id.toString()
            binding.edFirstname.setText(employe.data.firstname)
            binding.edLastname.setText(employe.data.lastname)
            binding.edMail.setText(employe.data.mail)
            binding.edAvatar.setText(employe.data.avatar)
            binding.edBase.setText(employe.data.base)
            binding.edTelephone.setText(employe.data.telephone)
            binding.edRole.text = role
            binding.btnOperation.setText(/* resid = */ R.string.btn_update)
        }
    }
}