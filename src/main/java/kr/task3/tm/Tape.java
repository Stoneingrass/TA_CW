package kr.task3.tm;

import java.util.ArrayList;
import java.util.List;

public class Tape {
    private final List<Integer> tape;
    private int position;

    public Tape() {
        tape=new ArrayList<>();
        tape.add(0);
        position=0;
    }

    public Tape(String string) {
        this();
        tape.remove(0);
        for (char ch: string.toCharArray()) {
            tape.add(Integer.parseInt(String.valueOf(ch)));
        }
        position=0;
    }

    public void move(Direction direction) {
        switch (direction) {
            case L -> position--;
            case R -> position++;
        }
        if (position<0) {
            position++;
            tape.add(0, 0);
        }
        if (position>=tape.size()) {
            tape.add(0);
        }
    }

    public void edit(int value) {
        tape.set(position, value);
    }

    public int read() {
        return tape.get(position);
    }

    public boolean isCorrect() {
        for (Integer i: tape) {
            if(i!=0 && i!=1) {
                return false;
            }
        }
        return true;
    }

    public int position() {
        return position;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i: tape) {
            sb.append(i);
        }
        return sb.toString();
    }
}
