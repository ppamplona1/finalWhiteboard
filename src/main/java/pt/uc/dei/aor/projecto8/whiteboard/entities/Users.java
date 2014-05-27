/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projecto8.whiteboard.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Users
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Users.findAll, query = "SELECT u FROM Users u"),
    @NamedQuery(name = Users.findByUsername, query = "SELECT u FROM Users u WHERE u.username = :username"),

    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid")})
public class Users implements Serializable {

    public static final String findAll = "Users.findAll";
    public static final String findByUsername = "Users.findByUsername";

    private static final long serialVersionUID = 1L;
     @Id
    @NotNull
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userid;
     @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "username", unique = true)
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersUsername")
    private List<Whiteboard> whiteboardList;
    @JoinColumn(name = "groups_groupid", referencedColumnName = "groupid")
    @ManyToOne(optional = false)
    private Groups groupsGroupid;

    public Users() {
    }

    public Users(Integer userid) {
        this.userid = userid;
    }

    public Users(Integer userid, String username, String password) {
        this.userid = userid;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @XmlTransient
    public List<Whiteboard> getWhiteboardList() {
        return whiteboardList;
    }

    public void setWhiteboardList(List<Whiteboard> whiteboardList) {
        this.whiteboardList = whiteboardList;
    }

    public Groups getGroupsGroupid() {
        return groupsGroupid;
    }

    public void setGroupsGroupid(Groups groupsGroupid) {
        this.groupsGroupid = groupsGroupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }

}
