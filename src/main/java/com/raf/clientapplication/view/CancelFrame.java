package com.raf.clientapplication.view;

import javax.swing.*;
import java.io.IOException;

public class CancelFrame extends JFrame {
    private CancelView cancelView;
    private CancelFrame() throws IllegalAccessException, NoSuchMethodException, IOException {
        this.setTitle("Cancel");
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        cancelView = new CancelView();
        setLayout(null);
        add(cancelView);
        cancelView.setVisible(true);
    }
    private static class InstanceHolder {
        private static CancelFrame instance;

        static {
            try {
                instance = new CancelFrame();
            } catch (IllegalAccessException | NoSuchMethodException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static CancelFrame getInstance() {
        return CancelFrame.InstanceHolder.instance;
    }
}
