package zura.pustota.securityjwt.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import zura.pustota.securityjwt.utility.FilterChainUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/token/refresh/**")){
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
                if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                    try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    FilterChainUtil.authorizationFilterChain(token, "secret");
                    filterChain.doFilter(request, response);
                    }
                    catch (Exception e){
                        log.error("Error logging in {}", e.getMessage());
                        response.setHeader("error", e.getMessage());
                        response.setStatus(FORBIDDEN.value());
//                        response.sendError(FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error", e.getMessage());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }
                }
                else{
                    filterChain.doFilter(request, response);
                }
        }
    }
}
