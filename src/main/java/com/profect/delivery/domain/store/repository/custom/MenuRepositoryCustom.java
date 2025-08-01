package com.profect.delivery.domain.store.repository.custom;

import com.profect.delivery.domain.store.dto.response.StoreSearchDto;

import java.util.List;

public interface MenuRepositoryCustom {
    List<StoreSearchDto> searchByKeyword(String keyword);
}
