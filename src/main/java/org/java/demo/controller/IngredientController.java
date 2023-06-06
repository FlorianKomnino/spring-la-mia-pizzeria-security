package org.java.demo.controller;

import java.util.List;
import java.util.Optional;

import org.java.demo.pojo.Ingredient;
import org.java.demo.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping("/ingredients/list")
	public String ingredientsPage(Model model) {
		
		List<Ingredient> foundIngredients = ingredientService.findAll();
		model.addAttribute("ingredients", foundIngredients);
		return "ingredients-index";
	}
	
	
	
	

	@GetMapping("/ingredients/create")
	public String createIngredient(Model model) {
		
		model.addAttribute("ingredient", new Ingredient());
		
		return "ingredient-create";
	}
	
	@PostMapping("/ingredients/create")
	public String storeIngredient(
			Model model,
			@Valid @ModelAttribute Ingredient ingredient,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {

			// system error print through lambda expression
			bindingResult.getAllErrors().forEach( (er) -> System.err.println("Errore: " + er.getDefaultMessage())  );
			
			model.addAttribute("ingredient", ingredient);
			model.addAttribute("errors", bindingResult);
			
			return "ingredient-create";
		}
		
		
		ingredientService.save(ingredient);
		
		return "redirect:/ingredients/list";
	}

	
	@GetMapping("/ingredients/update/{id}")
	public String editIngredient(
			Model model,
			@PathVariable int id
		) {
		
		
		Optional<Ingredient> ingredientOpt = ingredientService.findById(id);
		Ingredient ingredient = ingredientOpt.get();
		model.addAttribute("ingredient", ingredient);
		
		return "ingredient-update";
	}
	
	@PostMapping("/ingredients/update")
	public String updateIngredient(
			Model model,
			@PathVariable int id,
			@Valid @ModelAttribute Ingredient ingredient,
			BindingResult bindingResult
		) {
		
		
		if (bindingResult.hasErrors()) {
			
			// system error print through lambda expression
			bindingResult.getAllErrors().forEach( (er) -> System.err.println("Errore: " + er.getDefaultMessage())  );
			
			model.addAttribute("ingredient", ingredient);
			model.addAttribute("errors", bindingResult);
			
			return "ingredient-update";
		}
		
		ingredientService.save(ingredient);
		
		return "redirect:/";
	}
	
	
	
	@GetMapping("/ingredients/delete/{id}")
	public String deleteIngredient(
			@PathVariable int id
		) {
		
		Optional<Ingredient> ingredientOpt = ingredientService.findById(id);
		Ingredient selectedIngredient = ingredientOpt.get();
		ingredientService.deleteIngredient(selectedIngredient);
		
		return "redirect:/ingredients/list";
	}
	
}
