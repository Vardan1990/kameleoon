package com.example.kameleoon.repo;

import com.example.kameleoon.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByUserId(Long id);

}
