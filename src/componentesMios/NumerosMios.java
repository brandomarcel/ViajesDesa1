/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentesMios;

import javax.swing.JTextField;

/**
 *
 * @author Bmarc
 */
public class NumerosMios extends JTextField{
      
   
    
    
    
    public NumerosMios() {
this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTypedMio(evt);
            }
        });
    }
    
    public Integer getAsInteger() {
        Integer retorno;
        if (this.getText() == null) {
            return 0;
        }

        retorno = Integer.valueOf(this.getText());
        return retorno;
    }

    public void setAsInteger(Integer dato) {
       
        this.setText(String.valueOf(dato));
    }


    
    //copiamos y ahora si fabricamos 
       private void jTextField1KeyTypedMio(java.awt.event.KeyEvent evt) {                                     
        char c;
        c=evt.getKeyChar();
           
        if ( c< '0' || c> '9') {
               evt.consume();
              
           }
    } 

   
}
