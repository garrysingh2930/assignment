package quadraticequationsolution;

/**
 * HttpQuadraticEquationSolverTest class tests the functionality of the 
 * HttpQuadraticEquationSolver class
 *
 * @author Garry Singh
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assignment.quadraticequationsolution.HttpQuadraticEquationSolver;
import com.assignment.quadraticequationsolution.QuadraticEquationSolver;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpQuadraticEquationSolverTest {

    private HttpServer server;

    @Mock
    private HttpExchange exchange;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        // Throw an exception from the QuadraticEquationSolverTextInterface class
        QuadraticEquationSolver solver = mock(QuadraticEquationSolver.class);
        server.createContext("/solve", new HttpQuadraticEquationSolver.SolveHandler(solver));
        server.setExecutor(null);
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop(0);
    }

    @Test
    public void testHandlePostRequest() throws IOException {
        QuadraticEquationSolver solver = mock(QuadraticEquationSolver.class);
        when(solver.solve(ArgumentMatchers.anyString())).thenReturn("2.00,3.00");
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestBody()).thenReturn(new ByteArrayInputStream("1,-5,6".getBytes()));
        OutputStream os = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(os);
        new HttpQuadraticEquationSolver.SolveHandler(solver).handle(exchange);
        verify(solver, times(1)).solve("1,-5,6");
    }

    @Test
    public void testHandleNonPostRequest() throws IOException {
        when(exchange.getRequestMethod()).thenReturn("GET");
        OutputStream os = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(os);
        // Throw an exception from the QuadraticEquationSolverTextInterface class
        QuadraticEquationSolver solver = mock(QuadraticEquationSolver.class);
        new HttpQuadraticEquationSolver.SolveHandler(solver).handle(exchange);
        verify(exchange, times(1)).sendResponseHeaders(405, -1);
        verify(os, times(1)).close();
    }

    @Test
    public void testSolveHandlerWithIOException() throws Exception {
        HttpExchange exchange = mock(HttpExchange.class);
        InputStream is = mock(InputStream.class);
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestBody()).thenReturn(is);
        when(is.readAllBytes()).thenThrow(new IOException()); // throw an IOException
        OutputStream os = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(os);
        // Throw an exception from the QuadraticEquationSolverTextInterface class
        QuadraticEquationSolver solver = mock(QuadraticEquationSolver.class);
        HttpQuadraticEquationSolver.SolveHandler handler = new HttpQuadraticEquationSolver.SolveHandler(solver);
        handler.handle(exchange);
        verify(exchange, times(1)).sendResponseHeaders(500, -1); // verify that the correct status code is returned
    }

    @Test
    public void testSolveHandlerWithException() throws Exception {
        HttpExchange exchange = mock(HttpExchange.class);
        InputStream is = mock(InputStream.class);
        when(exchange.getRequestMethod()).thenReturn("POST");
        when(exchange.getRequestBody()).thenReturn(is);
        when(is.readAllBytes()).thenReturn("1,2,3".getBytes()); // provide invalid input to throw an exception
        OutputStream os = mock(OutputStream.class);
        when(exchange.getResponseBody()).thenReturn(os);
        
        // Throw an exception from the QuadraticEquationSolverTextInterface class
        QuadraticEquationSolver solver = mock(QuadraticEquationSolver.class);
        when(solver.solve(anyString())).thenThrow(new RuntimeException());
        
        HttpQuadraticEquationSolver.SolveHandler handler = new HttpQuadraticEquationSolver.SolveHandler(solver);
        handler.handle(exchange);
        
        verify(exchange, times(1)).sendResponseHeaders(400, -1); // verify that the correct status code is returned
    }
    
    @Test
    public void testMainMethod() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        HttpQuadraticEquationSolver.main(new String[0]);
        
        String expectedOutput = "HTTP server started on port 8000\n";
        assertEquals(expectedOutput, outContent.toString());
    }

}