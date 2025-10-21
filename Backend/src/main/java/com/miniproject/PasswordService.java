package com.miniproject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;

    public List<PasswordEntity> findByCharacter(String word) {

        List<PasswordEntity> allDetails = passwordRepository.findAll();
        List<PasswordEntity> requiredDetails = new ArrayList<>();
        List<String> websites = new ArrayList<>();
        List<String> requiredWebsites = new ArrayList<>();
        allDetails.forEach(p -> websites.add(p.getSiteUrl()));

        for (String j : websites) {

            int k = 0;
            for (int i = 0; i < j.length(); i++) {
                if (k == word.length()) {
                    requiredWebsites.add(j);
                    break;
                }
                char a = j.charAt(i);
                char b = word.charAt(k);
                if (a == b) k++;
                else {
                    k = 0;
                }


            }
        }
        requiredWebsites.forEach(d -> requiredDetails.add(passwordRepository.findBySiteUrl(d)));
        for (PasswordEntity p : requiredDetails) {
            System.out.println(p);
        }
        return requiredDetails;
    }
}
