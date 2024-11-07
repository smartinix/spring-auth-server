package com.smartinix.springauthserver.user.infrastructure.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Entity
@Table(name = "users")

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 36)
    @Column(length = 36)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 36)
    @Column(length = 36)
    private  String username;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @NotNull
    @Column(name = "expired")
    private  Boolean accountExpired;

    @NotNull
    @Column(name = "locked")
    private  Boolean accountLocked;

    @NotNull
    @Column(name = "credentials_expired")
    private  Boolean credentialsExpired;

    @NotNull
    @Column(name = "enabled")
    private  Boolean enabled;

    public static Consumer<Set<SimpleGrantedAuthority>> simpleUserAuthorities(List<Authority> authorityList) {
        return simpleGrantedAuthorities -> {
            for (Authority authority: authorityList) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        };
    }
}
