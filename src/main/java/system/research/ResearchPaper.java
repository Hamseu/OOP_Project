package system.research;

import system.User;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.Comparator;

public class ResearchPaper {
    private User author;
    private String title;
    private String theme;
    private String abstraction;
    private int citationsNumber;
    private int pages;
    private Date date;
    private Vector<User> coAuthors;
    private Map<String, Double> metrics;
    private Vector<ResearchProject> relatedProjects;

    public ResearchPaper(User author, String title, String theme, int pages) {
        this.author = author;
        this.title = title;
        this.theme = theme;
        this.pages = pages;
        this.citationsNumber = 0;
        this.date = new Date();
        this.coAuthors = new Vector<>();
        this.metrics = new HashMap<>();
        this.relatedProjects = new Vector<>();
    }

    // Getters / Setters
    public String getTitle()              { return title; }
    public void setTitle(String title)    { this.title = title; }

    public String getTheme()              { return theme; }
    public void setTheme(String theme)    { this.theme = theme; }

    public String getAbstraction()                    { return abstraction; }
    public void setAbstraction(String abstraction)    { this.abstraction = abstraction; }

    public int getCitationsNumber()       { return citationsNumber; }
    public void addCitation()             { this.citationsNumber++; }

    public User getAuthor()               { return author; }
    public Date getDate()                 { return date; }
    public int getPages()                 { return pages; }

    public void addCoAuthor(User user) {
        if (!coAuthors.contains(user)) coAuthors.add(user);
    }

    public Vector<User> getCoAuthors()    { return coAuthors; }

    public void setMetric(String key, double value) { metrics.put(key, value); }

    public void showMetrics() {
        System.out.println("Metrics for: " + title);
        metrics.forEach((k, v) -> System.out.println("  " + k + ": " + v));
    }

    public void addRelatedProject(ResearchProject p) {
        if (!relatedProjects.contains(p)) relatedProjects.add(p);
    }

    @Override
    public String toString() {
        return "[" + date.getYear() + 1900 + "] " + title + " by " + author.getUsername()
               + " | citations: " + citationsNumber;
    }
}