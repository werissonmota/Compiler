/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsemantico;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aurelio
 */
public class FunctionProcedureValues {
     HashMap<ArrayList,Object> values;
     
     public FunctionProcedureValues(ArrayList params, Object item){
         values = new <ArrayList,Object>HashMap();
       this.values.put(params, item);  
     }
     
     public void add(ArrayList params, Object item){
       this.values.put(params, item);  
     }
     
     
     
}
