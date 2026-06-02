package org.fleetflow.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    public void setHashedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

}

git add pom.xml
git commit -m " add JWT dependencies (jjwt)"

git add src/main/java/org/fleetflow/fleetflow/entity/User.java
git commit -m " add password hashing and validation to User entity"

git add src/main/java/org/fleetflow/fleetflow/dto/AuthRequest.java
git commit -m " create AuthRequest DTO with validation"

git add src/main/java/org/fleetflow/fleetflow/dto/AuthResponse.java
git commit -m " create AuthResponse and AuthRequest DTO for API "

git add src/main/java/org/fleetflow/fleetflow/repository/UserRepository.java
git commit -m " create UserRepository"

git add src/main/java/org/fleetflow/fleetflow/security/
git commit -m " add JWT utilities and security configuration"

git add src/main/java/org/fleetflow/fleetflow/service/AuthService.java
git commit -m " create AuthService with registration and login logic"

git add src/main/java/org/fleetflow/fleetflow/controller/AuthController.java
git commit -m " create AuthController with authentication endpoints"