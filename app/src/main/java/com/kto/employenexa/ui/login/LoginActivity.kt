package com.kto.employenexa.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kto.employenexa.R
import com.kto.employenexa.databinding.ActivityLoginBinding
import com.kto.employenexa.domain.models.LoginModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initUIState()
        initUIafterTextChanged()
        initSetOnClicListener()
    }

    private fun initSetOnClicListener() {
        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(), binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> loginViewModel.login(
                        LoginModel(
                            mail = binding.username.text.toString(),
                            password = binding.password.text.toString()
                        )
                    )
                }
                false
            }

            binding.login.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(
                    LoginModel(
                        mail = binding.username.text.toString(),
                        password = binding.password.text.toString()
                    )
                )
            }
        }
    }

    private fun initUIafterTextChanged() {
        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(), binding.password.text.toString()
            )
        }
    }

    private fun initUIState() {
        initUIloginFormState()
        initUILoginResult()

    }

    private fun initUILoginResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginResult.collect {
                    when (it) {
                        is LoginResult.Error -> errotState(it)
                        LoginResult.Init -> iniState()
                        is LoginResult.Success -> succesState(it)
                    }
                }
            }
        }
    }

    private fun errotState(loginResult: LoginResult.Error) {
        showLoginFailed(loginResult.error)
    }

    private fun iniState() {
    }


    private fun succesState(loginResult: LoginResult.Success) {

        updateUiWithUser(loginResult.success)
        binding.loading.visibility = View.GONE
    }

    private fun initUIloginFormState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFormState.collect {
                    when (it) {
                        LoginFormState.InitLd -> iniLdState()
                        is LoginFormState.IsDataValid -> isDataValidState(it)
                        is LoginFormState.PasswordError -> passwordErrorState(it)
                        is LoginFormState.UsernameError -> psernameErrorState(it)
                    }
                }
            }
        }
    }

    private fun iniLdState() {

    }

    private fun psernameErrorState(loginState: LoginFormState.UsernameError) {
        binding.username.error = getString(loginState.usernameError)

    }

    private fun passwordErrorState(loginState: LoginFormState.PasswordError) {
        binding.password.error = getString(loginState.passwordError)

    }

    private fun isDataValidState(loginState: LoginFormState.IsDataValid) {
        binding.login.isEnabled = loginState.isDataValid

    }

    private fun updateUiWithUser(model: String) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext, "$welcome $model", Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
