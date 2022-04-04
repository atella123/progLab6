package lab.common.commands;

import java.util.stream.Collectors;

import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class Show extends CollectionCommand {

    public Show(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public Show(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
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
    public boolean isVaildArgumnet(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] {};
    }
}
