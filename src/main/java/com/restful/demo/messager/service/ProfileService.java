package com.restful.demo.messager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.restful.demo.messager.database.DataBaseClass;
import com.restful.demo.messager.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DataBaseClass.getProfiles();
	
	public ProfileService() {
	
		profiles.put("Chetan", new Profile(1L,"Chetan","Chetan", "Parate"));
	}

	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName){
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getProfileName().isEmpty()){
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;	
	}
	
	public Profile removeProfile(String profileName){
		return profiles.remove(profileName);
	}
}
