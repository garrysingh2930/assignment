package com.assignment.quadraticequationsolution;

/**
 * QuadraticEquationProblemSolver class represents the solutions to a quadratic equation,
 * as two double values representing the roots.
 *
 * @author Garry Singh
 * @version 1.0
 */
public class QuadraticEquationProblemSolver {
    private final double root1;
    private final double root2;

    /**
     * Constructs a new QuadraticEquationProblemSolver object with the specified roots.
     *
     * @param root1 the first root of the quadratic equation
     * @param root2 the second root of the quadratic equation
     */
    public QuadraticEquationProblemSolver(double root1, double root2) {
        this.root1 = root1;
        this.root2 = root2;
    }

    /**
     * Returns the first root of the quadratic equation.
     *
     * @return the first root of the quadratic equation
     */
    public double getRoot1() {
        return root1;
    }

    /**
     * Returns the second root of the quadratic equation.
     *
     * @return the second root of the quadratic equation
     */
    public double getRoot2() {
        return root2;
    }
}
