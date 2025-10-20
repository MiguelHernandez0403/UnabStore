package me.miguelhernandez.unabstore

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onClickReturn :() -> Unit = {}, onSuccessfulRegister : () -> Unit = {}) {

    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    //ESTADOS
    var inputNombre by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputConfirmPassword by remember { mutableStateOf("") }
    var errorName by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf("") }
    var errorPassword by remember { mutableStateOf("") }
    var errorConfirmPassword by remember { mutableStateOf("") }
    var registerError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onClickReturn) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ícono de Usuario
            Image(
                painter = painterResource(id = R.drawable.img_icon_unab),
                contentDescription = "Usuario",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "Registro de Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de Nombre
            OutlinedTextField(
                value = inputNombre,
                onValueChange = {inputNombre = it},
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (errorName.isNotEmpty()){
                        Text(
                            errorName,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Correo Electrónico
            OutlinedTextField(
                value = inputEmail,
                onValueChange = {inputEmail = it},
                label = { Text("Correo Electrónico") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (errorEmail.isNotEmpty()){
                        Text(
                            errorEmail,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = inputPassword,
                onValueChange = {inputPassword = it},
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (errorPassword.isNotEmpty()){
                        Text(
                            errorPassword,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Confirmar Contraseña
            OutlinedTextField(
                value = inputConfirmPassword,
                onValueChange = {inputConfirmPassword = it},
                label = { Text("Confirmar Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirmar Contraseña"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (errorConfirmPassword.isNotEmpty()){
                        Text(
                            errorConfirmPassword,
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Registro

            if (registerError.isNotEmpty()){
                Text(
                    registerError,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    val isValidname: Boolean = validateName(inputNombre).first
                    val isValidEmail: Boolean = validateEmail(inputEmail).first
                    val isvalidPassword: Boolean = validatePassword(inputPassword).first
                    val isvalidConfirmPassword: Boolean = validateConfirmPassword(inputPassword, inputConfirmPassword).first

                    if(isvalidPassword && isvalidConfirmPassword && isValidEmail && isValidname){
                        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(activity) { task ->
                                if (task.isSuccessful){
                                    onSuccessfulRegister()
                                }
                                else{
                                    registerError = when(task.exception){
                                        is FirebaseAuthInvalidCredentialsException -> "Correo invalido"
                                        is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
                                        else -> "Error al registrarte. Intenta de nuevo"
                                    }
                                }
                            }
                    }
                    else{
                        errorName = validateName(inputNombre).second
                        errorEmail = validateEmail(inputEmail).second
                        errorPassword = validatePassword(inputPassword).second
                        errorConfirmPassword = validateConfirmPassword(inputPassword, inputConfirmPassword).second
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9900))
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

