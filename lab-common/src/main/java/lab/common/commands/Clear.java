package lab.common.commands;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;

public final class Clear extends CollectionCommand {

    public Clear() {
        super();
    }

    public Clear(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
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
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
    }
}
