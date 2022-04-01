package lab.common.commands;

import java.util.Objects;
import java.util.stream.Collectors;

import lab.common.data.Country;
import lab.common.data.PersonCollectionManager;
import lab.common.util.EnumUtil;
import lab.common.io.IOManager;

public final class FilterLessThanNationality extends CollectionCommand {

    public FilterLessThanNationality(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public FilterLessThanNationality(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(String arg) {
        if (Objects.isNull(arg)) {
            return new CommandResponse(CommandResult.ERROR, "Country type argument needed");
        }
        Country country;
        String formattedArg = arg.trim().toUpperCase();
        if (!EnumUtil.isEnumValue(formattedArg, Country.class)) {
            return new CommandResponse(CommandResult.ERROR, "Illegal argument");
        }
        country = Country.valueOf(formattedArg);
        return new CommandResponse(CommandResult.SUCCESS,
                getManager().filter(person -> person.getNationality().compareTo(country) < 0)
                        .map(Object::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "FilterLessThanNationality";
    }

    public String getMan() {
        return "filter_less_than_nationality nationality : вывести элементы, значение поля nationality которых меньше заданного";
    }
}
