-- Dummy Data for Food Delivery App

-- Users (30 users: 1 master, 2 managers, 7 owners, 20 customers)
INSERT INTO p_users (user_id, username, password, name, birth, email, role, is_public, is_banned, created_at, created_by) VALUES
('U000000001', 'master01', 'password123', '관리자', '1980-01-01', 'master@goorm.com', 'MASTER', true, false, NOW(), 'master01'),
('U000000002', 'manager01', 'password123', '매니저1', '1985-03-15', 'manager1@goorm.com', 'MANAGER', true, false, NOW(), 'manager01'),
('U000000003', 'manager02', 'password123', '매니저2', '1987-07-20', 'manager2@goorm.com', 'MANAGER', true, false, NOW(), 'manager02'),
('U000000004', 'owner01', 'password123', '사장님1', '1975-05-10', 'owner1@goorm.com', 'OWNER', true, false, NOW(), 'owner01'),
('U000000005', 'owner02', 'password123', '사장님2', '1978-09-25', 'owner2@goorm.com', 'OWNER', true, false, NOW(), 'owner02'),
('U000000006', 'owner03', 'password123', '사장님3', '1982-12-03', 'owner3@goorm.com', 'OWNER', true, false, NOW(), 'owner03'),
('U000000007', 'owner04', 'password123', '사장님4', '1979-04-18', 'owner4@goorm.com', 'OWNER', true, false, NOW(), 'owner04'),
('U000000008', 'owner05', 'password123', '사장님5', '1983-11-07', 'owner5@goorm.com', 'OWNER', true, false, NOW(), 'owner05'),
('U000000009', 'owner06', 'password123', '사장님6', '1976-08-14', 'owner6@goorm.com', 'OWNER', true, false, NOW(), 'owner06'),
('U000000010', 'owner07', 'password123', '사장님7', '1981-02-28', 'owner7@goorm.com', 'OWNER', true, false, NOW(), 'owner07'),
('U000000011', 'customer01', 'password123', '김고객', '1990-01-15', 'customer1@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer01'),
('U000000012', 'customer02', 'password123', '이고객', '1992-03-22', 'customer2@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer02'),
('U000000013', 'customer03', 'password123', '박고객', '1988-07-08', 'customer3@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer03'),
('U000000014', 'customer04', 'password123', '최고객', '1995-11-30', 'customer4@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer04'),
('U000000015', 'customer05', 'password123', '정고객', '1993-05-17', 'customer5@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer05'),
('U000000016', 'customer06', 'password123', '강고객', '1991-09-12', 'customer6@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer06'),
('U000000017', 'customer07', 'password123', '윤고객', '1989-12-25', 'customer7@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer07'),
('U000000018', 'customer08', 'password123', '장고객', '1994-04-03', 'customer8@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer08'),
('U000000019', 'customer09', 'password123', '임고객', '1996-08-19', 'customer9@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer09'),
('U000000020', 'customer10', 'password123', '한고객', '1987-06-11', 'customer10@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer10'),
('U000000021', 'customer11', 'password123', '오고객', '1992-10-05', 'customer11@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer11'),
('U000000022', 'customer12', 'password123', '서고객', '1990-02-14', 'customer12@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer12'),
('U000000023', 'customer13', 'password123', '신고객', '1993-07-27', 'customer13@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer13'),
('U000000024', 'customer14', 'password123', '권고객', '1991-11-09', 'customer14@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer14'),
('U000000025', 'customer15', 'password123', '황고객', '1989-03-16', 'customer15@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer15'),
('U000000026', 'customer16', 'password123', '안고객', '1994-09-21', 'customer16@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer16'),
('U000000027', 'customer17', 'password123', '송고객', '1995-01-08', 'customer17@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer17'),
('U000000028', 'customer18', 'password123', '전고객', '1988-05-13', 'customer18@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer18'),
('U000000029', 'customer19', 'password123', '홍고객', '1992-12-02', 'customer19@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer19'),
('U000000030', 'customer20', 'password123', '조고객', '1990-08-24', 'customer20@goorm.com', 'CUSTOMER', true, false, NOW(), 'customer20');

