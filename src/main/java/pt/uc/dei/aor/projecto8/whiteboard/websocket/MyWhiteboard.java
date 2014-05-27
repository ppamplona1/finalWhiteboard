/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import pt.uc.dei.aor.projecto8.whiteboard.messages.SenderBeanBytes;
import pt.uc.dei.aor.projecto8.whiteboard.messages.SendingCounter;

/**
 *
 * @author User
 */
@Singleton
//@ServerEndpoint(value = "/whiteboardendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
@ServerEndpoint(value = "/whiteboardendpoint")
public class MyWhiteboard {

    @Inject
    private SenderBeanBytes senderBean;
    @Inject
    private SendingCounter senderCounter;
    @Resource
    private SessionContext ctx;
    private static int numEdit = 0;
    private static int numAbort = 0;
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private static ByteBuffer bytebuffer = ByteBuffer.allocate(500000);
    private String userName = "";
    private boolean edit = true;

//    @OnMessage
//    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
//        System.out.println("broadcastFigure: " + figure);
//
//        for (Session peer : peers) {
//            if (!peer.equals(session) && peer.isOpen()) {
//                peer.getBasicRemote().sendObject(figure);
//            }
//        }
//    }
    @OnMessage
    public void getBooleanEdit(String string, Session session) throws IOException {
        System.out.println(string);
        if (string.equals("true")) {

            edit = true;
            senderCounter.sendCounter("second : true");

        } else if (string.equals("false")) {

            senderCounter.sendCounter("second : false");

            edit = false;
        }

    }

    @OnOpen
    public void onOpen(Session peer) throws IOException {

        peers.add(peer);

        if (peer.getUserPrincipal() != null) {

            senderCounter.sendCounter("first");

        }

        peer.getBasicRemote().sendBinary(bytebuffer);
        edit = true;

    }

    public void sendNumber(int number, String choice) throws IOException {
        for (Session p : peers) {
            if (p.isOpen()) {
                p.getBasicRemote().sendText("{\"" + choice + "\" : " + number + "}");
            }
        }
    }

    public void sendCounting(String cp) throws IOException {

        if (cp.contains("first")) {
            numEdit++;

            senderCounter.sendCounter("npeers : " + numEdit);
            senderCounter.sendCounter("numAbort : " + numAbort);
        } else if (cp.contains("second")) {
            if (cp.contains("true")) {
                numEdit++;
                sendNumber(numEdit, "npeers");
                if (numAbort > 0) {
                    numAbort--;
                }
                sendNumber(numAbort, "numAbort");
            } else if (cp.contains("false")) {
                numAbort++;
                sendNumber(numAbort, "numAbort");
                if (numEdit > 0) {
                    numEdit--;
                }
                sendNumber(numEdit, "npeers");

            }
        } else if (cp.contains("finish")) {
            if (cp.contains("true")) {
                if (numEdit > 0) {
                    numEdit--;
                }
                sendNumber(numEdit, "npeers");
            } else if (cp.contains("false")) {
                if (numAbort > 0) {
                    numAbort--;
                }
                sendNumber(numAbort, "numAbort");
            }
        } else if (cp.contains("npeers")) {
            numEdit = Integer.parseInt(cp.replaceAll("[\\D]", ""));
            sendNumber(numEdit, "npeers");
        } else if (cp.contains("numAbort")) {
            numAbort = Integer.parseInt(cp.replaceAll("[\\D]", ""));
            sendNumber(numAbort, "numAbort");
        }

    }

    @OnClose
    public void onClose(Session peer) throws IOException {
        userName = peer.getId();
        if (peer.getUserPrincipal() != null) {
            if (edit) {

                senderCounter.sendCounter("finish : true");

                sendNumber(numEdit, "npeers");
            } else {

                senderCounter.sendCounter("finish : false");
                sendNumber(numAbort, "numAbort");
            }
        }

        peers.remove(peer);

    }

    @OnMessage
    public void broadcastSnapshot(ByteBuffer data, Session session) throws IOException {
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendBinary(data);
            }
        }

        senderBean.sendMessage(data);
        bytebuffer = data;
    }

    public void onJMSMessage(ByteBuffer msg) {
        try {
            bytebuffer = msg;
            for (Session s : peers) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendBinary(bytebuffer);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(MyWhiteboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ByteBuffer getBytebuffer() {
        return bytebuffer;
    }

    public void setBytebuffer(ByteBuffer bytebuffer) {
        MyWhiteboard.bytebuffer = bytebuffer;
    }

    public static ByteBuffer getDataActive() {
        return bytebuffer;
    }
}
