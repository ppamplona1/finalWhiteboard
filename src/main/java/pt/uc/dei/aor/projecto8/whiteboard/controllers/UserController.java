/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Users;
import pt.uc.dei.aor.projecto8.whiteboard.facades.UsersFacade;

/**
 *
 * @author User
 */
@Named(value = "userController")
@RequestScoped
public class UserController {
    
    private Users entity;
    

    private String nameUser;
    private String passwordUser;

    @Inject
    private UsersFacade usersFacade;

    public UserController() {
    }

    private static Logger log = Logger.getLogger(UserController.class.getName());

    public String logout() {
        // Notice the redirect syntax. The forward slash means start at
        // the root of the web application.
        String destination = "/index?faces-redirect=true";

        // FacesContext provides access to other container managed objects,
        // such as the HttpServletRequest object, which is needed to perform
        // the logout operation.
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            // added May 12, 2014
            HttpSession session = request.getSession();
            session.invalidate();

            // this does not invalidate the session but does null out the user Principle
            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            destination = "/loginerror?faces-redirect=true";
        }

        return destination; // go to destination
    }

    
      public void insertUser() {
       
       usersFacade.newUser(getNameUser(), getPasswordUser());

    }

    /**
     * @return the nameUser
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * @param nameUser the nameUser to set
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * @return the passwordUser
     */
    public String getPasswordUser() {
        return passwordUser;
    }

    /**
     * @param passwordUser the passwordUser to set
     */
    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
    
}
