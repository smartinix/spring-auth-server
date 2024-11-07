package com.smartinix.springauthserver.client.infrastructure.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id")
    private String clientId;

    private String secret;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<AuthenticationMethod> authenticationMethods;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<GrantType> grantTypes;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<RedirectUrl> redirectUrls;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Scope> scopes;

    @OneToOne(mappedBy = "client")
    private ClientTokenSettings clientTokenSettings;

    public static RegisteredClient fromClient(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
            .clientId(client.getClientId())
            .clientSecret(client.getSecret())
            .clientAuthenticationMethods(clientAuthenticationMethods(client.getAuthenticationMethods()))
            .authorizationGrantTypes(authorizationGrantType(client.getGrantTypes()))
            .scopes(scopes(client.getScopes()))
            .redirectUris(redirectUris(client.redirectUrls))
            .tokenSettings(TokenSettings.builder()
                               .accessTokenTimeToLive(Duration.ofHours(client.clientTokenSettings.getAccessTokenTTL()))
                               .accessTokenFormat(new OAuth2TokenFormat(client.clientTokenSettings.getType()))
                               .build())
            .build();
    }

    private static Consumer<Set<AuthorizationGrantType>> authorizationGrantType(List<GrantType> grantTypes) {
        return s -> {
            for (GrantType g: grantTypes) {
                s.add(new AuthorizationGrantType(g.getGrantType()));
            }
        };
    }

    private static Consumer<Set<ClientAuthenticationMethod>> clientAuthenticationMethods(List<AuthenticationMethod> authenticationMethods) {
        return m -> {
            for (AuthenticationMethod a: authenticationMethods) {
                m.add(new ClientAuthenticationMethod(a.getAuthenticationMethod()));
            }
        };
    }

    private static Consumer<Set<String>> scopes(List<Scope> scopes) {
        return s -> {
            for (Scope scope : scopes) {
                s.add(scope.getScope());
            }
        };
    }

    private static Consumer<Set<String>> redirectUris(List<RedirectUrl> redirectUrls) {
        return r -> {
            for (RedirectUrl redirectUrl: redirectUrls) {
                r.add(redirectUrl.getUrl());
            }
        };
    }
}