-- Customer Addresses (20 customers)
INSERT INTO p_customer_address (address_cd, user_id, address_name, address1, address2, zip_cd, user_latitude, user_longitude, is_default, created_at) VALUES
(gen_random_uuid(), 'U000000011', '집', '서울특별시 강남구 테헤란로 123', '101동 1001호', '06142', 37.501234, 127.039876, true, NOW()),
(gen_random_uuid(), 'U000000012', '회사', '서울특별시 서초구 서초대로 456', '5층', '06789', 37.495123, 127.027654, true, NOW()),
(gen_random_uuid(), 'U000000013', '집', '서울특별시 송파구 올림픽로 789', '202동 503호', '05551', 37.514567, 127.105432, true, NOW()),
(gen_random_uuid(), 'U000000014', '집', '서울특별시 마포구 홍대입구역로 321', '3층', '04039', 37.556789, 126.924321, true, NOW()),
(gen_random_uuid(), 'U000000015', '회사', '서울특별시 중구 을지로 654', '12층', '04563', 37.566543, 126.982109, true, NOW()),
(gen_random_uuid(), 'U000000016', '집', '서울특별시 용산구 한강대로 987', '301호', '04386', 37.532876, 126.968754, true, NOW()),
(gen_random_uuid(), 'U000000017', '집', '서울특별시 성동구 왕십리로 147', '501동 201호', '04706', 37.561234, 127.037890, true, NOW()),
(gen_random_uuid(), 'U000000018', '회사', '서울특별시 영등포구 여의대로 258', '15층', '07327', 37.526789, 126.924567, true, NOW()),
(gen_random_uuid(), 'U000000019', '집', '서울특별시 관악구 관악로 369', '102동 304호', '08826', 37.478901, 126.951234, true, NOW()),
(gen_random_uuid(), 'U000000020', '집', '서울특별시 동작구 상도로 741', '401호', '06978', 37.502345, 126.939876, true, NOW()),
(gen_random_uuid(), 'U000000021', '회사', '서울특별시 강서구 화곡로 852', '7층', '07644', 37.551098, 126.849321, true, NOW()),
(gen_random_uuid(), 'U000000022', '집', '서울특별시 노원구 상계로 963', '103동 702호', '01695', 37.654321, 127.061234, true, NOW()),
(gen_random_uuid(), 'U000000023', '집', '서울특별시 도봉구 도봉로 159', '201동 405호', '01425', 37.668765, 127.047890, true, NOW()),
(gen_random_uuid(), 'U000000024', '회사', '서울특별시 은평구 은평로 357', '6층', '03444', 37.617654, 126.929876, true, NOW()),
(gen_random_uuid(), 'U000000025', '집', '서울특별시 서대문구 연세로 468', '302호', '03722', 37.559876, 126.942109, true, NOW()),
(gen_random_uuid(), 'U000000026', '집', '서울특별시 종로구 종로 579', '501호', '03198', 37.570123, 126.982654, true, NOW()),
(gen_random_uuid(), 'U000000027', '회사', '서울특별시 중랑구 망우로 681', '4층', '02174', 37.606789, 127.092345, true, NOW()),
(gen_random_uuid(), 'U000000028', '집', '서울특별시 성북구 성북로 792', '104동 603호', '02876', 37.589012, 127.016789, true, NOW()),
(gen_random_uuid(), 'U000000029', '집', '서울특별시 강북구 도봉로 814', '205동 801호', '01065', 37.639876, 127.025432, true, NOW()),
(gen_random_uuid(), 'U000000030', '회사', '서울특별시 광진구 능동로 925', '8층', '05029', 37.548765, 127.073210, true, NOW());

-- Regions (배달 가능 지역)
INSERT INTO p_regions (region_id, region_1depth_name, region_2depth_name, region_3depth_name, created_at) VALUES
(gen_random_uuid(), '서울특별시', '강남구', '역삼동', NOW()),
(gen_random_uuid(), '서울특별시', '강남구', '삼성동', NOW()),
(gen_random_uuid(), '서울특별시', '서초구', '서초동', NOW()),
(gen_random_uuid(), '서울특별시', '서초구', '반포동', NOW()),
(gen_random_uuid(), '서울특별시', '송파구', '잠실동', NOW()),
(gen_random_uuid(), '서울특별시', '송파구', '문정동', NOW()),
(gen_random_uuid(), '서울특별시', '마포구', '홍대입구동', NOW()),
(gen_random_uuid(), '서울특별시', '마포구', '합정동', NOW()),
(gen_random_uuid(), '서울특별시', '용산구', '한남동', NOW()),
(gen_random_uuid(), '서울특별시', '용산구', '이태원동', NOW());

