package lab.util;

import java.io.IOException;
import java.io.InputStreamReader;

import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.util.CommandRunner;

public final class PersonCollectionServer {

    private PersonCollectionServer() {
        throw new UnsupportedOperationException();
    }

    public static void start(CommandRunner<?, ?> serverCommandRunner,
            ServerToClientCommandRunner serverToClientCommandRunner) {
        boolean stop = false;
        InputStreamReader systemInReader = new InputStreamReader(System.in);
        while (!stop) {
            serverToClientCommandRunner.writeCommandResponse(serverToClientCommandRunner.runNextCommmand());
            try {
                if (systemInReader.ready()) {
                    CommandResponse response = serverCommandRunner.runNextCommmand();
                    serverCommandRunner.writeCommandResponse(response);
                    if (response.getResult() == CommandResult.END) {
                        stop = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
