package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Other {

    public static List<Question> getQuestions(){
        ArrayList<Question> questions = new ArrayList<>();

        ArrayList<String> answers = new ArrayList<>();
        answers.add("звезда");
        answers.add("кольцо");
        answers.add("шина");
        questions.add(new Question("Какая из топологий использует метод доступа к среде на основе маркера?", answers, 3, true));

        answers = new ArrayList<>();
        answers.add("из 2 персональных компьютеров, соединенных между собой 0–модемным кабелем");
        answers.add("из нескольких персональных компьютеров, соединенных между собой сетевым кабелем");
        answers.add("из нескольких ЭВМ, один из которых обязательно наделяется правами сервера");
        questions.add(new Question("Из чего состоит самая простая сеть? ", answers, 2, true));


        answers = new ArrayList<>();
        answers.add("подсети сетей отделов");
        answers.add("сети, объединяющие множество сетей различных отделов одного предприятия в пределах отдельного здания или в пределах одной территории");
        answers.add("локальные сети, не имеющие выход в глобальную сеть Internet и функционирующие без выделенного сервера");
        questions.add(new Question("Сети кампусов – это", answers, 3, true));


        answers = new ArrayList<>();
        answers.add("ни один из ПК не обладает полномочиями сервера");
        answers.add("каждый ПК является как сервером, так и клиентом");
        answers.add("существует выделенный сервер, предоставляющий всевозможные сервисы, и множество клиентских ПК, использующих их в своих целях");
        questions.add(new Question("Принцип архитектуры “клиент-сервер”: ", answers, 3, true));


        return questions;
    }
}
