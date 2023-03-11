package cinema.app.service.impl;

import cinema.app.model.Role;
import cinema.app.model.User;
import cinema.app.service.AuthenticationService;
import cinema.app.service.RoleService;
import cinema.app.service.ShoppingCartService;
import cinema.app.service.UserService;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public AuthenticationServiceImpl(
            UserService userService,
            ShoppingCartService shoppingCartService,
            RoleService roleService,
            PasswordEncoder encoder
    ) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRoles(Set.of(roleService.getByName(Role.RoleName.USER)));
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
