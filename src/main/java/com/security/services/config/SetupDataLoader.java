package com.security.services.config;

import static com.security.services.constants.ApplicationConstants.DEFAULT_EMAIL;
import static com.security.services.constants.ApplicationConstants.DEFAULT_FIRST_NAME;
import static com.security.services.constants.ApplicationConstants.DEFAULT_LAST_NAME;
import static com.security.services.constants.ApplicationConstants.DEFAULT_PASSWORD;
import static com.security.services.constants.ApplicationConstants.DEFAULT_USERNAME;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.security.services.constants.ApplicationConstants;
import com.security.services.constants.RoleConstants;
import com.security.services.constants.SuperAdminPrivileges;
import com.security.services.model.Privilege;
import com.security.services.model.Role;
import com.security.services.model.RolePrivilege;
import com.security.services.model.User;
import com.security.services.model.UserRole;
import com.security.services.repository.PrivilegeRepository;
import com.security.services.repository.RolePrivilegeRepository;
import com.security.services.repository.RoleRepository;
import com.security.services.repository.UserRepository;
import com.security.services.repository.UserRoleRepository;


/**
 * This class is responsible for checking system defined roles and privileges on startup
 * 
 * @author qasim
 *
 */
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = Logger.getLogger(SetupDataLoader.class);
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RolePrivilegeRepository rolePrivilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        LOGGER.info("---------------------------Application checking Default User ------------------------------");
        Privilege readPrivilege = createPrivilegeIfNotFound(SuperAdminPrivileges.READ_PRIVILEGE.name());
        Privilege writePrivilege = createPrivilegeIfNotFound(SuperAdminPrivileges.WRITE_PRIVILEGE.name());
        Privilege updatePrivilege = createPrivilegeIfNotFound(SuperAdminPrivileges.UPDATE_PRIVILEGE.name());

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, updatePrivilege);

        Role adminRole = createRoleIfNotFound(RoleConstants.ROLE_SUPER_ADMIN, adminPrivileges);
        createSuperAdminIfNotExists(adminPrivileges, adminRole);

        alreadySetup = true;
    }

    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = Privilege.builder().name(name).build();
        }
        return privilege;
    }

    Role createRoleIfNotFound(RoleConstants name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = Role.builder().name(name).build();
        }
        return role;
    }


    void createSuperAdminIfNotExists(List<Privilege> adminPrivileges, Role adminRole) {
        if (Objects.isNull(userRepository.findOneByUsername(DEFAULT_USERNAME))) {
            User user = new User();
            user.setUsername(ApplicationConstants.DEFAULT_USERNAME);
            user.setFirstName(DEFAULT_FIRST_NAME);
            user.setLastName(DEFAULT_LAST_NAME);
            user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            user.setEmail(DEFAULT_EMAIL);
            user.setEnabled(true);
            userRepository.save(user);
            roleRepository.save(adminRole);

            user.setUserRoles(Collections.singletonList(UserRole.builder().user(user).role(adminRole).build()));

            Collection<RolePrivilege> rolePrivilges = new ArrayList<RolePrivilege>();

            adminPrivileges.forEach(o -> {
                o.setRoles(Arrays.asList(adminRole));
                privilegeRepository.save(o);

                rolePrivilges
                        .add(RolePrivilege
                                .builder()
                                .privilege(o)
                                .role(adminRole)
                                .userRole(user.getUserRoles().get(0))
                                .build());
            });

            rolePrivilegeRepository.saveAll(rolePrivilges);
        }
    }
}
