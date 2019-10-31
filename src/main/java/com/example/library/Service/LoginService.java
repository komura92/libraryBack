package com.example.library.Service;

import com.example.library.Entity.Model.User.UserAuthenticatedModel;
import com.example.library.Entity.User;
import com.example.library.Entity.UserRole;
import com.example.library.Exceptions.BadPasswordException;
import com.example.library.Exceptions.UserAlreadyExistsException;
import com.example.library.Exceptions.UserNotFoundException;
import com.example.library.Login.MyUserDetails;
import com.example.library.Login.Roles;
import com.example.library.Login.Token.TokenProvider;
import com.example.library.Repository.UserRolesRepository;
import com.example.library.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UserRolesRepository userRolesRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(String username, String password) {
        if (usersRepository.findUserByLogin(username).isPresent())
            throw new UserAlreadyExistsException(username);

        List<UserRole> roles = new ArrayList<>();
            User user =
                    new User(
                            null,
                            username,
                            encoder.encode(password),
                            roles,
                            new ArrayList<>()
                    );
            UserRole userRole = new UserRole(null, usersRepository.save(user), Roles.USER);
            roles.add(userRolesRepository.save(userRole));
            user.setRoles(roles);

            usersRepository.save(user);
    }

    public void update(String username, String oldPassword, String newPassword) {
        User user = usersRepository.findUserByLogin(username).orElseThrow(() -> new UserNotFoundException(username));
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            usersRepository.save(user);
        } else {
            throw (new BadPasswordException(username));
        }
    }

    @Transactional
    public UserAuthenticatedModel login(String username, String password) {
        User user = usersRepository.findUserByLogin(username).orElseThrow(() -> new UserNotFoundException(username));
        if (BCrypt.checkpw(password, user.getPassword())){
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            authenticationManager.authenticate(authenticationToken);

            String token = tokenProvider.createToken(username);

            return UserAuthenticatedModel.builder()
                    .login(username)
                    .JWT(token)
                    .role(user.getRoles().get(0).getRole())
                    .build();
        }
        else
            throw (new BadPasswordException(username));
    }

    public void delete(String username, String password) {
        //TODO NO NO NO!!! User by token!!!
        User user = usersRepository.findUserByLogin(username).orElseThrow(() -> new UserNotFoundException(username));

        if (BCrypt.checkpw(password, user.getPassword()))
            usersRepository.delete(user);
        else
            throw (new BadPasswordException(username));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new MyUserDetails(usersRepository.findUserByLogin(s).orElseThrow(() -> new UserNotFoundException(s)));
    }
}
