package ru.home.chernyadieva.restapiapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.chernyadieva.restapiapp.entity.Person;
import ru.home.chernyadieva.restapiapp.repository.PeopleRepository;
import ru.home.chernyadieva.restapiapp.util.exception.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Класс взаимодействия с БД по запросам с помощью репозитория JPA
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
}
