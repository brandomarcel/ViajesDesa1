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
//Primero heredamos
public class textoMio extends JTextField {

    /**
     * @param args the command line arguments
     */

    public textoMio() {
this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTypedMio(evt);
            }
        });
    }

    public String getAsString() {
        String retorno;
        if (this.getText() == null) {
            return "";
        }

        retorno = String.valueOf(this.getText());
        return retorno;
    }
    
    //copiamos y ahora si fabricamos 
       private void jTextField1KeyTypedMio(java.awt.event.KeyEvent evt) {                                     
        char c;
        c=evt.getKeyChar();
         evt.setKeyChar(Character.toUpperCase(c));   
        if (!( c< '0' || c> '9')) {
               evt.consume();
              
           }
    } 

   

}
