package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class RemoveGreater extends CollectionCommand {

    public RemoveGreater(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public RemoveGreater(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgumnet(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Person p = (Person) args[0];
        return new CommandResponse(CommandResult.SUCCESS, new Person[0],
                getManager().removeIf(person -> p.compareTo(person) < 0).toArray(new Person[0]));
    }

    @Override
    public String toString() {
        return "RemoveGreater";
    }

    @Override
    public String getMan() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
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
