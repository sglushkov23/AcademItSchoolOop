package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class IconsCreator {
    public static ImageIcon createImageIcon(Image image, int size) {
        return new ImageIcon(getScaledImage(image, size, size));
    }

    public static ImageIcon createImageIcon(String path, String description) {
        URL imageUrl = ClassLoader.getSystemResource(path);

        if (imageUrl != null) {
            return new ImageIcon(imageUrl, description);
        }

        System.err.println("Couldn't find file: " + path);

        return null;
    }

    public static Image createImage(String path, String description) {
        URL imageUrl = ClassLoader.getSystemResource(path);

        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl, description);

            return icon.getImage();
        }

        System.err.println("Couldn't find file: " + path);

        return null;
    }

    public static Image getScaledImage(Image sourceImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(sourceImage, 0, 0, width, height, null);
        g2.dispose();

        return resizedImage;
    }

    public static void setResizedImage(ImageIcon icon, Image sourceImage, int size) {
        icon.setImage(getScaledImage(sourceImage, size, size));
    }
}