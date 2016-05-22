package pl.marchuck.myagh.tabs.helpdesk;

/**
 * @author Lukasz Marczak
 * @since 09.05.16.
 * Question and answer
 */
public class QA {
    public String question;
    public String answer;

    public QA(String q, String a) {
        question = q;
        answer = a;
    }

    public QA() {
        this("empty question", "empty answer");
    }

    @Override
    public String toString() {
        return question + " : " + answer;
    }
}