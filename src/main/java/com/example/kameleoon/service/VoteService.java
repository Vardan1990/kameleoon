package com.example.kameleoon.service;

import com.example.kameleoon.dto.UpdateQuoteDto;
import com.example.kameleoon.entity.Quote;
import com.example.kameleoon.entity.User;
import com.example.kameleoon.entity.Vote;
import com.example.kameleoon.repo.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final QuoteService quoteService;
    private final UserService userService;


    @Transactional
    public void voteQuotePlus(Long userId, Long quoteId, Long voteAmount) {
        UpdateQuoteDto updateQuoteDto = createUpdateQuoteDto(quoteId, voteAmount);
        log.info("update quote vote plus");
        quoteService.updateQuote(updateQuoteDto);
        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Quote> quoteOptional = quoteService.getQuote(quoteId);
        if (optionalUser.isPresent() && quoteOptional.isPresent()) {
            createVote(voteAmount, optionalUser.get(), quoteOptional.get());
        }
    }

    @Transactional
    public void voteQuoteMinus(Long userId, Long quoteId, Long voteAmount) {
        UpdateQuoteDto updateQuoteDto = createUpdateQuoteDto(quoteId, (-voteAmount));
        log.info("update quote vote minus");
        quoteService.updateQuote(updateQuoteDto);
        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Quote> quoteOptional = quoteService.getQuote(quoteId);
        if (optionalUser.isPresent() && quoteOptional.isPresent()) {
            createVote((-voteAmount), optionalUser.get(), quoteOptional.get());
        }
    }

    private UpdateQuoteDto createUpdateQuoteDto(Long quoteId, Long voteAmount) {
        UpdateQuoteDto updateQuoteDto = new UpdateQuoteDto();
        updateQuoteDto.setQuoteId(quoteId);
        updateQuoteDto.setVoteAmount(voteAmount);
        return updateQuoteDto;
    }

    private void createVote(Long voteAmount, User user, Quote quote) {
        Vote vote = new Vote();
        vote.setQuote(quote);
        vote.setVoteCount(voteAmount);
        vote.setUser(user);
        log.info("create vote");
        voteRepository.save(vote);
    }

    public List<Vote> getVoteListByQuoteId(Long quoteId) {
        List<Vote> voteList = voteRepository.findAllByQuoteId(quoteId).stream().sorted(Comparator.comparing(Vote::getCreated)).collect(Collectors.toList());
        if (voteList.isEmpty()) {
            throw new EntityNotFoundException("vote list by this quote id not found");
        }
        return voteList;
    }
}
