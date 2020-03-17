/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsemantico;

import java.util.LinkedList;

/**
 *
 * @author Aurelio
 */
public class Composta extends Var{
    private String parent;
    private LinkedList<Object> listVars;

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * @return the listVars
     */
    public LinkedList<Object> getListVars() {
        return listVars;
    }

    /**
     * @param listVars the listVars to set
     */
    public void setListVars(LinkedList<Object> listVars) {
        this.listVars = listVars;
    }
}
