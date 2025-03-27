package com.crisordonez.registro.configuration

import com.crisordonez.registro.service.CuentaUsuarioDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    @Autowired
    lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests { registry ->
                registry.requestMatchers("/usuarios/registro", "/usuarios/autenticar").permitAll()
                registry.requestMatchers("/anamnesis/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/usuarios/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/evolucion/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/info-socioeconomica/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/paciente/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/prueba/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/salud-sexual/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/sesion-chat/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/archivo/admin/**").hasRole("ADMIN")
                registry.requestMatchers("/usuarios/editar/**").hasRole("USER")
                registry.requestMatchers("/info-socioeconomica/editar/**", "/info-socioeconomica/usuario/**").hasRole("USER")
                registry.requestMatchers("/paciente/usuario/**", "/paciente/editar/**", "/registrar-dispositivo/**").hasRole("USER")
                registry.requestMatchers("/sesion-chat/usuario/**").hasRole("USER")
                registry.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun userDetailService(): UserDetailsService {
        return cuentaUsuarioDetailService
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(cuentaUsuarioDetailService)
        provider.setPasswordEncoder(passwordEncoder())

        return provider
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(authenticationProvider())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}