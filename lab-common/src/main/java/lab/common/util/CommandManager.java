package lab.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import lab.common.commands.*;
import lab.common.io.IOManager;

public class CommandManager {

    private IOManager io;

    private Map<String, Command> commands;

    public CommandManager() {
        this.io = new IOManager();
    }

    public CommandManager(Map<String, Command> commands) {
        this.io = new IOManager();
        this.commands = commands;
        setCommandsIO();
    }

    public CommandManager(IOManager io) {
        this.io = io;
    }

    public CommandManager(Map<String, Command> commands, IOManager io) {
        this.io = io;
        this.commands = commands;
        setCommandsIO();
    }

    public Command get(String key) {
        return commands.get(key);
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void setIO(IOManager io) {
        this.io = io;
    }

    private void setCommandsIO() {
        for (Command i : getCommands()) {
            i.setIO(io);
        }
    }

    public boolean containsValue(Command command) {
        return commands.containsValue(command);
    }

    public boolean containsKey(String key) {
        return commands.containsKey(key);
    }

    public IOManager getIO() {
        return io;
    }

    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }

}
