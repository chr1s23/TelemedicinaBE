package com.crisordonez.registro.service

import com.crisordonez.registro.model.entities.CuentaUsuarioEntity
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CuentaUsuarioDetailService : UserDetailsService {

    @Autowired
    lateinit var cuentaUsuarioRepository: CuentaUsuarioRepository

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        try {
            log.info("Iniciando sesion - Nombre usuario: $username")
            val usuario = cuentaUsuarioRepository.findByNombreUsuario(username!!)
            if (!usuario.isPresent) {
                throw UsernameNotFoundException(username)
            }
            return User.builder()
                .username(usuario.get().nombreUsuario)
                .password(usuario.get().contrasena)
                .roles(usuario.get().rol)
                .build()

        } catch (e: Exception) {
            throw e
        }
    }

    fun getRoles(usuario: CuentaUsuarioEntity): List<String> {
        return usuario.rol.split(",")
    }
}