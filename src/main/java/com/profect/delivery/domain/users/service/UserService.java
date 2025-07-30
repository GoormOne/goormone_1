package com.profect.delivery.domain.users.service;

import com.profect.delivery.domain.users.dto.UserResponseDto;
import com.profect.delivery.domain.users.repository.UserRepository;
import com.profect.delivery.global.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;

@Service
@RequiredArgsConstructor//생성자 자동 주입
@Slf4j
public class UserService{//비즈니스 로직
    @Autowired
    UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(String UserId) {
        return userRepository.findByUserId(UserId);
    }

//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

}