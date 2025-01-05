package com.example.vinylstore.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onPasswordReset: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Redefinir Password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Insera o seu email", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        message = "Link de redefinição enviado para $email"
                    } catch (e: Exception) {
                        message = "Erro: ${e.localizedMessage}"
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, color = if (message.startsWith("Erro")) Color.Red else Color.Green)
        }
    }
}