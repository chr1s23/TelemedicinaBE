package com.crisordonez.registro.configuration

import com.crisordonez.registro.service.CuentaUsuarioDetailService
import com.crisordonez.registro.utils.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
class JwtAuthenticationFilter: OncePerRequestFilter() {

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var cuentaUsuarioDetailService: CuentaUsuarioDetailService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        val userName = jwtUtil.extractUserName(jwt)

        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = cuentaUsuarioDetailService.loadUserByUsername(userName)
            if (userDetails != null && jwtUtil.isTokenValid(jwt)) {
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    userName,userDetails.password, userDetails.authorities
                )
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}