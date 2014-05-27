/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.messages;

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
public class SendingCounter {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    @JMSConnectionFactory("jms/myConnectionFactory")
    //private ConnectionFactory connectionFactory;
    private JMSContext context;

    @Resource(lookup = "jms/mySecondTopic")
    private Topic topic;

    public SendingCounter() {
    }

    public void sendCounter(String counters) {
        context.createProducer().send(topic, counters);
    }
}
