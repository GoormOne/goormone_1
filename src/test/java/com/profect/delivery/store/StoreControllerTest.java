//package com.profect.delivery.store;
//
//import com.profect.delivery.domain.store.repository.StoreRepository;
//import com.profect.delivery.global.entity.Store;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class StoreControllerTest {
//
//    @Autowired
//    private StoreRepository storeRepository;
//
//    @Test
//    public void saveAndFindStoreById() {
//        UUID storeId = UUID.randomUUID();
//        Store store = Store.builder()
//                .storeId(storeId)
//                .storeName("부산국밥집")
//                .storeDescription("국밥 맛집입니다.")
//                .storePhone("010-1111-1111")
//                .address1("부산광역시 해운대구")
//                .address2("마린시티")
//                .zipCd("06234")
//                .storeLatitude(BigDecimal.valueOf(37.4981))
//                .storeLongitude(BigDecimal.valueOf(127.0276))
//                .build();
//
//        storeRepository.save(store);
//
//        Store foundStore = storeRepository.findById(storeId).orElseThrow();
//
//        assertEquals("부산국밥집", foundStore.getStoreName());
//        assertEquals("국밥 맛집입니다.", foundStore.getStoreDescription());
//        assertEquals("010-1111-1111", foundStore.getStorePhone());
//
//
//
//        단위 테스트 > 통합 테스트
//
//    }
//}