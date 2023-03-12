package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.domain.checkout.CheckoutStatus.FAILURE;
import static com.learnjava.domain.checkout.CheckoutStatus.SUCCESS;
import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

@RequiredArgsConstructor
public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutResponse checkout(Cart cart) {

        startTimer();

        List<CartItem> priceValidationList = cart.getCartItemList().parallelStream().map(cartItem -> {
            boolean isPriceValid = priceValidatorService.isCartItemInvalid(cartItem);
            cartItem.setExpired(isPriceValid);
            return cartItem;
        }).filter(CartItem::isExpired).collect(Collectors.toList());

        if(!priceValidationList.isEmpty()) {

            timeTaken();
            return new CheckoutResponse(FAILURE, priceValidationList);

        }

//        double finalPrice = calculateFinalPrice(cart);

        double finalPrice = calculateFinalPrice_reduce(cart);

        log("Checkout complete and the final price is " + finalPrice);

        timeTaken();
        return new CheckoutResponse(SUCCESS, finalPrice);

    }

    private double calculateFinalPrice(Cart cart) {

        return cart.getCartItemList().parallelStream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getRate()).sum();

    }

    private double calculateFinalPrice_reduce(Cart cart) {

        return cart.getCartItemList().parallelStream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getRate()).reduce(0.0, Double::sum);

    }

}
