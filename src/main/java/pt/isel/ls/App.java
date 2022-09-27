package pt.isel.ls;

import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.commandresults.CreateNewMovieResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewReviewResult;
import pt.isel.ls.controller.commands.commandresults.CreateNewUserResult;
import pt.isel.ls.controller.commands.commandresults.DeleteReviewResult;
import pt.isel.ls.controller.commands.commandresults.ExitResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieRatingResult;
import pt.isel.ls.controller.commands.commandresults.GetMovieResult;
import pt.isel.ls.controller.commands.commandresults.GetMoviesResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewResult;
import pt.isel.ls.controller.commands.commandresults.GetReviewsResult;
import pt.isel.ls.controller.commands.commandresults.GetTopRatingsResult;
import pt.isel.ls.controller.commands.commandresults.GetUserResult;
import pt.isel.ls.controller.commands.commandresults.GetUsersResult;
import pt.isel.ls.controller.commands.commandresults.OptionResult;
import pt.isel.ls.controller.commands.commandresults.SubmitNewRatingResult;
import pt.isel.ls.controller.commands.handlers.CommandHandler;
import pt.isel.ls.controller.commands.handlers.CreateNewMovie;
import pt.isel.ls.controller.commands.handlers.CreateNewReviewForMovieByMid;
import pt.isel.ls.controller.commands.handlers.CreateNewUser;
import pt.isel.ls.controller.commands.handlers.DeleteReviewByMidAndRid;
import pt.isel.ls.controller.commands.handlers.Exit;
import pt.isel.ls.controller.commands.handlers.GetMainHandler;
import pt.isel.ls.controller.commands.handlers.GetMovieByMid;
import pt.isel.ls.controller.commands.handlers.GetMovieRatingByMid;
import pt.isel.ls.controller.commands.handlers.GetMovies;
import pt.isel.ls.controller.commands.handlers.GetReviewByMidAndRid;
import pt.isel.ls.controller.commands.handlers.GetReviewByUidAndRid;
import pt.isel.ls.controller.commands.handlers.GetReviewsByMid;
import pt.isel.ls.controller.commands.handlers.GetReviewsByUid;
import pt.isel.ls.controller.commands.handlers.GetTopRatings;
import pt.isel.ls.controller.commands.handlers.GetUserByUid;
import pt.isel.ls.controller.commands.handlers.GetUsers;
import pt.isel.ls.controller.commands.handlers.ListenServer;
import pt.isel.ls.controller.commands.handlers.Option;
import pt.isel.ls.controller.commands.handlers.SubmitNewRatingForMovie;
import pt.isel.ls.model.Headers;
import pt.isel.ls.model.Method;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.path.PathTemplate;
import pt.isel.ls.model.router.RouteResult;
import pt.isel.ls.model.router.Router;
import pt.isel.ls.view.MainView;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.commandviews.html.GetMovieHtmlView;
import pt.isel.ls.view.commandviews.html.GetMovieRatingHtmlView;
import pt.isel.ls.view.commandviews.html.GetMoviesHtmlView;
import pt.isel.ls.view.commandviews.html.GetReviewHtmlView;
import pt.isel.ls.view.commandviews.html.GetReviewsHtmlView;
import pt.isel.ls.view.commandviews.html.GetTopRatingsHtmlView;
import pt.isel.ls.view.commandviews.html.GetUserHtmlView;
import pt.isel.ls.view.commandviews.html.GetUsersHtmlView;
import pt.isel.ls.view.commandviews.plain.CreateNewMovieView;
import pt.isel.ls.view.commandviews.plain.CreateNewReviewView;
import pt.isel.ls.view.commandviews.plain.CreateNewUserView;
import pt.isel.ls.view.commandviews.plain.DeleteReviewView;
import pt.isel.ls.view.commandviews.plain.GetMovieRatingView;
import pt.isel.ls.view.commandviews.plain.GetMovieView;
import pt.isel.ls.view.commandviews.plain.GetMoviesView;
import pt.isel.ls.view.commandviews.plain.GetReviewView;
import pt.isel.ls.view.commandviews.plain.GetReviewsView;
import pt.isel.ls.view.commandviews.plain.GetTopRatingsView;
import pt.isel.ls.view.commandviews.plain.GetUserView;
import pt.isel.ls.view.commandviews.plain.GetUsersView;
import pt.isel.ls.view.commandviews.plain.OptionView;
import pt.isel.ls.view.commandviews.plain.SubmitNewRatingView;
import pt.isel.ls.view.utils.ViewsRouter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import static pt.isel.ls.controller.commands.commandresults.CommandStatus.INVALID_COMMAND_ERROR;
import static pt.isel.ls.controller.commands.commandresults.CommandStatus.PARAMETERS_ERROR;
import static pt.isel.ls.controller.commands.commandresults.CommandStatus.PATH_ERROR;
import static pt.isel.ls.controller.commands.commandresults.CommandStatus.ROUTE_NOT_FOUND_ERROR;
import static pt.isel.ls.controller.commands.commandresults.CommandStatus.SQL_ERROR;

