package com.example.kameleoon.service;

import com.example.kameleoon.dto.CreateQuoteDto;
import com.example.kameleoon.dto.UpdateQuoteDto;
import com.example.kameleoon.entity.Quote;
import com.example.kameleoon.entity.User;
import com.example.kameleoon.exceptions.QuoteException;
import com.example.kameleoon.exceptions.UserException;
import com.example.kameleoon.repo.QuoteRepository;
import com.example.kameleoon.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Quote createQuote(CreateQuoteDto createQuoteDto) {
        try {
            Optional<User> userOptional = userRepository.findById(createQuoteDto.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return quoteRepository.save(new Quote(createQuoteDto.getQuoteContent(), null, user));
            } else {
                throw new UserException("user by this id not found");
            }
        } catch (Exception e) {
            log.error("can not create quote {}" , e.getMessage(), e);
            return null;
        }
    }

    @Transactional
    public void deleteQuote(Long id) {
        quoteRepository.findById(id).ifPresent(quoteRepository::delete);
    }

    public Optional<Quote> getQuote(Long quoteId) {
        try {
            return quoteRepository.findById(quoteId);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public List<Quote> getQuoteList(Long userId) {
        List<Quote> quoteList = quoteRepository.findAllByUserId(userId);
        if (quoteList.isEmpty()) {
            throw new EntityNotFoundException("quotes by this user not found");
        }
        return quoteList;
    }

    @Transactional
    public synchronized Optional<Quote> updateQuote(UpdateQuoteDto updateQuoteDto) {
        Optional<Quote> quoteOptional = quoteRepository.findById(updateQuoteDto.getQuoteId());
        boolean updated = false;
        if (!quoteOptional.isPresent()) {
            return Optional.empty();
        }
        Quote quote = quoteOptional.get();
        if (!StringUtils.isBlank(updateQuoteDto.getQuoteContent())) {
            log.info("quote content {}", updateQuoteDto.getQuoteContent());
            quote.setQuoteContent(updateQuoteDto.getQuoteContent());
            updated = true;
        }
        if (updateQuoteDto.getVoteAmount() != null) {
            log.info("vote amount {} requested {}", quote.getVoteAmount(), updateQuoteDto.getVoteAmount());
            quote.setVoteAmount(quote.getVoteAmount() != null ? quote.getVoteAmount() + updateQuoteDto.getVoteAmount() : updateQuoteDto.getVoteAmount());
            updated = true;
        }
        if (!updated) {
            return Optional.of(quote);
        }
        return Optional.of(quoteRepository.save(quote));
    }

    public Quote getRandomQuote() {
        List<Quote> quoteList = quoteRepository.findAll();
        if (quoteList.isEmpty()) {
            throw new EntityNotFoundException("quotes not found");
        }
        int randomQuoteId = new Random().nextInt(quoteList.size());
        log.info("random quote id {} ", randomQuoteId);
        return quoteList.get(randomQuoteId);
    }

    public List<Quote> getTopTenQuotes() {
        List<Quote> quoteList = quoteRepository.findAll().stream().sorted(Comparator.comparing(Quote::getVoteAmount).reversed()).collect(Collectors.toList());
        if (quoteList.isEmpty()) {
            throw new EntityNotFoundException("quotes not found");
        }
        return quoteList.subList(0, quoteList.size() < 10 ? quoteList.size() : 9);
    }

    public List<Quote> getWorstTenQuotes() {
        List<Quote> quoteList = quoteRepository.findAll().stream().sorted(Comparator.comparing(Quote::getVoteAmount)).collect(Collectors.toList());
        if (quoteList.isEmpty()) {
            throw new EntityNotFoundException("quotes not found");
        }
        return quoteList.subList(0, quoteList.size() < 10 ? quoteList.size() : 9);
    }
}
