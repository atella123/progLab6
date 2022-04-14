package lab.common.commands;

public abstract class Command {

    protected final boolean isExecutableInstance;

    public Command() {
        isExecutableInstance = false;
    }

    protected Command(boolean isExecutableInstance) {
        this.isExecutableInstance = isExecutableInstance;
    }

    public abstract boolean isVaildArgument(Object... args);

    public abstract Class<?>[] getArgumentClasses();

    public abstract CommandResponse execute(Object... args);

    public abstract String getMan();

    @Override
    public String toString() {
        return "Command";
    }

    public boolean isExecutableInstance() {
        return isExecutableInstance;
    }
}
