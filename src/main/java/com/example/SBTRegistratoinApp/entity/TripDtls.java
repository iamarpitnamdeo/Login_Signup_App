package com.example.SBTRegistratoinApp.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class TripDtls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserDtls user;
    
    private String sourceState;
    private String sourceCity;
    private String sourcePincode;
    
    private String destinationState;
    private String destinationCity;
    private String destinationPincode;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;
    private int availableSeats;
    
	public TripDtls() {
		super();
	}

	public TripDtls(Long id, UserDtls user, String sourceState, String sourceCity, String sourcePincode,
			String destinationState, String destinationCity, String destinationPincode, LocalDateTime departureTime,
			int availableSeats) {
		super();
		this.id = id;
		this.user = user;
		this.sourceState = sourceState;
		this.sourceCity = sourceCity;
		this.sourcePincode = sourcePincode;
		this.destinationState = destinationState;
		this.destinationCity = destinationCity;
		this.destinationPincode = destinationPincode;
		this.departureTime = departureTime;
		this.availableSeats = availableSeats;
	}

	public TripDtls(UserDtls user, String sourceState, String sourceCity, String sourcePincode, String destinationState,
			String destinationCity, String destinationPincode, LocalDateTime departureTime, int availableSeats) {
		super();
		this.user = user;
		this.sourceState = sourceState;
		this.sourceCity = sourceCity;
		this.sourcePincode = sourcePincode;
		this.destinationState = destinationState;
		this.destinationCity = destinationCity;
		this.destinationPincode = destinationPincode;
		this.departureTime = departureTime;
		this.availableSeats = availableSeats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDtls getUser() {
		return user;
	}

	public void setUser(UserDtls user) {
		this.user = user;
	}

	public String getSourceState() {
		return sourceState;
	}

	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getSourcePincode() {
		return sourcePincode;
	}

	public void setSourcePincode(String sourcePincode) {
		this.sourcePincode = sourcePincode;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getDestinationPincode() {
		return destinationPincode;
	}

	public void setDestinationPincode(String destinationPincode) {
		this.destinationPincode = destinationPincode;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
    
	
    
}
