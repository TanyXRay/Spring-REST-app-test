package ru.home.chernyadieva.restapiapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.chernyadieva.restapiapp.entity.Person;
import ru.home.chernyadieva.restapiapp.service.PeopleService;
import ru.home.chernyadieva.restapiapp.util.PersonErrorResponse;
import ru.home.chernyadieva.restapiapp.util.exception.PersonNotFoundException;

import java.time.LocalDateTime;
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

    /**
     * Метод возврата ответа ошибки при несуществующем человеке с таким id
     * @param e
     * @return
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse("Person with this id was not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}