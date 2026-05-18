package com.sliit.oop_serverapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User implements AdminActions {

    private String adminPrivileges;

    @Override
    public void manageUsers() {
        System.out.println("Managing users...");
    }

    @Override
    public void monitorSystem() {
        System.out.println("Monitoring system...");
    }

    @Override
    public void generateReports() {
        System.out.println("Generating reports...");
    }
    
    public void performAdminAction() {
        System.out.println("Performing admin specific action.");
    }

    @Override
    public String getRoleMessage() {
        return "Logged in as Administrator: " + getName() + " (Full Access)";
    }
}
