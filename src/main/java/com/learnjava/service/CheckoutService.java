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

        timeTaken();
        return new CheckoutResponse(SUCCESS);

    }

}
