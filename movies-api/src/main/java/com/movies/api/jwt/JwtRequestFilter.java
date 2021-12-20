package com.movies.api.jwt;

import com.movies.api.support.LoginCache;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
        String url = request.getRequestURI();
        System.out.println("url : " + url);
        if (!url.equals("/api/userinfo") && !url.equals("/api/load/movie")) {
			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
				}
			} else {
				logger.warn("JWT Token does not begin with Bearer String");
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				if (!LoginCache.verify(username, jwtToken)) {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}

			}
		}
		chain.doFilter(request, response);
	}

}