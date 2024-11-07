package com.smartinix.springauthserver.user.infrastructure.persistance.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    private UserEntity user;

    public static Authority fromSimpleGrantedAuthority(SimpleGrantedAuthority simpleGrantedAuthority, UserEntity user) {
        Authority authority= new Authority();
        authority.user = user;
        authority.name = simpleGrantedAuthority.getAuthority();
        return authority;
    }
}
