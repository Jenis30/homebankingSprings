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
    @Override // Estoy sobreescribiendo el metodo configure que viene de WebSecurityConfigurerAdapter
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/WEB/**").permitAll()
                .antMatchers("/WEB/imagenes").permitAll()
                .antMatchers("/WEB/pages/login.html").permitAll()
                .antMatchers("/WEB/pages/register.html").permitAll()
                .antMatchers("/WEB/pages/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/accounts/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/").permitAll();

// Esto se refiere a cualquier solicitud entrante, sin importar la ruta o el método HTTP.
//                Esto deniega el acceso a cualquier solicitud que no haya sido autorizada previamente a través de reglas específicas.
        http.formLogin()

                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/app/login"); //cuando un usuario intenta acceder y no esta en nuestra bd se redirigirá a esta url o pagina
        http.logout()
                .logoutUrl("/app/logout").deleteCookies("JESSIONID");

//        http.logout().logoutUrl("/app/logout"); Esto inicia la configuración del proceso de cierre de sesión.
//        Esto establece la URL a la que los usuarios pueden acceder para cerrar sesión. En este caso, al acceder a "/app/logout", la sesión del usuario se cerrará.
        http.csrf()
                .disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers()
                .frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin()
                .successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin()
                .failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}
