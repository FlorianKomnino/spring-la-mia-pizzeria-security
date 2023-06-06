package org.java.demo.service;

import java.util.List;
import java.util.Optional;

import org.java.demo.pojo.SpecialOffer;
import org.java.demo.repo.SpecialOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {

	@Autowired
	private SpecialOfferRepo specialOfferRepo;
	
	public List<SpecialOffer> findAll() {
		
		return specialOfferRepo.findAll();
	}
	public Optional<SpecialOffer> findById(int id) {
		
		return specialOfferRepo.findById(id);
	}
	public SpecialOffer save(SpecialOffer specialOffer) {
		
		return specialOfferRepo.save(specialOffer);
	}
}
