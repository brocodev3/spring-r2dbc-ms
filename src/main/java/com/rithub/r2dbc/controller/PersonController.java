package com.rithub.r2dbc.controller;

import com.rithub.r2dbc.model.PersonDTO;
import com.rithub.r2dbc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Flux<PersonDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PersonDTO>> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
            .map(personDto -> ResponseEntity.ok().body(personDto))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<PersonDTO>> createPerson(@RequestBody PersonDTO personDto) {
        return personService.createPerson(personDto)
            .map(createdPerson -> ResponseEntity.status(HttpStatus.CREATED).body(createdPerson));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PersonDTO>> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDto) {
        return personService.updatePerson(id, personDto)
            .map(updatedPerson -> ResponseEntity.ok().body(updatedPerson))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id)
            .then(Mono.just(ResponseEntity.noContent().<Void>build()))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}