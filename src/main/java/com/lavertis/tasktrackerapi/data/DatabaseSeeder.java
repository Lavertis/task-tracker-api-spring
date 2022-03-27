package com.lavertis.tasktrackerapi.data;

import com.lavertis.tasktrackerapi.entities.Role;
import com.lavertis.tasktrackerapi.entities.User;
import com.lavertis.tasktrackerapi.repositories.IRoleRepository;
import com.lavertis.tasktrackerapi.repositories.IUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DatabaseSeeder(IUserRepository userRepository, IRoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedAdmin();
    }

    private void seedRoles() {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
        roleRepository.flush();
    }

    private void seedAdmin() {
        var encodedPassword = passwordEncoder.encode("admin");
        User admin = new User("admin", encodedPassword);
        Role adminRole = roleRepository.findByName("ADMIN");
        admin.addRole(adminRole);
        userRepository.saveAndFlush(admin);
    }
}
