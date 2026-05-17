package services;

import java.util.Vector;

import database.UDBM;
import system.News;

public class NewsService {
    private UDBM db;

    public NewsService(UDBM db) {
        this.db = db;
    }
    public static NewsService getInstance(UDBM db){
        return new NewsService(db);
    }

    public void addNews(String title, String content, String publishedBy) {
        db.addNews(title, content, publishedBy);
    }

    
    

    public Vector<News> getNewsByAuthorId(String newsAuthorId) {
        return db.getNewsByAuthorId(newsAuthorId);
    }

    public Vector<News> getAllNews() {
        return db.getAllNews();
    }
}