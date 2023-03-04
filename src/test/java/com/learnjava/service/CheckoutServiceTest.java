package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.learnjava.domain.checkout.CheckoutStatus.FAILURE;
import static com.learnjava.domain.checkout.CheckoutStatus.SUCCESS;
import static com.learnjava.util.DataSet.createCart;
import static com.learnjava.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    private final PriceValidatorService priceValidatorService = new PriceValidatorService();

    private final CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void noOfCores() {

        log("No. of cores: " + Runtime.getRuntime().availableProcessors());

    }

    @Test
    void checkout_6_items() {

        Cart cart = createCart(6);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(SUCCESS, checkoutResponse.getCheckoutStatus());

    }

    @Test
    @DisplayName("checking performance with 9 items that means definitely we will have failure items")
    void checkout_9_items() {

        Cart cart = createCart(9);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(FAILURE, checkoutResponse.getCheckoutStatus());

    }

}