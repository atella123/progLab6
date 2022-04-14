package lab.common.commands;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import lab.common.data.Person;

public class CommandResponse implements Serializable {
    protected static final Comparator<Person> ALPHABETICAL_COMPARATOR = (p1, p2) -> p1.getName()
            .compareTo(p2.getName());
    private final boolean printableResult;
    private final String message;
    private final CommandResult comandResult;
    private final boolean collectionToPrint;
    private final Person[] collectionResult;

    public CommandResponse(CommandResult result) {
        this.printableResult = false;
        this.message = null;
        this.comandResult = result;
        this.collectionToPrint = false;
        this.collectionResult = null;
    }

    public CommandResponse(CommandResult result, Person[] collectionResult) {
        this.printableResult = false;
        this.message = null;
        this.comandResult = result;
        if (collectionResult.length != 0) {
            this.collectionToPrint = true;
            Arrays.sort(collectionResult, ALPHABETICAL_COMPARATOR);
            this.collectionResult = collectionResult;
        } else {
            this.collectionToPrint = false;
            this.collectionResult = null;
        }
    }

    public CommandResponse(CommandResult result, String message) {
        this.printableResult = true;
        this.message = message;
        this.comandResult = result;
        this.collectionToPrint = false;
        this.collectionResult = null;
    }

    public CommandResponse(CommandResult result, String message, Person[] collectionResult) {
        this.printableResult = true;
        this.message = message;
        this.comandResult = result;
        if (collectionResult.length != 0) {
            this.collectionToPrint = true;
            Arrays.sort(collectionResult, ALPHABETICAL_COMPARATOR);
            this.collectionResult = collectionResult;
        } else {
            this.collectionToPrint = false;
            this.collectionResult = null;
        }
    }

    public boolean hasPrintableResult() {
        return printableResult;
    }

    public String getMessage() {
        return message;
    }

    public CommandResult getResult() {
        return comandResult;
    }

    public boolean hasCollectionToPrint() {
        return collectionToPrint;
    }

    public Person[] getCollection() {
        return collectionResult;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int n1 = 1231;
        final int n2 = 1237;
        int result = 1;
        result = prime * result + Arrays.hashCode(collectionResult);
        result = prime * result + (collectionToPrint ? n1 : n2);
        result = prime * result + ((comandResult == null) ? 0 : comandResult.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + (printableResult ? n1 : n2);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CommandResponse other = (CommandResponse) obj;
        if (!Arrays.equals(collectionResult, other.collectionResult)) {
            return false;
        }
        if (collectionToPrint != other.collectionToPrint) {
            return false;
        }
        if (comandResult != other.comandResult) {
            return false;
        }
        if (message == null) {
            return other.message == null;
        } else if (message.equals(other.message)) {
            return true;
        }
        return printableResult == other.printableResult;
    }

}
