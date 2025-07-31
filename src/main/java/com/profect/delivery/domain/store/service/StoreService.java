package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.RegionDto;
import com.profect.delivery.domain.store.dto.response.RegionListDto;
import com.profect.delivery.domain.store.dto.response.StoreDto;

import com.profect.delivery.domain.store.repository.RegionRepository;
import com.profect.delivery.domain.store.repository.StoreCategoryRepository;
import com.profect.delivery.domain.store.repository.StoreRegionRepository;
import com.profect.delivery.domain.store.repository.StoreRepository;
import com.profect.delivery.global.entity.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final RegionRepository regionRepository;

    private final StoreRegionRepository storeRegionRepository;

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

        //@피드백 하나의 리스트로 저장하는 방법을 생각해볼것

        return storeRepository.save(store);


    }

    @Transactional
    public boolean deleteStore(String storeId) {
        Optional<Store> storeOptional = storeRepository.findByStoreId(UUID.fromString(storeId));

        if (storeOptional.isEmpty()) {

            return false;
        }
        Store store = storeOptional.get();

        store.setDeletedAt(LocalDateTime.now());
        store.setDeletedBy(store.getUserId());
        store.setDeletedReason("사용자 요청으로 인한 삭제");
        //storeRepository.delete(store.get());
        return true;
    }

    public RegionListDto getRegions(String storeId) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<RegionDto> regionDtos = store.getRegions().stream()
                .map(region -> new RegionDto(
                        region.getRegionId().toString(),
                        region.getRegion1DepthName() + " " +
                                region.getRegion2DepthName() + " " +
                                region.getRegion3DepthName()
                ))
                .collect(Collectors.toList());

        return new RegionListDto(regionDtos);
    }

    @Transactional
    public List<UUID> registerRegions(String storeId, RegionListAddressDto regionListAddressDto) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .orElseThrow(() -> new RuntimeException("store not found"));

        List<UUID> regionIds = new ArrayList<>();

        for (RegionAddressDto dto : regionListAddressDto.getRegionListAddressDto()) {
            UUID regionId = storeRepository.findRegionIdByFullAddress(
                    dto.getAddress1(), dto.getAddress2(), dto.getAddress3()
            );

            if (regionId == null) {
                throw new RuntimeException("Region ID를 찾을 수 없습니다: " + dto.getAddress1() + " " + dto.getAddress2() + " " + dto.getAddress3());
            }

            Region region = regionRepository.findById(regionId)
                    .orElseThrow(() -> new RuntimeException("Region 엔티티를 찾을 수 없습니다: " + regionId));

            // 이미 등록된 StoreRegion인지 확인 (soft delete 제외)
            boolean exists = store.getStoreRegions().stream()
                    .anyMatch(sr -> sr.getRegion().equals(region) && sr.getDeletedAt() == null);

            if (!exists) {
                StoreRegion storeRegion = new StoreRegion(store, region);
                store.getStoreRegions().add(storeRegion);
            }
            regionIds.add(regionId);
        }

        return regionIds;
    }
    @Transactional
    public List<UUID> deleteRegion(String storeId, RegionListAddressDto regionListAddressDto) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<UUID> deletedRegionIds = new ArrayList<>();

        for (RegionAddressDto dto : regionListAddressDto.getRegionListAddressDto()) {

            UUID regionId = storeRepository.findRegionIdByFullAddress(
                    dto.getAddress1(), dto.getAddress2(), dto.getAddress3()
            );

            if (regionId == null) {
                throw new RuntimeException("Region ID를 찾을 수 없습니다: "
                        + dto.getAddress1() + " " + dto.getAddress2() + " " + dto.getAddress3());
            }

            Region region = regionRepository.findById(regionId)
                    .orElseThrow(() -> new RuntimeException("Region 엔티티를 찾을 수 없습니다."));


            StoreRegionId storeRegionId = new StoreRegionId(store.getStoreId(), region.getRegionId());


            StoreRegion storeRegion = storeRegionRepository.findById(storeRegionId)
                    .orElseThrow(() -> new RuntimeException("StoreRegion 관계가 없습니다."));


            if (storeRegion.getDeletedAt() == null) {
                storeRegion.softDelete();
                deletedRegionIds.add(regionId);
            }
        }

        return deletedRegionIds;
    }
}


//
//    public Optional<Store> getStoreById(Long storeid) {
//        return storeRepository.findById(storeid);
//    }
//
//    public urn storeRepository.findAll();
//    }List<Store> getAllStores() {
////        ret
