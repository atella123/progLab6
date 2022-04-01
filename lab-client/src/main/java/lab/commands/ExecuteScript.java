package lab.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

import lab.common.commands.CollectionCommand;
import lab.common.commands.CommandResponse;
import lab.common.commands.CommandResult;
import lab.common.data.PersonCollectionManager;
import lab.common.util.CommandRunner;
import lab.common.io.IOManager;
import lab.common.io.Reader;

public final class ExecuteScript extends CollectionCommand {

    private CommandRunner runner;
    private Stack<File> bannedFiles = new Stack<>();
    private Stack<IOManager[]> oldIO = new Stack<>();

    public ExecuteScript(PersonCollectionManager manager, CommandRunner runner) {
        super(manager);
        this.runner = runner;
    }

    public ExecuteScript(IOManager io, PersonCollectionManager manager, CommandRunner runner) {
        super(io, manager);
        this.runner = runner;
    }

    @Override
    public CommandResponse execute(String arg) {
        File file = new File(arg);
        if (!bannedFiles.contains(file)) {
            bannedFiles.push(file);
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
                oldIO.push(new IOManager[] { runner.getIO(), runner.getCommandManager().getIO() });
                IOManager newIO = new IOManager(createReader(bufferedReader), getIO().getWritter());
                runner.setIO(newIO);
                runner.getCommandManager().setIO(newIO);
                runner.run();
                runner.setIO(oldIO.peek()[0]);
                runner.getCommandManager().setIO(oldIO.pop()[1]);
                return new CommandResponse(CommandResult.SUCCESS);
            } catch (IOException e) {
                return new CommandResponse(CommandResult.ERROR, "Unable to read file");
            } finally {
                bannedFiles.pop();
            }
        }
        return new CommandResponse(CommandResult.ERROR, "File already opened");
    }

    private Reader createReader(BufferedReader bufferedReader) {
        return () -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                return null;
            }
        };
    }

    @Override
    public String toString() {
        return "ExecuteScript";
    }

    @Override
    public String getMan() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bannedFiles == null) ? 0 : bannedFiles.hashCode());
        result = prime * result + ((oldIO == null) ? 0 : oldIO.hashCode());
        result = prime * result + ((runner == null) ? 0 : runner.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExecuteScript other = (ExecuteScript) obj;
        if (bannedFiles == null) {
            if (other.bannedFiles != null) {
                return false;
            }
        } else if (!bannedFiles.equals(other.bannedFiles)) {
            return false;
        }
        if (oldIO == null) {
            if (other.oldIO != null) {
                return false;
            }
        } else if (!oldIO.equals(other.oldIO)) {
            return false;
        }
        if (runner == null) {
            if (other.runner != null) {
                return false;
            }
        } else if (!runner.equals(other.runner)) {
            return false;
        }
        return true;
    }

}
