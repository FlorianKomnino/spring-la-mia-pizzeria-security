package org.java.demo.pojo;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Pizza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min = 3, max = 250, message = "Il nome deve essere composto da un minimo di 3 a un massimo di 250 caratteri.")
	private String name;
	@Size(min = 3, max = 250, message = "La descirzione deve essere composta da un minimo di 3 a un massimo di 250 caratteri.")
	private String description;
	@Size(min = 3, max = 250, message = "La descirzione deve essere composta da un minimo di 3 a un massimo di 250 caratteri.")
	private String imgUrl;
	@Min(value = 100, message = "Il costo deve essere di almeno 100 centesimi")
	private Integer priceInCents;
	
	@OneToMany(mappedBy = "pizza")
	private List<SpecialOffer> SpecialOffer;
	
	@ManyToMany
	private List<Ingredient> ingredients;
	
	
	public Pizza() { }
	public Pizza(String name, String description, String imgUrl, Integer priceInCents, Ingredient... ingredients) {
		
		setName(name);
		setDescription(description);
		setImgUrl(imgUrl);
		setPriceInCents(priceInCents);
		
		setIngredients(ingredients);
	}

	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setIngredients(Ingredient[] ingredient) {
		
		setIngredients(Arrays.asList(ingredient));
	}

	public List<SpecialOffer> getSpecialOffer() {
		return SpecialOffer;
	}
	public void setSpecialOffer(List<SpecialOffer> specialOffer) {
		SpecialOffer = specialOffer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(Integer priceInCents) {
		this.priceInCents = priceInCents;
	}
	public Float priceInFloat() {
		float formattedPrice = getPriceInCents() / (float) 100;
		return formattedPrice;
	}
	
	@Override
	public String toString() {
		
		return "\n[" + id + "]"
				+ "\nNome: " + name
				+ "\nDescrizione: " + getDescription()
				+ "\nPrezzo: " + priceInFloat()
				+ "\n-------------------------";
	}
}