package lab.common.commands;

import java.util.Optional;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;

public final class Update extends CollectionCommand {

    public Update() {
        super();
    }

    public Update(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        if (!isVaildArgument(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Integer id = (Integer) args[0];
        Optional<Person> personToUpdate = getManager().getPersonByID(id);
        if (personToUpdate.isPresent()) {
            getManager().updatePerson(personToUpdate.get(), (Person) args[1]);
            return new CommandResponse(CommandResult.SUCCESS);
        }
        return new CommandResponse(CommandResult.ERROR, "No element with id (" + id + ") is present");
    }

    @Override
    public String toString() {
        return "Update";
    }

    @Override
    public String getMan() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        if (args.length < 2) {
            return false;
        }
        return args[0] instanceof Integer && args[1] instanceof Person;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] {
                Integer.class, Person.class };
    }
}
