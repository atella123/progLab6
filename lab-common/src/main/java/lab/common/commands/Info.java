package lab.common.commands;

import lab.common.data.PersonCollectionManager;

public final class Info extends CollectionCommand {

    public Info() {
        super();
    }

    public Info(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
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

    @Override
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
    }
}
