package com.reis.pdv.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private JwtService jwtService; 
	private CustomUserDetailService customUserDetailService; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		
		String authorization = request.getHeader("Authorization");
		
		if(authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			String username = jwtService.getUserName(token);
			
			UserDetails user = customUserDetailService.loadUserByUsername(username);
			
			//Cria um usuário que será inserido no contexto do Spring Security
			UsernamePasswordAuthenticationToken userCtx = 
					new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
			
			//Configurando o Spring Security como uma autenticacao Web
			userCtx.setDetails(new WebAuthenticationDetailsSource()
					.buildDetails(request));
			
			//Inserindo o usuario dentro do contexto do Spring Security
			SecurityContextHolder.getContext().setAuthentication(userCtx);
		}
		
		filterChain.doFilter(request,response);
	}

}
