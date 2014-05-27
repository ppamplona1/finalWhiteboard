/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.messages;

import java.nio.ByteBuffer;
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
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myTopic"),})
public class ReceiverByte implements MessageListener {

    @Inject
    private MyWhiteboard mywhiteboard;

    @Override
    public void onMessage(Message msg) {
        byte[] bytes;
        try {

            bytes = msg.getBody(byte[].class);
            ByteBuffer bytebuffer = ByteBuffer.wrap(bytes);
            mywhiteboard.onJMSMessage(bytebuffer);

        } catch (JMSException ex) {
            Logger.getLogger(ReceiverByte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
