package lab.common.commands;

public final class Exit extends Command {

    @Override
    public CommandResponse execute(Object... args) {
        return new CommandResponse(CommandResult.END);
    }

    @Override
    public String toString() {
        return "Exit";
    }

    public String getMan() {
        return "exit : завершить программу (без сохранения в файл)";
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
