package com.rojo.libraryapp.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Siteler arası istek sahteciliğini devre dışı bırakır
        http.csrf().disable();

        /* /api/books/secure/** altındaki tüm yollara erişimi yalnızca kimlik doğrulaması yapmış kullanıcılara kısıtlayan ve
         erişimi doğrulamak için JSON Web Token'ları (JWT'ler) kullanan bir OAuth 2.0 kaynak sunucusu ayarlar.*/

        http.authorizeRequests(configurer ->
                configurer
                        .antMatchers("/api/books/secure/**","/api/reviews/secure/**")
                        .authenticated())
                .oauth2ResourceServer()
                .jwt();

        // Cors filtrelerini ekler
        http.cors();

        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        Okta.configureResourceServer401ResponseBody(http);


        return http.build();
    }
}
