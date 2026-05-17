package controllers;

import java.util.Scanner;
import java.util.Vector;

import database.UDBM;
import services.NewsService;
import services.UserService;
import system.News;
import users.User;

public class NewsController extends Controller{
    private final NewsService NS;
    private final UserService US = UserService.getInstance();

    public NewsController(UDBM db) {
        this.db = db;
        this.NS = NewsService.getInstance(db);
    }
    public void displayNewsList() {
        Vector<News> newsList = NS.getAllNews();
        if (newsList.isEmpty()) {
            System.out.println("No news available.");
            return;
        }
        
        System.out.println("\n========== NEWS LIST (Most Recent First) ==========");
        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            System.out.println((i + 1) + ". [" + news.getFormattedDateTime() + "] " + news.getTitle());
        }
        System.out.println("==================================================\n");
    }

    public void displayNewsDetail(int newsIndex) {
        Vector<News> newsList = NS.getAllNews();
        
        if (newsIndex < 1 || newsIndex > newsList.size()) {
            System.out.println("Invalid news number!");
            return;
        }

        News news = newsList.get(newsIndex - 1);
        System.out.println("========== NEWS DETAILS ==========");
        System.out.println("Title: " + news.getTitle());
        System.out.println("Published: " + news.getFormattedDateTime());
        System.out.println("Published by: " + news.getPublishedBy());
        System.out.println("Content:\n" + news.getContent());
        System.out.println("==================================");
    }
    public void displayNewsByAuthor(Scanner scanner){
        Vector<User> users= US.getUsers();
        for (User us : users){
            System.out.println(us);
        }
        String id = RS.readLine("Id of Author", scanner);
        Vector<News> au_news = db.getNewsByAuthorId(id);
        for (News ne : au_news){
            System.out.println(ne);
        }
    }
}
