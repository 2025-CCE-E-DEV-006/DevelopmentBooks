package com.bnpp.kata.developmentbooks.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculateBookPriceServiceTest {

    @Autowired
    private CalculateBookPriceService calculateBookPriceService;

    @Test
    @DisplayName("Single book purchase with single quantity : book price will be 50")
    public void calculatePriceForASingleBookPurchase_ShouldReturnPriceFifty() {

        double price = calculateBookPriceService.calculatePrice(1,1);

        assertEquals(50.0, price);
    }
}
