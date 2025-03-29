package com.example.SBTRegistratoinApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SBTRegistratoinApp.entity.TripDtls;
import com.example.SBTRegistratoinApp.service.UserTripService;

@RestController
@RequestMapping("/api/trips")
public class TravelBuddiesUserRestController {
	
	@Autowired
	UserTripService userTripService;
	
	// add trips registered by user 
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<TripDtls>  registerTrip(@RequestBody TripDtls tripDtls) {
		TripDtls savedTrip = userTripService.saveUserTrip(tripDtls);
		return ResponseEntity.ok(savedTrip);
	}
	
	// fetch user trips by his id
	@RequestMapping(value="/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<TripDtls>>fetUserTripsById(@PathVariable Integer id) {
		List<TripDtls> userTrips = userTripService.findUserTripsById(id);
		if(userTrips.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userTrips);
	}
	// update user trips 
	@RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
	public ResponseEntity<TripDtls> updateUserTrips(@PathVariable Long id, @RequestBody TripDtls tripDtls) {
		TripDtls updatedTrip = userTripService.updateUserTrip(id,tripDtls);
		if(updatedTrip !=null) {
			return ResponseEntity.ok(updatedTrip);
		}
		return ResponseEntity.notFound().build();
	}
	// delete user trips 
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUserTrips(@PathVariable Long id) {
		 boolean isDeleted = userTripService.deleteUserTrip(id);
	        if (isDeleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	}
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<TripDtls>> getAllTrips(){
		List<TripDtls> allTrips = userTripService.findAllTrips();
		return ResponseEntity.ok(allTrips);
	}
	
	@RequestMapping(value="/state",method = RequestMethod.GET)
	public List<TripDtls> getTripsByStateFilter(@RequestParam String sourceState, @RequestParam String destinationState){
		return userTripService.getTripsByStateFilter(sourceState, destinationState);
	}
	
	 // Scenario 2: Search by sourceState, sourceCity, destinationState, destinationCity
    @GetMapping("/searchByCity")
    public List<TripDtls> getTripsByStateAndCityFilter(@RequestParam String sourceState, @RequestParam String sourceCity, 
                                            @RequestParam String destinationState, @RequestParam String destinationCity) {
        return userTripService.findTripsByCity(sourceState, sourceCity, destinationState, destinationCity);
    }

    // Scenario 3: Search by sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode
    @GetMapping("/searchByPincode")
    public List<TripDtls> getTripsByStateAndCityAndPincodeFilter(@RequestParam String sourceState, @RequestParam String sourceCity, @RequestParam String sourcePincode,
                                               @RequestParam String destinationState, @RequestParam String destinationCity, @RequestParam String destinationPincode) {
        return userTripService.findTripsByPincode(sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode);
    }
}
