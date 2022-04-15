package lab.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import lab.common.commands.Command;

public class CommandManager<K> {

    private Map<K, Command> commands;

    public CommandManager() {
    }

    public CommandManager(Map<K, Command> commands) {
        this.commands = commands;
    }

    public void add(K key, Command command) {
        commands.put(key, command);
    }

    public Command get(Object key) {
        return commands.get(key);
    }

    public Command getOrDefault(Object key, Command defaultValue) {
        return commands.getOrDefault(key, defaultValue);
    }

    public void setCommands(Map<K, Command> commands) {
        this.commands = commands;
    }

    public boolean containsValue(Command command) {
        return commands.containsValue(command);
    }

    public boolean containsKey(Object key) {
        return commands.containsKey(key);
    }

    public Collection<Command> getCommands() {
        return Collections.unmodifiableCollection(commands.values());
    }

}
