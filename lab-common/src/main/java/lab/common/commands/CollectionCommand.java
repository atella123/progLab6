package lab.common.commands;

import lab.common.data.PersonCollectionManager;
import lab.common.io.IOManager;

public abstract class CollectionCommand extends Command {

    private PersonCollectionManager manager;

    public CollectionCommand(PersonCollectionManager manager) {
        super();
        this.manager = manager;
    }

    public CollectionCommand(IOManager io, PersonCollectionManager manager) {
        super(io);
        this.manager = manager;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
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
        CollectionCommand other = (CollectionCommand) obj;
        if (manager == null) {
            if (other.manager != null) {
                return false;
            }
        } else if (!manager.equals(other.manager)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CollectionCommand";
    }

    public PersonCollectionManager getManager() {
        return manager;
    }

    public void setManager(PersonCollectionManager manager) {
        this.manager = manager;
    }

}
