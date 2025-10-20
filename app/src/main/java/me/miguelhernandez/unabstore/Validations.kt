package me.miguelhernandez.unabstore

import android.util.Patterns


fun validateEmail(email: String): Pair<Boolean,String>{
    return when{
        email.isEmpty() -> Pair(false, "El correo es requerido")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> Pair(false, "El correo ingresado no es valido")
        !email.endsWith("@algo.com") -> Pair(false, "El correo ingresado no es valido")
        else -> Pair(true, "")
    }
}

fun validatePassword(password: String): Pair<Boolean, String>{
    return when{
        password.isEmpty() -> Pair(false, "La contraseña es requerida")
        password.length < 7 -> Pair(false, "La contraseña debe contener minimo 7 caracteres")
        !password.any {it.isDigit()} -> Pair(false, "La contraseña debe contener al menos un digito")
        else -> Pair(true, "")
    }
}

fun validateName(name: String): Pair<Boolean,String>{
    return when{
        name.isEmpty() -> Pair(false, "El nombre es requerido")
        name.length < 3 ->  Pair(false, "El nombre debe contener un minimo de 3 caracteres")
        else -> Pair(true, "")
    }
}

fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String>{
    return when{
        password != confirmPassword -> Pair(false, "Las contraseñas no son iguales")
        else -> Pair(true, "")
    }
}