package cinema.app.config;

import cinema.app.model.Role;
import cinema.app.model.User;
import cinema.app.service.RoleService;
import cinema.app.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public DataInitializer(
            RoleService roleService,
            UserService userService,
            PasswordEncoder encoder
    ) {
        this.roleService = roleService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @PostConstruct
    public void inject() {
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);
        User user = new User();
        user.setEmail("admin@i.ua");
        user.setPassword(encoder.encode("admin123"));
        user.setRoles(Set.of(adminRole));
        userService.add(user);
    }
}
