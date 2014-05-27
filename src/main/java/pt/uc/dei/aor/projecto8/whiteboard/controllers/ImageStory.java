/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.controllers;

import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projecto8.whiteboard.beans.LoggedUser;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Whiteboard;
import pt.uc.dei.aor.projecto8.whiteboard.facades.WhiteboardFacade;

/**
 *
 * @author Vitor
 */
@Named
@ViewScoped
public class ImageStory {

    @Inject
    private WhiteboardFacade whiteboardFacade;

    @Inject
    private LoggedUser loggedUser;

    private Whiteboard whiteboard;

    private List<Whiteboard> whiteboardList;

    private boolean renderedImage;

    public ImageStory() {
    }

    @PostConstruct
    public void init() {
        this.renderedImage = false;
    }

    public void remove(Whiteboard whiteboard) {
        whiteboardFacade.remove(whiteboard);
        setWhiteboardList(whiteboardFacade.getAll(loggedUser.getLoggedUser()));

    }

    public List<Whiteboard> getWhiteboardList() {
        whiteboardList = whiteboardFacade.getAll(loggedUser.getLoggedUser());
        return whiteboardList;
    }

    public void setWhiteboardList(List<Whiteboard> whiteboardList) {

        this.whiteboardList = whiteboardList;
    }

    public WhiteboardFacade getWhiteboardFacade() {
        return whiteboardFacade;
    }

    public void setWhiteboardFacade(WhiteboardFacade whiteboardFacade) {
        this.whiteboardFacade = whiteboardFacade;
    }

    public Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(Whiteboard whiteboard) {

        if (whiteboard != null) {
            this.renderedImage = true;
        }
        this.whiteboard = whiteboard;
    }

    public boolean isRenderedImage() {
        return renderedImage;
    }

    public void setRenderedImage(boolean renderedImage) {
        this.renderedImage = renderedImage;
    }

    public void removeFile(String pathfile) {
        try {
            File file = new File(pathfile);
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
