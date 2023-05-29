package fh.aalen.video.person;

import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<Person, Long> {
}
