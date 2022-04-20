package lab.common.data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonCollectionManager {

    private final LocalDate initDate;
    private Collection<Person> collection;
    private final TreeSet<Integer> idSet = new TreeSet<>();

    public PersonCollectionManager() {
        idSet.add(0);
        initDate = LocalDate.now();
    }

    public PersonCollectionManager(Collection<Person> collection) {
        initDate = LocalDate.now();
        this.collection = collection;
        idSet.add(0);
        idSet.addAll(collection.stream().map(Person::getID).collect(Collectors.toSet()));
    }

    public void add(Person person) {
        if (idSet.contains(person.getID())) {
            person.setID(idSet.last() + 1);
        }
        idSet.add(person.getID());
        this.collection.add(person);
    }

    public boolean addIfAllMatch(Person person, Predicate<Person> predicate) {
        if (collection.stream().allMatch(predicate)) {
            add(person);
            return true;
        }
        return false;
    }

    public Integer nextID() {
        return idSet.last() + 1;
    }

    public void remove(Person person) {
        idSet.remove(person.getID());
        collection.remove(person);
    }

    public Collection<Person> removeIf(Predicate<Person> filter) {
        Collection<Person> toRemove = collection.stream().filter(filter).collect(Collectors.toSet());
        collection.removeAll(toRemove);
        idSet.clear();
        idSet.addAll(collection.stream().map(Person::getID).collect(Collectors.toSet()));
        return toRemove;
    }

    public Optional<Person> removePersonByID(Integer id) {
        Optional<Person> person = getPersonByID(id);
        if (person.isPresent()) {
            remove(person.get());
        }
        return person;
    }

    public Stream<Person> filter(Predicate<Person> predicate) {
        return collection.stream().filter(predicate);
    }

    public Collection<Person> filterCollection(Predicate<Person> predicate) {
        return filter(predicate).collect(Collectors.toSet());
    }

    public Optional<Person> getPersonByID(Integer id) {
        return collection.stream().filter(person -> person.getID().equals(id)).findAny();
    }

    public Collection<Person> getCollection() {
        return Collections.unmodifiableCollection(collection);
    }

    public <T> Map<T, Long> groupCounting(Function<Person, T> function) {
        return collection.stream().collect(Collectors.groupingBy(function, Collectors.counting()));
    }

    public void updatePerson(Person oldPerson, Person newPerson) {
        collection.remove(oldPerson);
        newPerson.setID(oldPerson.getID());
        newPerson.setCreationDate(oldPerson.getCreationDate());
        collection.add(newPerson);
    }

    public Optional<Person> getMinPerson(Comparator<Person> comparator) {
        return collection.stream().min(comparator);
    }

    @SuppressWarnings("rawtypes")
    public Class<? extends Collection> getCollectionType() {
        return collection.getClass();
    }

    public void clear() {
        collection.clear();
        idSet.clear();
        idSet.add(0);
    }

    public void setCollection(Collection<Person> collection) {
        this.collection = collection;
    }

    public LocalDate getInitDate() {
        return initDate;
    }
}
