package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
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

        BookResponse price = calculateBookPriceService.calculatePrice(bookRequests);

        assertEquals(50.0, price.getFinalPrice ());
    }

    @Test
    @DisplayName ("Two different book purchase : 5% discount is applicable")
    public void calculatePriceForTwoDifferentBookPurchase_ShouldApplyFivePercentDiscount() {

        List<BookRequest> bookRequests = Arrays.asList(new BookRequest(1, 1),new BookRequest(2, 1));

        BookResponse price = calculateBookPriceService.calculatePrice(bookRequests);

        assertEquals(95.0, price.getFinalPrice ());
    }

    @Test
    @DisplayName ("Three different book purchase : 10% discount is applicable")
    public void calculatePriceForThreeDifferentBookPurchase_ShouldGive10PercentDiscount() {

        List<BookRequest> bookRequests = Arrays.asList(new BookRequest(1, 1),new BookRequest(2, 1),
                new BookRequest(3, 1));

        BookResponse price = calculateBookPriceService.calculatePrice(bookRequests);

        assertEquals(135.0, price.getFinalPrice ());
    }
}
