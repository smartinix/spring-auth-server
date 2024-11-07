package com.smartinix.springauthserver.client.application;

import com.smartinix.springauthserver.client.domain.repository.ClientRepository;
import com.smartinix.springauthserver.client.infrastructure.persistance.entities.AuthenticationMethod;
import com.smartinix.springauthserver.client.infrastructure.persistance.entities.Client;
import com.smartinix.springauthserver.client.infrastructure.persistance.entities.GrantType;
import com.smartinix.springauthserver.client.infrastructure.persistance.entities.RedirectUrl;
import com.smartinix.springauthserver.client.infrastructure.persistance.entities.Scope;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public void save(RegisteredClient registeredClient) {
        Client client = new Client();

        client.setClientId(registeredClient.getClientId());
        client.setSecret(registeredClient.getClientSecret());
        client.setAuthenticationMethods(
            registeredClient.getClientAuthenticationMethods()
                .stream().map(a -> AuthenticationMethod.from(a, client))
                .collect(Collectors.toList())
        );
        client.setGrantTypes(
            registeredClient.getAuthorizationGrantTypes()
                .stream().map(g -> GrantType.from(g, client))
                .collect(Collectors.toList())
        );
        client.setRedirectUrls(
            registeredClient.getRedirectUris()
                .stream().map(u -> RedirectUrl.from(u, client))
                .collect(Collectors.toList())
        );
        client.setScopes(
            registeredClient.getScopes()
                .stream().map(s -> Scope.from(s, client))
                .collect(Collectors.toList())
        );

        clientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        var client = clientRepository.findById(Integer.parseInt(id));
        return client.map(Client::fromClient)
            .orElseThrow(() -> new RuntimeException("Client with ID: " + id + " is not found!"));
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientRepository.findByClientId(clientId);
        return client.map(Client::fromClient)
            .orElseThrow(() -> new RuntimeException("Client with clientId: " + clientId + " is not found!"));
    }
}
