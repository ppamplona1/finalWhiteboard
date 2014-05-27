/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.messages;

import java.nio.ByteBuffer;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Topic;

/**
 *
 * @author User
 */
@Stateless
public class SenderBeanBytes {

    @Inject
    @JMSConnectionFactory("jms/myConnectionFactory")
    //private ConnectionFactory connectionFactory;
    private JMSContext context;

    @Resource(lookup = "jms/myTopic")
    private Topic topic;

    public SenderBeanBytes() {
    }

    public void sendMessage(ByteBuffer message) {
        byte[] bytes = new byte[message.capacity()];
        message.get(bytes, 0, bytes.length);
        context.createProducer().send(topic, bytes);
        System.out.println("sending message " + message.toString());

    }

}
