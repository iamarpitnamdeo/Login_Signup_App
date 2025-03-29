package com.example.SBTRegistratoinApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SBTRegistratoinApp.entity.TripDtls;
import com.example.SBTRegistratoinApp.entity.UserDtls;
import com.example.SBTRegistratoinApp.repository.TripRepository;

@Service
public class UserTripService {
	
	@Autowired
	TripRepository tripRepository;
	
	public TripDtls saveUserTrip(TripDtls tripDtls) {
		TripDtls savedTrip = tripRepository.save(tripDtls);
		return savedTrip;
	}
	public List<TripDtls> findUserTripsById(Integer id){
		//return tripRepository.findAllByUserId(id);
		return tripRepository.findByUser_Id(id);// fetch trips by user ID
	}
	
	public List<TripDtls> findAllTrips(){
		return tripRepository.findAll();
	}
	
	public TripDtls updateUserTrip(Long id, TripDtls tripDtls) {
		Optional<TripDtls> existingTrip = tripRepository.findById(id);
		if(existingTrip.isPresent()) {
			TripDtls tripToUpdate = existingTrip.get();
			// Update the trip details
            tripToUpdate.setSourceState(tripDtls.getSourceState());
            tripToUpdate.setSourceCity(tripDtls.getSourceCity());
            tripToUpdate.setSourcePincode(tripDtls.getSourcePincode());
            tripToUpdate.setDestinationState(tripDtls.getDestinationState());
            tripToUpdate.setDestinationCity(tripDtls.getDestinationCity());
            tripToUpdate.setDestinationPincode(tripDtls.getDestinationPincode());
            tripToUpdate.setDepartureTime(tripDtls.getDepartureTime());
            tripToUpdate.setAvailableSeats(tripDtls.getAvailableSeats());
            return tripRepository.save(tripToUpdate);  // Save the updated trip

		}
		return null;
	}
	public boolean deleteUserTrip(Long id) {
        Optional<TripDtls> trip = tripRepository.findById(id);
        if (trip.isPresent()) {
            tripRepository.deleteById(id);
            return true;
        }
        return false;
    }
	
	public List<TripDtls> getTripsByStateFilter(String sourceState, String destinationState){
		
		return tripRepository.findBySourceStateAndDestinationState(sourceState, destinationState);
	}
	
//	public List<TripDtls> getTripsByStateAndCityFilter(String sourceState, String destinationState,
//			String sourceCity, String destinationCity){
//		return null;
//	}
//	
//	public List<TripDtls> getTripsByStateAndCityAndPincodeFilter(
//			String sourceState, String destinationState,
//			String sourceCity, String destinationCity,
//			String sourcePincode, String destinationPincode){
//		return null;
//	}
	 // Scenario 2: Find by sourceState, sourceCity, destinationState, destinationCity
    public List<TripDtls> findTripsByCity(String sourceState, String sourceCity, String destinationState, String destinationCity) {
        return tripRepository.findTripsByExactStateAndCity(
            sourceState, sourceCity, destinationState, destinationCity);
    }

    // Scenario 3: Find by sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode
    public List<TripDtls> findTripsByPincode(String sourceState, String sourceCity, String sourcePincode, 
                                             String destinationState, String destinationCity, String destinationPincode) {
        return tripRepository.findBySourceStateAndSourceCityAndSourcePincodeAndDestinationStateAndDestinationCityAndDestinationPincode(
            sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode);
    }
}
