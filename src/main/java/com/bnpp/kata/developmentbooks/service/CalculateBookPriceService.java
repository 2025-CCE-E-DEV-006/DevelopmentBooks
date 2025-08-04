package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookGroup;
import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.model.BookResponse;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import com.bnpp.kata.developmentbooks.store.DiscountEnum;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculateBookPriceService {

    public BookResponse calculatePrice (List<BookRequest> bookRequest) {

        Map<BooksEnum, Integer> bookCountMap = getBooksCount(bookRequest);
        List<Integer> applicableDiscountSet = getApplicableDiscountSet(bookCountMap.size());
        Map<Double, List<BookGroup>> priceToGroupMap = calculatePriceForEachDiscountSet (applicableDiscountSet,bookCountMap);

        Optional<Double> minPrice = priceToGroupMap.keySet().stream().reduce(Double::min);
        List<BookGroup> associatedBookGroups = priceToGroupMap.get(minPrice.get());

        return BookResponse.builder ()
                .listOfBookGroups(associatedBookGroups)
                .finalPrice (minPrice.get())
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

    private List<Integer> getApplicableDiscountSet(int numberOfBooks) {

        List<Integer> applicableDiscounts = Arrays.stream(DiscountEnum.values())
                .filter(level -> level.getNumberOfDistinctItems() <= numberOfBooks)
                .map(DiscountEnum::getNumberOfDistinctItems).sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return applicableDiscounts.isEmpty() ? Collections.singletonList(1) : applicableDiscounts;
    }

    private Map<Double, List<BookGroup>> calculatePriceForEachDiscountSet (List<Integer> applicableDiscountSet,Map<BooksEnum, Integer> bookCountMap) {

        Map<Double, List<BookGroup>> totalBookPrices = applicableDiscountSet.stream()
                .flatMap(discountSize -> calculatePriceForBookGroup(discountSize,bookCountMap)
                        .entrySet().stream()).collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue
                ));

        return totalBookPrices;
    }

    private Map<Double, List<BookGroup>> calculatePriceForBookGroup(int bookGroupSize,Map<BooksEnum, Integer> OriginalBooksCount) {

        Map<BooksEnum, Integer> booksCount = new HashMap<>(OriginalBooksCount);
        Map<Double, List<BookGroup>> priceToGroupMap = new HashMap<>();
        List<BookGroup> bookGroups = new ArrayList<>();
        double[] totalPrice =new double[1];
        processBooks(booksCount, bookGroupSize, bookGroups, totalPrice);
        priceToGroupMap.put(totalPrice[0], bookGroups);
        return priceToGroupMap;
    }

    private void processBooks(Map<BooksEnum, Integer> booksCount, int bookGroupSize, List<BookGroup> bookGroups, double[] totalPrice) {

        if (!hasBooksLeft(booksCount)) {
            return;
        }

        List<BooksEnum> selectedBooks = selectBooks(booksCount, bookGroupSize);
        if (!selectedBooks.isEmpty()) {

            double discount = getDiscount(selectedBooks.size());
            double actualPrice = selectedBooks.size() * DiscountEnum.PRICE;
            totalPrice[0] += actualPrice * (1 - discount);
            bookGroups.add(createBookGroup(selectedBooks, discount, actualPrice));

            processBooks(booksCount, bookGroupSize, bookGroups, totalPrice);
        }
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

    private BookGroup createBookGroup(List<BooksEnum> selectedBooks, double discount, double actualPrice) {

        return BookGroup.builder()
                .bookIds (selectedBooks.stream().map(BooksEnum::getId).collect(Collectors.toList()))
                .numberOfBooks(selectedBooks.size())
                .discountPercentage(discount * 100)
                .actualPrice(actualPrice)
                .discountAmount(actualPrice * discount)
                .finalGroupPrice(actualPrice - (actualPrice * discount))
                .build();
    }

    private double getDiscount (int uniqueBookCount) {
        return Arrays.stream(DiscountEnum.values())
                .filter(discount -> discount.getNumberOfDistinctItems() == uniqueBookCount)
                .map(DiscountEnum::getDiscountPercentage).findFirst().orElse(0.0);
    }
}
