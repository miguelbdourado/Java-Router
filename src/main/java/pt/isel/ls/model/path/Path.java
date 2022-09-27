package pt.isel.ls.model.path;

import pt.isel.ls.model.Directory;

import java.util.Iterator;

public class Path extends AbstractPath {
    public Path(String fullPath) {
        super(fullPath);
    }

    public Directory getDirectory(String directoryName) {
        Iterator<Directory> it = iterator();

        while (it.hasNext()) {
            Directory curr = it.next();
            if (curr.getName().equals(directoryName)) {
                return curr;
            }
        }
        return null;
    }

    public String getVariable(String directoryName) {
        Directory dir = getDirectory(directoryName);

        if (dir != null) {
            return dir.getValue();
        }

        return null;
    }

    public void clear() {
        Iterator<Directory> it = iterator();

        while (it.hasNext()) {
            it.next().reset();
        }
    }
}
