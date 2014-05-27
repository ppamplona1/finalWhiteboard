/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Whiteboard;
import pt.uc.dei.aor.projecto8.whiteboard.facades.WhiteboardFacade;

/**
 *
 * @author alvaro
 */
@Named
@ApplicationScoped
public class ImageView {

    /**
     * Creates a new instance of ImageView
     */
    @Inject
    private WhiteboardFacade whiteboardFacade;

    public ImageView() {
    }

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            Whiteboard wb = whiteboardFacade.find(Integer.valueOf(id));

            byte[] blobAsBytes = wb.getImagedata();
            BufferedImage bi = new BufferedImage(350, 350, BufferedImage.TYPE_4BYTE_ABGR);
            bi.getRaster().setDataElements(0, 0, 350, 350, blobAsBytes);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                ImageIO.write(bi, "png", os);
            } catch (IOException ex) {
                Logger.getLogger(ImageView.class.getName()).log(Level.SEVERE, null, ex);
            }

            return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");
        }

    }
}
