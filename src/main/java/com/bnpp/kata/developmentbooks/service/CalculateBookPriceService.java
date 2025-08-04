package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CalculateBookPriceService {

    public double calculatePrice(List<BookRequest> bookRequest) {

        return bookRequest.stream().mapToDouble(bookReq -> {
            BooksEnum book = Arrays.stream(BooksEnum.values())
                    .filter(bookValue -> bookValue.getId() == bookReq.getBookId ()).findFirst().orElse(null);
            return (book != null) ? book.getPrice() * bookReq.getQuantity() : 0.0;
        }).sum();
    }
}
