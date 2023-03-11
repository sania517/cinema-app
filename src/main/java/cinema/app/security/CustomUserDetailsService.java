package cinema.app.security;

import cinema.app.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        cinema.app.model.User user = userService.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Can not find user with username: " + username));
        UserBuilder builder = User.withUsername(username);
        builder.password(user.getPassword());
        String[] strings = user.getRoles()
                .stream()
                .map(r -> r.getName().name())
                .toArray(String[]::new);

        builder.authorities(user.getRoles()
                .stream()
                .map(r -> r.getName().name())
                .toArray(String[]::new));

        return builder.build();
    }
}
