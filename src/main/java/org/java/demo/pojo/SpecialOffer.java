package org.java.demo.pojo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class SpecialOffer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDate startingDate;
	
	@Future(message = "La data di fine offerta deve essere una data futura")
	private LocalDate endingDate;
	
	@Size(min=3, max=255, message="La lunghezza del titolo deve essere compresa tra 3 e 255 caratteri!")
	private String title;
	
	@Min(value=1, message="La percentuale minima di sconto deve essere 1")
	@Max(value=100, message="La percentuale massima di sconto deve essere 100")
	private Integer discountPercentage;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pizza pizza;
	
	public SpecialOffer() { }
	public SpecialOffer(LocalDate startingDate, LocalDate endingDate, String title, Integer discountPercentage, Pizza pizza) {
		
		setStartingDate(startingDate);
		setEndingDate(endingDate);
		setTitle(title);
		setDiscountPercentage(discountPercentage);
		setPizza(pizza);
	}
	
	@AssertTrue(message="La data di fine offerta deve essere dopo la data di inizio offerta!")
	private boolean isDateOrderCorrect() {
		
		return getStartingDate().isBefore(getEndingDate());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	public LocalDate getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(LocalDate startingDate) {

		this.startingDate = startingDate;
	}

	public LocalDate getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(LocalDate endingDate) {

		this.endingDate = endingDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	};
	
}
