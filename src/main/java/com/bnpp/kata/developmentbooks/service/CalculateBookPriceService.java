package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import com.bnpp.kata.developmentbooks.store.DiscountEnum;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CalculateBookPriceService {

    public BookResponse calculatePrice (List<BookRequest> bookRequest) {

        double finalPrice = 0.0;
        Map<BooksEnum, Integer> bookCountMap = getBooksCount(bookRequest);
        Map<BooksEnum, Integer> booksCount = new HashMap<> (bookCountMap);

        while (hasBooksLeft(booksCount)) {
            List<BooksEnum> selectedBooks = selectBooks(booksCount);

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

    private boolean hasBooksLeft(Map<BooksEnum, Integer> booksCount) {
        return booksCount.values().stream().anyMatch(count -> count > 0);
    }

    private List<BooksEnum> selectBooks(Map<BooksEnum, Integer> booksCount) {
        List<BooksEnum> selectedBooks = new ArrayList<>();

        for (BooksEnum bookEnum : BooksEnum.values()) {
            if (booksCount.getOrDefault(bookEnum, 0) > 0) {
                selectedBooks.add(bookEnum);
                booksCount.put(bookEnum, booksCount.get(bookEnum) - 1);
            }
        }
        return selectedBooks;
    }

    private double getDiscount (int uniqueBookCount) {
        return Arrays.stream(DiscountEnum.values())
                .filter(discount -> discount.getNumberOfDistinctItems() == uniqueBookCount)
                .map(DiscountEnum::getDiscountPercentage).findFirst().orElse(0.0);
    }
}
