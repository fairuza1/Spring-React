package com.hoaxify.ws.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //generic bir interface 2 tane pparametre alacak birisi bizim objemiz
    //ikincisi o objenin primaykey tipi

}
