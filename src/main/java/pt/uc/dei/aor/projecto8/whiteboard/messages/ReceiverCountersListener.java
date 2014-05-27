/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.messages;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import pt.uc.dei.aor.projecto8.whiteboard.websocket.MyWhiteboard;

/**
 *
 * @author User
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/mySecondTopic")
})
public class ReceiverCountersListener implements MessageListener {

    @Inject
    private MyWhiteboard mywhiteboard;

    @Override
    public void onMessage(Message msg) {
        try {
            mywhiteboard.sendCounting(msg.getBody(String.class));
            System.out.println("R:" + msg.getBody(String.class));
        } catch (JMSException | IOException ex) {
            Logger.getLogger(ReceiverCountersListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
