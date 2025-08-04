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

        Map<BooksEnum, Integer> bookCountMap = getBooksCount(bookRequest);
        List<Double> possiblePrices = new ArrayList<>();

        for (int numberOfBooks = 3; numberOfBooks <= 5; numberOfBooks++) {
            double totalPriceForBooks = calculateCombinationPrice(numberOfBooks,bookCountMap);
            possiblePrices.add(totalPriceForBooks);
        }
        double finalPrice = possiblePrices.stream().min(Double::compare).orElse(0.0);

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

    private double calculateCombinationPrice(int numberOfBooks, Map<BooksEnum, Integer> bookCountMap) {

        Map<BooksEnum, Integer> booksCount = new HashMap<> (bookCountMap);
        double totalPrice = 0.0;

        while (hasBooksLeft(booksCount)) {

            List<BooksEnum> selectedBooks = selectBooks(booksCount,numberOfBooks);
            if (!selectedBooks.isEmpty()) {
                double discount = getDiscount(selectedBooks.size());
                double actualPrice = selectedBooks.size() * 50;
                totalPrice += actualPrice * (1 - discount);
            }
        }
        return totalPrice;
    }

    private boolean hasBooksLeft(Map<BooksEnum, Integer> booksCount) {
        return booksCount.values().stream().anyMatch(count -> count > 0);
    }

    private List<BooksEnum> selectBooks(Map<BooksEnum, Integer> booksCount, int numberOfBooks) {
        List<BooksEnum> selectedBooks = new ArrayList<>();

        Arrays.stream(BooksEnum.values()).filter(bookEnum -> booksCount.getOrDefault(bookEnum, 0) > 0)
                .forEach(bookEnum -> {
                    if (selectedBooks.size() < numberOfBooks) {
                        selectedBooks.add(bookEnum);
                        booksCount.put(bookEnum, booksCount.get(bookEnum) - 1);
                    }
                });

        return selectedBooks;
    }

    private double getDiscount (int uniqueBookCount) {
        return Arrays.stream(DiscountEnum.values())
                .filter(discount -> discount.getNumberOfDistinctItems() == uniqueBookCount)
                .map(DiscountEnum::getDiscountPercentage).findFirst().orElse(0.0);
    }
}
