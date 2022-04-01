package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;
import lab.common.exceptions.StringIsNullException;
import lab.common.parsers.PersonParser;

public final class Add extends CollectionCommand {

    public Add(PersonCollectionManager manager) {
        super(manager);
    }

    public Add(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    @Override
    public CommandResponse execute(String arg) {
        try {
            Person p = PersonParser.parsePerson(getIO());
            getManager().add(p);
            return new CommandResponse(CommandResult.SUCCESS, new Person[] { p }, new Person[0]);
        } catch (StringIsNullException e) {
            return new CommandResponse(CommandResult.END, "Person not parsed");
        }
    }

    @Override
    public String getMan() {
        return "add {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public boolean isVaildArgumnet(Object o) {
        return o instanceof Person;
    }

}
