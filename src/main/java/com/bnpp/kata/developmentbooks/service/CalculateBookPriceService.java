package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CalculateBookPriceService {

    public BookResponse calculatePrice (List<BookRequest> bookRequest) {

        double totalPrice = bookRequest.stream ().mapToDouble (bookReq -> {
            BooksEnum book = Arrays.stream (BooksEnum.values ())
                    .filter (bookValue -> bookValue.getId () == bookReq.getBookId ()).findFirst ().orElse (null);
            return (book != null) ? book.getPrice () * bookReq.getQuantity () : 0.0;
        }).sum ();

        long uniqueBookCount = bookRequest.stream ().map (BookRequest::getBookId).distinct ().count ();

        double finalPrice = totalPrice * (1 - getDiscount (uniqueBookCount));

        return BookResponse.builder ()
                .finalPrice (finalPrice)
                .build ();
    }

    private double getDiscount (long uniqueBookCount) {

        if (uniqueBookCount == 3)
            return 0.10;
        else if (uniqueBookCount == 2)
            return 0.05;
        else
            return 0.0;
    }
}
