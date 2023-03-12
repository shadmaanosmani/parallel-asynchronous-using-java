package com.learnjava.service;

import com.learnjava.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private final ProductInfoService productInfoService = new ProductInfoService();

    private final ReviewService reviewService = new ReviewService();

    private final InventoryService inventoryService = new InventoryService();

    private final ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    void retrieveProductDetails() {

        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetails_approach2() {

        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetails_approach2(productId).join();

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory() {

        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());
        product.getProductInfo().getProductOptions().forEach(Assertions::assertNotNull);

    }

    @Test
    void retrieveProductDetailsWithInventory_approach2() {

        String productId = "ABC123";

        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertFalse(product.getProductInfo().getProductOptions().isEmpty());
        assertNotNull(product.getReview());
        product.getProductInfo().getProductOptions().forEach(Assertions::assertNotNull);

    }

}