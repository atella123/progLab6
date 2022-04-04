package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class Clear extends CollectionCommand {

    public Clear(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public Clear(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        Person[] res = getManager().getCollection().stream().toArray(Person[]::new);
        getManager().clear();
        return new CommandResponse(CommandResult.SUCCESS, new Person[0], res);
    }

    @Override
    public String toString() {
        return "Clear";
    }

    public String getMan() {
        return "clear : очистить коллекцию";
    }

    @Override
    public boolean isVaildArgumnet(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] {};
    }
}
