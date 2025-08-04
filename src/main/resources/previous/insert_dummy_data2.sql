CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

/* ---------- 1. USERS ---------- */
INSERT INTO p_users (
    user_id, username, password, name, birth, email, role,
    is_public, is_banned, created_at, created_by
)
SELECT
    lpad(seq::text, 10, 'U'),                     -- user_id
    'user' || seq,                                -- username
    'pass' || seq,                                -- password(예시)
    '사용자' || seq,                              -- name
    date '1990-01-01' + (seq % 100) * interval '30 days',
    'user' || seq || '@demo.com',
    (ARRAY['CUSTOMER','OWNER','MANAGER','MASTER'])[1 + (seq % 4)]::role_type,
    TRUE, FALSE,
    now(), 'SYSTEM'
FROM generate_series(1,30) AS seq;

/* ---------- 2. STORE CATEGORY ---------- */
INSERT INTO p_stores_category (stores_category_id, stores_category)
SELECT uuid_generate_v4(), '카테고리' || seq
FROM generate_series(1,30) seq;

/* ---------- 3. REGION ---------- */
INSERT INTO p_regions (
    region_id, region_1depth_name, region_2depth_name,
    region_3depth_name, created_at
)
SELECT uuid_generate_v4(),
       '서울','강남구','역삼동' || seq,
       now()
FROM generate_series(1,30) seq;

/* ---------- 4. STORES ---------- */
INSERT INTO p_stores (
    store_id, user_id, stores_category_id, store_name, store_description,
    address1, address2, zip_cd, store_phone,
    store_latitude, store_longitude,
    open_time, close_time, is_banned,
    created_at, created_by
)
SELECT
    uuid_generate_v4(),
    (SELECT user_id   FROM p_users            ORDER BY random() LIMIT 1),
    (SELECT stores_category_id FROM p_stores_category ORDER BY random() LIMIT 1),
    '매장' || seq,
    '매장 설명 ' || seq,
    '서울 강남구 테헤란로 ' || seq,
    '지하 ' || seq,
    lpad(seq::text,6,'0'),
    '02-0000-' || lpad(seq::text,4,'0'),
    37.5 + (random()/100),    -- 위도
    127.0 + (random()/100),   -- 경도
    '09:00','23:00', FALSE,
    now(), 'SYSTEM'
FROM generate_series(1,30) seq;

/* ---------- 5. STORE–REGION 다대다 ---------- */
INSERT INTO p_stores_regions (store_id, region_id, created_at)
SELECT  s.store_id, r.region_id, now()
FROM    (SELECT store_id  FROM p_stores  LIMIT 30) s
            CROSS JOIN LATERAL
    (SELECT region_id FROM p_regions ORDER BY random() LIMIT 1) r;

/* ---------- 6. MENU CATEGORY ---------- */
INSERT INTO p_menu_category (
    menu_category_id, store_id, menu_category_name, created_at
)
SELECT uuid_generate_v4(),
       store_id,
       '메뉴카테고리' || row_number() OVER (),
       now()
FROM p_stores
LIMIT 30;

/* ---------- 7. MENUS ---------- */
INSERT INTO p_menus (
    menu_id, store_id, menu_category_id, menu_name, menu_price,
    menu_description, is_public,
    created_at, created_by
)
SELECT uuid_generate_v4(),
       mc.store_id,
       mc.menu_category_id,
       '메뉴' || seq,
       5000 + (seq * 100),
       '맛있는 메뉴 ' || seq,
       (seq % 2 = 0),
       now(), 'SYSTEM'
FROM generate_series(1,30) seq
         JOIN LATERAL (
    SELECT menu_category_id, store_id
    FROM p_menu_category
    ORDER BY random() LIMIT 1
    ) mc ON TRUE;

/* ---------- 8. CARTS & CART_ITEMS ---------- */
INSERT INTO p_carts (cart_id, store_id, user_id, cart_status, created_at)
SELECT uuid_generate_v4(),
       (SELECT store_id FROM p_stores ORDER BY random() LIMIT 1),
       (SELECT user_id  FROM p_users  ORDER BY random() LIMIT 1),
       'ACTIVE', now()
FROM generate_series(1,30);

INSERT INTO p_cart_items (
    cart_item_id, cart_id, menu_id, quantity, created_at
)
SELECT uuid_generate_v4(),
       c.cart_id,
       (SELECT menu_id FROM p_menus ORDER BY random() LIMIT 1),
       1 + (random()*3)::int,
       now()
FROM p_carts c;

/* ---------- 9. ORDERS & ORDER_ITEMS ---------- */
INSERT INTO p_orders (
    order_id, user_id, store_id, loc_cd, cart_id,
    total_price, request_message, created_at, created_by
)
SELECT uuid_generate_v4(),
       c.user_id,
       c.store_id,
       (SELECT address_cd FROM p_customer_address ORDER BY random() LIMIT 1),
       c.cart_id,
       20000 + (random()*10000)::int,
       '요청사항 ' || row_number() OVER (),
       now(), 'SYSTEM'
FROM p_carts c
LIMIT 30;

INSERT INTO p_order_items (
    order_item_id, order_id, menu_id, quantity, created_at
)
SELECT uuid_generate_v4(),
       o.order_id,
       (SELECT menu_id FROM p_menus ORDER BY random() LIMIT 1),
       1 + (random()*2)::int,
       now()
FROM p_orders o;

/* ----------10. REVIEWS ---------- */
INSERT INTO p_reviews (
    review_id, store_id, user_id, rating, comment,
    is_public, created_at, created_by
)
SELECT uuid_generate_v4(),
       s.store_id,
       (SELECT user_id FROM p_users ORDER BY random() LIMIT 1),
       3 + (random()*2)::int,
       '리뷰 내용 ' || seq,
       TRUE,
       now(), 'SYSTEM'
FROM generate_series(1,30) seq
         JOIN LATERAL (
    SELECT store_id FROM p_stores ORDER BY random() LIMIT 1
    ) s ON TRUE;

/* ----------11. PAYMENTS ---------- */
INSERT INTO p_payments (
    payment_id, user_id, store_id, order_id,
    payment_method, card_number, payment_amount,
    payment_time, payment_result
)
SELECT uuid_generate_v4(),
       o.user_id,
       o.store_id,
       o.order_id,
       'CARD',
       lpad((random()*1e16)::bigint::text,16,'0'),
       o.total_price,
       now(),
       (ARRAY['SUCCESS','CANCELED','FAILED'])[1 + (random()*3)::int]::payment_result
FROM p_orders o
LIMIT 30;

/* ----------12. ERROR LOG ---------- */
INSERT INTO p_errors (
    error_id, user_id, request_url, http_method, error_code,
    error_message, client_ip, user_agent, created_at
)
SELECT uuid_generate_v4(),
       (SELECT user_id FROM p_users ORDER BY random() LIMIT 1),
       '/api/test/' || seq,
       'GET',
       'E' || lpad(seq::text,4,'0'),
       '테스트 오류 ' || seq,
       '127.0.0.1',
       'PostmanRuntime/7.38',
       now()
FROM generate_series(1,30) seq;
