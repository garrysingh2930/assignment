package com.assignment.quadraticequationsolution;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * HttpQuadraticEquationSolver class serves as a server for solving quadratic equations over HTTP.
 *
 * @author Garry Singh
 * @version 1.0
 */
public class HttpQuadraticEquationSolver {

    private static final Logger logger = LogManager.getLogger(HttpQuadraticEquationSolver.class);

    /**
     * The main method creates an instance of QuadraticEquationSolverTextInterface and starts
     * an HTTP server listening on port 8000.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();

            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/solve", new SolveHandler(solver));
            server.setExecutor(null);
            server.start();
            logger.info("HTTP server started on port 8000");
        } catch (IOException e) {
            logger.error("Failed to start HTTP server: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * SolveHandler is a nested class that implements HttpHandler interface to handle HTTP requests.
     */
    public static class SolveHandler implements HttpHandler {
        private QuadraticEquationSolver solver;

        public SolveHandler(QuadraticEquationSolver solver) {
            this.solver = solver;
        }

        /**
         * Handles HTTP POST requests to the /solve endpoint, solves the quadratic equation in the
         * request body, and returns the roots as a string.
         */
        public void handle(HttpExchange exchange) throws IOException {
            try {
                if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    String input = new String(exchange.getRequestBody().readAllBytes());
                    String output = solver.solve(input);
                    exchange.sendResponseHeaders(200, output.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(output.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            } catch (IOException e) {
                logger.error("Error handling HTTP request: " + e.getMessage());
                exchange.sendResponseHeaders(500, -1);
            } catch (Exception e) {
                logger.error("Invalid input: " + e.getMessage());
                exchange.sendResponseHeaders(400, -1);
            }
        }
    }
}
