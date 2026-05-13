package research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private String title;
    public String author_id;
    public String paper_id;
    public String theme;
    public String abstractions;
    private List<Researcher> authors;
    private String journal;
    private int pages;
    private int citations;
    private LocalDate publishedDate;
    private String doi;

    public ResearchPaper(
        String paperId,
        String authorId,
        String title,
        String theme,
        String abstractions,
        int citations,
        String doi,
        int pages,
        String journal,
        LocalDate publishedDate
) {
    this.paper_id = paperId;
    this.author_id = authorId;
    this.title = title;
    this.theme = theme;
    this.abstractions = abstractions;
    this.citations = citations;
    this.doi = doi;
    this.pages = pages;
    this.journal = journal;
    this.publishedDate = publishedDate;
    this.authors = new ArrayList<>();
    }

    public String getPaperId() {
    return this.paper_id;
}
    public void addAuthor(Researcher researcher) {
        authors.add(researcher);
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public String getTitle() {
        return title;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public int getCitations() {
        return citations;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getDoi() {
        return doi;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", citations=" + citations +
                ", publishedDate=" + publishedDate +
                ", doi='" + doi + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }
}
