package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class Add extends CollectionCommand {

    public Add(PersonCollectionManager manager) {
        super(manager);
    }

    public Add(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isVaildArgumnet(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Person p = (Person) args[0];
        getManager().add(p);
        return new CommandResponse(CommandResult.SUCCESS, new Person[] { p }, new Person[0]);
    }

    @Override
    public String getMan() {
        return "add {element} : добавить новый элемент в коллекцию";
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
