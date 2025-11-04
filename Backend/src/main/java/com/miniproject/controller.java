package com.miniproject;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class controller {
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    @GetMapping("/")
    public String Dashboard(Model model, Principal principal) {
        String username = principal.getName();
        long passwordCount = passwordRepository.countByUserUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("passwordCount", passwordCount);
        return "Dashboard";
    }


    @DeleteMapping("/delete-all")
    @Transactional
    public String deleteProfile(Principal principal) {
        String username = principal.getName();
        passwordRepository.deleteAllByUserUsername(username);
        userRepository.deleteByUsername(username);
        return "redirect:/public/login";
    }

    @GetMapping("delete-all")
    public String DeleteProfile() {
        return "deleteProfile";
    }
}
