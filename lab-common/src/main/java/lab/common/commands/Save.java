package lab.common.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public final class Save extends CollectionCommand {

    private Gson gson;
    private final File file;

    public Save(PersonCollectionManager manager, Gson gson, File file) {
        super(manager);
        this.gson = gson;
        this.file = file;
    }

    public Save(IOManager io, PersonCollectionManager manager, Gson gson, File file) {
        super(io, manager);
        this.gson = gson;
        this.file = file;
    }

    @Override
    public CommandResponse execute(String arg) {
        if (file.exists() && file.canWrite()) {
            String json = gson.toJson(getManager().getCollection());
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(json);
            } catch (IOException e) {
                return new CommandResponse(CommandResult.ERROR, "File is a directory");
            }
            return new CommandResponse(CommandResult.SUCCESS);
        }
        return new CommandResponse(CommandResult.ERROR, "Can't write to file");
    }

    @Override
    public String toString() {
        return "Save";
    }

    @Override
    public String getMan() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((gson == null) ? 0 : gson.hashCode());
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
        Save other = (Save) obj;
        if (gson == null) {
            if (other.gson != null) {
                return false;
            }
        } else if (!gson.equals(other.gson)) {
            return false;
        }
        return true;
    }
}
