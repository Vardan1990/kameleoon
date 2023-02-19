package com.example.kameleoon.repo;

import com.example.kameleoon.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findAllByQuoteId(Long quoteId);

}
