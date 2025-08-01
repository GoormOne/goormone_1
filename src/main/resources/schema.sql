CREATE TYPE role_type AS ENUM ('CUSTOMER', 'OWNER', 'MANAGER', 'MASTER');


CREATE TYPE cart_status AS ENUM ('ACTIVE', 'ORDERED', 'ABANDONED');


CREATE TYPE payment_method AS ENUM ('CARD');

CREATE TYPE payment_result AS ENUM ('PENDING', 'SUCCESS', 'CANCELED', 'FAILED');


CREATE TABLE "p_users" (
  "user_id" varchar(10) NOT NULL PRIMARY KEY,
  "username" varchar(10) UNIQUE NOT NULL,
  "password" varchar(15) NOT NULL,
  "name" varchar(10) NOT NULL,
  "birth" date NOT NULL,
  "email" varchar(30) UNIQUE NOT NULL,
  "role" role_type NOT NULL,
  "is_public" boolean NOT NULL,
  "is_banned" boolean NOT NULL,
  "created_at" timestamp NOT NULL,
  "created_by" varchar(10) NOT NULL,
  "updated_at" timestamp,
  "updated_by" varchar(10),
  "deleted_at" timestamp,
  "deleted_by" varchar(10),
  "deleted_rs" varchar(100)
);

CREATE TABLE "p_customer_address" (
  "address_cd" uuid NOT NULL PRIMARY KEY,
  "user_id" varchar(10) NOT NULL,
  "address_name" varchar(20) NOT NULL,
  "address1" varchar(50) NOT NULL,
  "address2" varchar(50) NOT NULL,
  "zip_cd" varchar(6) NOT NULL,
  "user_latitude" decimal(10,6) NOT NULL,
  "user_longitude" decimal(10,6) NOT NULL,
  "is_default" boolean NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "deleted_at" timestamp
);

CREATE TABLE "p_regions" (
  "region_id" uuid NOT NULL PRIMARY KEY,
  "region_1depth_name" varchar(50) NOT NULL,
  "region_2depth_name" varchar(50) NOT NULL,
  "region_3depth_name" varchar(50) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "deleted_at" timestamp
);

CREATE TABLE "p_stores_category" (
  "stores_category_id" uuid NOT NULL PRIMARY KEY,
  "stores_category" varchar(30) NOT NULL
);

CREATE TABLE "p_stores" (
    store_id            UUID PRIMARY KEY,
    user_id             VARCHAR(10) NOT NULL,
    stores_category_id  UUID NOT NULL,
    store_name          VARCHAR(30) NOT NULL,
    store_description   TEXT NOT NULL,
    address1            VARCHAR(50) NOT NULL,
    address2            VARCHAR(50) NOT NULL,
    zip_cd              VARCHAR(6) NOT NULL,
    store_phone         VARCHAR(15) NOT NULL,
    store_latitude      DECIMAL(10,6) NOT NULL,
    store_longitude     DECIMAL(10,6) NOT NULL,
    is_banned           BOOLEAN NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    created_by          VARCHAR(10) NOT NULL,
    updated_at          TIMESTAMP,
    updated_by          VARCHAR(10),
    deleted_at          TIMESTAMP,
    deleted_by          VARCHAR(10),
    deleted_rs          VARCHAR(100)
);

CREATE TABLE "p_stores_regions" (
  "store_id" uuid NOT NULL,
  "region_id" uuid NOT NULL,
  "created_at" timestamp NOT NULL
);

CREATE TABLE "p_stores_business_hr" (
  "business_hr_id" uuid NOT NULL PRIMARY KEY,
  "store_id" uuid NOT NULL,
  "day_of_week" varchar(3) NOT NULL,
  "open_time" time NOT NULL,
  "close_time" time NOT NULL
);

CREATE TABLE "p_menu_category" (
  "menu_category_id" uuid NOT NULL PRIMARY KEY,
  "store_id" uuid NOT NULL,
  "menu_category_name" varchar(20) NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "deleted_at" timestamp
);

