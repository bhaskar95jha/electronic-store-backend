package com.bhaskar.store.management.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        //Authorization
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header is : {}",requestHeader);
        //Bearer x.y.z
        String userName = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer")){
            token = requestHeader.split(" ")[1];
            try{
                userName = jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException ex){
                logger.info("Illegal Argument Exception while fatching the username !!");
                ex.printStackTrace();
            }catch (ExpiredJwtException ex){
                logger.info("Jwt token is expired while fatching the username !!");
                ex.printStackTrace();
            }catch (MalformedJwtException ex){
                logger.info("some changes has done in token !!  while fatching the username !!");
                ex.printStackTrace();
            }catch (Exception ex){
                logger.info(ex.getMessage());
                ex.printStackTrace();
            }
        }else{
            logger.info("Invelid requestHeader Value !!");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //fetch user  detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            Boolean validateToken = this.jwtHelper.validToken(token, userDetails);

            if(validateToken){
                //set the authentication
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }else {
                logger.info("Validation Fails !!");
            }

        }

        filterChain.doFilter(request,response);
    }
}
