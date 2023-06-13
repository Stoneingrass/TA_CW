package kr.task2.automatons;

import kr.task2.automatons.enums.MooreS;
import kr.task2.automatons.enums.X;
import kr.task2.automatons.enums.Y;

public class MooreAutomaton {
    private X[] inputWord;
    private Y[] outputWord;
    private MooreS[] states;
    private MooreS state;
    private Y y;

    public MooreAutomaton() {
        state = MooreS.S1;
        y=Y.Y1;
    }

    public void run(X... inputWord) {
        this.inputWord = inputWord;
        outputWord = new Y[inputWord.length+1];
        states = new MooreS[inputWord.length+1];

        outputWord[0]=y;
        states[0]=state;

        for (int i = 0; i < inputWord.length; i++) {
            outputWord[i+1]=config(inputWord[i]);
            states[i+1]=state;
        }
    }

    private Y config(X x) {
        if (state==MooreS.S1 && x==X.X1) {
            state=MooreS.S3;
            return Y.Y2;
        }
        if (state==MooreS.S1 && x==X.X2) {
            state=MooreS.S5;
            return Y.Y2;
        }
        if (state==MooreS.S2 && x==X.X1) {
            state=MooreS.S3;
            return Y.Y2;
        }
        if (state==MooreS.S2 && x==X.X2) {
            state=MooreS.S5;
            return Y.Y2;
        }
        if (state==MooreS.S3 && x==X.X1) {
            state=MooreS.S2;
            return Y.Y3;
        }
        if (state==MooreS.S3 && x==X.X2) {
            state=MooreS.S4;
            return Y.Y2;
        }
        if (state==MooreS.S4 && x==X.X1) {
            state=MooreS.S3;
            return Y.Y2;
        }
        if (state==MooreS.S4 && x==X.X2) {
            state=MooreS.S2;
            return Y.Y3;
        }
        if (state==MooreS.S5 && x==X.X1) {
            state=MooreS.S4;
            return Y.Y2;
        }
        if (state==MooreS.S5 && x==X.X2) {
            state=MooreS.S1;
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

    public MooreS getState() {
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
        for (MooreS s: states) {
            sb.append(s);
            sb.append("-");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
