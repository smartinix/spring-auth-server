package com.smartinix.springauthserver.client.infrastructure.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Entity
@Table(name = "authentication_methods")
@Getter
@Setter
public class AuthenticationMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "authentication_method")
    private String authenticationMethod;

    @ManyToOne
    private Client client;

    public static AuthenticationMethod from(ClientAuthenticationMethod authenticationMethod, Client client) {
        AuthenticationMethod a = new AuthenticationMethod();
        a.setAuthenticationMethod(authenticationMethod.getValue());
        a.setClient(client);
        return a;
    }
}