-- Store Categories
INSERT INTO p_stores_category (stores_category_id, stores_category) VALUES
(gen_random_uuid(), '한식'),
(gen_random_uuid(), '중식'),
(gen_random_uuid(), '일식'),
(gen_random_uuid(), '치킨'),
(gen_random_uuid(), '피자'),
(gen_random_uuid(), '족발보쌈'),
(gen_random_uuid(), '야식');

-- Stores (7 stores for 7 owners)
INSERT INTO p_stores (store_id, user_id, stores_category_id, store_name, store_description, address1, address2, zip_cd, store_phone, store_latitude, store_longitude, open_time, close_time, is_banned, created_at, created_by) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'U000000004', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '한식' LIMIT 1), '맛있는 한식당', '전통 한식을 맛볼 수 있는 곳', '서울특별시 강남구 테헤란로 100', '1층', '06142', '02-1234-5678', 37.501000, 127.039000, '09:00', '22:00', false, NOW(), 'owner01'),
('550e8400-e29b-41d4-a716-446655440002', 'U000000005', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '중식' LIMIT 1), '황금성 중국집', '정통 중화요리 전문점', '서울특별시 서초구 서초대로 200', '지하1층', '06789', '02-2345-6789', 37.495000, 127.027000, '11:00', '21:00', false, NOW(), 'owner02'),
('550e8400-e29b-41d4-a716-446655440003', 'U000000006', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '일식' LIMIT 1), '사쿠라 초밥', '신선한 초밥과 사시미', '서울특별시 송파구 올림픽로 300', '2층', '05551', '02-3456-7890', 37.514000, 127.105000, '12:00', '23:00', false, NOW(), 'owner03'),
('550e8400-e29b-41d4-a716-446655440004', 'U000000007', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '치킨' LIMIT 1), '바삭바삭 치킨', '겉바속촉 치킨 전문점', '서울특별시 마포구 홍대입구역로 400', '1층', '04039', '02-4567-8901', 37.556000, 126.924000, '16:00', '02:00', false, NOW(), 'owner04'),
('550e8400-e29b-41d4-a716-446655440005', 'U000000008', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '피자' LIMIT 1), '맘마미아 피자', '이탈리아 정통 피자', '서울특별시 중구 을지로 500', '1층', '04563', '02-5678-9012', 37.566000, 126.982000, '11:00', '24:00', false, NOW(), 'owner05'),
('550e8400-e29b-41d4-a716-446655440006', 'U000000009', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '족발보쌈' LIMIT 1), '족발야시장', '쫄깃한 족발과 보쌈', '서울특별시 용산구 한강대로 600', '1층', '04386', '02-6789-0123', 37.532000, 126.968000, '17:00', '03:00', false, NOW(), 'owner06'),
('550e8400-e29b-41d4-a716-446655440007', 'U000000010', (SELECT stores_category_id FROM p_stores_category WHERE stores_category = '야식' LIMIT 1), '야식천국', '밤늦은 야식 전문점', '서울특별시 성동구 왕십리로 700', '1층', '04706', '02-7890-1234', 37.561000, 127.037000, '20:00', '05:00', false, NOW(), 'owner07');

