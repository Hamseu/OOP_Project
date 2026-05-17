package services;

import java.util.Vector;
import database.UDBM;
import news.News;

public class NewsService {
    private UDBM db;

    public NewsService(UDBM db) {
        this.db = db;
    }

    public void addNews(String title, String content, String publishedBy) {
        db.addNews(title, content, publishedBy);
    }

    public Vector<News> getAllNewsSortedByDate() {
        return db.getAllNewsSortedByDate();
    }

    public News getNewsById(String newsId) {
        return db.getNewsById(newsId);
    }

    public Vector<News> getAllNews() {
        return db.getAllNews();
    }
}
