package fh.aalen.video.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping("/")
    public List<Person> getPersons() {
        return service.getPersons();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return service.getPerson(id);
    }

    @PostMapping("/")
    public void addPerson(@RequestBody Person person) {
        service.addPerson(person);
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable Long id, @RequestBody Person person) {
        service.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
    }

}
