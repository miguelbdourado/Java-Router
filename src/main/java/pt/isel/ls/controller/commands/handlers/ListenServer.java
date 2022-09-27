package pt.isel.ls.controller.commands.handlers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.http.RouterServlet;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.router.Router;
import pt.isel.ls.view.utils.ViewsRouter;

public class ListenServer implements CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(ListenServer.class);

    private final Router router;
    private final ViewsRouter views;

    public ListenServer(Router router, ViewsRouter viewsRouter) {
        this.router = router;
        this.views = viewsRouter;
    }

    @Override
    public CommandResult execute(CommandRequest commandRequest) {

        CommandResult commandResult = new CommandResult();

        try {

            Parameters parameters = commandRequest.getParameters();
            String givenPort = null;

            if (parameters != null) {
                givenPort = parameters.getParameter("port");
            }

            int port = (givenPort == null) ? Integer.parseInt(System.getenv("PORT")) : Integer.parseInt(givenPort);

            Server server = new Server(port);
            ServletHandler handler = new ServletHandler();
            RouterServlet servlet = new RouterServlet(this.router, this.views);

            handler.addServletWithMapping(new ServletHolder(servlet), "/*");
            log.info("registered {} on all paths", servlet);

            server.setHandler(handler);
            server.start();
            log.info(":: Server Listening on " + port);

            log.info("server started listening on port {}", port);

            commandResult.setStatus(CommandStatus.CLOSED_SERVER_SUCCESS);
        } catch (Exception throwable) {
            commandResult.setStatus(CommandStatus.INVALID_COMMAND_ERROR);
            commandResult.setErrorMessage("Error opening the server.");
        }
        return commandResult;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
