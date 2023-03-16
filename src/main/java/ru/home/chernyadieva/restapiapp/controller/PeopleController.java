package ru.home.chernyadieva.restapiapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.chernyadieva.restapiapp.model.Person;
import ru.home.chernyadieva.restapiapp.service.PeopleService;

import java.util.List;

/**
 * Класс представления данных (не html представление)
 */
@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll(); //Jackson автоматически конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public Person getPersonFromId(@PathVariable(value = "id") int id) {
        return peopleService.findById(id); //Jackson автоматически конвертирует этот объект в JSON
    }
}