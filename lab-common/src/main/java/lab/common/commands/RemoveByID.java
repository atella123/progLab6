package lab.common.commands;

import java.util.Objects;
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
    public CommandResponse execute(String arg) {
        Integer id = null;
        CommandResponse commandResponse = null;
        if (Objects.isNull(arg)) {
            commandResponse = new CommandResponse(CommandResult.ERROR, "Integer type argument needed");
        } else {
            try {
                id = Integer.parseInt(arg.replace(" ", ""));
            } catch (NumberFormatException e) {
                commandResponse = new CommandResponse(CommandResult.ERROR, "Illegal argument");
            }
        }
        if (Objects.nonNull(commandResponse)) {
            return commandResponse;
        }
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
}
