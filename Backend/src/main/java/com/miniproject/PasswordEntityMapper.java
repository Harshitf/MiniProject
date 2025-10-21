package com.miniproject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEntityMapper {

    public PasswordEntity getPasswordEntity(PasswordDto passwordDto) throws Exception {
        return PasswordEntity
                .builder()
                .password(PasswordUtil.encrypt(passwordDto.getPassword()))
                .email(passwordDto.getEmail())
                .notes(passwordDto.getNotes())
                .createdAt(passwordDto.getCreatedAt())
                .phoneNumber(passwordDto.getPhoneNumber())
                .siteUsername(passwordDto.getSiteUsername())
                .siteUrl(passwordDto.getSiteUrl())
                .build();
    }
    public PasswordDto getPasswordDto(PasswordEntity passwordEntity) throws Exception {
        return PasswordDto
                .builder()
                .id(passwordEntity.getId())
                .password(PasswordUtil.decrypt(passwordEntity.getPassword()))
                .email(passwordEntity.getEmail())
                .notes(passwordEntity.getNotes())
                .createdAt(passwordEntity.getCreatedAt())
                .phoneNumber(passwordEntity.getPhoneNumber())
                .siteUsername(passwordEntity.getSiteUsername())
                .siteUrl(passwordEntity.getSiteUrl())
                .build();
    }
}
