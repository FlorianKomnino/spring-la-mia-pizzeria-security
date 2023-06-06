package org.java.demo.pojo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 250, message = "Il nome deve essere composto da un minimo di 3 a un massimo di 250 caratteri.")
	private String name;
	private String imgUrl;
	
	@ManyToMany(mappedBy = "ingredients")
	private List<Pizza> pizzas;
	
	public Ingredient() { }
	public Ingredient(String name) {
		
		setName(name);
	}
	public Ingredient(String name, String imgUrl) {
		
		this(name);
		setImgUrl(imgUrl);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public void addPizza(Pizza pizza) {
		getPizzas().add(pizza);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ID [" + getId() + "] - Nome: "
				+ getName();
	}

	
}
