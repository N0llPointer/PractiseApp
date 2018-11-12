package app;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private int rightAnswer;
    private boolean isSingle;

    public Question(String question, List<String> answers, int rightAnswer, boolean isSingle) {
        this.question = question;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
        this.isSingle = isSingle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
}
