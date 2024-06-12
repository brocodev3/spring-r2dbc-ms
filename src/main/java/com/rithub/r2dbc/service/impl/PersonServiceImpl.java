package com.rithub.r2dbc.service.impl;

import com.rithub.r2dbc.entity.Person;
import com.rithub.r2dbc.model.PersonDTO;
import com.rithub.r2dbc.repository.PersonRepository;
import com.rithub.r2dbc.service.PersonService;
import com.rithub.r2dbc.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Flux<PersonDTO> getAllPersons() {
        return personRepository.findAll()
            .subscribeOn(Schedulers.boundedElastic())
            .map(this::toDto);
    }

    @Override
    public Mono<PersonDTO> getPersonById(Long id) {
        return personRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public Mono<PersonDTO> createPerson(PersonDTO personDTO) {
        Person person = toEntity(personDTO);
        String randomPassword = PasswordGenerator.generateRandomPassword();
        person.setPassword(randomPassword);  // Set random password
        return personRepository.save(person)
            .map(this::toDto);
    }

    @Override
    public Mono<PersonDTO> updatePerson(Long id, PersonDTO personDTO) {
        return personRepository.findById(id)
            .flatMap(existingPerson -> {
                existingPerson.setName(personDTO.getName());
                existingPerson.setAge(personDTO.getAge());
                // We don't update the password here, for security reasons
                return personRepository.save(existingPerson);
            })
            .map(this::toDto);
    }

    @Override
    public Mono<Void> deletePerson(Long id) {
        return personRepository.deleteById(id);
    }

    private PersonDTO toDto(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setName(person.getName());
        dto.setAge(person.getAge());
        return dto;
    }

    private Person toEntity(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        // No password mapping here, to ensure it's handled securely elsewhere
        return person;
    }
}