public class App {

    private final Router router = new Router();
    private final ViewsRouter viewsRouter = new ViewsRouter();

    private HashMap<CommandStatus, String> resultMessages = new HashMap<>();


    public static void main(String[] args) {

        App app = new App();

        app.addHandlersToRouter();
        app.populateViews();
        app.addStatusMessages();

        if (args.length == 0) {
            //Normal mode
            app.run();
        } else {
            //One shot mode
            CommandResult result = app.prepareAndExecuteCommand(args);
            app.processCommandResult(result);
        }
    }

    private void addStatusMessages() {
        resultMessages.put(INVALID_COMMAND_ERROR, "Invalid command");
        resultMessages.put(ROUTE_NOT_FOUND_ERROR, "Route not found");
        resultMessages.put(PATH_ERROR, "Error in path");
        resultMessages.put(PARAMETERS_ERROR, "Error in parameters");
        resultMessages.put(SQL_ERROR, "SQL error");
    }

    private void populateViews() {

        //HTML VIEWS
        viewsRouter.addView("TEXT/HTML", GetMovieRatingResult.class, new GetMovieRatingHtmlView());
        viewsRouter.addView("TEXT/HTML", GetMovieResult.class, new GetMovieHtmlView());
        viewsRouter.addView("TEXT/HTML", GetMoviesResult.class, new GetMoviesHtmlView());
        viewsRouter.addView("TEXT/HTML", GetReviewResult.class, new GetReviewHtmlView());
        viewsRouter.addView("TEXT/HTML", GetReviewsResult.class, new GetReviewsHtmlView());
        viewsRouter.addView("TEXT/HTML", GetTopRatingsResult.class, new GetTopRatingsHtmlView());
        viewsRouter.addView("TEXT/HTML", GetUserResult.class, new GetUserHtmlView());
        viewsRouter.addView("TEXT/HTML", GetUsersResult.class, new GetUsersHtmlView());
        viewsRouter.addView("TEXT/HTML", CommandResult.class, new MainView());

        //PLAIN VIEWS
        viewsRouter.addView("TEXT/PLAIN", CreateNewMovieResult.class, new CreateNewMovieView());
        viewsRouter.addView("TEXT/PLAIN", CreateNewReviewResult.class, new CreateNewReviewView());
        viewsRouter.addView("TEXT/PLAIN", CreateNewUserResult.class, new CreateNewUserView());
        viewsRouter.addView("TEXT/PLAIN", DeleteReviewResult.class, new DeleteReviewView());
        viewsRouter.addView("TEXT/PLAIN", GetMovieRatingResult.class, new GetMovieRatingView());
        viewsRouter.addView("TEXT/PLAIN", GetMovieResult.class, new GetMovieView());
        viewsRouter.addView("TEXT/PLAIN", GetMoviesResult.class, new GetMoviesView());
        viewsRouter.addView("TEXT/PLAIN", GetReviewResult.class, new GetReviewView());
        viewsRouter.addView("TEXT/PLAIN", GetReviewsResult.class, new GetReviewsView());
        viewsRouter.addView("TEXT/PLAIN", GetTopRatingsResult.class, new GetTopRatingsView());
        viewsRouter.addView("TEXT/PLAIN", GetUserResult.class, new GetUserView());
        viewsRouter.addView("TEXT/PLAIN", GetUsersResult.class, new GetUsersView());
        viewsRouter.addView("TEXT/PLAIN", SubmitNewRatingResult.class, new SubmitNewRatingView());
        viewsRouter.addView("TEXT/PLAIN", CommandResult.class, new MainView());
    }

    private void run() {
        System.out.println(":: APP STARTED ::");

        Scanner scanner = new Scanner(System.in);
        CommandResult result;
        do {
            System.out.print("\nInsert a command (For all commands type OPTION /).\n> ");
            String[] commandArgs = scanner.nextLine().split(" ");
            result = prepareAndExecuteCommand(commandArgs);
            System.out.println();
        } while (!processCommandResult(result));

        System.out.println(":: EXITING PROGRAM ::");
    }

