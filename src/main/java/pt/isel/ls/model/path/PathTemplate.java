package pt.isel.ls.model.path;

import pt.isel.ls.model.Directory;

import java.util.Iterator;

public class PathTemplate extends AbstractPath {

    public PathTemplate(String fullPath) {
        super(fullPath);
    }

    public boolean isTemplateOf(Path path) {
        if (getDirectories().size() != path.getDirectories().size()) {
            return false;
        }

        Iterator<Directory> templateIterator = iterator();
        Iterator<Directory> pathIterator = path.iterator();

        while (templateIterator.hasNext() && pathIterator.hasNext()) {
            Directory currentTemplateDirectory = templateIterator.next();
            Directory currentPathDirectory = pathIterator.next();
            if (currentTemplateDirectory.isVariable()) {
                currentPathDirectory.setValue(currentPathDirectory.getName());
                currentPathDirectory.setName(currentTemplateDirectory.getName());
                continue;
            }

            if (currentPathDirectory.getName() == null && currentTemplateDirectory.getName() == null) {
                continue;
            }

            if (currentPathDirectory.getName() != null
                    && !currentPathDirectory.getName().equals(currentTemplateDirectory.getName())) {
                path.clear();
                return false;
            }
        }
        return true;
    }
}
