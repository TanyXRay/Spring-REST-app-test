package ru.home.chernyadieva.restapiapp.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.home.chernyadieva.restapiapp.entity.Person;
import ru.home.chernyadieva.restapiapp.service.PeopleService;
import ru.home.chernyadieva.restapiapp.util.PersonErrorResponse;
import ru.home.chernyadieva.restapiapp.util.exception.PersonNotCreatedException;
import ru.home.chernyadieva.restapiapp.util.exception.PersonNotFoundException;

import javax.naming.Binding;
import javax.print.DocFlavor;
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

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
           StringBuilder errorMsg = new StringBuilder();
           List<FieldError> errors = bindingResult.getFieldErrors();

          for (FieldError error:errors){
              errorMsg.append(error.getField())
                      .append(" - ")
                      .append(error.getDefaultMessage())
                      .append(";");
          }
          throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод возврата ответа ошибки при несуществующем человеке с таким id
     * @param e
     * @return
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse("Person with this id was not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404
    }

    /**
     * Метод возврата ответа ошибки при невозможности создать человека
     * @param e
     * @return
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse( e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//400
    }
}