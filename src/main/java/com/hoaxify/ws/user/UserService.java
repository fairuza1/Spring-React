package com.hoaxify.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //instance öğrenn
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection dur
            UserRepository userRepository; //
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new NotUniqEmailException();

        }
    }


}
