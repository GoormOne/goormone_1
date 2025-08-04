package com.profect.delivery.domain.cart.repository;

import com.profect.delivery.global.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {


    List<Cart> findCartByUserId(String userId);

    @EntityGraph(attributePaths = "cartItems")
    List<Cart> findByUserId(String userId);

}