-- Store Regions (매장별 배달 가능 지역)
INSERT INTO p_stores_regions (store_id, region_id, created_at)
SELECT s.store_id, r.region_id, NOW()
FROM p_stores s
CROSS JOIN p_regions r
WHERE (s.store_id = '550e8400-e29b-41d4-a716-446655440001' AND r.region_2depth_name IN ('강남구', '서초구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440002' AND r.region_2depth_name IN ('서초구', '송파구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440003' AND r.region_2depth_name IN ('송파구', '강남구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440004' AND r.region_2depth_name IN ('마포구', '용산구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440005' AND r.region_2depth_name IN ('중구', '용산구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440006' AND r.region_2depth_name IN ('용산구', '마포구'))
   OR (s.store_id = '550e8400-e29b-41d4-a716-446655440007' AND r.region_2depth_name IN ('성동구', '중구'));

-- Menu Categories
INSERT INTO p_menu_category (menu_category_id, store_id, menu_category_name, created_at) VALUES
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', '1인메뉴', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', '세트메뉴', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', '1인메뉴', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', '세트메뉴', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', '초밥', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', '사시미', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', '후라이드', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', '양념치킨', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', '피자', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', '사이드', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440006', '족발', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440006', '보쌈', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440006', '음료', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440007', '라면', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440007', '떡볶이', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440007', '음료', NOW());

-- Menus
INSERT INTO p_menus (menu_id, store_id, menu_category_id, menu_name, menu_price, menu_description, is_public, menu_photo_url, is_public_photo, created_at, created_by) VALUES
-- 한식당 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440001' AND menu_category_name = '1인메뉴' LIMIT 1), '김치찌개', 8000, '돼지고기와 김치가 들어간 얼큰한 찌개', true, '/images/kimchi_jjigae.jpg', true, NOW(), 'owner01'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440001' AND menu_category_name = '1인메뉴' LIMIT 1), '된장찌개', 7000, '구수한 된장으로 끓인 찌개', true, '/images/doenjang_jjigae.jpg', true, NOW(), 'owner01'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440001' AND menu_category_name = '세트메뉴' LIMIT 1), '불고기정식', 15000, '불고기와 반찬이 포함된 정식', true, '/images/bulgogi_set.jpg', true, NOW(), 'owner01'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440001' AND menu_category_name = '음료' LIMIT 1), '콜라', 2000, '시원한 콜라', true, '/images/cola.jpg', true, NOW(), 'owner01'),

-- 중식당 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440002' AND menu_category_name = '1인메뉴' LIMIT 1), '짜장면', 6000, '달콤한 춘장소스의 짜장면', true, '/images/jjajangmyeon.jpg', true, NOW(), 'owner02'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440002' AND menu_category_name = '1인메뉴' LIMIT 1), '짬뽕', 7000, '얼큰한 해물 짬뽕', true, '/images/jjamppong.jpg', true, NOW(), 'owner02'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440002' AND menu_category_name = '세트메뉴' LIMIT 1), '탕수육세트', 18000, '탕수육과 짜장면 세트', true, '/images/tangsuyuk_set.jpg', true, NOW(), 'owner02'),

-- 일식당 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440003' AND menu_category_name = '초밥' LIMIT 1), '연어초밥', 12000, '신선한 연어 초밥 8피스', true, '/images/salmon_sushi.jpg', true, NOW(), 'owner03'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440003' AND menu_category_name = '사시미' LIMIT 1), '모듬사시미', 25000, '신선한 생선 모듬 사시미', true, '/images/assorted_sashimi.jpg', true, NOW(), 'owner03'),

-- 치킨집 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440004' AND menu_category_name = '후라이드' LIMIT 1), '후라이드치킨', 16000, '바삭한 후라이드 치킨', true, '/images/fried_chicken.jpg', true, NOW(), 'owner04'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440004' AND menu_category_name = '양념치킨' LIMIT 1), '양념치킨', 17000, '달콤매콤한 양념치킨', true, '/images/seasoned_chicken.jpg', true, NOW(), 'owner04'),

-- 피자집 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440005' AND menu_category_name = '피자' LIMIT 1), '마르게리타피자', 22000, '토마토와 모짜렐라의 클래식 피자', true, '/images/margherita.jpg', true, NOW(), 'owner05'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440005' AND menu_category_name = '피자' LIMIT 1), '페퍼로니피자', 25000, '매콤한 페퍼로니 피자', true, '/images/pepperoni.jpg', true, NOW(), 'owner05'),

-- 족발보쌈집 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440006', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440006' AND menu_category_name = '족발' LIMIT 1), '앞다리족발', 28000, '부드러운 앞다리 족발', true, '/images/jokbal.jpg', true, NOW(), 'owner06'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440006', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440006' AND menu_category_name = '보쌈' LIMIT 1), '수육보쌈', 26000, '담백한 수육 보쌈', true, '/images/bossam.jpg', true, NOW(), 'owner06'),

-- 야식집 메뉴
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440007', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440007' AND menu_category_name = '라면' LIMIT 1), '라면', 4000, '얼큰한 라면', true, '/images/ramyeon.jpg', true, NOW(), 'owner07'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440007', (SELECT menu_category_id FROM p_menu_category WHERE store_id = '550e8400-e29b-41d4-a716-446655440007' AND menu_category_name = '떡볶이' LIMIT 1), '떡볶이', 5000, '매콤달콤한 떡볶이', true, '/images/tteokbokki.jpg', true, NOW(), 'owner07');

