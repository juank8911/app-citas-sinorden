package com.colsubsidio.salud.auth.common.security;

import com.colsubsidio.salud.auth.models.Role;
import com.colsubsidio.salud.auth.models.User;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getAuth_key(),
                user.getPassword_hash(),
                user.getPassword_reset_token(),
                user.getEmail(),
                user.getStatus(),
                user.getPhone(),
                user.getName(),
                user.getChange_password(),
                user.getReset_token(),
                user.getEnabled(),
                user.getCreated_at(),
                user.getUpdated_at(),
                user.getLastpasswordresetdate(),
                user.getUsr_id_create(),
                user.getUsr_id_update(),
                mapToGrantedAuthorities(user.getAuthorities())
        ) {
        };
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
