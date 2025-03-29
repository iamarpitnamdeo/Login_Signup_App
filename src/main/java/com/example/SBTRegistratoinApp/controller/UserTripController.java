package com.example.SBTRegistratoinApp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SBTRegistratoinApp.entity.TripDtls;
import com.example.SBTRegistratoinApp.entity.UserDtls;
import com.example.SBTRegistratoinApp.repository.TripRepository;
import com.example.SBTRegistratoinApp.repository.UserRepository;
import com.example.SBTRegistratoinApp.service.UserTripService;





@Controller
public class UserTripController {
	
	@Autowired
	UserTripService userTripService;
	
	@Autowired
	TripRepository tripRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bp;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute UserDtls u,HttpSession session) {
		System.out.println(u);
		u.setPassword(bp.encode(u.getPassword()));
		u.setRole("ROLE_USER");
		userRepository.save(u);
		System.out.println(u);
		session.setAttribute("message", "User Registered Successfully..");
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user/profile")
	public String userProfile(Principal p, Model m) {
		String em=p.getName();
		System.out.println("----p.getName()---- : "+em);
		UserDtls u=userRepository.findByEmail(em);
		
		System.out.println("User Id : "+u.getId()+"User Email : "+u.getEmail());
	   
		if (u != null) {
	        List<TripDtls> userTrips = tripRepository.findByUser_Id(u.getId()); // Fetch trips by user ID
	        m.addAttribute("allTrips", userTrips);
	    }		
		m.addAttribute("fullName",u.getFullname());
		m.addAttribute("address",u.getAddress());
		m.addAttribute("email",u.getEmail());
		m.addAttribute("role",u.getRole());
		m.addAttribute("dob",u.getDob());
		return "userProfile";
	}
	
	@GetMapping("/trips/home")
	public String homePage()
	{
		return "home";
	}
	
	// Get global user trips
	@GetMapping(value="/trips")
	public String getAllUserTrips(Model model) {
		List<TripDtls> globalTrips = null;
		try {
			System.out.println("----1 Cursor Debug-----");
			globalTrips = tripRepository.findAll();
			System.out.println("----2 Cursor Debug-----");
			System.out.println("Trips fetched: " + globalTrips.size());
			System.out.println("From Controller"+globalTrips);
		}catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("allTrips",globalTrips);
		return "globalTrips";
	}
	
	@GetMapping("/trips/new")
	public String registerTripForm(Principal p,Model model) {
		
		
		String em=p.getName();
		UserDtls user=userRepository.findByEmail(em);
		
		TripDtls trip = new TripDtls();
		trip.setUser(user);
		
		model.addAttribute("trip", trip);
		model.addAttribute("user",user);
		
		return "addUserTrips";
	}

	@PostMapping("/trips")
	public String addTrip(@ModelAttribute("trip") TripDtls trip,Principal p) {
		String em = p.getName();
	    UserDtls user = userRepository.findByEmail(em);
	    if (user != null) {
	        trip.setUser(user);  // Set the logged-in user before saving the trip
	        tripRepository.save(trip);
	    }
		return "redirect:/user/profile";
	}
	
	@GetMapping("/trips/edit/{tripId}")
	public String editTripForm(@PathVariable Long tripId, Model model) {
			model.addAttribute("trip", tripRepository.findById(tripId).orElse(null));
		
		return "updateUserTrips";
	}

	@PostMapping("/trips/{tripId}")
	public String updateTrip(@PathVariable Long tripId, @ModelAttribute("trip") TripDtls trip, Model model)
			 {
		Optional<TripDtls> existingTrip = tripRepository.findById(tripId);
		if(existingTrip.isPresent()) {
			TripDtls tripToUpdate = existingTrip.get();
			// Update the trip details
            tripToUpdate.setSourceState(trip.getSourceState());
            tripToUpdate.setSourceCity(trip.getSourceCity());
            tripToUpdate.setSourcePincode(trip.getSourcePincode());
            tripToUpdate.setDestinationState(trip.getDestinationState());
            tripToUpdate.setDestinationCity(trip.getDestinationCity());
            tripToUpdate.setDestinationPincode(trip.getDestinationPincode());
            tripToUpdate.setDepartureTime(trip.getDepartureTime());
            tripToUpdate.setAvailableSeats(trip.getAvailableSeats());
            tripRepository.save(tripToUpdate);}		
		return "redirect:/user/profile";
			 }
	
	@RequestMapping(value = "/trips/delete/{tripId}", method = RequestMethod.GET)
	public String deleteTrip(@PathVariable Long tripId) {
		tripRepository.deleteById(tripId);
		return "redirect:/user/profile";
	}
	

    @GetMapping("/selectTrip")
    public String showSearchPage() {
        return "trip_search"; // Returns the Thymeleaf template
    }

    @GetMapping("/searchTrips")
    public String searchTrips(@RequestParam String sourceState, 
                              @RequestParam String destinationState,
                              @RequestParam(required = false) String sourceCity,
                              @RequestParam(required = false) String destinationCity,
                              @RequestParam(required = false) String sourcePincode,
                              @RequestParam(required = false) String destinationPincode,
                              Model model) {

        List<TripDtls> trips;

        // Scenario 3: Search by sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode
        if (sourceCity != null && !sourceCity.isEmpty() && sourcePincode != null && !sourcePincode.isEmpty() &&
            destinationCity != null && !destinationCity.isEmpty() && destinationPincode != null && !destinationPincode.isEmpty()) {
            
            trips = userTripService.findTripsByPincode(sourceState, sourceCity, sourcePincode, destinationState, destinationCity, destinationPincode);
        } 
        // Scenario 2: Search by sourceState, sourceCity, destinationState, and destinationCity
        else if (sourceCity != null && !sourceCity.isEmpty() && destinationCity != null && !destinationCity.isEmpty()) {
            
            trips = userTripService.findTripsByCity(sourceState, sourceCity, destinationState, destinationCity);
        } 
        // Scenario 1: Search by sourceState and destinationState only
        else {
            trips = userTripService.getTripsByStateFilter(sourceState, destinationState);
        }

        model.addAttribute("trips", trips);
        return "trip_search";
    }
	

}	

