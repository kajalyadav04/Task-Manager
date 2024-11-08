package com.app.service;

import java.util.Collection;
import java.util.Map;
import com.app.model.Profile;

public interface ProfileService {

	public Map<String, Object> createProfile(Profile profile);
	public Map<String, Object> getProfileById(Long id);
	public Map<String, Object> getProfileByEmail(String email);
	public Profile getProfileOnlyByEmail(String email);
	public Map<String, Object> getAllProfiles();
}
