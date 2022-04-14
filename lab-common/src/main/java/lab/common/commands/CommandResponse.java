package lab.common.commands;

import java.io.Serializable;
import java.util.Arrays;

import lab.common.data.Person;

public class CommandResponse implements Serializable {
    private final boolean printableResult;
    private final String message;
    private final CommandResult comandResult;
    private final boolean changedCollection;
    private final Person[] added;
    private final Person[] removed;

    public CommandResponse(CommandResult result) {
        this.printableResult = false;
        this.message = null;
        this.comandResult = result;
        this.changedCollection = false;
        this.added = null;
        this.removed = null;
    }

    public CommandResponse(CommandResult result, Person[] added, Person[] removed) {
        this.printableResult = false;
        this.message = null;
        this.comandResult = result;
        if (added.length != 0 && removed.length != 0) {
            this.changedCollection = true;
            this.added = added;
            this.removed = removed;
        } else {
            this.changedCollection = false;
            this.added = null;
            this.removed = null;
        }
    }

    public CommandResponse(CommandResult result, String message) {
        this.printableResult = true;
        this.message = message;
        this.comandResult = result;
        this.changedCollection = false;
        this.added = null;
        this.removed = null;
    }

    public CommandResponse(CommandResult result, String message, Person[] added, Person[] removed) {
        this.printableResult = true;
        this.message = message;
        this.comandResult = result;
        if (added.length != 0 && removed.length != 0) {
            this.changedCollection = true;
            this.added = added;
            this.removed = removed;
        } else {
            this.changedCollection = false;
            this.added = null;
            this.removed = null;
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

    public boolean hasChangedCollection() {
        return changedCollection;
    }

    public Person[] getAdded() {
        return added;
    }

    public Person[] getRemoved() {
        return removed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int num1 = 1231;
        final int num2 = 1237;
        int result = 1;
        result = prime * result + Arrays.hashCode(added);
        result = prime * result + (changedCollection ? num1 : num2);
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + (printableResult ? num1 : num2);
        result = prime * result + Arrays.hashCode(removed);
        result = prime * result + ((this.comandResult == null) ? 0 : this.comandResult.hashCode());
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
        if (!Arrays.equals(added, other.added)) {
            return false;
        }
        if (changedCollection != other.changedCollection) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (printableResult != other.printableResult) {
            return false;
        }
        if (!Arrays.equals(removed, other.removed)) {
            return false;
        }
        return comandResult == other.comandResult;
    }
}
