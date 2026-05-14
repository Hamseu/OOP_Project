package controllers;

import database.UDBM;
import services.FindService;
import services.ReadService;

public abstract class Controller {
    protected UDBM db;
    protected final FindService FS = FindService.getInstance(db);
    protected final ReadService RS  =ReadService.getInstance();

    
}
