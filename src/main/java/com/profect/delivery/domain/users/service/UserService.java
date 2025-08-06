package com.profect.delivery.domain.users.service;

import com.profect.delivery.domain.users.dto.UserResponseDto;
import com.profect.delivery.domain.users.dto.UserUpdateRequestDto;
import com.profect.delivery.domain.users.repository.UserRepository;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.entity.User;
import com.profect.delivery.global.exception.custom.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor//생성자 자동 주입
@Slf4j
public class UserService{//비즈니스 로직
    private final UserRepository userRepository;




    @Transactional(readOnly = true)
    public UserResponseDto getUserById(String UserId) {
            User user=userRepository.findByUserId(UserId);
            if(user==null){
                throw new BusinessException(UserErrorCode.NOT_FOUND_USER);
            }
            //예외 처리 작성

        return UserResponseDto.fromEntity(user);
    }


    public void updateUser(UserUpdateRequestDto userUpdateRequestDto, String UserId, String UpdatedBy) {
        User user=userRepository.findByUserId(UserId);
        if(user==null){
            throw new BusinessException(UserErrorCode.NOT_FOUND_USER);
        }//찾는 유저가 없으면 예왜ㅣ처리


        user.update(userUpdateRequestDto.getName(),
                userUpdateRequestDto.getPassword(),
                userUpdateRequestDto.getEmail(),
                userUpdateRequestDto.getIs_public(),
                UpdatedBy
        );
        userRepository.save(user);
        log.info("Updated user id {}",user);

    }


}