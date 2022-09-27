package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.controller.commands.handlers.CreateNewReviewForMovieByMid;
import pt.isel.ls.controller.commands.handlers.Exit;
import pt.isel.ls.controller.commands.handlers.GetMovies;
import pt.isel.ls.controller.commands.handlers.GetReviewByUidAndRid;
import pt.isel.ls.controller.commands.handlers.GetUserByUid;
import pt.isel.ls.controller.commands.handlers.GetUsers;
import pt.isel.ls.model.Method;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.model.router.RouteResult;
import pt.isel.ls.model.router.Router;

import java.util.Optional;
import static org.junit.Assert.assertTrue;

public class RouterTests {

    @Test
    public void find_route_no_variable_params_test() {
        Router router = new Router();
        router.addRoute(Method.GET, new PathTemplate("/users"), new GetUsers());
        router.addRoute(Method.GET, new PathTemplate("/movies"), new GetMovies());
        router.addRoute(Method.EXIT, new PathTemplate("/"), new Exit());

        Optional<RouteResult> result = router.findRoute(Method.GET, new Path("/users"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler() instanceof GetUsers));

        result = router.findRoute(Method.GET, new Path("/movies"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler() instanceof GetMovies));

        result = router.findRoute(Method.EXIT, new Path("/"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler() instanceof Exit));
    }

    @Test
    public void find_route_variable_params_test() {
        Router router = new Router();
        router.addRoute(Method.POST, new PathTemplate("/movies/{mid}/ratings"), new CreateNewReviewForMovieByMid());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByUid());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/reviews/{rid}"), new GetReviewByUidAndRid());

        Optional<RouteResult> result = router.findRoute(Method.POST, new Path("/movies/143/ratings"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler()
                instanceof CreateNewReviewForMovieByMid));

        result = router.findRoute(Method.GET, new Path("/users/932"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler() instanceof GetUserByUid));

        result = router.findRoute(Method.GET, new Path("/users/43/reviews/87"));
        result.ifPresent(routeResult -> assertTrue(routeResult.getCommandHandler() instanceof GetReviewByUidAndRid));
    }
}
