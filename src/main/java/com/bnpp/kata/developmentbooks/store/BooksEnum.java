package com.bnpp.kata.developmentbooks.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooksEnum {

    CLEAN_CODE(1, "Clean Code", "Robert Martin", 2008, 50.00),
    THE_CLEAN_CODER(2, "The Clean Coder", "Robert Martin", 2011, 50.00),
    CLEAN_ARCHITECTURE(3, "Clean Architecture", "Robert Martin", 2017, 50.00),
    TEST_DRIVEN_DEVELOPMENT(4, "Test Driven Development By Example", "Kent Beck", 2003, 50.00),
    WORKING_WITH_LEGACY_CODE(5, "Working Effectively With Legacy Code", "Michael C. Feathers", 2004, 50.00);

    private int id;
    private String title;
    private String author;
    private int year;
    private double price;
}
