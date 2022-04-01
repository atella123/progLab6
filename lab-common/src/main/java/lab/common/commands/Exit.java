package lab.common.commands;

public final class Exit extends Command {

    @Override
    public CommandResponse execute(String arg) {
        return new CommandResponse(CommandResult.END);
    }

    @Override
    public String toString() {
        return "Exit";
    }

    public String getMan() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
