package net.salesianos.model;

public class Question {
    private String question;
    private String optionA, optionB, optionC, optionD;
    private char correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, char correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return question + "\n" +
                "A) " + optionA + "\n" +
                "B) " + optionB + "\n" +
                "C) " + optionC + "\n" +
                "D) " + optionD + "\n";
    }
}
