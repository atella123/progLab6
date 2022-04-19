package lab.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import lab.common.commands.Command;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.io.IOManager;
import lab.common.io.Reader;
import lab.common.io.Writter;

public abstract class CommandRunner<R, C> {

    private static final int HISTORY_SIZE = 11;
    private IOManager<R, CommandResponse> io;
    private final CommandManager<C> commandManager;
    private ArrayList<Command> history = new ArrayList<>(HISTORY_SIZE);
    private ArgumentParser<Object> argumentParser;

    public CommandRunner(CommandManager<C> commandManager, ArgumentParser<Object> argumentParser,
            IOManager<R, CommandResponse> io) {
        this.commandManager = commandManager;
        this.argumentParser = argumentParser;
        this.io = io;
    }

    public abstract Command parseCommand(R arg);

    public abstract Object[] parseArgumentsFromReadedObject(R arg);

    public void run() {
        CommandResponse resp;
        do {
            resp = runNextCommmand();
            io.write(resp);
        } while (!resp.getResult().equals(CommandResult.END) && !resp.getResult().equals(CommandResult.NO_INPUT));
    }

    public CommandResponse runNextCommmand() {
        R nextCommandWithArgumnets;
        nextCommandWithArgumnets = io.read();
        if (Objects.isNull(nextCommandWithArgumnets)) {
            return new CommandResponse(CommandResult.NO_INPUT);
        }
        return runCommand(nextCommandWithArgumnets);
    }

    public void writeCommandResponse(CommandResponse response) {
        io.write(response);
    }

    public CommandResponse runCommand(R commandWithArgs) {
        Command command = parseCommand(commandWithArgs);
        if (Objects.nonNull(command)) {
            Object[] arguments = parseArguments(command, parseArgumentsFromReadedObject(commandWithArgs));
            return runCommand(command, arguments);
        }
        return new CommandResponse(CommandResult.COMMAND_NOT_FOUND, "Unknown command");
    }

    public CommandResponse runCommand(Command cmd, Object... args) {
        if (history.size() > HISTORY_SIZE) {
            history.remove(HISTORY_SIZE);
        }
        history.add(cmd);
        return cmd.execute(args);
    }

    public Object[] parseArguments(Command command, Object[] argumentsToParse) {
        Class<?>[] argumentClasses = command.getArgumentClasses();
        ArrayList<Object> arguments = new ArrayList<>(argumentClasses.length);
        arguments.addAll(Arrays.asList(argumentsToParse));
        if (argumentClasses.length > argumentsToParse.length) {
            arguments.addAll(Arrays.asList(new Object[argumentClasses.length - argumentsToParse.length]));
        }
        for (int i = 0; i < argumentClasses.length; i++) {
            Object nextArg = argumentParser.convert(argumentClasses[i], arguments.get(i));
            if (Objects.isNull(nextArg)) {
                return new Object[0];
            }
            arguments.set(i, nextArg);
        }
        return arguments.toArray();
    }

    public Collection<Command> getHistory() {
        return history;
    }

    public IOManager<R, CommandResponse> getIO() {
        return io;
    }

    public void setIO(IOManager<R, CommandResponse> newIO) {
        this.io = newIO;
    }

    public void setIO(Reader<R> reader, Writter<CommandResponse> writter) {
        this.io = new IOManager<>(reader, writter);
    }

    public Reader<R> getReader() {
        return io.getReader();
    }

    public void setReader(Reader<R> reader) {
        io.setReader(reader);
    }

    public Writter<CommandResponse> getWritter() {
        return io.getWritter();
    }

    public void setWritter(Writter<CommandResponse> writter) {
        io.setWritter(writter);
    }

    public CommandManager<C> getCommandManager() {
        return commandManager;
    }

}
