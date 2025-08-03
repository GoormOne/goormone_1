package com.profect.delivery.domain.cart.repository;

import com.profect.delivery.global.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {


    List<CartItem> findCartItemByCartItemId(UUID cartItemId);

    List<CartItem> findByCart_CartId(UUID cartId);
}
