package com.example.trabalhomoveispstotall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabalhomoveispstotall.data.UsuarioViewModel
import com.example.trabalhomoveispstotall.ui.theme.TrabalhoMoveisPStotallTheme

@Composable
fun BotaoPerfil(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Perfil"
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabalhoMoveisPStotallTheme {
                AppNavegacao()
            }
        }
    }
}

@Composable
fun AppNavegacao(viewModel: UsuarioViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            TelaEvento(
                viewModel = viewModel,
                onEntrarClick = {
                    navController.navigate("home")
                },
                onCadastrarUsuarioClick = {
                    viewModel.mensagemErro = ""
                    navController.navigate("cadastro_usuario")
                }
            )
        }
        composable("home") {
            TelaPrincipal(
                viewModel = viewModel,
                onEntrarClick = {
                    navController.navigate("perfil")
                },
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCadastrarAlunoClick = {
                    viewModel.mensagemErro = ""
                    navController.navigate("cadastro_usuario")
                },
                onCadastrarProcessoClick = {
                    viewModel.mensagemErro = ""
                    navController.navigate("cadastro_processo")
                }
            )
        }
        composable("cadastro_usuario") {
            TelaCadastroUsuario(
                viewModel = viewModel,
                onVoltarClick = {
                    viewModel.mensagemErro = ""
                    navController.popBackStack()
                }
            )
        }
        composable("cadastro_processo") {
            TelaCadastroProcesso(
                viewModel = viewModel,
                onVoltarClick = {
                    viewModel.mensagemErro = ""
                    navController.popBackStack()
                }
            )
        }
        composable("perfil") {
            TelaPerfilUsuario(
                viewModel = viewModel,
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun TelaEvento(
    viewModel: UsuarioViewModel,
    onEntrarClick: () -> Unit,
    onCadastrarUsuarioClick: () -> Unit
) {
    var nomeUsuario by remember { mutableStateOf("") }
    var senhaUsuario by remember { mutableStateOf("") }

    val corFundoVerde = Color(0xFF1B4D2F)
    val corAmarela = Color(0xFFFFC107)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bannerrussas),
                        contentDescription = "banner UFC Russas",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(width = 140.dp, height = 48.dp)
                    )
                }

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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = nomeUsuario,
                    onValueChange = { nomeUsuario = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = senhaUsuario,
                    onValueChange = { senhaUsuario = it },
                    label = { Text("Senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (viewModel.mensagemErro.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagemErro,
                        color = corAmarela
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.fazerLogin(nomeUsuario, senhaUsuario) {
                            onEntrarClick()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = nomeUsuario.isNotBlank() && senhaUsuario.isNotBlank()
                ) {
                    Text("Entrar", color = Color.White)
                }

                Button(
                    onClick = onCadastrarUsuarioClick,
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar Usuario", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TelaPrincipal(
    viewModel: UsuarioViewModel,
    onVoltarClick: () -> Unit,
    onEntrarClick: () -> Unit,
    onCadastrarAlunoClick: () -> Unit = {},
    onCadastrarProcessoClick: () -> Unit = {}
) {
    val corFundoVerde = Color(0xFF1B4D2F)
    val corAmarela = Color(0xFFFFC107)

    var mostrarPesquisa by remember { mutableStateOf(false) }
    var textoBusca by remember { mutableStateOf("") }

    val listaStatus = listOf(
        "Todos",
        "Em andamento",
        "Ainda vai abrir",
        "Já ocorreu"
    )
    var indiceStatus by remember { mutableIntStateOf(0) }
    val statusSelecionado = listaStatus[indiceStatus]

    val processosFiltrados = viewModel.listaProcessos.filter { ps ->
        val correspondeProjeto = ps.projeto.contains(textoBusca, ignoreCase = true)
        val correspondeStatus =
            statusSelecionado == "Todos" || ps.status.equals(statusSelecionado, ignoreCase = true)
        correspondeProjeto && correspondeStatus
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde)
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(corAmarela)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onVoltarClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Abrir menu"
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.bannerrussas),
                    contentDescription = "banner UFC Russas",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(width = 140.dp, height = 48.dp)
                )

                BotaoPerfil(onClick = onEntrarClick)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        mostrarPesquisa = !mostrarPesquisa
                        if (!mostrarPesquisa) {
                            textoBusca = ""
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = corAmarela,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Pesquisar",
                    )
                }

                Text(
                    text = "Filtro: $statusSelecionado",
                    color = Color.White
                )

                IconButton(
                    onClick = {
                        indiceStatus = (indiceStatus + 1) % listaStatus.size
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = corAmarela,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtro",
                    )
                }
            }

            if (mostrarPesquisa) {
                TextField(
                    value = textoBusca,
                    onValueChange = { textoBusca = it },
                    label = { Text("Buscar processo seletivo por projeto") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            if (viewModel.usuarioLogado?.nome.equals("admin", ignoreCase = true)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onCadastrarAlunoClick,
                        colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cadastrar Aluno", color = Color.Black)
                    }

                    Button(
                        onClick = onCadastrarProcessoClick,
                        colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cadastrar Processo Seletivo", color = Color.Black)
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(processosFiltrados) { ps ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Projeto: ${ps.projeto}", color = Color.Black)
                            Text(
                                text = "Início: ${ps.dataInicio} | Término: ${ps.dataTermino}",
                                color = Color.DarkGray
                            )
                            Text(text = "Status: ${ps.status}", color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TelaPerfilUsuario(
    viewModel: UsuarioViewModel,
    onVoltarClick: () -> Unit
) {
    val corFundoVerde = Color(0xFF1B4D2F)
    val corAmarela = Color(0xFFFFC107)
    val usuario = viewModel.usuarioLogado

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil do Usuário",
                tint = corAmarela,
                modifier = Modifier.size(80.dp)
            )

            Text(text = "Perfil do Aluno", color = Color.White)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Nome: ${usuario?.nome ?: "Não informado"}",
                        color = Color.Black
                    )
                    Text(
                        text = "Gmail: ${usuario?.gmail ?: "Não informado"}",
                        color = Color.Black
                    )
                    Text(
                        text = "Curso: ${usuario?.curso ?: "Não informado"}",
                        color = Color.Black
                    )
                }
            }

            Button(
                onClick = onVoltarClick,
                colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar", color = Color.Black)
            }
        }
    }
}

