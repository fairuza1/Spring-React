package com.hoaxify.ws.user;

import com.hoaxify.ws.email.EmailService;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.NotUniqEmailException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    //instance öğrenn
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection du

            UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    EmailService emailService;


    @Transactional(rollbackOn = MailException.class)//database kayıt edilenler yanlış ise geri alacak
//eğer bu method çalışırken bir mail exception ile karşılaşırsa db ye yazılan verileri geri al
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.activationToken);//silinebilir
// emailService.sendActivationEmail(user.getEmail(), user.getActivationToken());
        } catch (DataIntegrityViolationException ex) {
            throw new NotUniqEmailException();

        } catch (MailException ex) {
            throw new ActivationNotificationException();
        }
    }


    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);//burada user databaseden bulmaya çalışacağız eğer böyle bir user yoksa hata mesajı dönmek gerekiyor
        if (inDB == null) {
    //burdakş hatayı tetikleyen şey şu olabilir bu token artık geçerlilik hükmü kalmamış ise buraya girerek hata verdirece
       throw new InvalidTokenException();

        }//user varsa active true olacak ve bu tokenla işlemyapıldığı için bu token null olur ve database kayıt olacaktır
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }
}
