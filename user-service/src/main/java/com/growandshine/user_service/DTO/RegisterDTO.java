package com.growandshine.user_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {

    private String userName;

    private String email;

    private String password;

    private String role;
}
