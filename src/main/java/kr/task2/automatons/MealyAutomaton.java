package kr.task2.automatons;

import kr.task2.automatons.enums.MealyS;
import kr.task2.automatons.enums.X;
import kr.task2.automatons.enums.Y;

public class MealyAutomaton {
    private X[] inputWord;
    private Y[] outputWord;
    private MealyS[] states;
    private MealyS state;

    public MealyAutomaton() {
        state = MealyS.S1;
    }

    public void run(X... inputWord) {
        this.inputWord = inputWord;
        outputWord = new Y[inputWord.length];
        states = new MealyS[inputWord.length+1];

        for (int i = 0; i < inputWord.length; i++) {
            states[i]=state;
            outputWord[i]=config(inputWord[i]);
        }
        states[states.length-1]=state;

    }

    private Y config(X x) {
        if (state==MealyS.S1 && x==X.X1) {
            state=MealyS.S2;
            return Y.Y2;
        }
        if (state==MealyS.S1 && x==X.X2) {
            state=MealyS.S4;
            return Y.Y2;
        }
        if (state==MealyS.S2 && x==X.X1) {
            state=MealyS.S1;
            return Y.Y3;
        }
        if (state==MealyS.S2 && x==X.X2) {
            state=MealyS.S3;
            return Y.Y2;
        }
        if (state==MealyS.S3 && x==X.X1) {
            state=MealyS.S2;
            return Y.Y2;
        }
        if (state==MealyS.S3 && x==X.X2) {
            state=MealyS.S1;
            return Y.Y3;
        }
        if (state==MealyS.S4 && x==X.X1) {
            state=MealyS.S3;
            return Y.Y2;
        }
        if (state==MealyS.S4 && x==X.X2) {
            state=MealyS.S1;
            return Y.Y1;
        }
        return null;
    }

    public X[] getInputWord() {
        return inputWord;
    }

    public Y[] getOutputWord() {
        return outputWord;
    }

    public MealyS getState() {
        return state;
    }

    public String inputToString() {
        StringBuilder sb = new StringBuilder();
        for (X x : inputWord) {
            sb.append(x);
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String outputToString() {
        StringBuilder sb = new StringBuilder();
        for (Y y : outputWord) {
            sb.append(y);
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String statesToString() {
        StringBuilder sb = new StringBuilder();
        for (MealyS s: states) {
            sb.append(s);
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