-- Carts
INSERT INTO p_carts (cart_id, store_id, user_id, cart_status, created_at) VALUES
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', 'U000000011', 'ACTIVE', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', 'U000000012', 'ORDERED', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', 'U000000013', 'ACTIVE', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', 'U000000014', 'ORDERED', NOW()),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440005', 'U000000015', 'ABANDONED', NOW());

-- Cart Items
INSERT INTO p_cart_items (cart_item_id, cart_id, menu_id, quantity, created_at) VALUES
(gen_random_uuid(), (SELECT cart_id FROM p_carts WHERE user_id = 'U000000011' LIMIT 1), (SELECT menu_id FROM p_menus WHERE store_id = '550e8400-e29b-41d4-a716-446655440001' LIMIT 1), 2, NOW()),
(gen_random_uuid(), (SELECT cart_id FROM p_carts WHERE user_id = 'U000000012' LIMIT 1), (SELECT menu_id FROM p_menus WHERE store_id = '550e8400-e29b-41d4-a716-446655440002' LIMIT 1), 1, NOW()),
(gen_random_uuid(), (SELECT cart_id FROM p_carts WHERE user_id = 'U000000013' LIMIT 1), (SELECT menu_id FROM p_menus WHERE store_id = '550e8400-e29b-41d4-a716-446655440003' LIMIT 1), 3, NOW());

-- Orders
INSERT INTO p_orders (order_id, user_id, store_id, address_cd, cart_id, total_price, request_message, created_at, created_by) VALUES
(gen_random_uuid(), 'U000000012', '550e8400-e29b-41d4-a716-446655440002', (SELECT address_cd FROM p_customer_address WHERE user_id = 'U000000012' LIMIT 1), (SELECT cart_id FROM p_carts WHERE user_id = 'U000000012' LIMIT 1), 6000, '빨리 배달해주세요', NOW(), 'customer02'),
(gen_random_uuid(), 'U000000014', '550e8400-e29b-41d4-a716-446655440004', (SELECT address_cd FROM p_customer_address WHERE user_id = 'U000000014' LIMIT 1), (SELECT cart_id FROM p_carts WHERE user_id = 'U000000014' LIMIT 1), 16000, '매운맛으로 해주세요', NOW(), 'customer04');

-- Order Items
INSERT INTO p_order_items (order_item_id, order_id, menu_id, quantity, created_at) VALUES
(gen_random_uuid(), (SELECT order_id FROM p_orders WHERE user_id = 'U000000012' LIMIT 1), (SELECT menu_id FROM p_menus WHERE store_id = '550e8400-e29b-41d4-a716-446655440002' LIMIT 1), 1, NOW()),
(gen_random_uuid(), (SELECT order_id FROM p_orders WHERE user_id = 'U000000014' LIMIT 1), (SELECT menu_id FROM p_menus WHERE store_id = '550e8400-e29b-41d4-a716-446655440004' LIMIT 1), 1, NOW());

-- Reviews
INSERT INTO p_reviews (review_id, store_id, user_id, rating, comment, is_public, created_at, created_by) VALUES
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', 'U000000011', 5, '정말 맛있어요!', true, NOW(), 'customer01'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', 'U000000012', 4, '짜장면이 달콤해요', true, NOW(), 'customer02'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440003', 'U000000013', 5, '신선한 회가 최고', true, NOW(), 'customer03'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440004', 'U000000014', 4, '바삭바삭 맛있네요', true, NOW(), 'customer04');

-- Review Summary
INSERT INTO p_review_summary (batch_id, store_id, summary_refresh_date, summary_text, created_at, created_by) VALUES
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440001', '2025-08-01', '고객들이 맛을 높게 평가', NOW(), 'master01'),
(gen_random_uuid(), '550e8400-e29b-41d4-a716-446655440002', '2025-08-01', '달콤한 맛이 인기', NOW(), 'master01');

-- Payments
INSERT INTO p_payments (payment_id, user_id, store_id, order_id, payment_method, card_number, payment_amount, payment_time, payment_result) VALUES
(gen_random_uuid(), 'U000000012', '550e8400-e29b-41d4-a716-446655440002', (SELECT order_id FROM p_orders WHERE user_id = 'U000000012' LIMIT 1), 'CARD', '1234567890123456', 6000, NOW(), 'SUCCESS'),
(gen_random_uuid(), 'U000000014', '550e8400-e29b-41d4-a716-446655440004', (SELECT order_id FROM p_orders WHERE user_id = 'U000000014' LIMIT 1), 'CARD', '9876543210987654', 16000, NOW(), 'SUCCESS');