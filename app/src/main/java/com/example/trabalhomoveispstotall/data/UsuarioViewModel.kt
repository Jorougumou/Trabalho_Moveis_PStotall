package com.example.trabalhomoveispstotall.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val usuarioDao = db.usuarioDao()
    private val processoDao = db.processoSeletivoDao()

    var listaProcessos by mutableStateOf<List<ProcessoSeletivo>>(emptyList())
    var usuarioLogado by mutableStateOf<Usuario?>(null)
    var mensagemErro by mutableStateOf("")

    init {
        viewModelScope.launch {
            if (usuarioDao.listarTodos().isEmpty()) {
                usuarioDao.inserir(Usuario(nome = "Felipe", gmail = "felipe@alu.ufc.br", curso = "Ciência da Computação", senha = "123"))
                usuarioDao.inserir(Usuario(nome = "Vinicius", gmail = "vinicius@alu.ufc.br", curso = "Ciência da Computação", senha = "123"))
                usuarioDao.inserir(Usuario(nome = "Alberto", gmail = "alberto@alu.ufc.br", curso = "Engenharia da Computação", senha = "123"))
                usuarioDao.inserir(Usuario(nome = "Felipe do bem", gmail = "felipedobem@alu.ufc.br", curso = "Engenharia de Software", senha = "123"))
            }

            if (usuarioDao.autenticar("admin", "admin") == null) {
                usuarioDao.inserir(Usuario(nome = "admin", gmail = "admin@ufc.br", curso = "Administrador", senha = "admin"))
            }

            if (processoDao.listarTodos().isEmpty()) {
                processoDao.inserir(ProcessoSeletivo(projeto = "Vortex", dataInicio = "10/11/2026", dataTermino = "30/11/2026", status = "Em andamento"))
                processoDao.inserir(ProcessoSeletivo(projeto = "Include", dataInicio = "15/11/2026", dataTermino = "05/12/2026", status = "Em andamento"))
                processoDao.inserir(ProcessoSeletivo(projeto = "ARES", dataInicio = "10/12/2026", dataTermino = "22/12/2026", status = "Ainda vai abrir"))
                processoDao.inserir(ProcessoSeletivo(projeto = "LearningLab", dataInicio = "01/10/2026", dataTermino = "20/10/2026", status = "Já ocorreu"))
            }

            listaProcessos = processoDao.listarTodos()
        }
    }

    fun fazerLogin(nome: String, senha: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioDao.autenticar(nome, senha)
            if (usuario != null) {
                usuarioLogado = usuario
                mensagemErro = ""
                onSucesso()
            } else {
                mensagemErro = "Usuário ou senha incorretos!"
            }
        }
    }

    fun cadastrarUsuario(
        nome: String,
        gmail: String,
        curso: String,
        senha: String,
        confirmarSenha: String,
        onSucesso: () -> Unit
    ) {
        if (nome.isBlank() || gmail.isBlank() || curso.isBlank() || senha.isBlank()) {
            mensagemErro = "Preencha todos os campos!"
            return
        }
        if (senha != confirmarSenha) {
            mensagemErro = "As senhas não coincidem!"
            return
        }

        viewModelScope.launch {
            val novoUsuario = Usuario(
                nome = nome,
                gmail = gmail,
                curso = curso,
                senha = senha
            )
            usuarioDao.inserir(novoUsuario)
            mensagemErro = ""
            onSucesso()
        }
    }

    fun cadastrarProcesso(
        projeto: String,
        dataInicio: String,
        dataTermino: String,
        status: String,
        onSucesso: () -> Unit
    ) {
        if (projeto.isBlank() || dataInicio.isBlank() || dataTermino.isBlank() || status.isBlank()) {
            mensagemErro = "Preencha todos os campos!"
            return
        }

        viewModelScope.launch {
            val novoProcesso = ProcessoSeletivo(
                projeto = projeto,
                dataInicio = dataInicio,
                dataTermino = dataTermino,
                status = status
            )
            processoDao.inserir(novoProcesso)
            listaProcessos = processoDao.listarTodos()
            mensagemErro = ""
            onSucesso()
        }
    }

    fun atualizarStatusProcesso(processo: ProcessoSeletivo) {
        val novoStatus = when (processo.status) {
            "Ainda vai abrir" -> "Em andamento"
            "Em andamento" -> "Já ocorreu"
            else -> "Ainda vai abrir"
        }

        viewModelScope.launch {
            val atualizado = processo.copy(status = novoStatus)
            processoDao.atualizar(atualizado)
            listaProcessos = processoDao.listarTodos()
        }
    }

    fun excluirProcesso(processo: ProcessoSeletivo) {
        viewModelScope.launch {
            processoDao.deletar(processo)
            listaProcessos = processoDao.listarTodos()
        }
    }

    fun atualizarPerfil(novoNome: String, novoGmail: String, novoCurso: String) {
        val atual = usuarioLogado ?: return
        if (novoNome.isBlank() || novoGmail.isBlank() || novoCurso.isBlank()) {
            return
        }

        viewModelScope.launch {
            val atualizado = atual.copy(
                nome = novoNome,
                gmail = novoGmail,
                curso = novoCurso
            )
            usuarioDao.atualizar(atualizado)
            usuarioLogado = atualizado
        }
    }

    fun excluirConta(onSucesso: () -> Unit) {
        val atual = usuarioLogado ?: return
        viewModelScope.launch {
            usuarioDao.deletar(atual)
            usuarioLogado = null
            onSucesso()
        }
    }
}
