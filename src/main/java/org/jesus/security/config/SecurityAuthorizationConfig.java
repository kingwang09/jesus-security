package org.jesus.security.config;

import lombok.RequiredArgsConstructor;
import org.jesus.security.domain.user.constant.UserRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
public class SecurityAuthorizationConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .and()
            .formLogin();
        //http.authorizeRequests().anyRequest().authenticated();//모든 요청 권한 체크
        //http.authorizeRequests().anyRequest().permitAll();//모든 요청 권한 체크 안함


        http.authorizeRequests().anyRequest().hasAnyAuthority(UserRole.USER.toString(), UserRole.ADMIN.toString());//허용됨.
        //http.authorizeRequests().anyRequest().hasAnyRole(UserRole.USER.toString(), UserRole.ADMIN.toString());//거부됨.
    }

    /**
     * CustomAuthenticationProvider 지정
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}
