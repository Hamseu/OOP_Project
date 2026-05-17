package news;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class News {
    private String newsId;
    private String title;
    private String content;
    private LocalDateTime publishDateTime;
    private String publishedBy;

    public News(String newsId, String title, String content, LocalDateTime publishDateTime, String publishedBy) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.publishDateTime = publishDateTime;
        this.publishedBy = publishedBy;
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

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
