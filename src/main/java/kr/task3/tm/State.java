package kr.task3.tm;

public class State {
    private final String name;
    private final Action ifInput0;
    private final Action ifInput1;

    public State(String name, int newValue0, Direction direction0, String nextState0,
                                int newValue1, Direction direction1, String nextState1) {
        this.name = name;
        this.ifInput0 = new Action(newValue0, direction0, nextState0);
        this.ifInput1 = new Action(newValue1, direction1, nextState1);
    }

    public String getName() {
        return name;
    }

    public Action check(int value) {
        if (value==0) return ifInput0;
        else return ifInput1;
    }
}
