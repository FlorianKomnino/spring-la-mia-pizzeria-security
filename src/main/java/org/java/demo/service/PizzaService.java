package org.java.demo.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.java.demo.pojo.Pizza;
import org.java.demo.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;
	
	public List<Pizza> findAll() {
		
		return pizzaRepo.findAll();
	}
	@Transactional
	public Optional<Pizza> findByIdWithBorrowing(int id) {
		
		Optional<Pizza> pizzaOpt = pizzaRepo.findById(id);
		Hibernate.initialize(pizzaOpt.get().getSpecialOffer());
		
		return pizzaOpt;
	}
	public Pizza save(Pizza pizza) {
		
		return pizzaRepo.save(pizza);
	}
	public Optional<Pizza> findById(int id) {
		
		return pizzaRepo.findById(id);
	}
	public List<Pizza> findByName(String name) {
		
		return pizzaRepo.findByNameContaining(name);
	}
	public void deletePizza(Pizza pizza) {
		
		pizzaRepo.delete(pizza);
	}
}
