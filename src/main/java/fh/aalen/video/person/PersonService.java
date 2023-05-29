package fh.aalen.video.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public List<Person> getPersons() {
        return repository.findAll();
    }

    public Person getPerson(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void addPerson(Person person) {
        repository.save(person);
    }

    public void updatePerson(Person person) {
        repository.save(person);
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }

}
