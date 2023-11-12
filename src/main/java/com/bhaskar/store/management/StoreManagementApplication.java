package com.bhaskar.store.management;

import com.bhaskar.store.management.entity.Role;
import com.bhaskar.store.management.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class StoreManagementApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StoreManagementApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${admin.role.id}")
    String role_admin_id = "";
    @Value("${normal.role.id}")
    String role_normal_id = "";

    @Override
    public void run(String... args)throws Exception{
        //System.out.println(passwordEncoder.encode("kapil@123"));
        try {

            Role roleAdmin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
            Role roleNormal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
            //roleRepository.saveAll(Arrays.asList(roleAdmin,roleNormal));
            roleRepository.save(roleAdmin);
            roleRepository.save(roleNormal);
        }catch (Exception ex){
            ex.getStackTrace();
        }
    }

}
