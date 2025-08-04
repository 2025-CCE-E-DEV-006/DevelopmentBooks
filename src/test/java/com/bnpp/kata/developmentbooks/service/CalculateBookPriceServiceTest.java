package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculateBookPriceServiceTest {

    @Autowired
    private CalculateBookPriceService calculateBookPriceService;

    @Test
    @DisplayName("Single book purchase with single quantity : book price will be 50")
    public void calculatePriceForASingleBookPurchase_ShouldReturnPriceFifty() {

        List<BookRequest> bookRequests = Arrays.asList(new BookRequest(1, 1));

        double price = calculateBookPriceService.calculatePrice(bookRequests);

        assertEquals(50.0, price);
    }
}
