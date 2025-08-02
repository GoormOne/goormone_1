package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.*;

import com.profect.delivery.domain.store.repository.*;
import com.profect.delivery.global.entity.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreCategoryRepository storeCategoryRepository;
    private final RegionRepository regionRepository;
    private final ReviewAverageRepository reviewAverageRepository;
    private final StoreRegionRepository storeRegionRepository;


    public StoreDto findStoreById(String storeId) {
        UUID storeuuid = UUID.fromString(storeId);
        Store store = storeRepository.findByStoreId(storeuuid)
                .map(s -> {
                    if (s.getDeletedAt() != null) {
                        throw new RuntimeException("이미 삭제된 매장입니다.");
                    }
                    return s;
                })
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다."));

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
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
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
                .createdAt(LocalDateTime.now())
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
                .openTime(storeRegisterDto.getOpenTime())
                .closeTime(storeRegisterDto.getCloseTime())
                .build();

        //@피드백 하나의 리스트로 저장하는 방법을 생각해볼것

//        return storeRepository.save(store);

        Store savedStore = storeRepository.save(store);
        System.out.println("Saved store ID: " + savedStore.getStoreId()); // ✅ 이거 추가해보세요
        return savedStore;
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
                .map(s -> {
                    if (s.getDeletedAt() != null) {
                        throw new RuntimeException("이미 삭제된 매장입니다.");
                    }
                    return s;
                })
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다."));

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
                .map(s -> {
                    if (s.getDeletedAt() != null) {
                        throw new RuntimeException("이미 삭제된 매장입니다.");
                    }
                    return s;
                })
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다."));

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
                .map(s -> {
                    if (s.getDeletedAt() != null) {
                        throw new RuntimeException("이미 삭제된 매장입니다.");
                    }
                    return s;
                })
                .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 않습니다."));

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

    public boolean isStoreOpen(Store store){
        LocalTime now = LocalTime.now();

        if (store.getOpenTime() == null || store.getCloseTime() == null) {
            System.out.println("운영시간 정보가 없습니다.");
            return false;
        }
        System.out.println("store: " + store.getStoreId());
        System.out.println("openTime: " + store.getOpenTime());  // null? → 로딩 안 된 것
        System.out.println("closeTime: " + store.getCloseTime());
        return now.isAfter(store.getOpenTime()) && now.isBefore(store.getCloseTime());
    }

    public BigDecimal calculateAverageRating(UUID storeId) {
        List<Object[]> resultList = reviewAverageRepository.findReviewStatsByStoreId(storeId);

        if (resultList.isEmpty()) return BigDecimal.ZERO;

        Object[] result = resultList.get(0);

        System.out.println(">>>> result" + Arrays.toString(result));

        int count = (int) result[0];
        int total = (int) result[1];

        if (count == 0) return BigDecimal.ZERO;

        return BigDecimal.valueOf(total).divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }
    public List<StoreSearchDto> searchStoreByKeyword(String keyword) {
        List<Store> stores = storeRepository.searchStoresByKeyword(keyword);


        return stores.stream().map(store -> new StoreSearchDto(
                store.getStoreId().toString(),
                store.getStoreName(),
                store.getStoreDescription(),
                calculateAverageRating(store.getStoreId()),
                isStoreOpen(store)
        )).collect(Collectors.toList());
    }


}


