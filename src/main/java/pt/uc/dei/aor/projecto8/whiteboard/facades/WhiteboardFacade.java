/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projecto8.whiteboard.facades;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Users;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Whiteboard;
import pt.uc.dei.aor.projecto8.whiteboard.websocket.MyWhiteboard;

/**
 *
 * @author Vitor
 */
@Stateless
public class WhiteboardFacade extends AbstractFacade<Whiteboard> {

    @PersistenceContext(unitName = "WhiteboardPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WhiteboardFacade() {
        super(Whiteboard.class);
    }

    public void insertImage(String name, Users user) {
        Whiteboard novo = new Whiteboard();
        novo.setName(name);
        novo.setImageDateCreator(Calendar.getInstance());
        novo.setImagedata(MyWhiteboard.getDataActive().array());
        novo.setUsersUsername(user);
        this.create(novo);

    }

    public List<Whiteboard> getAll(Users user) {
        Query query = em.createNamedQuery("Whiteboard.findByUser", Whiteboard.class).setParameter("user", user);
        return query.getResultList();
    }

    public byte[] getImageByte(Long idWhiteboard) {
        Whiteboard whiteboard = em.find(Whiteboard.class, idWhiteboard);
        return whiteboard.getImagedata();
    
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
