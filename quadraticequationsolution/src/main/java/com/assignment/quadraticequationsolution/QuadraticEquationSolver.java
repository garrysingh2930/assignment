package com.assignment.quadraticequationsolution;

/**
 * QuadraticEquationSolver interface provides methods for solving quadratic equations and
 * returning the solutions as a QuadraticEquationProblemSolver object or a string.
 *
 * Implementations of this interface should provide their own method of solving quadratic
 * equations and returning the solutions.
 *
 * @author Garry Singh
 * @version 1.0
 */
public interface QuadraticEquationSolver {
    
    /**
     * Solves the quadratic equation with the specified coefficients and returns the roots
     * as a QuadraticEquationProblemSolver object.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant coefficient
     * @return a QuadraticEquationProblemSolver object representing the roots of the equation
     */
    public QuadraticEquationProblemSolver solve(double a, double b, double c);

    /**
     * Solves the quadratic equation specified in the input string and returns the roots
     * as a comma-separated string.
     *
     * @param input a string representing the coefficients of the quadratic equation, in the
     *              format "a,b,c"
     * @return a string representing the roots of the equation, in the format "root1,root2"
     */
    public String solve(String input);
}
