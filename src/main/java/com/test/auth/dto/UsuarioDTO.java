package com.test.auth.dto;

import com.test.roles.dto.RolDTO;
import com.test.roles.entity.Rol;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    private int idUsuario;

    @NotNull(message = "Correo del usuario es requerido")
    @Size(max = 80, message = "Longitud de correo máximo es de 80 caracteres")
    private String correo;

    @NotNull(message = "Constraseña del usuario es requerido")
    @Size(max = 30, message = "Longitud de la contraseña máxima es de 30 caracteres")
    private String contrasena;

    @NotNull(message = "Roles del usuario son requeridos")
    private Set<RolDTO> roles;

    private boolean activo;

    private LocalDateTime fechaCreacion;

    private Set<? extends GrantedAuthority> authorities;

    public void setGrantedAuthorities(Set<Rol> roles) {
        this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @PrePersist
    public void setValuesPrePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }
}