-- CREATE TABLE "p_menus" (
--   "menu_id" uuid NOT NULL PRIMARY KEY,
--   "store_id" uuid NOT NULL,
--   "menu_category_id" uuid NOT NULL,
--   "menu_name" varchar(20) NOT NULL,
--   "menu_price" int NOT NULL,
--   "menu_description" text NOT NULL,
--   "is_public" boolean NOT NULL,
--   "created_at" timestamp NOT NULL,
--   "created_by" varchar(10) NOT NULL,
--   "updated_at" timestamp,
--   "updated_by" varchar(10),
--   "deleted_at" timestamp,
--   "deleted_by" varchar(10),
--   "deleted_rs" varchar(100)
-- );
CREATE TABLE p_menus (
                         menu_id UUID NOT NULL,
                         store_id UUID NOT NULL,
                         menu_category_id UUID,
                         menu_name VARCHAR(20),
                         menu_price INT,
                         menu_description TEXT,
                         is_public BOOLEAN,
                         created_at TIMESTAMP,
                         created_by VARCHAR(10),
                         updated_at TIMESTAMP DEFAULT NULL,
                         updated_by VARCHAR(10) DEFAULT NULL,
                         deleted_at TIMESTAMP DEFAULT NULL,
                         deleted_by VARCHAR(10) DEFAULT NULL,
                         deleted_rs VARCHAR(100) DEFAULT NULL,


                         PRIMARY KEY (menu_id, store_id),


                         FOREIGN KEY (store_id) REFERENCES p_stores(store_id),
                         FOREIGN KEY (menu_category_id) REFERENCES p_menu_category(menu_category_id)
);
CREATE TABLE "p_menu_photos" (
  "menu_photo_id" uuid NOT NULL PRIMARY KEY,
  "menu_id" uuid NOT NULL,
  "menu_photo_url" varchar(100) NOT NULL,
  "is_public" boolean NOT NULL,
  "created_at" timestamp NOT NULL,
  "created_by" varchar(10) NOT NULL,
  "updated_at" timestamp,
  "updated_by" varchar(10),
  "deleted_at" timestamp,
  "deleted_by" varchar(10),
  "deleted_rs" varchar(100)
);

CREATE TABLE "p_carts" (
  "cart_id" uuid NOT NULL PRIMARY KEY,
  "store_id" uuid NOT NULL,
  "user_id" varchar(10) NOT NULL,
  "cart_status" cart_status NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "updated_by" varchar(10)
);

CREATE TABLE "p_cart_items" (
  "cart_item_id" uuid NOT NULL PRIMARY KEY,
  "cart_id" uuid NOT NULL,
  "menu_id" uuid NOT NULL,
  "quantity" int NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "updated_by" varchar(10)
);

CREATE TABLE "p_orders" (
  "order_id" uuid NOT NULL PRIMARY KEY,
  "user_id" varchar(10) NOT NULL,
  "store_id" uuid NOT NULL,
  "loc_cd" uuid NOT NULL,
  "cart_id" uuid NOT NULL,
  "total_price" int NOT NULL,
  "request_message" text,
  "created_at" timestamp NOT NULL,
  "created_by" varchar(10) NOT NULL,
  "deleted_at" timestamp,
  "deleted_by" varchar(10),
  "deleted_rs" varchar(100)
);

CREATE TABLE "p_order_items" (
  "order_item_id" uuid NOT NULL PRIMARY KEY,
  "order_id" uuid NOT NULL,
  "menu_id" uuid NOT NULL,
  "quantity" int NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp,
  "updated_by" varchar(10)
);

CREATE TABLE "p_reviews" (
  "review_id" uuid NOT NULL PRIMARY KEY,
  "store_id" uuid NOT NULL,
  "user_id" varchar(10) NOT NULL,
  "rating" smallint NOT NULL,
  "comment" text,
  "is_public" boolean NOT NULL,
  "created_at" timestamp NOT NULL,
  "created_by" varchar(10) NOT NULL
);

