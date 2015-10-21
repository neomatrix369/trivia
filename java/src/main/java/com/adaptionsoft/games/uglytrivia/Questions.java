package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Questions {
    private LinkedList questions = new LinkedList();

    public void add(String questionType, int totalQuestionsCount) {
        for (int i = 0; i < totalQuestionsCount; i++) {
            questions.addLast(questionType + i);
        }
    }

    public void add(String question) {
        questions.addLast(question);
    }

    public Object removeFirst() {
        return questions.removeFirst();
    }
}
