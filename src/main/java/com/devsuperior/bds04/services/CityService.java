package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll(){
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());		
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CityDTO(entity);
	}

	private void copyDtoToEntity(CityDTO dto, City entity) {
		
		entity.setName(dto.getName());
		
		entity.getEvents().clear();
		for (EventDTO evtDto : dto.getEvents()) {
			Event event = eventRepository.getOne(evtDto.getId());
			entity.getEvents().add(event);
		}
	}

}
