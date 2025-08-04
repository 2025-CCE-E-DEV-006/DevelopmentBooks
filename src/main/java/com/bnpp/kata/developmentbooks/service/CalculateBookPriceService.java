package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CalculateBookPriceService {

    public BookResponse calculatePrice (List<BookRequest> bookRequest) {

        double finalPrice = 0.0;
        Map<BooksEnum, Integer> bookCountMap = getBooksCount(bookRequest);
        Map<BooksEnum, Integer> booksCount = new HashMap<> (bookCountMap);

        while ((booksCount.values().stream().anyMatch(count -> count > 0))) {

            List<BooksEnum> selectedBooks = new ArrayList<> ();
            for (BooksEnum bookEnum : BooksEnum.values()) {
                if (booksCount.getOrDefault(bookEnum, 0) > 0) {
                    selectedBooks.add(bookEnum);
                    booksCount.put(bookEnum, booksCount.get(bookEnum) - 1);
                }
            }
            if (!selectedBooks.isEmpty()) {
                double actualPrice = selectedBooks.size() * 50;
                finalPrice += actualPrice * (1 - getDiscount(selectedBooks.size()));
            }
        }

        return BookResponse.builder ()
                .finalPrice (finalPrice)
                .build ();
    }

    private Map<BooksEnum, Integer> getBooksCount(List<BookRequest> bookRequest) {

        Map<BooksEnum, Integer> booksCount = new LinkedHashMap<> ();

        bookRequest.forEach(request -> {
            BooksEnum book = BooksEnum.values()[request.getBookId () - 1];
            booksCount.put(book, request.getQuantity());
        });

        return booksCount;
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
