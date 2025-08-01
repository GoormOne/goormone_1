CREATE TYPE "role_type" AS ENUM (
  'CUSTOMER',
  'OWNER',
  'MANAGER',
  'MASTER'
);

CREATE TYPE "cart_status" AS ENUM (
  'ACTIVE',
  'ORDERED',
  'ABANDONED'
);

CREATE TYPE "payment_method" AS ENUM (
  'CARD'
);

CREATE TYPE "payment_result" AS ENUM (
  'PENDING',
  'SUCCESS',
  'CANCELED',
  'FAILED'
);

CREATE TABLE "p_users" (
                           "user_id" varchar(10) PRIMARY KEY,
                           "username" varchar(10) UNIQUE,
                           "password" varchar(15),
                           "name" varchar(10),
                           "birth" date,
                           "email" varchar(30) UNIQUE,
                           "role" role_type,
                           "is_public" boolean,
                           "is_banned" boolean,
                           "created_at" timestamp,
                           "created_by" varchar(10),
                           "updated_at" timestamp,
                           "updated_by" varchar(10),
                           "deleted_at" timestamp,
                           "deleted_by" varchar(10),
                           "deleted_rs" varchar(100)
);

CREATE TABLE "p_customer_address" (
                                      "address_cd" uuid PRIMARY KEY,
                                      "user_id" varchar(10),
                                      "address_name" varchar(20),
                                      "address1" varchar(50),
                                      "address2" varchar(50),
                                      "zip_cd" varchar(6),
                                      "user_latitude" decimal(10,6),
                                      "user_longitude" decimal(10,6),
                                      "is_default" boolean,
                                      "created_at" timestamp,
                                      "updated_at" timestamp,
                                      "deleted_at" timestamp
);

CREATE TABLE "p_regions" (
                             "region_id" uuid PRIMARY KEY,
                             "region_1depth_name" varchar(50),
                             "region_2depth_name" varchar(50),
                             "region_3depth_name" varchar(50),
                             "created_at" timestamp,
                             "updated_at" timestamp,
                             "deleted_at" timestamp
);

CREATE TABLE "p_stores_category" (
                                     "stores_category_id" uuid PRIMARY KEY,
                                     "stores_category" varchar(30)
);

CREATE TABLE "p_stores" (
                            "store_id" uuid PRIMARY KEY,
                            "user_id" varchar(10),
                            "stores_category_id" uuid,
                            "address1" varchar(50),
                            "address2" varchar(50),
                            "zip_cd" varchar(6),
                            "store_phone" varchar(15),
                            "store_latitude" decimal(10,6),
                            "store_longitude" decimal(10,6),
                            "is_banned" boolean,
                            "created_at" timestamp,
                            "created_by" varchar(10),
                            "updated_at" timestamp,
                            "updated_by" varchar(10),
                            "deleted_at" timestamp,
                            "deleted_by" varchar(10),
                            "deleted_rs" varchar(100)
);

CREATE TABLE "p_stores_regions" (
                                    "store_id" uuid,
                                    "region_id" uuid,
                                    "created_at" timestamp
);

CREATE TABLE "p_stores_business_hr" (
                                        "business_hr_id" uuid PRIMARY KEY,
                                        "store_id" uuid,
                                        "day_of_week" varchar(3),
                                        "open_time" time,
                                        "close_time" time
);

CREATE TABLE "p_menu_category" (
                                   "menu_category_id" uuid PRIMARY KEY,
                                   "store_id" uuid,
                                   "menu_category_name" varchar(20),
                                   "created_at" timestamp,
                                   "updated_at" timestamp,
                                   "deleted_at" timestamp
);

CREATE TABLE "p_menus" (
                           "menu_id" uuid PRIMARY KEY,
                           "store_id" uuid,
                           "menu_category_id" uuid,
                           "menu_name" varchar(20),
                           "menu_price" int,
                           "menu_description" text,
                           "is_public" boolean,
                           "created_at" timestamp,
                           "created_by" varchar(10),
                           "updated_at" timestamp,
                           "updated_by" varchar(10),
                           "deleted_at" timestamp,
                           "deleted_by" varchar(10),
                           "deleted_rs" varchar(100)
);

CREATE TABLE "p_menu_photos" (
                                 "menu_photo_id" uuid PRIMARY KEY,
                                 "menu_id" uuid,
                                 "menu_photo_url" varchar(100),
                                 "is_public" boolean,
                                 "created_at" timestamp,
                                 "created_by" varchar(10),
                                 "updated_at" timestamp,
                                 "updated_by" varchar(10),
                                 "deleted_at" timestamp,
                                 "deleted_by" varchar(10),
                                 "deleted_rs" varchar(100)
);

CREATE TABLE "p_carts" (
                           "cart_id" uuid PRIMARY KEY,
                           "store_id" uuid,
                           "user_id" varchar(10),
                           "cart_status" cart_status,
                           "created_at" timestamp,
                           "updated_at" timestamp,
                           "updated_by" varchar(10)
);

CREATE TABLE "p_cart_items" (
                                "cart_item_id" uuid PRIMARY KEY,
                                "cart_id" uuid,
                                "menu_id" uuid,
                                "quantity" int,
                                "created_at" timestamp,
                                "updated_at" timestamp,
                                "updated_by" varchar(10)
);

CREATE TABLE "p_orders" (
                            "order_id" uuid PRIMARY KEY,
                            "user_id" varchar(10),
                            "store_id" uuid,
                            "loc_cd" uuid,
                            "cart_id" uuid,
                            "total_price" int,
                            "request_message" text,
                            "created_at" timestamp,
                            "created_by" varchar(10),
                            "deleted_at" timestamp,
                            "deleted_by" varchar(10),
                            "deleted_rs" varchar(100)
);

CREATE TABLE "p_order_items" (
                                 "order_item_id" uuid PRIMARY KEY,
                                 "order_id" uuid,
                                 "menu_id" uuid,
                                 "quantity" int,
                                 "created_at" timestamp,
                                 "updated_at" timestamp,
                                 "updated_by" varchar(10)
);

CREATE TABLE "p_reviews" (
                             "review_id" uuid PRIMARY KEY,
                             "store_id" uuid,
                             "user_id" varchar(10),
                             "rating" smallint,
                             "comment" text,
                             "is_public" boolean,
                             "created_at" timestamp,
                             "created_by" varchar(10)
);

CREATE TABLE "p_review_summary" (
                                    "batch_id" uuid PRIMARY KEY,
                                    "store_id" uuid,
                                    "period_start" date,
                                    "period_end" date,
                                    "review_cnt" smallint,
                                    "avg_rating" decimal(3,2),
                                    "summary_text" text,
                                    "created_at" timestamp,
                                    "created_by" varchar(10)
);

CREATE TABLE "p_payments" (
                              "payment_id" uuid PRIMARY KEY,
                              "user_id" varchar(10),
                              "store_id" uuid,
                              "order_id" uuid,
                              "payment_method" payment_method,
                              "card_number" varchar(16),
                              "payment_amount" int,
                              "payment_time" timestamp,
                              "payment_result" payment_result,
                              "failure_reason" text
);

CREATE TABLE "p_errors" (
                            "error_id" uuid PRIMARY KEY,
                            "user_id" varchar(10),
                            "request_url" varchar(255),
                            "http_method" varchar(10),
                            "error_code" varchar(20),
                            "error_message" text,
                            "client_ip" varchar(45),
                            "user_agent"  text,
                            "created_at" timestamp
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
