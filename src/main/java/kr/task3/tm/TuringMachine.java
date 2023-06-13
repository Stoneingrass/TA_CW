package kr.task3.tm;

public class TuringMachine {
    final private State[] states;

    private String currentState;
    private Tape tape;
    private long time;

    public TuringMachine(State... states) {
        this.states = states;
        if (states[0]!=null) {
            currentState = states[0].getName();
        }
        tape = new Tape();
        time=0;
    }

    //stateInfoExample: "S1 0RS2 1LS0", "S2 1SS0 0LS1"
    //state name has exactly 2 char
    //final states example: A0 B0 C0
    public TuringMachine(String... states) {
        this(new State[states.length]);

        for (int i=0; i<states.length; i++) {
            String[] stateInfo = states[i].split(" ");

            String stateName = stateInfo[0];

            int changeValue0 = Integer.parseInt(String.valueOf(stateInfo[1].charAt(0)));
            Direction direction0 = Direction.S;

            char dir = stateInfo[1].charAt(1);
            switch (dir) {
                case 'L' -> direction0 = Direction.L;
                case 'S' -> direction0 = Direction.S;
                case 'R' -> direction0 = Direction.R;
            }

            String nextState0=stateInfo[1].substring(2,4);


            int changeValue1 = Integer.parseInt(String.valueOf(stateInfo[2].charAt(0)));
            Direction direction1 = Direction.S;

            dir = stateInfo[2].charAt(1);
            switch (dir) {
                case 'L' -> direction1 = Direction.L;
                case 'S' -> direction1 = Direction.S;
                case 'R' -> direction1 = Direction.R;
            }

            String nextState1=stateInfo[2].substring(2,4);

            this.states[i] = new State(stateName,
                                    changeValue0, direction0, nextState0,
                                    changeValue1, direction1, nextState1);
        }

        currentState = this.states[0].getName();
    }

    private State getStateByName(String stateName) {
        for (State state : states) {
            if (stateName.equals(state.getName())) {
                return state;
            }
        }
        System.out.println("The state is not exist: " + stateName);
        return null;
    }

    private boolean isStateFinal() {
        return currentState.charAt(1)=='0';
    }

    public void run(Tape tape) {
        this.tape=tape;
        do {
            printCurrentAction();
            printSystemTime();

            Action currentAction = getStateByName(currentState).check(tape.read()); //action by state and head value

            tape.edit(currentAction.newValue());    //edit tape
            tape.move(currentAction.direction());   //move head
            currentState = currentAction.nextState(); //change state

            printCurrentTape();
            printSystemTime();

            if (isStateFinal()) {
                currentState = states[0].getName();
                return;
            }
        } while (true);
    }

    public void print() {
        System.out.println("state\t0\t\t1");
        for (State state: states) {
            System.out.print(state.getName() + "\t\t");
            System.out.print(String.valueOf(state.check(0).newValue()) + state.check(0).direction() + state.check(0).nextState());
            System.out.print("\t");
            System.out.print(String.valueOf(state.check(1).newValue()) + state.check(1).direction() + state.check(1).nextState());
            System.out.println();
        }
    }

    public void printCurrentAction() {
        Action currentAction = getStateByName(currentState).check(tape.read());
        System.out.print("state=" + currentState + "; ");
        System.out.print("position=" + tape.position() + "; ");
        System.out.print("value=" + tape.read() + ": ");
        System.out.print(String.valueOf(currentAction.newValue()) + currentAction.direction() + currentAction.nextState());
    }

    public void printCurrentTape() {
        System.out.println("; result: " + tape.toString());
    }

    public void printSystemTime() {
        if(time==0) {
            time=System.nanoTime();
        }
        else {
            time=System.nanoTime()-time;
            System.out.println("executionTime=" + time + " ns");
            time=0;
        }
    }
}