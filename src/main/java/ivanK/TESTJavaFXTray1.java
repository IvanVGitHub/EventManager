package ivanK;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TESTJavaFXTray1 {
    public static void main(String[] args ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle desktopBounds = ge.getMaximumWindowBounds();

        System.out.println( desktopBounds );

        JFrame frame = new JFrame( "test" );
        final int width = 320;
        final int height = 200;
        frame.setBounds(
                desktopBounds.x + desktopBounds.width - width,
                desktopBounds.y + desktopBounds.height - height,
                width,
                height
        );

        frame.setVisible( true );
    }
}