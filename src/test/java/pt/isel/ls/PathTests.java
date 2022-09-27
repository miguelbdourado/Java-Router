package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathTests {

    @Test
    public void compareTwoIdenticalPaths() {
        PathTemplate pathTemplate = new PathTemplate("/tops/ratings");
        Path path = new Path("/tops/ratings");
        assertTrue(pathTemplate.isTemplateOf(path));
    }

    @Test
    public void compareDifferentPaths() {
        PathTemplate pathTemplate = new PathTemplate("/users");
        Path path = new Path("/movies");
        assertFalse(pathTemplate.isTemplateOf(path));
    }

    @Test
    public void comparePartiallyEqualPaths() {
        PathTemplate pathTemplate = new PathTemplate("/users/directory1");
        Path path = new Path("/users/directory2");
        assertFalse(pathTemplate.isTemplateOf(path));
    }

    @Test
    public void comparePathsWithVariableDirectoriesEqual() {
        PathTemplate pathTemplate = new PathTemplate("/users/{uid}");
        Path path = new Path("/users/123");
        assertTrue(pathTemplate.isTemplateOf(path));
    }

    @Test
    public void comparePathsWithVariableDirectoriesEqual2() {
        PathTemplate pathTemplate = new PathTemplate("/movies/{mid}/ratings");
        Path path = new Path("/movies/420/ratings");
        assertTrue(pathTemplate.isTemplateOf(path));
    }

    @Test
    public void comparePathsWithVariableDirectoriesNotEqual() {
        PathTemplate pathTemplate = new PathTemplate("/movies/{uid}");
        Path path = new Path("/users/123");
        assertFalse(pathTemplate.isTemplateOf(path));
    }
}
