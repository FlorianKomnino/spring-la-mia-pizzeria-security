package org.java.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.java.demo.pojo.Ingredient;
import org.java.demo.pojo.Pizza;
import org.java.demo.pojo.SpecialOffer;
import org.java.demo.service.IngredientService;
import org.java.demo.service.PizzaService;
import org.java.demo.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class PizzaController {

	@Autowired
	PizzaService pizzaService;
	
	@Autowired
	SpecialOfferService specialOfferService;
	
	@Autowired
	IngredientService ingredientService;
		
	@GetMapping("/users/pizzasIndex") 
	public String pizzaPlace(Model model) {
		
		List<Pizza> foundPizzas = pizzaService.findAll();
		model.addAttribute("pizzasRes", foundPizzas);
		return "index";
	}
	
	@GetMapping("/users/pizzas/{id}")
	public String findPizzaById(
			Model model,
			@PathVariable("id") int id
			) {
		Optional<Pizza> optPizza = pizzaService.findById(id);
		Pizza pizza = optPizza.get();
		List<SpecialOffer> pizzaSpecOff = pizza.getSpecialOffer();
		Boolean isSpecOffActive = false;
		for (SpecialOffer specOff : pizzaSpecOff) {
			if ( specOff.getStartingDate().isBefore(LocalDate.now()) && specOff.getEndingDate().isAfter(LocalDate.now())) {
				isSpecOffActive = true;
			}
		}
		List<Ingredient> ingredients = pizza.getIngredients();
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("isOfferActive", isSpecOffActive);
		model.addAttribute("specialOffers", pizzaSpecOff);
		model.addAttribute("singlePizza", pizza);
		
		return "singlePizza";
	}
	
	@PostMapping("/users/pizzas/search")
	public String getPizzaByTitle(Model model, @RequestParam(required = false) String name) {
		
		List<Pizza> foundPizzas = pizzaService.findByName(name);
		model.addAttribute("pizzasRes", foundPizzas);
		model.addAttribute("name", name);
		
		return "index";
	}
	
	@GetMapping("/admin/pizzas/create")
	public String createPizza(Model model) {
		
		List<Ingredient> ingredients = ingredientService.findAll();
		
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("pizza", new Pizza());
		
		return "pizza-create";
	}
	
	@PostMapping("/admin/pizzas/create")
	public String storePizza(
			Model model,
			@Valid @ModelAttribute Pizza pizza,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {

			// system error print through lambda expression
			bindingResult.getAllErrors().forEach( (er) -> System.err.println("Errore: " + er.getDefaultMessage())  );
			
			model.addAttribute("pizza", pizza);
			model.addAttribute("errors", bindingResult);
			
			return "pizza-create";
		}
		
		
		pizzaService.save(pizza);
		
		for (Ingredient ing : pizza.getIngredients()) {
			ing.addPizza(pizza);
			ingredientService.save(ing);
		}
		
		return "redirect:/users/pizzasIndex";
	}
	
	@GetMapping("/admin/pizzas/update/{id}")
	public String editPizza(
			Model model,
			@PathVariable int id
		) {
		
		
		Optional<Pizza> pizzaOpt = pizzaService.findById(id);
		Pizza pizza = pizzaOpt.get();
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients", ingredients);
		model.addAttribute("pizzaToUpdate", pizza);
		
		return "pizza-update";
	}
	
	@PostMapping("/admin/pizzas/update/{id}")
	public String updatePizza(
			Model model,
			@PathVariable int id,
			@Valid @ModelAttribute Pizza pizza,
			BindingResult bindingResult
		) {
		
		
		if (bindingResult.hasErrors()) {
			
			// system error print through lambda expression
			bindingResult.getAllErrors().forEach( (er) -> System.err.println("Errore: " + er.getDefaultMessage())  );
			
			model.addAttribute("pizzaToUpdate", pizza);
			model.addAttribute("errors", bindingResult);
			
			return "pizza-update";
		}
		
		pizzaService.save(pizza);
		
		return "redirect:/pizzasIndex";
	}
	
	@GetMapping("/admin/pizza/special/offer/{id}")
	public String specialOfferCreate(
			Model model,
			@PathVariable int id
			) {
		
		Optional<Pizza> pizzaOpt = pizzaService.findById(id);
		Pizza pizza = pizzaOpt.get();
		model.addAttribute("specialOffer", new SpecialOffer());
		model.addAttribute("pizza", pizza);
		return "special-offer-create";
	}
	
	@PostMapping("/admin/pizza/special/offer")
	public String specialOfferStore(Model model,
			@Valid @ModelAttribute SpecialOffer specialOffer,
			BindingResult bindingResult
			) {
		
		if (bindingResult.hasErrors()) {
			

			// system error print through lambda expression
			bindingResult.getAllErrors().forEach( (er) -> System.err.println("Errore: " + er.getDefaultMessage())  );
			
			Pizza pizza = specialOffer.getPizza();
			model.addAttribute("pizza", pizza);
			model.addAttribute("specialOffer", specialOffer);
			model.addAttribute("errors", bindingResult);
			
			return "special-offer-create";
		}
		
		specialOfferService.save(specialOffer);
		return "redirect:/users/pizzasIndex";
	}
	
	@GetMapping("/admin/pizzas/delete/{id}")
	public String deletePizza(
			@PathVariable int id
		) {
		
		Optional<Pizza> pizzaOpt = pizzaService.findById(id);
		Pizza selectedPizza = pizzaOpt.get();
		pizzaService.deletePizza(selectedPizza);
		
		return "redirect:/users/pizzasIndex";
	}
}
