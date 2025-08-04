package com.bnpp.kata.developmentbooks.validator;

import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.store.BooksEnum;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookValidator {

    public void validateBooks (List<BookRequest> bookRequest) {

        Set<Integer> validBookIds = Arrays.stream(BooksEnum.values())
                .map(BooksEnum::getId).collect(Collectors.toSet());

        List<Integer> invalidBookIds = bookRequest.stream ()
                .map (BookRequest::getBookId)
                .filter (id -> !validBookIds.contains (id)).collect (Collectors.toList ());

        if (!invalidBookIds.isEmpty ()) {
            throw new InvalidBookException ("Invalid Book IDs : " + invalidBookIds);
        }

        List<Integer> invalidQuantities = bookRequest.stream()
                .map(BookRequest::getQuantity)
                .filter(qty -> qty <= 0).collect(Collectors.toList());

        if (!invalidQuantities.isEmpty()) {
            throw new InvalidBookException("Invalid Book Quantities: " + invalidQuantities);
        }
    }
}
