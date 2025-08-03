package com.bnpp.kata.developmentbooks.service;

import org.springframework.stereotype.Service;

@Service
public class CalculateBookPriceService {

    public double calculatePrice(int bookId, int quantity) {
        return quantity * 50;
    }
}
