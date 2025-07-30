package com.profect.delivery.domain.users.service;

import com.profect.delivery.domain.users.repository.UserAdressRepository;
import com.profect.delivery.global.entity.UserAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAddressService {
    @Autowired
    UserAdressRepository userAdressRepository;

    public UserAddressService(UserAdressRepository userAdressRepository){
        this.userAdressRepository = userAdressRepository;
    }

    @Transactional(readOnly = true)
    public List<UserAddress> findByUserId(String userId) {
        return userAdressRepository.findByUserId(userId);
    }



}
