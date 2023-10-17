package com.groupa1.resq.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    String jwt;
    Long id;
    String username;
    String email;
    List<String> roles;
}
