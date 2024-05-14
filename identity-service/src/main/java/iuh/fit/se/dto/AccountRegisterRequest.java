package iuh.fit.se.dto;

import iuh.fit.se.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisterRequest {
    private String username;
    private Role role;
    private long user_id;

}
