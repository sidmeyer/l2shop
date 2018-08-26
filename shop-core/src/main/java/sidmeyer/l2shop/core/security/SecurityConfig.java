package sidmeyer.l2shop.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sidmeyer.l2shop.api.Api;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Autowired
    CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.csrf().disable();
        http
                .authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher(Api.USER + "/**")).hasRole(ROLE_USER)
                .requestMatchers(new AntPathRequestMatcher(Api.ADMIN + "/**")).hasRole(ROLE_ADMIN)
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(customBasicAuthenticationEntryPoint).realmName("SHOPREALM");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin").password("$2a$04$0bFhduHgyPjLd/vNglfcq.9khw5EJ/GRxejJT8HXP9P/ysZ5Mk29G").roles(ROLE_ADMIN);
//                .and()
//                .withUser("user").password("$2a$04$q66Fuh1MrRhqthvBwo4NXeJOTjaOuWKV66dPqyuUR/hgi17zPoGte").roles(ROLE_USER);

        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public MyDaoAuthenticationProvider authenticationProvider() {
        MyDaoAuthenticationProvider authProvider = new MyDaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
