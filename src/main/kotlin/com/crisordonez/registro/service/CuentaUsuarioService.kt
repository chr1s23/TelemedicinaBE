package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toEntity
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toResponse
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import com.crisordonez.registro.utils.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class CuentaUsuarioService(
    @Autowired private val cuentaUsuarioRepository: CuentaUsuarioRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
): CuentaUsuarioServiceInterface {

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    private val log = LoggerFactory.getLogger(CuentaUsuarioService::class.java)

    override fun crearCuentaUsuario(cuentaUsuario: CuentaUsuarioRequest) {
        try {
            log.info("Creando cuenta de usuario - Nomnre: ${cuentaUsuario.nombreUsuario}")
            val usuarioDuplicado = if (cuentaUsuario.correo != null) {
                cuentaUsuarioRepository.findByCorreoOrNombreUsuario(cuentaUsuario.correo, cuentaUsuario.nombreUsuario)
            } else {
                cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)
            }

            if (usuarioDuplicado.isPresent) {
                log.error("La informacion ya esta registrada")
                throw Exception("El correo o nombre de usuario ya estan registrados")
            }

            cuentaUsuarioRepository.save(cuentaUsuario.toEntity(passwordEncoder.encode(cuentaUsuario.contrasena)))
            log.info("Cuenta de usuario creada exitosamente")
        } catch (e: Exception) {
            log.error("Error al crear la cuenta de usuario", e)
            throw e
        }
    }

    override fun editarCuentaUsuario(publicId: UUID, cuentaUsuario: CuentaUsuarioRequest) {
        TODO("Not yet implemented")
    }

    override fun getAllCuentas(): List<CuentaUsuarioResponse> {
        TODO("Not yet implemented")
    }

    override fun getCuentaUsuario(publicId: UUID): CuentaUsuarioResponse {
        TODO("Not yet implemented")
    }

    override fun login(cuentaUsuario: CuentaUsuarioRequest): CuentaUsuarioResponse {
        try {
            log.info("Iniciando sesion - Nombre: ${cuentaUsuario.nombreUsuario}")
            val usuario = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

            if (!usuario.isPresent) {
                log.error("Usuario no encontrado")
                throw Exception("Usuario no encontrado")
            }

//            val autenticado = BCrypt.checkpw(cuentaUsuario.contrasena, usuario.get().contrasena)
//
//            if (!autenticado) {
//                log.error("Contrasena incorrecta")
//                throw Exception("Contrasena incorrecta")
//            }

//            val jwt = jwtUtil.generateToken(usuario.get().nombreUsuario)

            log.info("Sesion iniciada exitosamente")
            return usuario.get().toResponse("jwt")
        } catch (e: Exception) {
            log.error("Error al iniciar sesion", e)
            throw e
        }
    }

    override fun autenticar(cuentaUsuario: CuentaUsuarioRequest): String {
        try {
            log.info("Autenticando usuario - Nombre: ${cuentaUsuario.nombreUsuario}")
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                cuentaUsuario.nombreUsuario, cuentaUsuario.contrasena
            ))

            if (authentication.isAuthenticated) {
                return jwtUtil.generateToken(cuentaUsuarioDetailService.loadUserByUsername(cuentaUsuario.nombreUsuario))
            } else {
                throw UsernameNotFoundException("Credenciales inválidas")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}