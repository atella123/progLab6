package lab.common.util;

import java.util.Collection;
import java.util.Map;

import lab.common.commands.*;
import lab.common.io.IOManager;

public class CommandManager {

    private Map<String, Command> commands;

    public CommandManager() {
    }

    public CommandManager(Map<String, Command> commands) {
        this.commands = commands;
    }

    public Command get(String key) {
        return commands.get(key);
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    public void setIO(IOManager io) {
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
        return commands.values().iterator().next().getIO();
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

}
