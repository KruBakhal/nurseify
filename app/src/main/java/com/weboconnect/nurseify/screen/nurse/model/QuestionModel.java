package com.weboconnect.nurseify.screen.nurse.model;

public class QuestionModel {
    String question;
    int answer = 2;
    int leader = 0;

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public QuestionModel(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public String getAnswer_Str() {
        return String.valueOf(answer);
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isAnyCheck() {
        if (answer != 2) {
            return true;
        }
        return false;
    }
}
