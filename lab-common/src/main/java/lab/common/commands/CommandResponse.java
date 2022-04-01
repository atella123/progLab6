package lab.common.commands;

import lab.common.data.Person;

public class CommandResponse {
    private final boolean printableResult;
    private final String message;
    private final CommandResult result;
    private final boolean changedCollection;
    private final Person[] added;
    private final Person[] removed;

    public CommandResponse(CommandResult result) {
        this.printableResult = false;
        this.message = null;
        this.result = result;
        this.changedCollection = false;
        this.added = null;
        this.removed = null;
    }

    public CommandResponse(CommandResult result, Person[] added, Person[] removed) {
        this.printableResult = false;
        this.message = null;
        this.result = result;
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
        this.result = result;
        this.changedCollection = false;
        this.added = null;
        this.removed = null;
    }

    public CommandResponse(CommandResult result, String message, Person[] added, Person[] removed) {
        this.printableResult = true;
        this.message = message;
        this.result = result;
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
        return result;
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
}
