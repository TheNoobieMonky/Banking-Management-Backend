package com.banking.BankingManagement.Filter;

import com.banking.BankingManagement.Model.Users;
import com.banking.BankingManagement.Services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.equals("/api/users") && method.equals("POST") || path.equals("/api/users/login")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")){

            jwt = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
            }
            catch (Exception e){
                System.out.println("JWT Error: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(jwt, userDetails.getUsername())){

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication((authToken));
            }
        }

        filterChain.doFilter(request, response);
    }
}
