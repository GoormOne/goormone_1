package com.profect.delivery.menu.service;

import com.profect.delivery.global.exception.ConflictException;
import com.profect.delivery.global.exception.NotFoundException;
import com.profect.delivery.menu.dto.CreateMenuRequest;
import com.profect.delivery.menu.dto.CreateMenuResponse;
import com.profect.delivery.menu.dto.UpdateMenuRequest;
import com.profect.delivery.menu.dto.UpdateMenuResponse;
import com.profect.delivery.menu.entity.Menu;
import com.profect.delivery.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public CreateMenuResponse createMenu(UUID storeId,
                                         CreateMenuRequest reqDto,
                                         String username) {
        // 이름 중복 검사
        if (menuRepository.existsByStoreIdAndMenuName(storeId, reqDto.menuName())) {
            throw new ConflictException("DUPLICATED MENU NAME");
        }

        Menu menu = Menu.builder()
                        .storeId(storeId)
                        .menuCategoryId(reqDto.menuCategoryId())
                        .menuName(reqDto.menuName())
                        .menuPrice(reqDto.menuPrice())
                        .menuDescription(reqDto.menuDescription())
                        .isPublic(Boolean.TRUE.equals(reqDto.isPublic()))
                        .createdAt(LocalDateTime.now())
                        .createdBy(username)
                        .build();

        Menu saved = menuRepository.save(menu);
        return new CreateMenuResponse(saved.getMenuId(), saved.getMenuName());
    }

    @Transactional
    public UpdateMenuResponse updateMenu(UUID storeId,
                                         UUID menuId,
                                         UpdateMenuRequest reqDto,
                                         String username) {

        Menu menu = menuRepository.findByStoreIdAndMenuId(storeId, menuId)
                .orElseThrow(() -> new NotFoundException("MENU NOT FOUND"));
        // 동일 이름 중복 확인
        if (reqDto.menuName() != null && menuRepository.existsByStoreIdAndMenuNameAndMenuIdNot(storeId, reqDto.menuName(), menuId)) {
            throw new ConflictException("DUPLICATED MENU NAME");
        }

        // 엔티티 무결성을 위해 setter 사용 자제
        if (reqDto.menuCategoryId()  != null) menu.changeCategory(reqDto.menuCategoryId());
        if (reqDto.menuName()        != null) menu.changeName(reqDto.menuName());
        if (reqDto.menuPrice()       != null) menu.changePrice(reqDto.menuPrice());
        if (reqDto.menuDescription() != null) menu.changeDescription(reqDto.menuDescription());
        if (reqDto.isPublic()        != null) menu.changeIsPublic(reqDto.isPublic());
        menu.auditUpdate(username);

        return new UpdateMenuResponse(menu.getMenuId(), menu.getMenuName());
    }

    @Transactional
    public void deleteMenu(UUID storeId, UUID menuId, String username) {
        Menu menu = menuRepository.findByStoreIdAndMenuId(storeId, menuId)
                .orElseThrow(() -> new NotFoundException("MENU NOT FOUND"));

        menu.auditDelete(username);
    }
}
