package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class AddIfMax extends CollectionCommand {

    public AddIfMax(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public AddIfMax(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgumnet(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Person p = (Person) args[0];
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
    public boolean isVaildArgumnet(Object... args) {
        return args.length > 0 && args[0] instanceof Person;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] { Person.class };
    }
}
