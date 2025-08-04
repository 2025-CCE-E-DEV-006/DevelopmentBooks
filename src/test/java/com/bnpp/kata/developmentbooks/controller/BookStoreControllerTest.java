package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.model.BookRequest;
import com.bnpp.kata.developmentbooks.service.CalculateBookPriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookStoreControllerTest {

    @Mock
    private CalculateBookPriceService calculateBookPriceService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName ("Rest API to calculate the price of book with valid request")
    void calculatePriceApiWithValidRequest_shouldReturnStatusOK() throws Exception {

        List<BookRequest> bookRequests = Arrays.asList(new BookRequest(1, 1),new BookRequest(2, 1));

        mockMvc.perform(post("/api/bookstore/calculateprice").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper ().writeValueAsString(bookRequests))).andExpect(status().isOk())
                .andExpect(jsonPath ("$.finalPrice").exists());
    }

    @Test
    @DisplayName ("Rest API : empty request should throw exception")
    void calculatePriceApiWithEmptyRequest_shouldReturn_StatusBadRequest() throws Exception {

        List<BookRequest> emptyRequest = new ArrayList<> ();

        mockMvc.perform (post ("/api/bookstore/calculateprice").contentType (MediaType.APPLICATION_JSON)
                .content (new ObjectMapper ().writeValueAsString (emptyRequest))).andExpect (status ().isBadRequest ());
    }
}
