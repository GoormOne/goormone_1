package com.profect.delivery.domain.users.service;

import com.profect.delivery.domain.users.dto.UserResponseDto;
import com.profect.delivery.domain.users.dto.UserUpdateRequestDto;
import com.profect.delivery.domain.users.repository.UserRepository;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.entity.Role;
import com.profect.delivery.global.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // @Builder를 사용하여 테스트에 사용할 공통 User 객체 생성
        testUser = User.builder()
                .userId("testUserId")
                .username("test_username")
                .password("test_password123!")
                .name("Test User")
                .birth(new Date())
                .email("test@email.com")
                .role(Role.CUSTOMER)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy("system")
                .updatedAt(null) // 처음 생성 시에는 null
                .updatedBy(null) // 처음 생성 시에는 null
                .deletedAt(null)
                .deletedBy(null)
                .deletedRs(null)
                .build();

    }

//    @Test
//    @DisplayName("유저 아이디 조회 서비스 테스트")
////    void getUserById() {
////        //Given
////        String userId = "testUserId";
////        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(testUser));
////
////        // When
////        User foundUser = userService.getUserById(userId);
////
////        // Then
////        assertThat(foundUser).isPresent();
////        assertThat(foundUser.get().getUserId()).isEqualTo(userId);
////        verify(userRepository, times(1)).findByUserId(userId);
//
//    }


//    @Test
//    @DisplayName("유저 정보 업데이트 서비스 테스트")
//    void updateUser_Success() {
//        // Given
//        String userId = "testUserId";
//        String updatedBy = "updater";
//        UserUpdateRequestDto updateDto = new UserUpdateRequestDto();
//        updateDto.setName("NewName");
//        updateDto.setPassword("NewPassword");
//        updateDto.setEmail("new@email.com");
//        updateDto.setIs_public(true);
//
//        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(testUser));
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // When
//        userService.updateUser(updateDto, userId, updatedBy);
//
//        // Then
//        assertThat(testUser.getName()).isEqualTo("NewName");
//        assertThat(testUser.getPassword()).isEqualTo("NewPassword");
//        assertThat(testUser.getEmail()).isEqualTo("new@email.com");
//        assertThat(testUser.getUpdatedBy()).isEqualTo(updatedBy);
//
//        // userRepository의 findByUserId와 save 메서드가 각각 한 번씩 호출되었는지 검증
//        verify(userRepository, times(1)).findByUserId(userId);
//        verify(userRepository, times(1)).save(testUser);
//    }
//}
}