/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projecto8.whiteboard.pojo;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class CountingPojo implements Serializable {

    private int usersEditing;

    private int usersAborting;

    public CountingPojo() {

    }

    public CountingPojo(int usersEditing, int usersAborting) {
        this.usersEditing = usersEditing;
        this.usersAborting = usersAborting;
    }

    public int getUsersEditing() {
        return usersEditing;
    }

    public void setUsersEditing(int usersEditing) {
        this.usersEditing = usersEditing;
    }

    public int getUsersAborting() {
        return usersAborting;
    }

    public void setUsersAborting(int usersAborting) {
        this.usersAborting = usersAborting;
    }

    @Override
    public String toString() {
        return "CountingPojo{" + "usersEditing=" + usersEditing + ", usersAborting=" + usersAborting + '}';
    }

}
