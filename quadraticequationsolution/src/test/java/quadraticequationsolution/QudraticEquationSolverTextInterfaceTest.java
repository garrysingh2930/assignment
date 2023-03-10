package quadraticequationsolution;

/**
 * QudraticEquationSolverTextInterfaceTest class tests the functionality of the 
 * QuadraticEquationSolverTextInterface class
 *
 * @author Garry Singh
 * @version 1.0
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.assignment.quadraticequationsolution.QuadraticEquationProblemSolver;
import com.assignment.quadraticequationsolution.QuadraticEquationSolver;
import com.assignment.quadraticequationsolution.QuadraticEquationSolverTextInterface;

public class QudraticEquationSolverTextInterfaceTest {
    private static final double DELTA = 0.0001;

    @Test
    public void testSolve() {
        QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();
        QuadraticEquationProblemSolver solution = solver.solve(1, -5, 6);
        assertEquals(3.0, solution.getRoot1(), DELTA);
        assertEquals(2.0, solution.getRoot2(), DELTA);
    }

    @Test
    public void testSolveWithNegativeDiscriminant() {
        QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();
        QuadraticEquationProblemSolver solution = solver.solve(1, 1, 1);
        assertTrue(Double.isNaN(solution.getRoot1()));
        assertTrue(Double.isNaN(solution.getRoot2()));
    }

    @Test
    public void testSolveWithZeroDiscriminant() {
        QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();
        QuadraticEquationProblemSolver solution = solver.solve(1, -2, 1);
        assertEquals(1.0, solution.getRoot1(), DELTA);
        assertEquals(1.0, solution.getRoot2(), DELTA);
    }

    @Test
    public void testSolveWithTextInput() {
        QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();
        String input = "1,-5,6";
        String expectedOutput = "3.00,2.00";
        String output = solver.solve(input);
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testSolveWithInvalidInput() {
        QuadraticEquationSolver solver = new QuadraticEquationSolverTextInterface();
        
        // Test with invalid input string that cannot be parsed as double values
        String invalidInput = "a,b,c";
        String expectedOutput = "NaN,NaN";
        String output = solver.solve(invalidInput);
        assertEquals(expectedOutput, output);
        
    }
    
}
