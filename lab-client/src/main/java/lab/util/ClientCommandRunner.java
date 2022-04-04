package lab.util;

import lab.common.io.IOManager;
import lab.common.util.ArgumentParser;
import lab.common.util.CommandManager;
import lab.common.util.CommandRunner;

public class ClientCommandRunner extends CommandRunner {

    public ClientCommandRunner(IOManager io, CommandManager commands, ArgumentParser argumentParser) {
        super(io, commands, argumentParser);
    }

    public ClientCommandRunner(CommandManager commands, ArgumentParser argumentParser) {
        super(commands, argumentParser);
    }

}
