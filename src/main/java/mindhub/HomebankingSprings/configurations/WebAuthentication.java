package mindhub.HomebankingSprings.configurations;

import mindhub.HomebankingSprings.models.Client;
import mindhub.HomebankingSprings.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    ClientRepository repositories;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override //indico que voy a sobre escribir este metodo init que viene de la clase
// GlobalAuthenticationConfigurerAdapter tambien me ayuda a sobreescribirlo bien, evitar errores.
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            Client client = repositories.findByEmail(email);

//            En esta línea, se busca al cliente en el repositorio de clientes (repositories
//                   ) utilizando la dirección de correo electrónico (email) proporcionada durante el proceso de autenticación.
//            Si se encuentra un cliente con ese correo electrónico, se almacena en la variable client.
            if (client != null) {
                if (client.getEmail().equals("AdminAgileBank@gmail.com")) {
                    return new User(  // 2. Si se encuentra el cliente, se crea un objeto UserDetails con su nombre, contraseña y roles.
                            client.getEmail(),// Nombre del usuario
                            client.getPassword(),// Contraseña del usuario
                            AuthorityUtils.createAuthorityList("ADMIN"));
//                        AuthorityUtils.createAuthorityList("CLIENT")  AuthorityUtil Es una clase proporcionada por Spring Security que contiene métodos de utilidad para trabajar con autoridades (roles) de usuario.
                }
                ;
                return new User( // 2. Si se encuentra el cliente, se crea un objeto UserDetails con su nombre, contraseña y roles.

                        client.getEmail(),// Nombre del usuario
                        client.getPassword(),// Contraseña del usuario
                        AuthorityUtils.createAuthorityList("CLIENT")
//                        AuthorityUtils.createAuthorityList("CLIENT")  AuthorityUtil Es una clase proporcionada por Spring Security que contiene métodos de utilidad para trabajar con autoridades (roles) de usuario.
                );
            }
            else {
                throw new UsernameNotFoundException("Unknown user: " + email);
            }
        });
    }
}
