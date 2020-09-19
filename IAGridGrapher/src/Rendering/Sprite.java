
package Rendering;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class Sprite {
    
    private BufferedImage image;
    
    public Sprite(SpriteSheet spriteSheet,int x,int y){
        this.image = spriteSheet.getTexture().getImage().getSubimage( (x*spriteSheet.getWidth())-spriteSheet.getWidth() ,
                (y*spriteSheet.getHeight())-spriteSheet.getHeight(), 
                spriteSheet.getWidth(), 
                spriteSheet.getHeight());
    }
    
    public void render(Graphics2D g,double x,double y,double angle,double scale){
        AffineTransform at = new AffineTransform();
        at.rotate((angle*Math.PI)/180.0, x + image.getWidth()/2,y+ image.getHeight()/2);
        at.scale(scale,scale);
        at.translate(x, y);
        
        g.drawImage(image, at, null);
        
    }
    
    
}