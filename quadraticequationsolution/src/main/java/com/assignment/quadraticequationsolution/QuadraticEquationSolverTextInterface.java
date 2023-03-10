package com.assignment.quadraticequationsolution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * QuadraticEquationSolverTextInterface class provides a text-based interface for solving
 * quadratic equations. It implements the QuadraticEquationSolver interface and provides
 * methods for solving quadratic equations and returning the solutions as a string.
 *
 * @author Garry Singh
 * @version 1.0
 */
public class QuadraticEquationSolverTextInterface implements QuadraticEquationSolver {
    
    private static final Logger logger = LogManager.getLogger(QuadraticEquationSolverTextInterface.class);

    /**
     * Solves the quadratic equation with the specified coefficients and returns the roots
     * as a QuadraticEquationProblemSolver object.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant coefficient
     * @return a QuadraticEquationProblemSolver object representing the roots of the equation
     */
    public QuadraticEquationProblemSolver solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            logger.warn("The equation {}x^2 + {}x + {} does not have real roots", a, b, c);
            return new QuadraticEquationProblemSolver(Double.NaN, Double.NaN);
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            logger.info("The equation {}x^2 + {}x + {} has one root: {}", a, b, c, root);
            return new QuadraticEquationProblemSolver(root, root);
        } else {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            logger.info("The equation {}x^2 + {}x + {} has two roots: {}, {}", a, b, c, root1, root2);
            return new QuadraticEquationProblemSolver(root1, root2);
        }
    }

    /**
     * Solves the quadratic equation specified in the input string and returns the roots
     * as a comma-separated string.
     *
     * @param input a string representing the coefficients of the quadratic equation, in the
     *              format "a,b,c"
     * @return a string representing the roots of the equation, in the format "root1,root2"
     */
    public String solve(String input) {
    	  try {
    	        String[] params = input.split(",");
    	        double a = Double.parseDouble(params[0]);
    	        double b = Double.parseDouble(params[1]);
    	        double c = Double.parseDouble(params[2]);
    	        QuadraticEquationProblemSolver solution = solve(a, b, c);
    	        return String.format("%.2f,%.2f", solution.getRoot1(), solution.getRoot2());
    	    } catch (NumberFormatException e) {
    	        logger.error("Invalid input: {}", input);
    	        return "NaN,NaN";
    	    }
    }
}
