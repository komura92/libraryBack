package com.example.library.Service;

import com.example.library.Entity.User;
import com.example.library.Exceptions.UserNotFoundException;
import com.example.library.Login.MyUserDetails;
import com.example.library.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public MyUserDetails getUserByUsername(String username) {
        User user = usersRepository.findUserByLogin(username).orElseThrow(() -> new UserNotFoundException(username));
        return new MyUserDetails(user);
    }
}
