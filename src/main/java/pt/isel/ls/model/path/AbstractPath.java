package pt.isel.ls.model.path;

import pt.isel.ls.model.Directory;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class AbstractPath {
    protected LinkedList<Directory> path;

    public LinkedList<Directory> getDirectories() {
        return this.path;
    }

    public Iterator<Directory> iterator() {
        return path.iterator();
    }

    public AbstractPath(String fullPath) {
        path = new LinkedList<>();

        if (fullPath == null || fullPath.isEmpty()) {
            throw new IllegalArgumentException("Path can not be empty.");
        }

        fullPath = fullPath.substring(1);
        String[] directories = fullPath.split("/");

        if (directories[0].isEmpty()) {
            return;
        }

        for (String currDirectory : directories) {
            if (currDirectory.isEmpty()) {
                throw new IllegalArgumentException("Invalid path. Check if no directory is empty.");
            }

            if (currDirectory.startsWith("{") && currDirectory.endsWith("}")) {
                path.add(new Directory(currDirectory.substring(1, currDirectory.length() - 1), true));
                continue;
            }

            path.add(new Directory(currDirectory, false));
        }
    }

    public String getFullPath() {
        Iterator<Directory> it = iterator();
        StringBuilder sb = new StringBuilder();

        sb.append("/");
        while (it.hasNext()) {
            sb.append(it.next().getName()).append("/");
        }

        return sb.toString();
    }
}
