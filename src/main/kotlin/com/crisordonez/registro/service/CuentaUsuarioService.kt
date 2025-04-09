package com.crisordonez.registro.service

import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toEntity
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toEntityUpdated
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toResponse
import com.crisordonez.registro.model.mapper.CuentaUsuarioMapper.toUpdateContrasena
import com.crisordonez.registro.model.mapper.InformacionSocioeconomicaMapper.toEntity
import com.crisordonez.registro.model.mapper.PacienteMapper.toEntity
import com.crisordonez.registro.model.requests.CuentaUsuarioRequest
import com.crisordonez.registro.model.responses.CuentaUsuarioResponse
import com.crisordonez.registro.repository.CuentaUsuarioRepository
import com.crisordonez.registro.repository.InformacionSocioeconomicaRepository
import com.crisordonez.registro.repository.PacienteRepository
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
    @Autowired private val pacienteRepository: PacienteRepository,
    @Autowired private val informacionSocioeconomicaRepository: InformacionSocioeconomicaRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
): CuentaUsuarioServiceInterface {

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    private val log = LoggerFactory.getLogger(CuentaUsuarioService::class.java)

    override fun crearCuentaUsuario(cuentaUsuario: CuentaUsuarioRequest): CuentaUsuarioResponse {
        try {
            log.info("Creando cuenta de usuario - Nombre: ${cuentaUsuario.nombreUsuario}")
            val usuarioDuplicado = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

            if (usuarioDuplicado.isPresent) {
                log.error("La informacion ya esta registrada")
                throw Exception("El nombre de usuario ya estan registrados")
            }
            if (cuentaUsuario.paciente == null) {
                throw Exception("La informacion del paciente no puede ser nula")
            }
            val cuenta = cuentaUsuarioRepository.save(cuentaUsuario.toEntity(passwordEncoder.encode(cuentaUsuario.contrasena)))
            val paciente = pacienteRepository.save(cuentaUsuario.paciente.toEntity(cuenta))
            cuenta.paciente = paciente
            cuentaUsuarioRepository.save(cuenta)
            if (cuentaUsuario.paciente.infoSocioeconomica != null) {
                val info = informacionSocioeconomicaRepository.save(cuentaUsuario.paciente.infoSocioeconomica.toEntity(paciente))
                paciente.informacionSocioeconomica = info
                pacienteRepository.save(paciente)
            }
            log.info("Cuenta de usuario creada exitosamente, iniciando sesion")
            val token = autenticar(cuentaUsuario)
            return token
        } catch (e: Exception) {
            log.error("Error al crear la cuenta de usuario", e)
            throw e
        }
    }

    override fun editarCuentaUsuario(publicId: UUID, cuentaUsuario: CuentaUsuarioRequest) {
        try {
            log.info("Editando cuenta de usuario - PublicId: $publicId")

            val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

            if (!cuenta.isPresent) {
                throw Exception("La cuenta de usuario no existe")
            }

            val cuentaExistente = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

            if (cuentaExistente.isPresent && cuentaExistente.get().publicId != publicId) {
                throw Exception("El correo o nombre de usuario ya existen")
            }
            cuentaUsuarioRepository.save(cuentaUsuario.toEntityUpdated(cuenta.get()))
            log.info("Cuenta de usuario actualizada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getAllCuentas(): List<CuentaUsuarioResponse> {
        try {
            log.info("Consultando todas las cuentas de usuario")
            val cuentas = cuentaUsuarioRepository.findAll().map { it.toResponse(null) }
            log.info("Cuentas de usuario consultadas - Total: ${cuentas.size}")
            return cuentas
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getCuentaUsuario(publicId: UUID): CuentaUsuarioResponse {
        try {
            log.info("Consultando cuenta de usuario - PublicId: $publicId")
            val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

            if (!cuenta.isPresent) {
                throw Exception("La cuenta de usuario no existe")
            }

            log.info("Cuenta de usuario consultada correctamemte")
            return cuenta.get().toResponse(null)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun autenticar(cuentaUsuario: CuentaUsuarioRequest): CuentaUsuarioResponse {
        try {
            log.info("Autenticando usuario - Nombre: ${cuentaUsuario.nombreUsuario}")
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                cuentaUsuario.nombreUsuario, cuentaUsuario.contrasena
            ))

            if (authentication.isAuthenticated) {
                val token = jwtUtil.generateToken(cuentaUsuarioDetailService.loadUserByUsername(cuentaUsuario.nombreUsuario))
                val usuario = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario).get()
                return usuario.toResponse(token)
            } else {
                throw UsernameNotFoundException("Credenciales inválidas")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun eliminarCuentaUsuario(publicId: UUID) {
        try {
            log.info("Eliminando cuenta de usuario - PublicId: $publicId")
            val cuenta = cuentaUsuarioRepository.findByPublicId(publicId)

            if (!cuenta.isPresent) {
                throw Exception("La cuenta de usuario no existe")
            }

            cuentaUsuarioRepository.delete(cuenta.get())
            log.info("Cuenta de usuario eliminada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }

    override fun validarExpiracionToken(token: String): Boolean {
        try {
            log.info("Validando expiracion del token")
            val valido = jwtUtil.isTokenValid(token)
            log.info("Token validado correctamente - Estado: $valido")
            return valido
        } catch (e: Exception) {
            throw e
        }
    }

    override fun updateContrasena(cuentaUsuario: CuentaUsuarioRequest) {
        try {
            log.info("Cambiando contrasena de la cuenta ${cuentaUsuario.nombreUsuario}")
            val cuenta = cuentaUsuarioRepository.findByNombreUsuario(cuentaUsuario.nombreUsuario)

            if (!cuenta.isPresent) {
                throw Exception("La cuenta de usuario ${cuentaUsuario.nombreUsuario} no existe")
            }

            cuentaUsuarioRepository.save(cuenta.get().toUpdateContrasena(cuentaUsuario.contrasena))
            log.info("Contrasena cambiada correctamente")
        } catch (e: Exception) {
            throw e
        }
    }
}