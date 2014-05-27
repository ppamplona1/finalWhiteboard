/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import pt.uc.dei.aor.projecto8.whiteboard.beans.LoggedUser;
import pt.uc.dei.aor.projecto8.whiteboard.facades.WhiteboardFacade;
import pt.uc.dei.aor.projecto8.whiteboard.websocket.MyWhiteboard;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Vitor
 */
@Named
@ViewScoped
public class ImageController implements Serializable {


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
        System.out.println("Imagem----->" + dataURL);
        String png = new String(dataURL);
        String find = "base64,";
        String tokens = png.substring(png.indexOf(find) + find.length());
        System.out.println("Imagem2---->" + tokens);
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(tokens);
        } catch (IOException ex) {
            Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);

        }
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bImageFromConvert;
        try {
            bImageFromConvert = ImageIO.read(in);
            Random rand = new Random();
            File f = new File("C:/whiteboard/");
            f.mkdirs();
            File file = new File(f.getAbsolutePath() + "\\" + rand + "yourImage.png");
            ImageIO.write(bImageFromConvert, "png", file);
            System.out.println(file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return bytes;
    }

    public void test() {
////        String path = imagePath(loggedUser.getLoggedUser().getUsername());
////        String find = "C:/whiteboard//";
////        String nameimage = path.substring(path.indexOf(find) + find.length());
//        String nameimage=imagePath(loggedUser.getLoggedUser().getUsername());
//        String path= pathImages+nameimage;
//        whiteboardfacade.insertImage(nameimage, path, loggedUser.getLoggedUser());

    }

    public void saveImage() {
      String name=loggedUser.getLoggedUser().getUsername()+Calendar.getInstance().getTimeInMillis();
      whiteboardfacade.insertImage(name, loggedUser.getLoggedUser());
  
    }
    
    
//   

}
