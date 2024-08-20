package com.example.grocery_store_sales_online.utils;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.mysema.commons.lang.Pair;
@Slf4j
public class ProcessImage {
    public static final float DEFAULT_M_SIZE = (float)345/465;
    public static final float DEFAULT_S_SIZE = (float)65/90;
    private static final int MEDIUM_WIDTH = 600;
    private static final int SMALL_WIDTH = 300;

    public static boolean checkExtensionImage(String nameImage){
        if (nameImage == null || nameImage.isEmpty()) {
            return false;
        }

        try {
            String extension = nameImage.substring(nameImage.lastIndexOf(CommonConstants.DOT) + 1).toLowerCase();
            return extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg");
        } catch (Exception ex) {
            log.error("Exception occurred while checking if file is an image. Exception message: {}", ex.getMessage());
            return false;
        }
    }
    public static List<Pair<Integer, Integer>> getHeightSmallAndMedium2() {
        int heightMedium = 0;
        int heightSmall = 0;
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        int widthMediumConf = MEDIUM_WIDTH;
        int widthSmallConf = SMALL_WIDTH;
        if (widthMediumConf != 0) {
            heightMedium = (int) (widthMediumConf * DEFAULT_M_SIZE);
            Pair<Integer, Integer> pairMedium = new Pair<>(widthMediumConf, heightMedium);
            list.add(pairMedium);
        }
        if (widthSmallConf != 0) {
            heightSmall = (int) (widthSmallConf * DEFAULT_S_SIZE);
            Pair<Integer, Integer> pairSmall = new Pair<>(widthSmallConf, heightSmall);
            list.add(pairSmall);
        }
        return list;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage,
                                            int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        BufferedImage thumbImage = new BufferedImage(width, height,
                type);

        if ((double) width / height >= (double) originalImage.getWidth() / originalImage.getHeight()) {
            // Fit to width
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_WIDTH,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, 0,
                    (resizedImage.getHeight() - height) / 2, width,
                    (resizedImage.getHeight() - height) / 2 + height, null); //draw the image scaled */
            tGraphics2D.dispose();
            // Fit to height
        } else {
            resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_TO_HEIGHT,
                    width, height, Scalr.OP_ANTIALIAS);
            Graphics2D tGraphics2D = thumbImage.createGraphics(); //create a graphics object to paint to
            tGraphics2D.drawImage(resizedImage, 0, 0, width, height, (resizedImage.getWidth() - width) / 2, 0,
                    (resizedImage.getWidth() - width) / 2 + width, height, null); //draw the image scaled */
            tGraphics2D.dispose();
        }
        return thumbImage;
    }

}
