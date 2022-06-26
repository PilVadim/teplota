package com.pilvadim.teplota.configuration;

import com.pilvadim.teplota.configuration.handler.CustomAuthenticationEntryPoint;
import com.pilvadim.teplota.configuration.handler.CustomAuthenticationFailureHandler;
import com.pilvadim.teplota.configuration.handler.CustomLogoutSuccessHandler;
import com.pilvadim.teplota.configuration.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(users());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.formLogin().failureHandler(authenticationFailureHandler());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.formLogin().successHandler(simpleUrlAuthenticationSuccessHandler());
        http.logout().logoutSuccessHandler(logoutSuccessHandler());


        http
                .requestCache()
                .requestCache( new NullRequestCache())
                .and()

                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/api/v1/temperatures**",
                                        "/api/v1/places**"
                ).permitAll()

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/place").authenticated()
                .antMatchers("/api/v1/start").authenticated()
                .antMatchers("/api/v1/stop").authenticated()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginProcessingUrl("/auth/login")
                .and()
                .logout().logoutUrl("/auth/logout")
        .and().headers().frameOptions().disable();

    }

    @Bean
    public UserDetailsService users() {

        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
