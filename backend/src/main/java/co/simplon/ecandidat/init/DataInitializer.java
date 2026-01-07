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
import co.simplon.ecandidat.service.MontpellierFormationService;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final FormationRepository formationRepository;
    private final PasswordEncoder passwordEncoder;
    private final MontpellierFormationService montpellierFormationService;

    public DataInitializer(RoleRepository roleRepository,
                          UserRepository userRepository,
                          FormationRepository formationRepository,
                          PasswordEncoder passwordEncoder,
                          MontpellierFormationService montpellierFormationService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
        this.passwordEncoder = passwordEncoder;
        this.montpellierFormationService = montpellierFormationService;
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
            System.out.println("Chargement des formations depuis l'API de Montpellier...");

            List<FormationEntity> formations = montpellierFormationService.fetchFormationsFromMontpellier();

            if (!formations.isEmpty()) {
                formationRepository.saveAll(formations);
                System.out.println(formations.size() + " formations de Montpellier importées avec succès");
            } else {
                System.out.println("Échec du chargement depuis l'API, création de formations de test...");

                formationRepository.save(new FormationEntity(
                        "Master en Santé Publique", "Master 2", "UFR Santé", "Candidatures 2024-2025"));
                formationRepository.save(new FormationEntity(
                        "Master en Biologie", "Master 1", "UFR Sciences", "Candidatures 2024-2025"));
                formationRepository.save(new FormationEntity(
                        "Master en Informatique", "Master 2", "UFR Informatique", "Candidatures 2024-2025"));
                formationRepository.save(new FormationEntity(
                        "Master en Droit", "Master 1", "UFR Droit et Sciences Politiques", "Candidatures 2024-2025"));
                formationRepository.save(new FormationEntity(
                        "Master en Économie", "Master 2", "UFR Économie et Gestion", "Candidatures 2024-2025"));
                formationRepository.save(new FormationEntity(
                        "Master en Histoire", "Master 1", "UFR Lettres et Sciences Humaines", "Candidatures 2024-2025"));

                System.out.println("Formations de test créées avec succès");
            }
        }
    }
}
