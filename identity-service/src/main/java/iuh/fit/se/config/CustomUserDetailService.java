package iuh.fit.se.config;

import iuh.fit.se.entity.Account;
import iuh.fit.se.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = repository.findByUsername(username);
        return account.map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}
