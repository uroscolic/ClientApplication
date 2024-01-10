package com.raf.clientapplication.view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AdminFrame extends JFrame {
    private AdminView adminView;
    private AdminFrame() throws IllegalAccessException, NoSuchMethodException, IOException {
        this.setTitle("Reservation");
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        adminView = new AdminView();
        setLayout(new BorderLayout());
        add(adminView, BorderLayout.CENTER);
        adminView.setVisible(true);
    }
    private static class InstanceHolder {
        private static AdminFrame instance;

        static {
            try {
                instance = new AdminFrame();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static AdminFrame getInstance() {
        return AdminFrame.InstanceHolder.instance;
    }

}
