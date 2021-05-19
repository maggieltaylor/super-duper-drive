package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialView;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public int addCred(Credential credential, String username) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedKey));
        credential.setKey(encodedKey);
        if (credential.getCredentialId() != null) {
            return credentialsMapper.update(credential);
        } else {
            User user = userMapper.selectByUsername(username);
            credential.setUserId(user.getUserId());
            return credentialsMapper.insert(credential);
        }
    }

    public List<CredentialView> getCreds() {
        return credentialsMapper.selectAll()
                .stream()
                .map(credential -> new CredentialView(
                        credential.getCredentialId(),
                        credential.getUrl(),
                        credential.getUsername(),
                        credential.getKey(),
                        credential.getPassword(),
                        encryptionService.decryptValue(credential.getPassword(), credential.getKey()),
                        credential.getUserId()))
                .collect(Collectors.toList());
    }

    public int deleteCred(Long id) {
        return credentialsMapper.delete(id);
    }


}