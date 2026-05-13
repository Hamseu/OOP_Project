package reports;

import java.io.Serializable;

public class Report implements Serializable {
    private String title;
    private String content;

    public Report(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void print() {
        System.out.println("===== " + title + " =====");
        System.out.println(content);
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }
}
