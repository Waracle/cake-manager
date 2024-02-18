package com.waracle.cakemgr.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.waracle.cakemgr.dao.CakeRepository;
import com.waracle.cakemgr.model.CakeEntity;

/**
 * We should use consumes=MediaType.APPLICATION_JSON_VALUE)
 * V1 was not fussy about enforcing Content-Type
 * We should not break existing API without explicit mandate
 */
@RestController
@RequestMapping(path="cakes", consumes={MediaType.ALL_VALUE})
public class CakeController {

	private final CakeRepository repo;

	@Autowired
	public CakeController(CakeRepository repo) {
		this.repo = repo;
	}

	@GetMapping("/{id}")
	public CakeEntity getById(@PathVariable int id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cake Not Found"));
	}
	
	@GetMapping
    public List<CakeEntity> list() {
		return repo.findAll();
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CakeEntity create(@RequestBody CakeEntity cake) {
		return repo.saveAndFlush(cake);
	}

	@PatchMapping("/{id}")
	public CakeEntity update(@PathVariable int id, @RequestBody CakeEntity cake) {
		cake.setId(id);
		return repo.saveAndFlush(cake);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable int id) {
		repo.deleteById(id);
	}
}
