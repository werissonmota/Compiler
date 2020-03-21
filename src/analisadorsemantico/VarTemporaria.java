/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsemantico;

import java.util.ArrayList;

/**
 *
 * @author Aurelio
 */
public class VarTemporaria {
    private String id;
    private String type;
    private String escopo;
    private String value,typeValue;
    private String parent;
    private ArrayList<Object> listVars;
    private ArrayList dimensao;
    
    public VarTemporaria(String type, String id, String escopo){
        this.type = type;
        this.id = id;
        this.dimensao = new ArrayList();
        this.escopo = escopo;
    }
    public int getDimensao(){
        return dimensao.size();
    }
    public void setDimensao(String value){
        dimensao.add(value);
    }
    public boolean isArray(){
        return dimensao.size() != 0;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the escopo
     */
    public String getEscopo() {
        return escopo;
    }

    /**
     * @param escopo the escopo to set
     */
    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     * @param typeValue
     */
    public void setValue(String value, String typeValue) {
        this.value = value;
        this.typeValue = typeValue;
    }

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
    public ArrayList<Object> getListVars() {
        return listVars;
    }

    /**
     * @param listVars the listVars to set
     */
    public void setListVars(ArrayList<Object> listVars) {
        this.listVars = listVars;
    }

    /**
     * @return the typeValue
     */
    public String getTypeValue() {
        return typeValue;
    }

   
}
