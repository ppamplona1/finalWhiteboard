/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.controllers;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projecto8.whiteboard.beans.LoggedUser;
import pt.uc.dei.aor.projecto8.whiteboard.facades.WhiteboardFacade;
import pt.uc.dei.aor.projecto8.whiteboard.websocket.MyWhiteboard;
import sun.misc.BASE64Decoder;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vitor
 */
@Named
@RequestScoped
public class CanvasController implements Serializable {

    @Inject
    private MyWhiteboard mywhiteboard;

    @Inject
    private LoggedUser loggedUser;

    @Inject
    private WhiteboardFacade whiteboardfacade;

    private String dataURL;

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public byte[] image() {
        // System.out.println("Imagem----->" + dataURL);
        String png = new String(dataURL);
        String find = "base64,";
        String tokens = png.substring(png.indexOf(find) + find.length());
        //System.out.println("Imagem2---->" + tokens);
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(tokens);
        } catch (IOException ex) {
            Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return bytes;
    }

    public void test() {

        whiteboardfacade.insertImage("teste", image(), loggedUser.getLoggedUser());

    }

}
