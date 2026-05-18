package com.sliit.oop_serverapp.config;

import com.sliit.oop_serverapp.entity.Admin;
import com.sliit.oop_serverapp.entity.User;
import com.sliit.oop_serverapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.sliit.oop_serverapp.repository.StatusRepository statusRepository;

    @Override
    public void run(String... args) throws Exception {

        if (statusRepository.count() == 0) {
            com.sliit.oop_serverapp.entity.Status pending = new com.sliit.oop_serverapp.entity.Status();
            pending.setName("Pending");
            statusRepository.save(pending);

            com.sliit.oop_serverapp.entity.Status complete = new com.sliit.oop_serverapp.entity.Status();
            complete.setName("Complete");
            statusRepository.save(complete);

            com.sliit.oop_serverapp.entity.Status cancelled = new com.sliit.oop_serverapp.entity.Status();
            cancelled.setName("Cancelled");
            statusRepository.save(cancelled);
            System.out.println("Order Statuses seeded: Pending, Complete, Cancelled");
        }

        if (userRepository.findByGmail("admin@lumina.com") == null) {
            Admin admin = new Admin();
            admin.setName("Lumina Administrator");
            admin.setGmail("admin@lumina.com");
            admin.setPassword("admin123");
            admin.setAge(30);
            admin.setIsadmin((byte) 1);
            userRepository.save(admin);
            System.out.println("Default Admin created: admin@lumina.com / admin123");
        }

        if (userRepository.findByGmail("user@lumina.com") == null) {
            User user = new User();
            user.setName("Orion Vance");
            user.setGmail("user@lumina.com");
            user.setPassword("user123");
            user.setAge(25);
            user.setIsadmin((byte) 0);
            userRepository.save(user);
            System.out.println("Default User created: user@lumina.com / user123");
        }
    }
}
