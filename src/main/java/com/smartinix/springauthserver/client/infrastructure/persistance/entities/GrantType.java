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
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Entity
@Table(name = "grant_types")
@Getter
@Setter
public class GrantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "grant_type")
    private String grantType;

    @ManyToOne
    private Client client;

    public static GrantType from(AuthorizationGrantType authorizationGrantType, Client client) {
        GrantType g = new GrantType();

        g.setGrantType(authorizationGrantType.getValue());
        g.setClient(client);

        return g;
    }
}
