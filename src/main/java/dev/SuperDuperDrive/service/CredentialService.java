package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.Credential;
import dev.SuperDuperDrive.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void saveCredential(Credential credential) {
        if (credential.getCredentialid() == null) {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

            credential.setKeyy(encodedKey);
            credential.setPassword(encryptedPassword);
            credentialMapper.insertCredential(credential);
            return;
        }

        Credential storedCredential = credentialMapper.getCredential(credential.getCredentialid(),
                credential.getEmail());
        if (storedCredential == null) {
            return;
        }

        String encodedKey = storedCredential.getKeyy();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKeyy(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credential);
    }

    public Credential decryptPass(Credential credential) {
        String keyencoded = credential.getKeyy();
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), keyencoded);
        credential.setPassword(decryptedPassword);
        return credential;
    }

    public List<Credential> loadCredentials(String email, String keyword) {
        List<Credential> credentials;
        if (keyword.equals("")) {
            credentials = credentialMapper.selectCredentials(email);
        } else {
            credentials = credentialMapper.searchCredentials(email, keyword);
        }

        return credentials.stream().map(credential -> decryptPass(credential)).collect(Collectors.toList());
    }

    public int deleteCredential(Integer credentialid, String email) {
        return credentialMapper.deleteCredential(credentialid, email);
    }
}
