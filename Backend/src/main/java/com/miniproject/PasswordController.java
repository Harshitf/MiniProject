package com.miniproject;

import com.miniproject.configuration.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PasswordController {
   private final PasswordRepository passwordRepository;
   private final PasswordEntityMapper passwordEntityMapper;


   @GetMapping("/search-password")
   public String searchPassword(@RequestParam(value = "keyword",required = false) String keyword, Model model) {
       if (keyword == null || keyword.trim().isEmpty()) {
           model.addAttribute("message", "Please enter a keyword to search.");
           return "search-password";
       }
       List<PasswordEntity> passwords=passwordRepository.findByKeyword(keyword);
       if (passwords.isEmpty()) {
           model.addAttribute("message", "No passwords found for '" + keyword + "'");
       } else {
           model.addAttribute("passwords", passwords);
       }
       return "search-password";
   }

   @GetMapping("/create-password-entry")
   public String passwordEntryForm(Model model) {
       model.addAttribute("passwordDto", new PasswordDto());
       return "password-entry-form";
   }

   @DeleteMapping("/delete-password/{id}")
   public String deletePassword(@PathVariable("id") Long id, Authentication authentication) {
       try {
           passwordRepository.deleteById(id);
       }
       catch (Exception e) {
           log.error(e.getMessage());
           return "error";
       }
       return "redirect:/password-list";
   }
   @GetMapping("/password-list")
   public String passwordListForm(Model model, Authentication authentication) {
      List<PasswordEntity> passwords = passwordRepository.findByUserUsername(authentication.getName());
      List<PasswordDto> passwordDtos =  passwords.stream()
              .map(this::sneakyGetDto)
              .toList();
       model.addAttribute("passwordList", passwordDtos);
       return "password-list";
   }
    @SneakyThrows
    private PasswordDto sneakyGetDto(PasswordEntity password) {
        return passwordEntityMapper.getPasswordDto(password);
    }




    @PostMapping("/save-password-entry")
    @Transactional
    public String savePassword(@ModelAttribute PasswordDto passwordDto,Authentication authentication) throws Exception {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        PasswordEntity passwordEntity=passwordEntityMapper.getPasswordEntity(passwordDto);
        passwordEntity.setUser(userPrincipal.getUser());
        try {
            passwordRepository.save(passwordEntity);
        } catch (Exception e) {
            System.out.println("error");
            return "error";
        }
        return "redirect:/password-list";
    }
}
