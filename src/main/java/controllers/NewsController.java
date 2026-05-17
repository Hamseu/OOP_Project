package controllers;

import java.util.Vector;
import services.NewsService;
import news.News;

public class NewsController {
    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    public void addNews(String title, String content, String publishedBy) {
        newsService.addNews(title, content, publishedBy);
        System.out.println("News published successfully!");
    }

    public Vector<News> getNewsSortedByDate() {
        return newsService.getAllNewsSortedByDate();
    }

    public void displayNewsList() {
        Vector<News> newsList = newsService.getAllNewsSortedByDate();
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
        Vector<News> newsList = newsService.getAllNewsSortedByDate();
        
        if (newsIndex < 1 || newsIndex > newsList.size()) {
            System.out.println("Invalid news number!");
            return;
        }

        News news = newsList.get(newsIndex - 1);
        System.out.println("\n========== NEWS DETAILS ==========");
        System.out.println("Title: " + news.getTitle());
        System.out.println("Published: " + news.getFormattedDateTime());
        System.out.println("Published by: " + news.getPublishedBy());
        System.out.println("\nContent:\n" + news.getContent());
        System.out.println("==================================\n");
    }
}
