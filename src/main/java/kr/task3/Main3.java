package kr.task3;

import kr.task3.tm.Tape;
import kr.task3.tm.TuringMachine;

import java.util.Scanner;

//отслеживать корректность ввода параметров машин
//отслеживать корректность ввода начального слова

public class Main3 {
    public static void main(String[] args) {
        //прописать список final states
        TuringMachine MT14 = new TuringMachine(
                "A1 0RA1 0SA2",
                "A2 0SE1 1RA2",
                "E1 0RE2 0RA1",
                "E2 1RE1 1SB1",
                "B1 0RB2 1SC1",
                "B2 1LB2 1SB0",
                "C1 1RC1 0RC2",
                "C2 0SC0 1SC0");

        System.out.println("Turing machine MT14:");
        MT14.print();

        Tape tape = input();

        MT14.run(tape);

        String outputWord = tape.toString();

        System.out.println("Result word: " + outputWord);
    }

    public static Tape input() {
        Tape tape;
        do {
            System.out.println("Input word (only 0 and 1):");
            Scanner scanner = new Scanner(System.in);
            String inputWord = scanner.nextLine();

            try {
                tape = new Tape(inputWord);
                if (!tape.isCorrect()) {
                    throw new Exception();
                }
                return tape;
            } catch (Exception e) {
                System.out.println("Incorrect input word!");
            }
        } while (true);
    }
}