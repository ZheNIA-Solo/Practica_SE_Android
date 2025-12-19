package com.example.project_practice.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_practice.R

@Composable
fun SignIn(
    onBackClick: () -> Unit,
    onSignInSuccess: () -> Unit,
    onSignInClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var showEmailErrorDialog by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    fun isValidEmail(email: String): Boolean {
        val pattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}\$")
        return pattern.matches(email)
    }

    // Кнопка активна только когда email валидный и пароль не пустой
    val isButtonEnabled = isValidEmail(email) && password.isNotEmpty()

    fun validateAndSignIn() {
        if (!isValidEmail(email)) {
            emailError = when {
                email.isEmpty() -> "Поле email не может быть пустым"
                !email.contains("@") -> "Email должен содержать символ @"
                !email.contains(".") -> "Email должен содержать точку"
                email.any { it.isUpperCase() } -> "Email должен содержать только строчные буквы"
                !email.substringBefore("@").all { it.isLowerCase() || it.isDigit() } ->
                    "Имя в email может содержать только строчные буквы и цифры"
                !email.substringAfter("@").substringBefore(".").all { it.isLowerCase() || it.isDigit() } ->
                    "Доменное имя может содержать только строчные буквы и цифры"
                email.substringAfterLast(".").length < 2 ->
                    "Старший домен должен содержать минимум 2 символа"
                else -> "Неверный формат email. Пример: name@domain.ru"
            }
            showEmailErrorDialog = true
            return
        }

        // Если email валиден и пароль есть, вызываем успешный вход
        onSignInSuccess()
    }

    if (showEmailErrorDialog) {
        AlertDialog(
            onDismissRequest = { showEmailErrorDialog = false },
            title = { Text("Ошибка email") },
            text = { Text(emailError) },
            confirmButton = {
                TextButton(
                    onClick = { showEmailErrorDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Кнопка "Назад" со стрелкой слева
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = colorResource(R.color.text)
                )
            }
        }

        Text(
            text = stringResource(R.string.hello),
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.filling_data),
            color = colorResource(R.color.sub_text_dark),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        Text(
            text = stringResource(R.string.email),
            color = colorResource(R.color.text),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp)
        )

        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            placeholder = { Text("example@domain.ru") },
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            shape = RoundedCornerShape(20.dp),
            isError = !isValidEmail(email) && email.isNotEmpty()
        )

        if (email.isNotEmpty() && !isValidEmail(email)) {
            Text(
                text = "Пример: name@domain.ru",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 4.dp)
            )
        }

        Text(
            text = stringResource(R.string.password),
            color = colorResource(R.color.text),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 20.dp, top = 20.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("********") },
            textStyle = TextStyle(fontSize = 14.sp),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) {
                            "Скрыть пароль"
                        } else {
                            "Показать пароль"
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            shape = RoundedCornerShape(20.dp),
        )

        Text(
            text = stringResource(R.string.recovery),
            fontSize = 12.sp,
            textAlign = TextAlign.End,
            color = colorResource(R.color.sub_text_dark),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validateAndSignIn()
            },
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = if (isButtonEnabled) {
                    colorResource(R.color.accent)      // Цвет когда активна
                } else {
                    colorResource(R.color.disable)     // Цвет когда неактивна
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp, top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.new_user) + " ",
                color = colorResource(R.color.hint),
                fontSize = 16.sp
            )

            Text(
                text = stringResource(R.string.create),
                color = colorResource(R.color.text),
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}

@Preview(showBackground = true, name = "SignIn")
@Composable
fun SignInPreview() {
    SignIn(
        onBackClick = {},
        onSignInSuccess = {},
        onSignInClick = {}
    )
}