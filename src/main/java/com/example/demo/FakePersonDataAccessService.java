package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

  private final static List<Person> DB = new ArrayList<>();

  @Override
  public List<Person> getPeople() {
    return DB;
  }

  @Override
  public UUID addPerson(UUID id, Person person) {
    DB.add(new Person(id, person.getName(), person.getAge()));
    return id;
  }

  @Override
  public Optional<Person> getPerson(UUID id) {
    return DB
        .stream()
        .filter(person -> person.getId().equals(id))
        .findFirst();
  }

  @Override
  public void deletePerson(UUID id) {
    getPerson(id).ifPresent(DB::remove);
  }

  @Override
  public void updatePerson(UUID id, Person personUpdate) {
    getPerson(id).ifPresent(existingPerson -> {
      int indexOf = DB.indexOf(existingPerson);
      if (indexOf >= 0) {
        DB.set(indexOf, new Person(id, personUpdate.getName(), personUpdate.getAge()));
      }
    });
  }
}
