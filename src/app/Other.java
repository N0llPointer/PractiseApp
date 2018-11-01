package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Other {

    public static List<Question> getQuestions(){
        ArrayList<Question> questions = new ArrayList<>();

        Scanner scanner = new Scanner("/res/data/questions.txt");
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();

        }

        return questions;
    }
}
