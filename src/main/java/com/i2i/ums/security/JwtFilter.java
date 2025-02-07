package com.i2i.ums.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.i2i.ums.exception.UnAuthorizedException;
import com.i2i.ums.service.MemberCredentialService;
import com.i2i.ums.service.impl.MemberCredentialServiceImpl;
import com.i2i.ums.utils.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private ApplicationContext context;

    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver
                             handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private String validateAndExtractToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Invalid token format or No-Token");
        }
        return authHeader.substring(7);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        if (request.getRequestURI().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            token = validateAndExtractToken(authHeader);
            username = JwtUtil.extractUserName(token);

            User memberCredentials = (User) context.getBean(MemberCredentialService.class)
                    .loadUserByUsername(username);
            request.setAttribute("username", username);
            JwtUtil.validateToken(token, memberCredentials);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(memberCredentials.getUsername(), null,
                            memberCredentials.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
