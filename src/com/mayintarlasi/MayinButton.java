package com.mayintarlasi;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Bir alanın özelliklerini tutan button
 * @author Turgay
 */
public class MayinButton extends JButton {

    /**
     * Alandaki değeri tutar (-1,8) -1 Mayin var 0 Mayin yok (1-8) etrafındaki mayin sayısı
     */
    public int value = 0;

    /**
     * Işaretlendi mı?
     */
    public boolean isMarked = false;

    /**
     * button genişlik
     */
    public int width=16;

    /**
     * button yükseklik
     */
    public int height=16;

    /**
     * Buttonun kontrolden geçip geçmediğini kontrol eder.
     */
    public boolean isChecked=false;

    /**
     * Mayinin x kordinatı
     */
    public int posX;

    /**
     * Mayinin y kordinatı
     */
    public int posY;

    /**
     * Butonda mayın var mi
     */
    public boolean isMine=false;
    
    Resim icon = Resim._unknown;
    ImageIcon imageIcon=new ImageIcon(icon.getPath());
    
    /**
     * MayinButton constructor
     */
    public MayinButton() {
        //this.setBorder(BorderFactory.createEmptyBorder());
        this.setSize(width, height);
        this.setContentAreaFilled(false);
        this.setVisible(true);
        setButtonImage(icon);
    }
    
    /**
     * Button değerine göre resim ayarlar
     */
    public void SetMayinImage(){
        if(this.value==0)
            setButtonImage(Resim._0);
        if(this.value==1)
            setButtonImage(Resim._1);
        if(this.value==2)
            setButtonImage(Resim._2);
        if(this.value==3)
            setButtonImage(Resim._3);
        if(this.value==4)
            setButtonImage(Resim._4);
        if(this.value==5)
            setButtonImage(Resim._5);
        if(this.value==6)
            setButtonImage(Resim._6);
        if(this.value==7)
            setButtonImage(Resim._7);
        if(this.value==8)
            setButtonImage(Resim._8);
    }

    /**
     * Buttonun boyutunu ayarlar
     * @param width genişlik
     * @param height yükseklik
     * @return degistirilen ImageIcon'u döner.
     */
    public ImageIcon setImageSize(int width, int height) {
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        
        return imageIcon;
    }

    /**
     * Resim konumlarini tutan enum 
     */
    public enum Resim {

        _unknown(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-unknown.png"),

        _0(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-0.png"),

        _1(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-1.png"),

        _2(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-2.png"),

        _3(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-3.png"),

        _4(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-4.png"),

        _5(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-5.png"),

        _6(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-6.png"),

        _7(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-7.png"),

        _8(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-8.png"),

        _flag(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-flag.png"),

        _bomb(System.getProperty("user.dir") + "/src/com/mayintarlasi/sources/grid-bomb.png");
        private String path;

        Resim(String path) {
            this.path = path;
        }

        /**
         *
         * @return resim konumunu döner.
         */
        public String getPath() {
            return path;
        }

    }

    /**
     * Butonun verilen değere göre resmini ayarlar
     * @param icon Resim tipinde bir enum değerini alır.
     */
    public void setButtonImage(Resim icon) {
        this.icon = icon;
        imageIcon=new ImageIcon(icon.getPath());
        imageIcon=setImageSize(width, height);
        this.setIcon(imageIcon);
    }


}
