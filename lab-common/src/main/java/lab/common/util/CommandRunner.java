package lab.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.io.IOManager;

public class CommandRunner {

    private static final int HISTORY_SIZE = 11;
    private IOManager io;
    private CommandManager commands;
    private ArrayList<Command> history = new ArrayList<>(HISTORY_SIZE);
    private ArgumentParser argumentParser;

    public CommandRunner(CommandManager commands, ArgumentParser argumentParser) {
        this.io = new IOManager();
        commands.setIO(io);
        this.commands = commands;
        this.argumentParser = argumentParser;
    }

    public CommandRunner(IOManager io, CommandManager commands, ArgumentParser argumentParser) {
        this.io = io;
        commands.setIO(io);
        this.commands = commands;
        this.argumentParser = argumentParser;
    }

    // @TODO divide command runned into servercommandrunner and clientcommnadrunner
    public void run() {
        String nextLine;
        CommandResponse resp;
        do {
            nextLine = io.readLine();
            if (Objects.isNull(nextLine)) {
                return;
            }
            resp = runCommand(nextLine);
            if (resp.hasPrintableResult()) {
                io.write(resp.getMessage());
            }
        } while (!resp.getResult().equals(CommandResult.END));
    }

    public CommandResponse runCommand(String commandWithArgs) {
        Command command = parseCommand(commandWithArgs);
        if (Objects.nonNull(command)) {
            return runCommand(command, parseCommandArguments(command, parseArguments(commandWithArgs)));
        }
        return new CommandResponse(CommandResult.ERROR, "Unknown command");
    }

    public CommandResponse runCommand(Command cmd, Object[] args) {
        if (history.size() == HISTORY_SIZE) {
            history.remove(HISTORY_SIZE);
        }
        history.add(cmd);
        return cmd.execute(args);
    }

    public Collection<Command> getHistory() {
        return history;
    }

    public Command parseCommand(String arg) {
        String cmd = arg.split("\\s+", 2)[0].trim();
        return commands.get(cmd);
    }

    public String[] parseArguments(String arg) {
        String[] splittedString = arg.trim().split("\\s+");
        return Arrays.copyOfRange(splittedString, 1, splittedString.length);
    }

    public Object[] parseCommandArguments(Command command, String[] argumentsToParse) {
        Class<?>[] argumentClasses = command.getArgumentClasses();
        ArrayList<Object> arguments = new ArrayList<>(argumentClasses.length);
        for (int i = 0; i < argumentClasses.length; i++) {
            arguments.add(argumentParser.convert(argumentClasses[i], argumentsToParse[i]));
        }
        return arguments.toArray();
    }

    public IOManager getIO() {
        return io;
    }

    public CommandManager getCommandManager() {
        return commands;
    }

    public void setIO(IOManager newIO) {
        this.io = newIO;
    }
}
