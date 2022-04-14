package lab.common.commands;

import java.util.stream.Collectors;

import lab.common.data.PersonCollectionManager;

public final class Show extends CollectionCommand {

    public Show() {
        super();
    }

    public Show(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Person manager elements:\n");
        stringBuilder
                .append(getManager().getCollection().stream().map(Object::toString).collect(Collectors.joining("\n")));
        return new CommandResponse(CommandResult.SUCCESS, stringBuilder.toString());
    }

    @Override
    public String toString() {
        return "Show";
    }

    @Override
    public String getMan() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
