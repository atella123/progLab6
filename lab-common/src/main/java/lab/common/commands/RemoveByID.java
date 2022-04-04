package lab.common.commands;

import java.util.Optional;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class RemoveByID extends CollectionCommand {

    public RemoveByID(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public RemoveByID(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgumnet(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Integer id = (Integer) args[0];
        Optional<Person> person = getManager().removePersonByID(id);
        if (person.isPresent()) {
            return new CommandResponse(CommandResult.SUCCESS, new Person[0], new Person[] { person.get() });
        }
        return new CommandResponse(CommandResult.ERROR, "No such element");

    }

    @Override
    public String toString() {
        return "RemoveByID";
    }

    @Override
    public String getMan() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public boolean isVaildArgumnet(Object... args) {
        return args.length > 0 && args[0] instanceof Integer;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] { Integer.class };
    }
}
