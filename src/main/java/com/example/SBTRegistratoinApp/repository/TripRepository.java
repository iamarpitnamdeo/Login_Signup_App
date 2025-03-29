package com.example.SBTRegistratoinApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SBTRegistratoinApp.entity.TripDtls;

public interface TripRepository extends JpaRepository<TripDtls,Long>{

	 List<TripDtls> findByUser_Id(Integer userId);
	 List<TripDtls> findBySourceStateAndDestinationState(String sourceState,String destinationState);
	 // Scenario 2: Search by sourceState, sourceCity, destinationState, destinationCity
//	    List<TripDtls> findBySourceStateAndSourceCityAndDestinationStateAndDestinationCity(
//	        String sourceState, String sourceCity, String destinationState, String destinationCity);
	 // Scenario 2: Enforce strict match of all four fields
	    @Query("SELECT t FROM TripDtls t WHERE t.sourceState = :sourceState " +
	           "AND t.sourceCity = :sourceCity " +
	           "AND t.destinationState = :destinationState " +
	           "AND t.destinationCity = :destinationCity")
	    List<TripDtls> findTripsByExactStateAndCity(
	        @Param("sourceState") String sourceState, 
	        @Param("sourceCity") String sourceCity, 
	        @Param("destinationState") String destinationState, 
	        @Param("destinationCity") String destinationCity);
	    // Scenario 3: Search by sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode
	    List<TripDtls> findBySourceStateAndSourceCityAndSourcePincodeAndDestinationStateAndDestinationCityAndDestinationPincode(
	        String sourceState, String sourceCity, String sourcePincode, String destinationState, String destinationCity, String destinationPincode); 
}
