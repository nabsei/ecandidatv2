package co.simplon.ecandidat.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.simplon.ecandidat.entity.RoleEntity;
import co.simplon.ecandidat.entity.UserEntity;
import co.simplon.ecandidat.repository.RoleRepository;
import co.simplon.ecandidat.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        RoleEntity userRole = roleRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_USER")));

        RoleEntity adminRole = roleRepository.findByAuthority("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleEntity("ROLE_ADMIN")));

        if (userRepository.findByEmail("admin@univ-occitanie.com").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setFirstname("Admin");
            admin.setLastname("Occitanie");
            admin.setEmail("admin@univ-occitanie.com");
            admin.setPassword(passwordEncoder.encode("univoccitanie"));

            Set<RoleEntity> adminAuthorities = new HashSet<>();
            adminAuthorities.add(adminRole);
            adminAuthorities.add(userRole);
            admin.setAuthorities(adminAuthorities);

            userRepository.save(admin);
            System.out.println("Utilisateur admin créé: admin@univ-occitanie.com / univoccitanie");
        }

        if (userRepository.findByEmail("user@test.com").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setFirstname("Jean");
            user.setLastname("Dupont");
            user.setEmail("user@test.com");
            user.setPassword(passwordEncoder.encode("password"));

            Set<RoleEntity> userAuthorities = new HashSet<>();
            userAuthorities.add(userRole);
            user.setAuthorities(userAuthorities);

            userRepository.save(user);
            System.out.println("Utilisateur test créé: user@test.com / password");
        }
    }
}
