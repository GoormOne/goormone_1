package com.profect.delivery.domain.menu.service;

import com.profect.delivery.domain.menu.dto.response.MenuDetailResponse;
import com.profect.delivery.domain.menu.dto.response.MenuCategoriesResponse;
import com.profect.delivery.domain.menu.dto.response.MenuCategoryResponse;
import com.profect.delivery.domain.menu.dto.response.MenuItemResponse;
import com.profect.delivery.domain.menu.repository.MenuCategoryRepository;
import com.profect.delivery.domain.store.repository.StoreRepository;
import com.profect.delivery.global.entity.MenuCategory;
import com.profect.delivery.global.entity.Role;
import com.profect.delivery.global.entity.Store;
import com.profect.delivery.global.exception.ConflictException;
import com.profect.delivery.global.exception.NotFoundException;
import com.profect.delivery.domain.menu.dto.request.CreateMenuRequest;
import com.profect.delivery.domain.menu.dto.response.CreateMenuResponse;
import com.profect.delivery.domain.menu.dto.request.UpdateMenuRequest;
import com.profect.delivery.domain.menu.dto.response.UpdateMenuResponse;
import com.profect.delivery.global.entity.Menu;
import com.profect.delivery.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public CreateMenuResponse createMenu(UUID storeId,
                                         CreateMenuRequest reqDto,
                                         String username) {

        // Store 엔티티 조회
        Store store = storeRepository.findByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException("STORE NOT FOUND"));

        // 메뉴 카테고리 엔티티 조회
        MenuCategory menuCategory = null;
        if (reqDto.menuCategoryId() != null) {
            menuCategory = menuCategoryRepository.findByMenuCategoryId(reqDto.menuCategoryId())
                    .orElseThrow(() -> new NotFoundException("MENU CATEGORY NOT FOUND"));
        }

        // 이름 중복 검사
        if (menuRepository.existsByStoreStoreIdAndMenuName(storeId, reqDto.menuName())) {
            throw new ConflictException("DUPLICATED MENU NAME");
        }

        Menu menu = Menu.builder()
                        .store(store)
                        .menuCategory(menuCategory)
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

        Menu menu = menuRepository.findByStoreStoreIdAndMenuId(storeId, menuId)
                .orElseThrow(() -> new NotFoundException("MENU NOT FOUND"));
        // 동일 이름 중복 확인
        if (reqDto.menuName() != null && menuRepository.existsByStoreStoreIdAndMenuNameAndMenuIdNot(storeId, reqDto.menuName(), menuId)) {
            throw new ConflictException("DUPLICATED MENU NAME");
        }

        // 엔티티 무결성을 위해 setter 사용 자제
        if (reqDto.menuCategoryId() != null) {
            MenuCategory newCat = menuCategoryRepository.findByMenuCategoryId(reqDto.menuCategoryId())
                    .orElseThrow(() -> new NotFoundException("MENU CATEGORY NOT FOUND"));
            menu.changeCategory(newCat);
        }
        if (reqDto.menuName()        != null) menu.changeName(reqDto.menuName());
        if (reqDto.menuPrice()       != null) menu.changePrice(reqDto.menuPrice());
        if (reqDto.menuDescription() != null) menu.changeDescription(reqDto.menuDescription());
        if (reqDto.isPublic()        != null) menu.changeIsPublic(reqDto.isPublic());
        menu.auditUpdate(username);

        return new UpdateMenuResponse(menu.getMenuId(), menu.getMenuName());
    }

    @Transactional
    public void deleteMenu(UUID storeId, UUID menuId, String username) {
        Menu menu = menuRepository.findByStoreStoreIdAndMenuId(storeId, menuId)
                .orElseThrow(() -> new NotFoundException("MENU NOT FOUND"));

        menu.auditDelete(username);
    }

    @Transactional(readOnly = true)
    public MenuCategoriesResponse listMenusByCategory(UUID storeId,
                                                     UUID categoryId,
                                                     Pageable pageable,
                                                     Role role) {
        boolean publicOnly = (role == Role.CUSTOMER);
        Page<Menu> menuPage = menuRepository.searchMenus(storeId, categoryId, publicOnly, pageable);
        
        Map<UUID, List<Menu>> menusByCategory = menuPage.getContent().stream()
                .collect(Collectors.groupingBy(menu -> menu.getMenuCategory().getMenuCategoryId()));
        
        List<MenuCategoryResponse> categories = menusByCategory.entrySet().stream()
                .map(entry -> {
                    Menu firstMenu = entry.getValue().get(0);
                    return new MenuCategoryResponse(
                            entry.getKey(),
                            firstMenu.getMenuCategory().getMenuCategoryName(),
                            entry.getValue().stream()
                                    .map(MenuItemResponse::from)
                                    .toList()
                    );
                })
                .toList();
        
        return new MenuCategoriesResponse(categories, menuPage.getTotalElements(), menuPage.getTotalPages());
    }

    @Transactional(readOnly = true)
    public MenuDetailResponse getMenuDetail(UUID storeId, UUID menuId) {
        Menu menu = menuRepository.findByStoreStoreIdAndMenuId(storeId, menuId)
                .orElseThrow(() -> new NotFoundException("MENU NOT FOUND"));
        
        return MenuDetailResponse.from(menu);
    }


}
