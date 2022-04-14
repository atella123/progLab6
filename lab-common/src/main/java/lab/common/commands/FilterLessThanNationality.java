package lab.common.commands;

import java.util.stream.Collectors;

import lab.common.data.Country;
import lab.common.data.PersonCollectionManager;

public final class FilterLessThanNationality extends CollectionCommand {

    public FilterLessThanNationality() {
        super();
    }

    public FilterLessThanNationality(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(Object... args) {
        if (!isExecutableInstance) {
            return new CommandResponse(CommandResult.ERROR, "Execute called on unexecutable instance");
        }
        if (!isVaildArgument(args)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        Country country = (Country) args[0];
        return new CommandResponse(CommandResult.SUCCESS,
                getManager().filter(person -> person.getNationality().compareTo(country) < 0)
                        .map(Object::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "FilterLessThanNationality";
    }

    @Override
    public boolean isVaildArgument(Object... args) {
        return args.length > 0 && args[0] instanceof Country;
    }

    @Override
    public String getMan() {
        return "filter_less_than_nationality nationality : вывести элементы, значение поля nationality которых меньше заданного";
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[] {
                Country.class };
    }
}
