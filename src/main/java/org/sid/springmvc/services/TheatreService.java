
package org.sid.springmvc.services;

import org.sid.springmvc.dao.*;
import org.sid.springmvc.entities.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class TheatreService implements ITheatreService{
	@Autowired
	private TheatreRepository theatreRepository;
	@Autowired
	private PlaceRepository placeRepository;
	
	@Override
	public void saveTheatre(Theatre theatre) {
		Theatre t = theatreRepository.save(theatre);
		for(int i=1;i<=t.getNombrePlacesVIP();i++) {
			placeRepository.save(new Place(i,true,t));
		}
		for(int i=t.getNombrePlacesVIP()+1;i<=t.getNombrePlaces();i++) {
			placeRepository.save(new Place(i,false,t));
		}
	}
}
