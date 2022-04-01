package lab.common.commands;

import java.util.Map;
import java.util.stream.Collectors;

import lab.common.data.Person;
import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class GroupCountingByPassportID extends CollectionCommand {

    public GroupCountingByPassportID(IOManager io, PersonCollectionManager manager) {
        super(io, manager);
    }

    public GroupCountingByPassportID(PersonCollectionManager manager) {
        super(manager);
    }

    @Override
    public CommandResponse execute(String arg) {
        Map<String, Long> groupCounting = getManager().groupCounting(Person::getPassportID);
        return new CommandResponse(CommandResult.SUCCESS,
                groupCounting.entrySet().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "GroupCountingByPassportID";
    }

    @Override
    public String getMan() {
        return "group_counting_by_passport_id : сгруппировать элементы коллекции по значению поля passportID, вывести количество элементов в каждой группе";
    }
}
