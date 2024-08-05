package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
//srping security otomatik davranışı default davranışı devre dışı bırak anlamına geliyor
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }
//uygulamını  ayağa kalmakmasında sonra belli işler yapmasını istersek srping boot bize şöyle bir secenek sunuyor

@Bean
@Profile("dev")// bu sadece dev de çalışacak product ortamında olunca çalışmaz
    CommandLineRunner userCreator(UserRepository userRepository){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return( args)-> {
                for(var i=1;i<=25;i++){
                    User user = new User();
                    user.setUsername("user"+i);
                    user.setEmail("user"+i+"@mail.com");
                    user.setPassword(passwordEncoder.encode("P1ssword"+i));
                    user.setActive(true);
                    userRepository.save(user);//burada database eklenme yapıldı


                }

            };
        }

}




