package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;
import com.profect.delivery.domain.store.dto.response.StoreReponseDto;
import com.profect.delivery.domain.store.repository.StoreCategoryRepository;
import com.profect.delivery.domain.store.repository.StoreRepository;
import com.profect.delivery.global.entity.Store;
import com.profect.delivery.global.entity.StoreCategory;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService{
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;



    //public void deleteStore(final String storeId) {}


//    public StoreListDto getStore(final String storeId) {
//        //return storeRepository.findById(storeId);
//    }

//    public void getStoreRegions (final String storeId) {    }
//    public void registerStoreRegion(final String storeId, final String regionId) {}
//    public void deleteStoreRegion(final String storeId, final String regionId) {}

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

    public Store saveStore(@Valid StoreRegisterDto storeRegisterDto) {
        System.out.println("category in DTO: " + storeRegisterDto.getCategory());
        StoreCategory storeCategory = storeCategoryRepository.findByStoresCategory(storeRegisterDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Store category not found"));
            String dummyUserId = "user002";
            Store store = Store.builder()
                    .storeId(UUID.randomUUID())
                    .userId(dummyUserId)
                    .isBanned(false)
                    .createdBy(dummyUserId)
                    .storeName(storeRegisterDto.getStoreName())
                    .storeDescription(storeRegisterDto.getStoreDescription())
                    .storesCategoryId(storeCategory.getStoresCategoryId())
                    .address1(storeRegisterDto.getAddress1())
                    .address2(storeRegisterDto.getAddress2())
                    .zipCd(storeRegisterDto.getZipCd())
                    .storePhone(storeRegisterDto.getStorePhone())
                    .storeLatitude(storeRegisterDto.getStoreLatitude())
                    .storeLongitude(storeRegisterDto.getStoreLongitude())
                    .build();

            return storeRepository.save(store);


    }
    @Transactional
    public boolean deleteStore(String storeId) {
        Optional<Store> store = storeRepository.findByStoreId(UUID.fromString(storeId));
        if (store.isPresent()) {
            storeRepository.delete(store.get());
            return true;
        }
        return false;
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
