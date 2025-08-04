package com.bnpp.kata.developmentbooks.validator;

import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BookValidatorTest {

    @Autowired
    private BookValidator bookValidator;

    @Test
    @DisplayName ("Invalid Book ID should throw exception")
    public void validateBooks_withInvalidBookId_shouldThrowException() {

        List<BookRequest> invalidBookId = Arrays.asList(new BookRequest(99, 1));

        assertThrows(InvalidBookException.class, () -> bookValidator.validateBooks (invalidBookId));
    }
}
