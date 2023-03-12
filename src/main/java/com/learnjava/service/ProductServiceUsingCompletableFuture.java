package com.learnjava.service;

import com.learnjava.domain.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {

        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;

    }

    public Product retrieveProductDetails(String productId) {

        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture =  supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture =  supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                            .thenCombine(reviewCompletableFuture,
                                    (productInfo, review) -> new Product(productId, productInfo, review)).join();

        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());

        return product;

    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {

        CompletableFuture<ProductInfo> productInfoCompletableFuture =  supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture =  supplyAsync(() -> reviewService.retrieveReviews(productId));

        return productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review));

    }

    public Product retrieveProductDetailsWithInventory(String productId) {

        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture =  supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {

                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;

                });

        CompletableFuture<Review> reviewCompletableFuture =  supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)).join();

        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());

        return product;

    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {

        return productInfo.getProductOptions().stream()
                .map(productOption -> {

                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;

                })
                .collect(Collectors.toList());

    }

    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {

        return productInfo.getProductOptions().stream()
                .map(productOption ->

                    supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            })

                )
                .map(CompletableFuture::join).collect(Collectors.toList());

    }

    public Product retrieveProductDetailsWithInventory_approach2(String productId) {

        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture =  supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {

                    productInfo.setProductOptions(updateInventory_approach2(productInfo));
                    return productInfo;

                });

        CompletableFuture<Review> reviewCompletableFuture =  supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)).join();

        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());

        return product;

    }

}
