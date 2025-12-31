package co.simplon.ecandidat.init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import co.simplon.ecandidat.entity.FormationEntity;
import co.simplon.ecandidat.entity.RoleEntity;
import co.simplon.ecandidat.entity.UserEntity;
import co.simplon.ecandidat.repository.FormationRepository;
import co.simplon.ecandidat.repository.RoleRepository;
import co.simplon.ecandidat.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final FormationRepository formationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                          UserRepository userRepository,
                          FormationRepository formationRepository,
                          PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
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

        if (formationRepository.count() == 0) {
            formationRepository.save(new FormationEntity(
                    "Master en Santé Publique", "Master", "UFR Santé", "01/03/2025 - 30/04/2025"));
            formationRepository.save(new FormationEntity(
                    "Doctorat en Médecine", "Doctorat", "UFR Santé", "15/01/2025 - 15/03/2025"));
            formationRepository.save(new FormationEntity(
                    "Doctorat en Pharmacie", "Doctorat", "UFR Santé", "15/01/2025 - 15/03/2025"));

            formationRepository.save(new FormationEntity(
                    "Licence en Biologie", "Licence", "UFR Sciences", "20/01/2025 - 20/03/2025"));
            formationRepository.save(new FormationEntity(
                    "Master en Physique", "Master", "UFR Sciences", "01/03/2025 - 30/04/2025"));
            formationRepository.save(new FormationEntity(
                    "Doctorat en Chimie", "Doctorat", "UFR Sciences", "15/01/2025 - 15/03/2025"));

            formationRepository.save(new FormationEntity(
                    "Licence en Histoire", "Licence", "UFR Lettres et Sciences Humaines", "20/01/2025 - 20/03/2025"));
            formationRepository.save(new FormationEntity(
                    "Master en Philosophie", "Master", "UFR Lettres et Sciences Humaines", "01/03/2025 - 30/04/2025"));

            formationRepository.save(new FormationEntity(
                    "Licence en Droit", "Licence", "UFR Droit et Sciences Politiques", "20/01/2025 - 20/03/2025"));
            formationRepository.save(new FormationEntity(
                    "Master en Droit Public", "Master", "UFR Droit et Sciences Politiques", "01/03/2025 - 30/04/2025"));

            System.out.println("Formations créées avec succès");
        }
    }
}
