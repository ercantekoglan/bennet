package com.partymanager.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.partymanager.finalproject.domain.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

}
