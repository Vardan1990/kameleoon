package com.example.kameleoon.controllers;

import com.example.kameleoon.entity.Vote;
import com.example.kameleoon.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/plus")
    public void votePlus(@RequestParam(name = "userId") Long userId,
                         @RequestParam(name = "quoteId") Long quoteId,
                         @RequestParam(name = "voteAmount") Long voteAmount) {
        log.info("vote plus quote by id {}", quoteId);
        voteService.voteQuotePlus(userId, quoteId, voteAmount);

    }

    @PutMapping(value = "/minus")
    public void voteMinus(@RequestParam(name = "userId") Long userId,
                          @RequestParam(name = "quoteId") Long quoteId,
                          @RequestParam(name = "voteAmount") Long voteAmount) {
        log.info("vote minus quote by id {}", quoteId);
        voteService.voteQuoteMinus(userId, quoteId, voteAmount);

    }

    @GetMapping(value = "/getVoteList")
    public ResponseEntity<List<Vote>> getVoteListByQuoteId(@RequestParam("quoteId") Long quoteId) {
        log.info("get vote list by quote_id {}", quoteId);
        return ResponseEntity.ok(voteService.getVoteListByQuoteId(quoteId));
    }

}
