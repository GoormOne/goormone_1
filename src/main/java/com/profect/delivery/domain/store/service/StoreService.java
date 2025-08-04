package com.profect.delivery.domain.store.service;

import com.profect.delivery.domain.store.dto.request.RegionAddressDto;
import com.profect.delivery.domain.store.dto.request.RegionListAddressDto;
import com.profect.delivery.domain.store.dto.request.StoreRegisterDto;
import com.profect.delivery.domain.store.dto.response.*;

import com.profect.delivery.domain.store.repository.*;
import com.profect.delivery.global.entity.*;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import com.profect.delivery.global.exception.custom.StoreErrorCode;
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
                        throw new BusinessException(BusinessErrorCode.ALREADY_DELETED);
                    }
                    return s;
                })
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.NOT_FOUND));

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

    public void registerStore(
            final String userId,
            final StoreRegisterDto storeRegisterDto) {


        StoreCategory storeCategory = storeCategoryRepository.findByStoresCategory(storeRegisterDto.getCategory())
                .orElseThrow(() -> new RuntimeException("매장 카테고리를 찾을 수 없습니다."));

        String dummyUserId = "U000000004";
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


        Store savedStore = storeRepository.save(store);
        System.out.println("Saved store ID: " + savedStore.getStoreId());

    }

    @Transactional
    public  void deleteStore(String userId, String storeId) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_STORE));
        store.setDeletedAt(LocalDateTime.now());
        store.setDeletedBy(store.getUserId());
        store.setDeletedReason("사용자 요청으로 인한 삭제");


    }

    public RegionListDto getRegions(String storeId) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .map(s -> {
                    if (s.getDeletedAt() != null) {
                        throw new BusinessException(StoreErrorCode.ALREADY_DELETED_STORE);
                    }
                    return s;
                })
                .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_STORE));

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
        Store store = findValidStore(storeId);
        List<UUID> regionIds = new ArrayList<>();

        for (RegionAddressDto dto : regionListAddressDto.getRegionListAddressDto()) {
            Region region = findRegionByAddress(dto);

            // 중복된 지역이 아닌 경우만 추가
            boolean isAlreadyRegistered = store.getStoreRegions().stream()
                    .anyMatch(sr -> sr.getRegion().equals(region) && sr.getDeletedAt() == null);

            if (!isAlreadyRegistered) {
                store.getStoreRegions().add(new StoreRegion(store, region));
            }

            regionIds.add(region.getRegionId());
        }

        return regionIds;
    }

    private Store findValidStore(String storeId) {
        return storeRepository.findByStoreId(UUID.fromString(storeId))
                .filter(s -> s.getDeletedAt() == null)
                .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_STORE));
    }

    private Region findRegionByAddress(RegionAddressDto dto) {
        return Optional.ofNullable(
                        storeRepository.findRegionIdByFullAddress(dto.getAddress1(), dto.getAddress2(), dto.getAddress3()))
                .flatMap(regionRepository::findById)
                .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_REGION));
    }



    @Transactional
    public List<UUID> deleteRegion(String storeId, RegionListAddressDto regionListAddressDto) {
        Store store = storeRepository.findByStoreId(UUID.fromString(storeId))
                .filter(s -> s.getDeletedAt() == null)
                .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_STORE));

        List<UUID> deletedRegionIds = new ArrayList<>();

        for (RegionAddressDto dto : regionListAddressDto.getRegionListAddressDto()) {
            UUID regionId = storeRepository.findRegionIdByFullAddress(
                    dto.getAddress1(), dto.getAddress2(), dto.getAddress3()
            );

            if (regionId == null) {
                throw new BusinessException(StoreErrorCode.NOT_FOUND_REGION);
            }

            Region region = regionRepository.findById(regionId)
                    .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_REGION));

            StoreRegionId storeRegionId = new StoreRegionId(store.getStoreId(), region.getRegionId());

            StoreRegion storeRegion = storeRegionRepository.findById(storeRegionId)
                    .orElseThrow(() -> new BusinessException(StoreErrorCode.NOT_FOUND_STORE_REGION));

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
        System.out.println("openTime: " + store.getOpenTime());
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


