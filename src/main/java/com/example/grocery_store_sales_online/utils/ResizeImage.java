package com.example.grocery_store_sales_online.utils;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mysema.commons.lang.Pair;
@Slf4j
public class ResizeImage {
    public static final float DEFAULT_M_SIZE = (float)345/465;
    public static final float DEFAULT_S_SIZE = (float)65/90;
    private static final int MEDIUM_WIDTH = 600;
    private static final int SMALL_WIDTH = 300;


    private static List<Pair<Integer, Integer>> getHeightSmallAndMedium2() {
        int heightMedium = 0;
        int heightSmall = 0;
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        int widthMediumConf = MEDIUM_WIDTH;
        int widthSmallConf = SMALL_WIDTH;
        if (widthMediumConf != 0) {
            heightMedium = (int) (widthMediumConf*DEFAULT_M_SIZE);
            Pair<Integer, Integer> pairMedium = new Pair<>(widthMediumConf, heightMedium);
            list.add(pairMedium);
        }
        if (widthSmallConf != 0) {
            heightSmall = (int) (widthSmallConf*DEFAULT_S_SIZE);
            Pair<Integer, Integer> pairSmall = new Pair<>(widthSmallConf,heightSmall);
            list.add(pairSmall);
        }
        return list;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage,
                                             int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        BufferedImage thumbImage = new BufferedImage(width, height,
                type);

        if((double)width/height >= (double)originalImage.getWidth()/originalImage.getHeight()){
            // Fit to width
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_WIDTH,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, 0,
                    (resizedImage.getHeight() - height)/2 , width,
                    (resizedImage.getHeight() - height)/2 + height, null); //draw the image scaled */
            tGraphics2D.dispose();
            // Fit to height
        }else{
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_HEIGHT,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, (resizedImage.getWidth() - width)/2, 0,
                    (resizedImage.getWidth() - width)/2 + width, height, null); //draw the image scaled */
            tGraphics2D.dispose();
        }
        return thumbImage;
    }
}
