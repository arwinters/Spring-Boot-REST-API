package com.example.web.controller;

import com.example.model.Server;
import com.example.repository.ServerRepository;
import com.example.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServerController {
	@Autowired
	ServerService serverService;

	@Autowired
	ServerRepository serverRepository ;

	@GetMapping("/api/server/{id}")
	public PagedResources<Server> getServer(@PathVariable("id") Long id, Pageable pageable ,
												  PagedResourcesAssembler pagedResourcesAssembler) {
		Page<Server> serverPage = serverRepository.findById(id,pageable);
		PagedResources<Server>result = pagedResourcesAssembler.toResource(serverPage);
		return result;
	}

	@GetMapping("/api/servers/")
	public ResponseEntity<List<Server>> getAllServers() {
		ArrayList<Server >cricketersList = (ArrayList<Server>) serverService.getAllPlayers();
		return new ResponseEntity<List<Server>>(cricketersList, HttpStatus.OK);
	}

	@PostMapping("/api/server/")
	@CacheEvict(value = "servers", allEntries=true)
	public ResponseEntity<Server> addServer(@RequestBody Server server) {
		System.out.print(server);
		Server sServer = new Server();
		sServer.setName(server.getName());
		serverRepository.save(sServer);
		return new ResponseEntity<Server>(server, HttpStatus.OK);
	}

	@PutMapping("/api/server/{id}")
	@CacheEvict(value = "servers", allEntries=true)
	public ResponseEntity<Server> updateServer(@PathVariable("id") Long id, @RequestBody Server server) {
		Server sServer = serverService.findById(id);
		sServer.setName(server.getName());
		serverRepository.save(sServer);
		return new ResponseEntity<Server>(server, HttpStatus.OK);
	}

	@CacheEvict(value = "servers", allEntries=true)
	@DeleteMapping("/api/server/{id}")
	public ResponseEntity<String> deleteServer(@PathVariable("id") Long id) {
		Server sServer = serverService.findById(id);
		serverRepository.delete(sServer);
		return new ResponseEntity<String>("server removed", HttpStatus.OK);
	}
}
