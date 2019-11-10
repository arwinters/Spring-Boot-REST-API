package com.example.service;

import com.example.model.Server;
import com.example.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ServerService {

	@Autowired
	ServerRepository serverRepository;
	public void save(Server server) {
		serverRepository.save(server);
	}

	public Server findById(Long id){
		return serverRepository.findById(id).orElseThrow(() ->
				new NoSuchElementException("Server does not exist" + id)
		);
	}

	@Cacheable("servers")
	public List<Server> getAllPlayers(){
		return serverRepository.findAll();
	}


	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {
		return ex.getMessage();
	}

}
