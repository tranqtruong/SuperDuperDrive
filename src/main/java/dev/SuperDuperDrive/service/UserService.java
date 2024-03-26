package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.User;
import dev.SuperDuperDrive.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    private UserMapper userMapper;
    private HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int createUser(User user) {
        String encodedSalt = hashService.randomEncodedSalt();
        String passwordHashed = hashService.getHashedValue(user.getPassword(), encodedSalt);

        user.setSalt(encodedSalt);
        user.setPassword(passwordHashed);
        return userMapper.insertUser(user);
    }

    public int changePasswordUser(String email, String newPassword) {
        String newEncodedSalt = hashService.randomEncodedSalt();
        String newPasswordHashed = hashService.getHashedValue(newPassword, newEncodedSalt);

        User user = new User();
        user.setSalt(newEncodedSalt);
        user.setPassword(newPasswordHashed);
        user.setEmail(email);
        return userMapper.updateUser(user);
    }

    public boolean doesUserExist(String email) {
        return userMapper.selectUser(email) != null;
    }

    public User findUserByEmail(String email) {
        return userMapper.selectUser(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }
}
