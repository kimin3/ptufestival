// dto/LoginRequestDto.java
package com.capstone7.ptufestival.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
