package com.thanhtd.glassstore.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.model.User;
import com.thanhtd.glassstore.service.JwtService;
import com.thanhtd.glassstore.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.io.OutputStream;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            final String authHeader = request.getHeader("Authorization");
            if (ObjectUtils.isEmpty(authHeader) || !authHeader.toLowerCase().startsWith("bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            String phone = jwtService.extractEmailAddress(token);
            if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.findByEmailAddress(phone);
                ErrorCode tokenValidating = jwtService.validateToken(token, user);
                if (tokenValidating == ErrorCode.TOKEN_INVALID) throw new LogicException(ErrorCode.LOGIN_FAIL);
                if (tokenValidating == ErrorCode.TOKEN_VALID) {
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            exceptionHandler(response, HttpServletResponse.SC_UNAUTHORIZED, "You are not authenticated", start);
        } catch (Exception e) {
            logger.error(e);
            exceptionHandler(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error", start);
        }

    }

    private void exceptionHandler(HttpServletResponse response, int codeStatus, String message, long start) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, new APIResponse(codeStatus, "success", message, System.currentTimeMillis() - start, null));
        responseStream.flush();
    }
}
