package com.example.project_practice.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_practice.R

@Composable
fun RegisterAccount(
    onBackClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onSignInClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }
    var showEmailErrorDialog by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var registrationSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            onRegisterSuccess()
        }
    }

    fun isValidEmail(email: String): Boolean {
        val pattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}\$")
        return pattern.matches(email)
    }

    fun validateAndRegister() {
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

        println("Регистрация успешна: $name, $email")
        registrationSuccess = true
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
        Button(
            onClick = onBackClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.background),
                contentColor = colorResource(R.color.text)
            ),
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
                .size(45.dp)
        ) {
            Text(
                text = "<",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = stringResource(R.string.registration),
            color = colorResource(R.color.text),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
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
            text = stringResource(R.string.name),
            color = colorResource(R.color.text),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("xxxxxxxx") },
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            shape = RoundedCornerShape(20.dp)
        )

        Text(
            text = stringResource(R.string.email),
            color = colorResource(R.color.text),
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp)
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
            shape = RoundedCornerShape(20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val borderColor = if (isChecked) {
                Color.Transparent
            } else {
                colorResource(id = R.color.sub_text_light)
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        color = if (isChecked) colorResource(R.color.accent)
                        else colorResource(R.color.sub_text_light)
                    )
                    .border(
                        width = if (isChecked) 0.dp else 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable { isChecked = !isChecked },
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) {
                    Icon(
                        painter = painterResource(R.drawable.policy_check),
                        contentDescription = "Согласен с условиями",
                        tint = colorResource(R.color.text),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.approval),
                color = colorResource(R.color.hint),
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { isChecked = !isChecked }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                validateAndRegister()
            },
            enabled = isChecked,
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(R.color.block),
                containerColor = if (isChecked) {
                    colorResource(R.color.accent)
                } else {
                    colorResource(R.color.disable)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorResource(R.color.hint))) {
                    append(stringResource(R.string.already_have_account))
                    append(" ")
                }
                withStyle(style = SpanStyle(color = colorResource(R.color.text))) {
                    append(stringResource(R.string.sign_in))
                }
            },
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .clickable(onClick = onSignInClick)
        )
    }
}

// Preview функция
@Preview(showBackground = true, name = "Register Account")
@Composable
fun RegisterAccountPreview() {
    RegisterAccount(
        onBackClick = { /* заглушка для preview */ },
        onRegisterSuccess = { /* заглушка для preview */ },
        onSignInClick = { /* заглушка для preview */ }
    )
}