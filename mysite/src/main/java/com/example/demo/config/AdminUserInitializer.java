package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AdminUser;
import com.example.demo.repository.AdminUserRepository;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final String bootstrapUsername;
    private final String bootstrapPassword;

    public AdminUserInitializer(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.bootstrap-username}") String bootstrapUsername,
            @Value("${app.admin.bootstrap-password}") String bootstrapPassword) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.bootstrapUsername = bootstrapUsername;
        this.bootstrapPassword = bootstrapPassword;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (adminUserRepository.count() > 0) {
            return;
        }

        AdminUser admin = new AdminUser();
        admin.setUsername(bootstrapUsername);
        admin.setPasswordHash(passwordEncoder.encode(bootstrapPassword));
        admin.setRole("ADMIN");
        admin.setEnabled(true);
        adminUserRepository.save(admin);

        log.warn("初期管理者をDBへ作成しました。公開前にADMIN_USERNAMEとADMIN_PASSWORDを変更してください。");
        log.info("初期管理者ユーザー名: {}", bootstrapUsername);
    }
}