@Composable
fun TelaCadastroUsuario(
    viewModel: UsuarioViewModel,
    onVoltarClick: () -> Unit
) {
    var nomeUsuario by remember { mutableStateOf("") }
    var senhaUsuario by remember { mutableStateOf("") }
    var senha2Usuario by remember { mutableStateOf("") }
    var gmailUsuario by remember { mutableStateOf("") }
    var cursoUsuario by remember { mutableStateOf("") }

    val corFundoVerde = Color(0xFF1B4D2F)
    val corAmarela = Color(0xFFFFC107)
    val corCinza = Color(0xFFD6D6D6)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("[Logo UFC - Inserir Imagem]", color = Color.White)
                }

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

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = nomeUsuario,
                    onValueChange = { nomeUsuario = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = gmailUsuario,
                    onValueChange = { gmailUsuario = it },
                    label = { Text("Gmail") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = cursoUsuario,
                    onValueChange = { cursoUsuario = it },
                    label = { Text("Curso") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = senhaUsuario,
                    onValueChange = { senhaUsuario = it },
                    label = { Text("Senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = senha2Usuario,
                    onValueChange = { senha2Usuario = it },
                    label = { Text("Confirmar senha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (viewModel.mensagemErro.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagemErro,
                        color = corAmarela
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.cadastrarUsuario(
                            nome = nomeUsuario,
                            gmail = gmailUsuario,
                            curso = cursoUsuario,
                            senha = senhaUsuario,
                            confirmarSenha = senha2Usuario,
                            onSucesso = onVoltarClick
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar-se", color = Color.Black)
                }

                Button(
                    onClick = onVoltarClick,
                    colors = ButtonDefaults.buttonColors(containerColor = corCinza),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Voltar", color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun TelaCadastroProcesso(
    viewModel: UsuarioViewModel,
    onVoltarClick: () -> Unit
) {
    var projeto by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf("") }
    var dataTermino by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    val corFundoVerde = Color(0xFF1B4D2F)
    val corAmarela = Color(0xFFFFC107)
    val corCinza = Color(0xFFD6D6D6)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(corFundoVerde)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bannerrussas),
                        contentDescription = "banner UFC Russas",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(width = 140.dp, height = 48.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cadastrar Processo Seletivo", color = Color.Black)
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextField(
                    value = projeto,
                    onValueChange = { projeto = it },
                    label = { Text("Projeto (ex: Vortex, Include)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = dataInicio,
                    onValueChange = { dataInicio = it },
                    label = { Text("Data de início (ex: 10/11/2026)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = dataTermino,
                    onValueChange = { dataTermino = it },
                    label = { Text("Data de término (ex: 30/11/2026)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status (Em andamento / Ainda vai abrir / Já ocorreu)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (viewModel.mensagemErro.isNotEmpty()) {
                    Text(
                        text = viewModel.mensagemErro,
                        color = corAmarela
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.cadastrarProcesso(
                            projeto = projeto,
                            dataInicio = dataInicio,
                            dataTermino = dataTermino,
                            status = status,
                            onSucesso = onVoltarClick
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = corAmarela),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar PS", color = Color.Black)
                }

                Button(
                    onClick = onVoltarClick,
                    colors = ButtonDefaults.buttonColors(containerColor = corCinza),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Voltar", color = Color.Black)
                }
            }
        }
    }
}
