package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.domain.store.repository.StoreRepository;
import com.profect.delivery.global.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService{
    private final StoreRepository storeRepository;

    public void registerStore(final String storeId, final StoreRegisterDto storeRegisterDto) {

    }
    public void deleteStore(final String storeId) {

    }
//    public StoreListDto getStore(final String storeId) {
//        //return storeRepository.findById(storeId);
//    }

    public void getStoreRegions (final String storeId) {    }
    public void registerStoreRegion(final String storeId, final String regionId) {}
    public void deleteStoreRegion(final String storeId, final String regionId) {}

    public StoreDto findStoreById(String storeId) {
        UUID storeuuid = UUID.fromString(storeId);
        Store store = storeRepository.findByStoreId(storeuuid)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        return StoreDto.builder()
                .storeId(store.getStoreId().toString())
                .storeName(store.getStoreName())
                .description(store.getStoreDescription())
                .category(String.valueOf(store.getStoresCategoryId()))
                .zip_cd(store.getZipCd())
                .store_phone(store.getStorePhone())
                .address(StoreDto.AddressDto.builder()
                        .address1(store.getAddress1())
                        .address2(store.getAddress2())
                        .build())
                .store_location(StoreDto.StoreLocationDto.builder()
                        .store_latitude(String.valueOf(store.getStoreLatitude()))
                        .store_longtitude(String.valueOf(store.getStoreLongitude()))
                        .build())
                .build();
    }

}

//
//    public Optional<Store> getStoreById(Long storeid) {
//        return storeRepository.findById(storeid);
//    }
//
//    public List<Store> getAllStores() {
//        return storeRepository.findAll();
//    }
