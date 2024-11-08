package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.exception.ProfileNotFoundException;
import com.app.model.Profile;
import com.app.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired private ProfileRepository profileRepository;
	
//	@Autowired private JwtService jwtService;
	
	@Override
	public Map<String, Object> createProfile(Profile profile) {
		Map<String, Object> map = new HashMap<>();
		
		profile.setRole("ROLE_"+profile.getRole().toUpperCase());
		Profile save = profileRepository.save(profile);
		
		if(save!=null) {
			map.put("msg", "User Successfully Created");
			map.put("userData", save);
			map.put("status", "OK");
		}else {
			map.put("msg", "User Creation Failed");
			map.put("status", "Bad-Request");
		}
		return map;		
	}

	@Override
	public Map<String, Object> getProfileById(Long id) {
		Map<String, Object> map = new HashMap<>(); 
		Optional<Profile> optional = profileRepository.findById(id);
		if(optional.isEmpty()) {
			map.put("Message", "User Not Found with id:- "+id);
			map.put("status", "NOT-FOUND");
		} else {
			map.put("userDetails", optional.get());
			map.put("status", "OK");
		}
		return map;
	}

	@Override
	public Map<String, Object> getProfileByEmail(String email) {
		Map<String, Object> map = new HashMap<>(); 
		Optional<Profile> optional = profileRepository.findByEmail(email);
		if(optional.isEmpty()) {
			map.put("Message", "User Not Found with Email:- "+email);
			map.put("status", "NOT-FOUND");
		} else {
			map.put("userDetails", optional.get());
			map.put("status", "OK");
		}
		return map;
	}

	@Override
	public Map<String, Object> getAllProfiles() {
		Map<String, Object> map = new HashMap<>(); 
		List<Profile> list = profileRepository.findAll();
		if(list.isEmpty()) {
			map.put("Message", "Users Not Found");
			map.put("status", "NOT-FOUND");
		} else {
			map.put("users", list);
			map.put("total", list.size());
		}
		return map;
	}

	

	@Override
	public Profile getProfileOnlyByEmail(String email) {
        Optional<Profile> optional = profileRepository.findByEmail(email);
		
		if(optional.isEmpty()) throw new ProfileNotFoundException("User "+email+" Not Found!");
		else {
			Profile profile = optional.get();
			
			return profile;
		}
	}
}