    private CommandResult prepareAndExecuteCommand(String[] commandArgs) {

        CommandResult result = new CommandResult();

        if (commandArgs.length <= 1) {
            result.setStatus(INVALID_COMMAND_ERROR);
            return result;
        }
        Method method = null;
        try {
            method = Method.valueOf(commandArgs[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("This method does not exist. Type \"OPTION /\" for all commands.");
        }

        Path path = new Path(commandArgs[1]);
        Parameters parameters = null;
        Headers headers = null;

        if (commandArgs.length == 3) {
            if (commandArgs[2].contains(":")) {
                headers = new Headers(commandArgs[2]);
            } else {
                parameters = new Parameters((commandArgs[2]));
            }
        } else if (commandArgs.length == 4) {
            headers = new Headers(commandArgs[2]);
            parameters = new Parameters(commandArgs[3]);
        }

        CommandHandler handler;
        Optional<RouteResult> routeResult = router.findRoute(method, path);
        CommandRequest request = new CommandRequest(path, headers, parameters);

        if (routeResult.isPresent()) {
            handler = routeResult.get().getCommandHandler();
            if (handler != null) {
                return handler.execute(request);
            }
        }
        result.setStatus(ROUTE_NOT_FOUND_ERROR);
        result.setErrorMessage("There is no route with the given name.");
        return result;
    }

    private boolean processCommandResult(CommandResult result) {
        String statusMessage = resultMessages.get(result.getStatus());

        if (statusMessage != null) {
            System.out.println(statusMessage);
            System.out.println(result.getErrorMessage());
            return false;
        }

        if (result instanceof OptionResult) {
            OptionView view = new OptionView();
            HashMap<PathTemplate, String> temp = router.getAllDescriptions();
            String viewText = view.render((OptionResult) result, temp);
            printToOutput(viewText, null);
            return false;
        }

        String accept = "TEXT/PLAIN";
        String filePath = null;
        if (result.getHeaders() != null) {
            filePath = result.getHeaders().getHeader("file-name");
        }

        if (result.getHeaders() != null) {
            accept = result.getHeaders().getHeader("accept");
        }

        ICommandView view = viewsRouter.getView(accept, result);

        String viewText = "";
        if (view != null) {
            viewText = view.render(result);
        }

        printToOutput(viewText, filePath);

        return result instanceof ExitResult;
    }

    void printToOutput(String viewText, String filePath) {
        PrintStream originalStdOut = System.out;
        FileOutputStream stream = null;

        if (filePath != null) {
            try {
                File file = new File(filePath);
                if (!file.createNewFile()) {
                    throw new IOException();
                }
                stream = new FileOutputStream(file);
                System.setOut(new PrintStream(stream));
            } catch (IOException e) {
                System.out.println("Error creating new file. Now printing to default output.");
                System.setOut(originalStdOut);
            }
        }

        System.out.println(viewText);
        System.setOut(originalStdOut);

        if (stream != null) {
            try {
                stream.close();
            } catch (IOException ignored) {
                System.out.println("Error during file handling.");
            }
        }
    }

    private void addHandlersToRouter() {
        router.addRoute(Method.POST, new PathTemplate("/movies"), new CreateNewMovie());
        router.addRoute(Method.POST, new PathTemplate("/movies/{mid}/reviews"), new CreateNewReviewForMovieByMid());
        router.addRoute(Method.POST, new PathTemplate("/users"), new CreateNewUser());
        router.addRoute(Method.EXIT, new PathTemplate("/"), new Exit());
        router.addRoute(Method.GET, new PathTemplate("/movies/{mid}"), new GetMovieByMid());
        router.addRoute(Method.GET, new PathTemplate("/movies/{mid}/ratings"), new GetMovieRatingByMid());
        router.addRoute(Method.GET, new PathTemplate("/movies"), new GetMovies());
        router.addRoute(Method.GET, new PathTemplate("/movies/{mid}/reviews"), new GetReviewsByMid());
        router.addRoute(Method.GET, new PathTemplate("/movies/{mid}/reviews/{rid}"), new GetReviewByMidAndRid());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/reviews"), new GetReviewsByUid());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}/reviews/{rid}"), new GetReviewByUidAndRid());
        router.addRoute(Method.GET, new PathTemplate("/tops/ratings"), new GetTopRatings());
        router.addRoute(Method.GET, new PathTemplate("/users/{uid}"), new GetUserByUid());
        router.addRoute(Method.GET, new PathTemplate("/users"), new GetUsers());
        router.addRoute(Method.GET, new PathTemplate("/"), new GetMainHandler());
        router.addRoute(Method.POST, new PathTemplate("/movies/{mid}/ratings"), new SubmitNewRatingForMovie());
        router.addRoute(Method.DELETE, new PathTemplate("/movies/{mid}/review/{rid}"), new DeleteReviewByMidAndRid());
        router.addRoute(Method.LISTEN, new PathTemplate("/"), new ListenServer(router, viewsRouter));
        router.addRoute(Method.OPTION, new PathTemplate("/"), new Option());

    }
}
