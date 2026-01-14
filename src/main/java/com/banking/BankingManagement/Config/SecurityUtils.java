package com.banking.BankingManagement.Config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static String getCurrentUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}