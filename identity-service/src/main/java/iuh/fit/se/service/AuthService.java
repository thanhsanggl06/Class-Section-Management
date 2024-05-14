package iuh.fit.se.service;

import iuh.fit.se.constant.Constant;
import iuh.fit.se.dto.AccountRegisterRequest;
import iuh.fit.se.entity.Account;
import iuh.fit.se.entity.Lecturer;
import iuh.fit.se.entity.Student;
import iuh.fit.se.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveAccount(AccountRegisterRequest accountRegisterRequest){
        Account account = new Account();
        if(accountRegisterRequest.getUsername().length() == 7){
            account.setLecturer(new Lecturer(accountRegisterRequest.getUser_id()));
        }else if(accountRegisterRequest.getUsername().length() == 8){
            account.setStudent(Student.builder().id(accountRegisterRequest.getUser_id()).build());
        }else{
            return "username invalid";
        }
        account.setUsername(accountRegisterRequest.getUsername());
        account.setRole(accountRegisterRequest.getRole());
        account.setPassword(passwordEncoder.encode(Constant.DEFAULT_PWD));
        repository.save(account);
        return "account added to the system";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
