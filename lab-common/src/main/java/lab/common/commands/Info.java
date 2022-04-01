package lab.common.commands;

import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class Info extends CollectionCommand {

    public Info(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public Info(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(String arg) {
        return new CommandResponse(CommandResult.SUCCESS, new StringBuilder()
                .append("Collection type: ")
                .append(getManager().getCollectionType())
                .append("\nInit date: ")
                .append(getManager().getInitDate().toString())
                .append("\nElement count: ")
                .append(getManager().getCollection().size())
                .toString());
    }

    @Override
    public String toString() {
        return "Info";
    }

    @Override
    public String getMan() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
