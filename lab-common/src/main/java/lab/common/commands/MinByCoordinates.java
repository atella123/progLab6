package lab.common.commands;

import java.util.Optional;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;

public final class MinByCoordinates extends CollectionCommand {

    public MinByCoordinates() {
        super();
    }

    public MinByCoordinates(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        Optional<Person> minPerson = getManager()
                .getMinPerson((person1, person2) -> person1.getCoordinates().compareTo(person2.getCoordinates()));
        if (minPerson.isPresent()) {
            return new CommandResponse(CommandResult.SUCCESS, minPerson.get().toString());
        }
        return new CommandResponse(CommandResult.ERROR, "Collection is empty");
    }

    @Override
    public String getMan() {
        return "min_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является минимальным";
    }

    @Override
    public String toString() {
        return "MinByCoordinates";
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
    }
}
