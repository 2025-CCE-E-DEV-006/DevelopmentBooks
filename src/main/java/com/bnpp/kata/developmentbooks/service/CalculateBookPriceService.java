package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.store.BooksEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CalculateBookPriceService {

    public double calculatePrice(int bookId, int quantity) {

        BooksEnum book = Arrays.stream(BooksEnum.values()).filter(bookValue-> bookValue.getId() == bookId)
                .findFirst().orElse(null);

        return book.getPrice() * quantity;
    }
}
