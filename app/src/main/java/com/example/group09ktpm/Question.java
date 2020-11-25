package com.example.group09ktpm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
    private int id;
    private String question;
    private List<String> choices;
    private String RightAnswer;
    private String Category;
    private int hardLevel;
    private boolean isConfirmed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return Category;
    }

    public int getHardLevel() {
        return hardLevel;
    }

    public boolean getIsConfirmed(){return  isConfirmed;}
    public Question(String question, List<String> choices, String rightAnswer, String category, int hardLevel,boolean isConfirmed) {
        this.question = question;
        this.choices = choices;
        RightAnswer = rightAnswer;
        Category = category;
        this.hardLevel = hardLevel;
        this.isConfirmed=isConfirmed;
    }

    public Question(int id, String question, List<String> choices, String rightAnswer, String category, int hardLevel) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        RightAnswer = rightAnswer;
        Category = category;
        this.hardLevel = hardLevel;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return RightAnswer;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void setHardLevel(int hardLevel) {
        this.hardLevel = hardLevel;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setRightAnswer(String rightAnswer) {
        RightAnswer = rightAnswer;
    }

    public void setConfirmed(boolean confirmed){isConfirmed=confirmed;}

    public static Question mapToQuestion(Map ques, String question){
        return new Question(question, (List<String>) ques.get("choices"), (String) ques.get("answer"), (String) ques.get("category"), Integer.parseInt(ques.get("hard").toString()), Boolean.parseBoolean(ques.get("isConfirmed").toString()));
    }
    public Map toMap(){
        Map newques = new HashMap();
        newques.put("choices", choices);
        newques.put("answer", getRightAnswer());
        newques.put("category", getCategory());
        newques.put("hard", getHardLevel());
        newques.put("isConfirmed",getIsConfirmed());
        return newques;
    }
}
