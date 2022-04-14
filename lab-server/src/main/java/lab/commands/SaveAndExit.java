package lab.commands;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.commands.Save;

public class SaveAndExit extends Command {

    private final Save saveCommand;

    public SaveAndExit() {
        super();
        this.saveCommand = null;
    }

    public SaveAndExit(Save saveCommand) {
        super(true);
        this.saveCommand = saveCommand;
    }

    @Override
    public CommandResponse execute(Object... args) {
        saveCommand.execute(args);
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
    public boolean isVaildArgument(Object... args) {
        return true;
    }

    @Override
    public Class<?>[] getArgumentClasses() {
        return new Class<?>[0];
    }

}