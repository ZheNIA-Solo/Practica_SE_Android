package com.example.project_practice.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import com.example.project_practice.R
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Home() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var search by remember { mutableStateOf("") }
        Text(
            text = stringResource(R.string.explore),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
        )
        TextField(
            value = search,
            onValueChange = { newSearch -> search = newSearch },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    modifier = Modifier.padding(start = 4.dp) // Добавляем отступ для текста
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 12.dp) // Контролируем отступ иконки
                )
            },
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp), // Упрощаем отступы
            shape = RoundedCornerShape(20.dp),
        )
        Text(
            text = stringResource(R.string.category),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
        )
        Text(
            text = stringResource(R.string.popular_shoes),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
        )
    }
}

@Preview(showBackground = true, name = "Home")
@Composable
fun HomePreview() {
    Home(

    )
}