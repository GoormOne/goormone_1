
-- INSERT INTO p_users
INSERT INTO p_users (user_id, username, password, name, birth, email, role, is_public, is_banned, created_at, created_by)
VALUES
    ('user001', 'userone', 'pw1234', '홍길동', '1990-01-01', 'user1@example.com', 'CUSTOMER', true, false, now(), 'system'),
    ('user002', 'usertwo', 'pw5678', '김철수', '1985-05-05', 'user2@example.com', 'OWNER', false, false, now(), 'system');

-- INSERT INTO p_customer_address
INSERT INTO p_customer_address (address_cd, user_id, address_name, address1, address2, zip_cd, user_latitude, user_longitude, is_default, created_at)
VALUES
    (gen_random_uuid(), 'user001', '집', '서울시 강남구', '101동 202호', '06234', 37.4981, 127.0276, true, now()),
    (gen_random_uuid(), 'user002', '회사', '서울시 서초구', '301동 302호', '06611', 37.4923, 127.0145, false, now());

-- INSERT INTO p_regions
INSERT INTO p_regions (region_id, region_1depth_name, region_2depth_name, region_3depth_name, created_at)
VALUES
    (gen_random_uuid(), '서울특별시', '강남구', '역삼동', now()),
    (gen_random_uuid(), '서울특별시', '서초구', '반포동', now()),
    (gen_random_uuid(), '서울특별시', '서대문구', '대현동', now()),
    (gen_random_uuid(), '서울특별시', '서대문구', '신촌동', now()),
    (gen_random_uuid(), '서울특별시', '서대문구', '아현동', now());;

-- INSERT INTO p_stores_category
INSERT INTO p_stores_category (stores_category_id, stores_category)
VALUES
    (gen_random_uuid(), '치킨'),
    (gen_random_uuid(), '피자');

-- INSERT INTO p_stores
INSERT INTO p_stores (
    store_id, user_id, stores_category_id, store_name, store_description,
    address1, address2, zip_cd, store_phone, store_latitude, store_longitude,
    is_banned, created_at, created_by, updated_at, updated_by, deleted_at, deleted_by, deleted_rs
) VALUES
      (
          gen_random_uuid(), 'user002', (SELECT stores_category_id FROM p_stores_category LIMIT 1),
          '강남고기집', '고기 맛집입니다.',
          '서울시 강남구', '101호', '06234', '010-1111-1111', 37.4981, 127.0276,
          false, now(), 'user002', NULL, NULL, NULL, NULL, NULL
      ),
      (
          gen_random_uuid(), 'user002', (SELECT stores_category_id FROM p_stores_category OFFSET 1 LIMIT 1),
          '서초분식', '분식 전문점입니다.',
          '서울시 서초구', '202호', '06611', '010-2222-2222', 37.4923, 127.0145,
          false, now(), 'user002', NULL, NULL, NULL, NULL, NULL
      );
-- INSERT INTO p_stores_regions
INSERT INTO p_stores_regions (store_id, region_id, created_at)
SELECT s.store_id, r.region_id, now()
FROM (SELECT store_id FROM p_stores LIMIT 2) s, (SELECT region_id FROM p_regions LIMIT 2) r
    LIMIT 2;

-- INSERT INTO p_stores_business_hr
INSERT INTO p_stores_business_hr (business_hr_id, store_id, day_of_week, open_time, close_time)
SELECT gen_random_uuid(), store_id, 'MON', '10:00', '22:00' FROM p_stores LIMIT 2;

-- INSERT INTO p_menu_category
INSERT INTO p_menu_category (menu_category_id, store_id, menu_category_name, created_at)
SELECT gen_random_uuid(), store_id, '대표 메뉴', now() FROM p_stores LIMIT 2;

-- INSERT INTO p_menus
INSERT INTO p_menus (menu_id, store_id, menu_category_id, menu_name, menu_price, menu_description, is_public, created_at, created_by)
SELECT gen_random_uuid(), s.store_id, mc.menu_category_id, '후라이드치킨', 18000, '바삭한 치킨', true, now(), 'user002'
FROM p_stores s JOIN p_menu_category mc ON s.store_id = mc.store_id LIMIT 2;

-- INSERT INTO p_menu_photos
INSERT INTO p_menu_photos (menu_photo_id, menu_id, menu_photo_url, is_public, created_at, created_by)
SELECT gen_random_uuid(), menu_id, 'https://example.com/chicken.jpg', true, now(), 'user002' FROM p_menus LIMIT 2;

-- INSERT INTO p_carts
INSERT INTO p_carts (cart_id, store_id, user_id, cart_status, created_at)
SELECT gen_random_uuid(), store_id, 'user001', 'ACTIVE', now() FROM p_stores LIMIT 2;

-- INSERT INTO p_cart_items
INSERT INTO p_cart_items (cart_item_id, cart_id, menu_id, quantity, created_at)
SELECT gen_random_uuid(), c.cart_id, m.menu_id, 2, now()
FROM p_carts c, p_menus m LIMIT 2;

-- INSERT INTO p_orders
INSERT INTO p_orders (order_id, user_id, store_id, loc_cd, cart_id, total_price, created_at, created_by)
SELECT gen_random_uuid(), 'user001', s.store_id, ca.address_cd, c.cart_id, 20000, now(), 'user001'
FROM p_stores s, p_customer_address ca, p_carts c LIMIT 2;

-- INSERT INTO p_order_items
INSERT INTO p_order_items (order_item_id, order_id, menu_id, quantity, created_at)
SELECT gen_random_uuid(), o.order_id, m.menu_id, 2, now()
FROM p_orders o, p_menus m LIMIT 2;

-- INSERT INTO p_reviews
INSERT INTO p_reviews (review_id, store_id, user_id, rating, comment, is_public, created_at, created_by)
SELECT gen_random_uuid(), store_id, 'user001', 5, '맛있어요', true, now(), 'user001' FROM p_stores LIMIT 2;

-- INSERT INTO p_review_summary
INSERT INTO p_review_summary (batch_id, store_id, period_start, period_end, review_cnt, avg_rating, summary_text, created_at, created_by)
SELECT gen_random_uuid(), store_id, '2025-01-01', '2025-01-31', 2, 4.5, '좋은 평가', now(), 'system' FROM p_stores LIMIT 2;

-- INSERT INTO p_payments
INSERT INTO p_payments (payment_id, user_id, store_id, order_id, payment_method, card_number, payment_amount, payment_time, payment_result)
SELECT gen_random_uuid(), 'user001', p_stores.store_id, o.order_id, 'CARD', '1234567890123456', 20000, now(), 'SUCCESS'
FROM p_stores, p_orders o LIMIT 2;

-- INSERT INTO p_errors
INSERT INTO p_errors (error_id, user_id, request_url, http_method, error_code, error_message, client_ip, user_agent, created_at)
VALUES
    (gen_random_uuid(), 'user001', '/api/test', 'GET', '404', 'Not Found', '127.0.0.1', 'PostmanRuntime', now()),
    (gen_random_uuid(), 'user002', '/api/test2', 'POST', '500', 'Internal Server Error', '127.0.0.1', 'PostmanRuntime', now());

