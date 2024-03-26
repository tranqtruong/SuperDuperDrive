package dev.SuperDuperDrive.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    private int passwordLength = 10;
    private String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateRandomPassword(){
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
