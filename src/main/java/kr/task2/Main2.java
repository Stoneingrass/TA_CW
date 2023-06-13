package kr.task2;

import kr.task2.automatons.MealyAutomaton;
import kr.task2.automatons.MooreAutomaton;
import kr.task2.automatons.enums.X;

public class Main2 {
    public static void main(String[] args) {
        MealyAutomaton mealy = new MealyAutomaton();
        mealy.run(X.X1, X.X2, X.X2, X.X2, X.X2, X.X1);

        System.out.println("Mealy automaton:");
        System.out.println("input:  " + mealy.inputToString());
        System.out.println("states: " + mealy.statesToString());
        System.out.println("output: " + mealy.outputToString());

        System.out.println();

        MooreAutomaton moore = new MooreAutomaton();
        moore.run(X.X1, X.X2, X.X2, X.X2, X.X2, X.X1);

        System.out.println("Moore automaton:");
        System.out.println("input:  " + moore.inputToString());
        System.out.println("states: " + moore.statesToString());
        System.out.println("output: " + moore.outputToString());
    }
}