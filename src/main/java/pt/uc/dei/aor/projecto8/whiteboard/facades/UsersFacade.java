/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projecto8.whiteboard.facades;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Groups;
import pt.uc.dei.aor.projecto8.whiteboard.entities.Users;
import pt.uc.dei.aor.projecto8.whiteboard.utilities.MessagesForUser;

/**
 *
 * @author Vitor
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "WhiteboardPU")
    private EntityManager em;

    @Resource
    private SessionContext ctx;
    
    @Inject
    private GroupsFacade groupsFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    public Users findUserByUserName() {

        try {
            return (Users) em.createNamedQuery(Users.findByUsername).setParameter("username", ctx.getCallerPrincipal().getName()).getSingleResult();
        } catch (NoResultException ex) {
            MessagesForUser.addMessageError("User not found");
            return new Users();
        }
    }
    
    public void newUser(String nameUser,String passwordUser){ 
        Groups groups=new Groups("user", nameUser);
        groupsFacade.create(groups);
        String encrypt=Hashing.sha256().hashString(passwordUser, Charsets.UTF_8).toString();
        Users users=new Users(nameUser, encrypt);  
        users.setGroupsGroupid(groups);
        super.create(users);
        groups.getUsersList().add(users);
        groupsFacade.edit(groups);
        
//        users.setUsername(nameUser);
//        users.setPassword(encrypt);
//        edit(users);
        
    }

}
