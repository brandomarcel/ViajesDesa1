/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

/**
 *
 * @author Xavier Cañizares
 */

public class imagenfondo implements Border
{
    
         public BufferedImage back;
 
    public imagenfondo(){
        try {
           URL imagePath = new URL(getClass().getResource("../Imagenes/viajes.jpg").toString());
            back = ImageIO.read(imagePath);
        } catch (IOException ex) { 
            System.out.println("error de imagen"+ ex);
        }
    }

         @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(back, (x + (width - back.getWidth())/2),(y + (height - back.getHeight())/2), null);
    }
 
         @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
 
         @Override
    public boolean isBorderOpaque() {
        return false;
    }
}