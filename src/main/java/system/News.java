package system;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class News {
    private String newsId;
    private String title;
    private String content;
    private LocalDate publishDateTime;
    private String publishedBy;

    public News(String newsId, String title, String content, LocalDate publishDateTime, String publishedBy) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.publishDateTime = publishDateTime;
        this.publishedBy = publishedBy;
    }
    public News(){

    }

    public void setID(String id){
        this.newsId = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String con){
        this.content = con;
    }

    public void setPublisher(String id){
        this.publishedBy = id;
    }

    public void setPublishedDate(int year, int month, int day)
    {
        this.publishDateTime = LocalDate.of(year, month, day);
    }

    public void setPublishedDate(LocalDate date)
    {
        this.publishDateTime = date;
    }

    public String getNewsId() {
        return newsId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getPublishDateTime() {
        return publishDateTime;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return publishDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", publishDateTime=" + publishDateTime +
                ", publishedBy='" + publishedBy + '\'' +
                '}';
    }
}