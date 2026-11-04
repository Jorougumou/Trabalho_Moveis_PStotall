package com.example.trabalhomoveispstotall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trabalhomoveispstotall.ui.theme.TrabalhoMoveisPStotallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabalhoMoveisPStotallTheme {
                TelaEvento()
            }
        }
    }
}

@Composable
fun TelaEvento() {
    var nomeUsuario by remember { mutableStateOf("") }
    var senhaUsuario by remember { mutableStateOf("") }

    // Cor de fundo verde baseada no protótipo do Figma
    val corFundoVerde = Color(0xFF1B4D2F)
    // Cor amarela dos botões do protótipo
    val corAmarela = Color(0xFFFFC107)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde) // Aplica o fundo verde na tela toda
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Distribui elementos de cima a baixo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Seção Superior: Logos (UFC e PSTotal)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // TODO: Substitua 'R.drawable.logo_ufc' pela imagem real da logo da UFC na sua pasta res/drawable
                /*
                Image(
                    painter = painterResource(id = R.drawable.logo_ufc),
                    contentDescription = "Logo UFC",
                    modifier = Modifier.height(60.dp)
                )
                */
                // Placeholder temporário caso a imagem não exista ainda:
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("[Logo UFC - Inserir Imagem]", color = Color.White)
                }

                // TODO: Substitua 'R.drawable.logo_pstotal' pela logo principal central
                /*
                Image(
                    painter = painterResource(id = R.drawable.logo_pstotal),
                    contentDescription = "Logo PSTotal",
                    modifier = Modifier.height(70.dp)
                )
                */
                // Placeholder temporário para o logotipo PSTotal:
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("PSTotal (Inserir Imagem)", color = Color.Black)
                }
            }

            // Seção do Meio: Campos de Texto (Usuário e Senha)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = nomeUsuario,
                    onValueChange = { nomeUsuario = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = senhaUsuario,
                    onValueChange = { senhaUsuario = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Seção Inferior: Botões de Ação
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botão Entrar
                Button(
                    onClick = { /* Ação de entrar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = nomeUsuario.isNotBlank() && senhaUsuario.isNotBlank()
                ) {
                    Text("Entrar", color = Color.Black)
                }

                // Botão Cadastrar Usuário
                Button(
                    onClick = { /* Ação de cadastrar usuário */ },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar Usuario", color = Color.Black)
                }

                // Botão Cadastrar Projeto
                Button(
                    onClick = { /* Ação de cadastrar projeto */ },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar Projeto", color = Color.Black)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaEventoPreview() {
    TrabalhoMoveisPStotallTheme {
        TelaEvento()
    }
}