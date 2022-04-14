package lab.util;

import java.io.IOException;
import java.io.InputStreamReader;

import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;

public final class PersonCollectionServer {

    private final ServerCommandRunner serverCommandRunner;
    private final ServerToClientCommandRunner serverToClientCommandRunner;

    public PersonCollectionServer(ServerCommandRunner serverCommandRunner,
            ServerToClientCommandRunner serverToClientCommandRunner) {
        this.serverCommandRunner = serverCommandRunner;
        this.serverToClientCommandRunner = serverToClientCommandRunner;
    }

    public void start() {
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

    public ServerCommandRunner getServerCommandRunner() {
        return serverCommandRunner;
    }

    public ServerToClientCommandRunner getServerToClientCommandRunner() {
        return serverToClientCommandRunner;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serverCommandRunner == null) ? 0 : serverCommandRunner.hashCode());
        result = prime * result + ((serverToClientCommandRunner == null) ? 0 : serverToClientCommandRunner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PersonCollectionServer other = (PersonCollectionServer) obj;
        if (serverCommandRunner == null) {
            return other.serverCommandRunner == null;
        }
        if (!serverCommandRunner.equals(other.serverCommandRunner)) {
            return false;
        }
        if (serverToClientCommandRunner == null) {
            return other.serverToClientCommandRunner == null;
        }
        return serverToClientCommandRunner.equals(other.serverToClientCommandRunner);
    }

}
