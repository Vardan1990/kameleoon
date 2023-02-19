package com.example.kameleoon.controllers;

import com.example.kameleoon.dto.CreateQuoteDto;
import com.example.kameleoon.dto.UpdateQuoteDto;
import com.example.kameleoon.entity.Quote;
import com.example.kameleoon.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping(value = "/createQuote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quote> createQuote(@RequestBody @Valid CreateQuoteDto createQuoteDto) {
        log.info("quote# create from dto {}", createQuoteDto);
        return ResponseEntity.ok(quoteService.createQuote(createQuoteDto));

    }

    @PutMapping(value = "/updateQuote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateQuoteDto updateQuoteDto) {
        log.info("quote# update from dto {}", updateQuoteDto);
        return quoteService.updateQuote(updateQuoteDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/getQuote")
    public ResponseEntity<?> getQuote(@RequestParam("quoteId") Long id) {
        log.info("get quote by id {}", id);
        return quoteService.getQuote(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/getRandomQuote")
    public Quote getRandomQuote() {
        log.info("get random quote");
        return quoteService.getRandomQuote();
    }

    @GetMapping(value = "/getTopTenQuotes")
    public ResponseEntity<List<Quote>> getTopTenQuotes() {
        log.info("get top ten quotes");
        return ResponseEntity.ok(quoteService.getTopTenQuotes());
    }

    @GetMapping(value = "/getWorstTenQuotes")
    public ResponseEntity<List<Quote>> getWorstTenQuotes() {
        log.info("get Worst ten quotes");
        return ResponseEntity.ok(quoteService.getWorstTenQuotes());
    }

    @GetMapping(value = "/getQuoteList")
    public ResponseEntity<List<Quote>> getQuoteList(@RequestParam("userId") Long userId) {
        log.info("get quote list by user_id {}", userId);
        return ResponseEntity.ok(quoteService.getQuoteList(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteQuote(@RequestParam(name = "quoteId") Long id) {
        log.info("delete quote by id {}", id);
        quoteService.deleteQuote(id);
        return ResponseEntity.ok().build();
    }
}
