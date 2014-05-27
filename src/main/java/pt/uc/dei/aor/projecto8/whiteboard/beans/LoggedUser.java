/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Users;
import pt.uc.dei.aor.projecto8.whiteboard.facades.UsersFacade;
import pt.uc.dei.aor.projecto8.whiteboard.websocket.MyWhiteboard;

/**
 *
 * @author Users
 */
@Named
@SessionScoped
public class LoggedUser implements Serializable {

    @Inject
    private UsersFacade userFacade;

    @Inject
    private MyWhiteboard myWhiteboard;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Users loggedUser;

    @PostConstruct
    public void init() {
        loggedUser = userFacade.findUserByUserName();

    }

    public Users getLoggedUser() {

        return loggedUser;
    }

    public void setLoggedUser(Users loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean confirmLoggedUser() {
        return loggedUser != null;
    }

    public UsersFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UsersFacade userFacade) {
        this.userFacade = userFacade;
    }

    public MyWhiteboard getMyWhiteboard() {
        return myWhiteboard;
    }

    public void setMyWhiteboard(MyWhiteboard myWhiteboard) {
        this.myWhiteboard = myWhiteboard;
    }

}
