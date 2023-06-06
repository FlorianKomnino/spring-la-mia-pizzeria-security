package org.java.demo.repo;

import org.java.demo.pojo.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepo extends JpaRepository<SpecialOffer, Integer> {

}