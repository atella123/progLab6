package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.exceptions.StringIsNullException;
import lab.common.io.IOManager;
import lab.common.parsers.PersonParser;

public final class AddIfMax extends CollectionCommand {

    public AddIfMax(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public AddIfMax(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(String arg) {
        Person p;
        try {
            p = PersonParser.parsePerson(getIO());
        } catch (StringIsNullException e) {
            return new CommandResponse(CommandResult.END);
        }
        if (getManager().addIfAllMatch(p, person -> person.compareTo(p) < 0)) {
            return new CommandResponse(CommandResult.SUCCESS, new Person[] { p }, new Person[0]);
        }
        return new CommandResponse(CommandResult.SUCCESS);
    }

    @Override
    public String toString() {
        return "AddIfMax";
    }

    @Override
    public String getMan() {
        return "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public boolean isVaildArgumnet(Object o) {
        return o instanceof Person;
    }
}
