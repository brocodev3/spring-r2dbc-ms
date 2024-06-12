package com.rithub.r2dbc.service;

import com.rithub.r2dbc.model.PersonDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Flux<PersonDTO> getAllPersons();

    Mono<PersonDTO> getPersonById(Long id);

    Mono<PersonDTO> createPerson(PersonDTO personDTO);

    Mono<PersonDTO> updatePerson(Long id, PersonDTO personDTO);

    Mono<Void> deletePerson(Long id);
}
