package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class ParameterizedServiceTest {

    @Autowired
    private CalculateBookPriceService calculateBookPriceService;


    @ParameterizedTest(name = "{index} => books={0}, expectedPrice={1}")
    @MethodSource("getBookPurchaseList")
    @DisplayName ("Parameterized Test : Various Book Purchase")
    public void calculatePriceParameterizedTest(List<BookRequest> bookRequests, double expectedPrice) {

        BookResponse actualPrice = calculateBookPriceService.calculatePrice(bookRequests);

        assertEquals(expectedPrice, actualPrice.getFinalPrice ());
    }

    private static Stream<Arguments> getBookPurchaseList() {
        return Stream.of(
                Arguments.of(Arrays.asList(new BookRequest(1, 3)), 150.0),
                Arguments.of(Arrays.asList(new BookRequest(1, 2), new BookRequest(2, 2)), 190.0),
                Arguments.of(Arrays.asList(new BookRequest(1, 2), new BookRequest(2, 2), new BookRequest(3, 3)), 320.0),
                Arguments.of(Arrays.asList(new BookRequest(1, 2), new BookRequest(2, 1), new BookRequest(3, 2), new BookRequest(4, 1)), 255.0),
                Arguments.of(Arrays.asList(new BookRequest(1, 2), new BookRequest(2, 2), new BookRequest(3, 2), new BookRequest(4, 2), new BookRequest(5, 1)), 347.5)
        );
    }
}
