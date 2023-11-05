package mindhub.HomebankingSprings.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()//acceso
                        .antMatchers("/WEB/index.html","/WEB/css/**","/WEB/imagenes/**","/WEB/pages/login.html",
                                "/WEB/pages/register.html","/WEB/js/login.js","/WEB/js/register.js").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/clients","/api/login").permitAll()
                        .antMatchers("/WEB/pages/manager.html","/WEB/js/manager.js","/h2-console/**","/rest/**").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                        .antMatchers("/WEB/pages/account.html","/WEB/pages/accounts.html","/WEB/pages/cards.html",
                                "/WEB/pages/create-card.html","/WEB/pages/account.html","/WEB/js/**","/WEB/pages/loansAplication.html").hasAuthority("CLIENT")
                         .antMatchers(HttpMethod.POST, "/api/clients/current/accounts","/api/client/current/cards","/api/transactions").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.GET, "/api/clients/current","/api/accounts/{id}","/api/clients/current/accounts").hasAuthority("CLIENT")
                        .antMatchers("/WEB/pages/accounts.html","/WEB/pages/transfer.html","/WEB/js/loansAplication.js").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.GET, "/api/accounts/**").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.GET, "/api/loans").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                        .anyRequest().denyAll();


     // configuracion de authenticacion

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");

        // csrf se encarga de protejer los formularios de solicitudes maliciosas
        http.csrf().disable();

        // .frameOptions
        http.headers().frameOptions().disable();

        //si el usuario no esta autenticado
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));


        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}

