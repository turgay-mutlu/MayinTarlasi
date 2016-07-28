package com.mayintarlasi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Butun alanların yerleştiridiği panel.
 *
 * @author Turgay
 */
public class Tarla extends JPanel {

    private MayinButton[][] mayinlar;
    public int mX, mY;

    /**
     * Maksimum mayin sayısı
     */
    public int mayinSayisi;

    /**
     * (1-4) Oyundaki zorluk değeri
     */
    public int zorluk;

    /**
     * Patlama olmuşmu kontrolu
     */
    public boolean isBoom = false;

    int placedMine = 0;

    /**
     * Doğru işaretlenmiş mayin sayısı
     */
    public int correctMineCount = 0;

    /**
     * Paneli ayarlayan constructor
     *
     * @param zorluk oyunun zorluk değeri
     */
    public Tarla(int zorluk) {
        setTarla(zorluk);

    }

    /**
     * Zorluk değerine göre tarlanın ne kadar büyük olacağını ve mayın sayısını
     * ayarlar. Butun alanların değerlerini belirler ve her bir buttonun
     * eventını ayarlar.
     *
     * @param zorluk
     */
    public void setTarla(int zorluk) {
        mayinlar = null;
        this.zorluk = zorluk;
        int width = 0, height = 0;

        if (zorluk <= 1) {
            mayinSayisi = 5;
            this.mX = 10;
            this.mY = 10;
        } else if (zorluk == 2) {
            mayinSayisi = 30;
            this.mX = 16;
            this.mY = 16;
        } else if (zorluk == 3) {
            mayinSayisi = 99;
            this.mX = 32;
            this.mY = 16;
        } else if (zorluk == 4) {
            mayinSayisi = 400;
            this.mX = 90;
            this.mY = 50;
        }

        Oyun.mineCount = this.mayinSayisi;

        mayinlar = new MayinButton[mX][mY];

        this.setLayout(new GridBagLayout());

        this.setVisible(true);

        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < mX; i++) {
            for (int j = 0; j < mY; j++) {
                double rand = Math.random();
                gbc.gridx = i;
                gbc.gridy = j;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                MayinButton mayin = new MayinButton();
                mayin.posX = i;
                mayin.posY = j;
                mayin.setVisible(true);
                mayin.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.lightGray));
                mayin.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);

                        if (!isBoom) {
                            if (!Oyun.isStart) {
                                Oyun.isStart = true;
                            }
                            if (correctMineCount != mayinSayisi) {
                                if (mayin.icon == MayinButton.Resim._flag || mayin.icon == MayinButton.Resim._unknown) {
                                    if (e.getButton() == MouseEvent.BUTTON3) {
                                        mayin.isMarked = !mayin.isMarked;
                                        if (mayin.icon == MayinButton.Resim._flag) {
                                            mayin.setButtonImage(MayinButton.Resim._unknown);
                                            Oyun.mineCount++;
                                            if (mayin.isMine) {
                                                correctMineCount--;
                                            }

                                            //System.out.println(correctMineCount);
                                        } else if (mayin.icon == MayinButton.Resim._unknown) {
                                            if (Oyun.mineCount > 0) {
                                                mayin.setButtonImage(MayinButton.Resim._flag);
                                                Oyun.mineCount--;
                                                if (mayin.isMine) {
                                                    correctMineCount++;
                                                }

                                                //System.out.println(correctMineCount);
                                            }

                                        }
                                    }
                                    if (e.getButton() == MouseEvent.BUTTON1) {
                                        mayin.SetMayinImage();
                                        if (mayin.value == -1) {
                                            isBoom = true;
                                            mayin.setButtonImage(MayinButton.Resim._bomb);
                                        } else {

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    checkFreeSpace(mayin);
                                                }
                                            }).start();

                                        }
                                    }

                                }
                            }
                        }
                        System.out.println(correctMineCount);
                    }
                });
                mayinlar[i][j] = mayin;
                if (placedMine < mayinSayisi) {
                    if (rand > 0.95) {
                        mayinlar[i][j].value = -1;
                        mayinlar[i][j].isMine = true;
                        placedMine++;
                    }
                }

                this.add(mayinlar[i][j], gbc);
            }
            width += 1;
            height += 1;
        }
        width += mayinlar[0][0].width * mX;
        height += mayinlar[0][0].height * mY;

        this.setSize(width, height);
        setMines();
        checkMines();

    }

    /**
     * Mayınları random x ve y kordınatına göre yerleştırır.
     */
    public void setMines() {
        while (placedMine < mayinSayisi) {
            Random randX = new Random();
            int x = randX.nextInt(mX);

            Random randY = new Random();
            int y = randY.nextInt(mY);

            if (mayinlar[x][y].value != -1) {
                mayinlar[x][y].value = -1;
                mayinlar[x][y].isMine = true;
                placedMine++;
            }
        }
    }

    /**
     * Mayınların etrafındakı alanların değerini belirler.
     */
    public void checkMines() {
        for (int x = 0; x < mX; x++) {
            for (int y = 0; y < mY; y++) {
                if (mayinlar[x][y].value == -1) {
                    for (int i = -1; i <= 1; i++) {
                        int xIndex = x + i;
                        if (xIndex < 0 || xIndex >= mX) {
                            continue;
                        }
                        for (int j = -1; j <= 1; j++) {
                            int yIndex = y + j;
                            if (yIndex < 0 || yIndex >= mY) {
                                continue;
                            }
                            if (mayinlar[xIndex][yIndex].value == -1) {
                                continue;
                            }

                            mayinlar[xIndex][yIndex].value += 1;
                            //mayinlar[xIndex][yIndex].SetMayinImage();
                        }
                    }
                }
            }
        }
    }

    /**
     * Boş alana tıklanırsa etrafında değeri boş olmayan bütün alanları açar
     *
     * @param mayin Tılanılan mayin
     */
    public void checkFreeSpace(MayinButton mayin) {

        mayinlar[mayin.posX][mayin.posY].isChecked = true;
        if (mayinlar[mayin.posX][mayin.posY].isMarked) {
            Oyun.mineCount++;
        }
        if (mayinlar[mayin.posX][mayin.posY].value != 0) {
            return;
        }
        for (int i = -1; i <= 1; i++) {
            int xIndex = mayin.posX + i;
            if (xIndex < 0 || xIndex >= mX) {
                continue;
            }
            for (int j = -1; j <= 1; j++) {

                int yIndex = mayin.posY + j;

                if (yIndex < 0 || yIndex >= mY) {
                    continue;
                }
                if (mayinlar[xIndex][yIndex].value == -1) {
                    break;
                }

                if (mayinlar[xIndex][yIndex].value == 0 && mayinlar[xIndex][yIndex].isChecked == false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            checkFreeSpace(mayinlar[xIndex][yIndex]);
                        }
                    }).start();

                } else {
                    mayinlar[xIndex][yIndex].SetMayinImage();
                }
            }
        }

    }

}
