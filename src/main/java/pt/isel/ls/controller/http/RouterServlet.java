package pt.isel.ls.controller.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.controller.commands.CommandRequest;
import pt.isel.ls.controller.commands.commandresults.CommandResult;
import pt.isel.ls.controller.commands.commandresults.CommandStatus;
import pt.isel.ls.controller.commands.handlers.CommandHandler;
import pt.isel.ls.model.Headers;
import pt.isel.ls.model.Method;
import pt.isel.ls.model.Parameters;
import pt.isel.ls.model.path.Path;
import pt.isel.ls.model.router.RouteResult;
import pt.isel.ls.model.router.Router;
import pt.isel.ls.view.commandviews.ICommandView;
import pt.isel.ls.view.commandviews.html.ErrorView;
import pt.isel.ls.view.utils.ViewsRouter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class RouterServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(RouterServlet.class);

    private final Router router;
    private final ViewsRouter views;

    public RouterServlet(Router router, ViewsRouter views) {
        this.router = router;
        this.views = views;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Path path = new Path(req.getRequestURI());
        Method method = Method.valueOf(req.getMethod());
        Parameters parameters = new Parameters(req.getParameterMap());

        String acceptValue = req.getHeader("Accept");
        Headers header = new Headers("accept:" + acceptValue.substring(0, acceptValue.indexOf(',')));

        resp.setContentType(String.format("text/html; charset=%s", StandardCharsets.UTF_8.name()));

        //Ir buscar o comando ao router
        Optional<RouteResult> maybeRouteResult = router.findRoute(method, path);
        CommandHandler handler = null;
        CommandResult result = null;
        if (maybeRouteResult.isPresent()) {
            handler = maybeRouteResult.get().getCommandHandler();
            if (handler != null) {
                // Executar
                result = handler.execute(new CommandRequest(path, header, parameters));
            }
        }

        // Ir buscar a view
        ICommandView view;
        String respBody;

        if (handler == null) {
            result = new CommandResult();
            result.setStatus(CommandStatus.ROUTE_NOT_FOUND_ERROR);
            result.setErrorMessage("ROUTE NOT FOUND");
            view = new ErrorView();
        } else {
            view = this.views.getView(header.getHeader("accept"), result);
        }

        respBody = view.render(result);

        // Enviar a resposta de volta
        try {
            resp.setStatus(result.getStatus().getStatusCode());
            sendResponse(resp, respBody);
        } catch (Exception e) {
            log.info("Error sending back the response.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Path path = new Path(req.getRequestURI());
        Method method = Method.valueOf(req.getMethod());
        Parameters parameters = new Parameters(req.getParameterMap()); //TODO::

        String acceptValue = req.getHeader("Accept");
        Headers header = new Headers("accept:" + acceptValue.substring(0, acceptValue.indexOf(',')));

        resp.setContentType(String.format("text/html; charset=%s", StandardCharsets.UTF_8.name()));

        //Ir buscar o comando ao router
        Optional<RouteResult> maybeRouteResult = router.findRoute(method, path);

        if (maybeRouteResult.isPresent()) {
            CommandHandler handler = maybeRouteResult.get().getCommandHandler();
            if (handler != null) {
                handler.execute(new CommandRequest(path, header, parameters));
            }
        }

        // Enviar a resposta de volta
        try {
            resp.setStatus(303);
            resp.setHeader("Location", req.getRequestURL().toString());
            sendResponse(resp, "");
        } catch (Exception e) {
            log.info("Error sending back the response.");
        }
    }

    public void sendResponse(HttpServletResponse resp, String respBody) throws Exception {
        byte[] respBodyBytes = respBody.getBytes(StandardCharsets.UTF_8);
        resp.setContentLength(respBodyBytes.length);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.flush();
    }
}
