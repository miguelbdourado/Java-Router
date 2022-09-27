package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.controller.commands.handlers.CommandHandler;
import pt.isel.ls.controller.commands.handlers.CreateNewMovie;
import pt.isel.ls.controller.commands.handlers.GetMovieByMid;
import pt.isel.ls.controller.commands.handlers.GetMovies;
import pt.isel.ls.model.Method;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.model.router.utils.NTree;

import static org.junit.Assert.assertTrue;

public class TreeTests {

    @Test
    public void check_getCommand() {
        NTree tree = new NTree();
        tree.addCommand(Method.POST, new PathTemplate("/movies"), new CreateNewMovie());
        tree.addCommand(Method.GET, new PathTemplate("/movies"), new GetMovies());
        tree.addCommand(Method.GET, new PathTemplate("/movies/{mid}"), new GetMovieByMid());

        CommandHandler createMovie = tree.getCommand(Method.POST, new Path("/movies"));
        CommandHandler getMovies  = tree.getCommand(Method.GET, new Path("/movies"));
        CommandHandler getMovieMid = tree.getCommand(Method.GET, new Path("/movies/123"));

        assertTrue(createMovie instanceof CreateNewMovie);
        assertTrue(getMovies instanceof GetMovies);
        assertTrue(getMovieMid instanceof GetMovieByMid);
    }
}
