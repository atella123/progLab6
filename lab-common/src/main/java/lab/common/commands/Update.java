package lab.common.commands;

import java.util.Objects;
import java.util.Optional;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.exceptions.StringIsNullException;
import lab.common.io.IOManager;
import lab.common.parsers.PersonParser;

public final class Update extends CollectionCommand {

    public Update(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public Update(PersonCollectionManager manager) {
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
        Optional<Person> personToUpdate = getManager().getPersonByID(id);
        if (personToUpdate.isPresent()) {
            try {
                getManager().updatePerson(personToUpdate.get(), PersonParser.parsePerson(getIO()));
                return new CommandResponse(CommandResult.SUCCESS);
            } catch (StringIsNullException e) {
                return new CommandResponse(CommandResult.END, "Person not parsed");
            }
        }
        return new CommandResponse(CommandResult.ERROR, "No element with id (" + arg.replace(" ", "") + ") is present");
    }

    @Override
    public String toString() {
        return "Update";
    }

    @Override
    public String getMan() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
