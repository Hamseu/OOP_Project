package research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    public String title;
    public String project_id;
    public double budget;
    private List<Researcher> participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String id, String title, Double budget) {
        this.title = title;
        this.project_id = id;
        this.budget = budget;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public void addPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
    }

    public String getTopic() {
        return title;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "topic='" + title + '\'' +
                ", participants=" + participants.size() +
                ", publishedPapers=" + publishedPapers.size() +
                '}';
    }
}
