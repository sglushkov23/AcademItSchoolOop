package ru.academits.java.glushkov.minesweeper.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IconsCreator {
    public ImageIcon createImageIcon(String path, String description, int size) {
        java.net.URL imageUrl = getClass().getResource(path);

        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl, description);

            return new ImageIcon(getScaledImage(icon.getImage(), size, size));
        }

        System.err.println("Couldn't find file: " + path);

        return null;
    }

    public ImageIcon createImageIcon(String path, String description) {
        java.net.URL imageUrl = getClass().getResource(path);

        if (imageUrl != null) {
            return new ImageIcon(imageUrl, description);
        }

        System.err.println("Couldn't find file: " + path);

        return null;
    }

    public Image getScaledImage(Image sourceImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(sourceImage, 0, 0, width, height, null);
        g2.dispose();

        return resizedImage;
    }
}