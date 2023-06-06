package org.java.demo.service;


import java.util.List;
import java.util.Optional;

import org.java.demo.pojo.Ingredient;
import org.java.demo.repo.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepo ingredientRepo;
	
	public List<Ingredient> findAll() {
		
		return ingredientRepo.findAll();
	}
	public Optional<Ingredient> findById(int id) {
		
		return ingredientRepo.findById(id);
	}
	public Ingredient save(Ingredient ingredient) {
		
		return ingredientRepo.save(ingredient);
	}
	public void deleteIngredient(Ingredient ingredient) {
		
		ingredientRepo.delete(ingredient);
	}
}