CREATE TABLE "p_review_summary" (
  "batch_id" uuid NOT NULL PRIMARY KEY,
  "store_id" uuid NOT NULL,
  "period_start" date NOT NULL,
  "period_end" date NOT NULL,
  "review_cnt" smallint NOT NULL,
  "avg_rating" decimal(3,2) NOT NULL,
  "summary_text" text NOT NULL,
  "created_at" timestamp NOT NULL,
  "created_by" varchar(10) NOT NULL
);

CREATE TABLE p_review_average (
                                  store_id UUID PRIMARY KEY REFERENCES p_stores(store_id),
                                  count INT,
                                  total INT
);
CREATE TABLE "p_payments" (
  "payment_id" uuid NOT NULL PRIMARY KEY,
  "user_id" varchar(10) NOT NULL,
  "store_id" uuid NOT NULL,
  "order_id" uuid NOT NULL,
  "payment_method" payment_method NOT NULL,
  "card_number" varchar(16) NOT NULL,
  "payment_amount" int NOT NULL,
  "payment_time" timestamp NOT NULL,
  "payment_result" payment_result NOT NULL,
  "failure_reason" text
);

CREATE TABLE "p_errors" (
  "error_id" uuid NOT NULL PRIMARY KEY,
  "user_id" varchar(10) NOT NULL,
  "request_url" varchar(255) NOT NULL,
  "http_method" varchar(10) NOT NULL,
  "error_code" varchar(20) NOT NULL,
  "error_message" text NOT NULL,
  "client_ip" varchar(45) NOT NULL,
  "user_agent" text NOT NULL,
  "created_at" timestamp NOT NULL
);

COMMENT ON TABLE "p_stores_regions" IS '교차 테이블';

ALTER TABLE "p_customer_address" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_stores" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_stores" ADD FOREIGN KEY ("stores_category_id") REFERENCES "p_stores_category" ("stores_category_id");

ALTER TABLE "p_stores_regions" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_stores_regions" ADD FOREIGN KEY ("region_id") REFERENCES "p_regions" ("region_id");

ALTER TABLE "p_stores_business_hr" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_menu_category" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_menus" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_menus" ADD FOREIGN KEY ("menu_category_id") REFERENCES "p_menu_category" ("menu_category_id");

ALTER TABLE "p_menu_photos" ADD FOREIGN KEY ("menu_id") REFERENCES "p_menus" ("menu_id");

ALTER TABLE "p_carts" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_carts" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_cart_items" ADD FOREIGN KEY ("cart_id") REFERENCES "p_carts" ("cart_id");

ALTER TABLE "p_cart_items" ADD FOREIGN KEY ("menu_id") REFERENCES "p_menus" ("menu_id");

ALTER TABLE "p_orders" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_orders" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_orders" ADD FOREIGN KEY ("loc_cd") REFERENCES "p_customer_address" ("address_cd");

ALTER TABLE "p_orders" ADD FOREIGN KEY ("cart_id") REFERENCES "p_carts" ("cart_id");

ALTER TABLE "p_order_items" ADD FOREIGN KEY ("order_id") REFERENCES "p_orders" ("order_id");

ALTER TABLE "p_order_items" ADD FOREIGN KEY ("menu_id") REFERENCES "p_menus" ("menu_id");

ALTER TABLE "p_reviews" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_reviews" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_review_summary" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_payments" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");

ALTER TABLE "p_payments" ADD FOREIGN KEY ("store_id") REFERENCES "p_stores" ("store_id");

ALTER TABLE "p_payments" ADD FOREIGN KEY ("order_id") REFERENCES "p_orders" ("order_id");

ALTER TABLE "p_errors" ADD FOREIGN KEY ("user_id") REFERENCES "p_users" ("user_id");
