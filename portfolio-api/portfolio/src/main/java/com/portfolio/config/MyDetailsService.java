package com.portfolio.config;

import com.portfolio.model.User;
import com.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyDetailsService implements UserDetailsService {

    private final UserRepository usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepo.findByUsername(username);
        if(user == null){
            System.out.println("User not found");
            log.info("User not found with this username :{}", username);
            throw  new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user.get());
    }
}
