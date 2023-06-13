package kr.task3.tm;

public record Action(int newValue, Direction direction, String nextState) {
}