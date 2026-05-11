package system.research;

import system.User;
import java.util.Date;
import java.util.Vector;

public class ResearchProject {
    private String projectId;
    private String title;
    private String description;
    private User lead;
    private Date startDate;
    private Date endDate;
    private Vector<User> participants;
    private Vector<ResearchPaper> papers;
    private boolean active;

    public ResearchProject(String projectId, String title, User lead) {
        this.projectId = projectId;
        this.title = title;
        this.lead = lead;
        this.startDate = new Date();
        this.participants = new Vector<>();
        this.papers = new Vector<>();
        this.active = true;
        // лид автоматически становится участником
        this.participants.add(lead);
    }

    public void addParticipant(User user) {
        if (!participants.contains(user)) participants.add(user);
    }

    public void removeParticipant(User user) {
        participants.remove(user);
    }

    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) papers.add(paper);
    }

    public void close() {
        this.active = false;
        this.endDate = new Date();
    }

    // Getters
    public String getProjectId()              { return projectId; }
    public String getTitle()                  { return title; }
    public String getDescription()            { return description; }
    public void setDescription(String desc)   { this.description = desc; }
    public User getLead()                     { return lead; }
    public boolean isActive()                 { return active; }
    public Vector<User> getParticipants()     { return participants; }
    public Vector<ResearchPaper> getPapers()  { return papers; }
    public Date getStartDate()                { return startDate; }
    public Date getEndDate()                  { return endDate; }

    @Override
    public String toString() {
        return "[Project] " + title + " | lead: " + lead.getUsername()
               + " | active: " + active + " | members: " + participants.size();
    }
}