package com.raf.clientapplication.view;

import javax.swing.*;
import java.io.IOException;

public class EditFrame extends JFrame {
    private EditView editView;
    private EditFrame() throws IOException {
        this.setTitle("Edit");
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        editView = new EditView();
        setLayout(null);
        add(editView);
        editView.setVisible(true);
    }
    private static class InstanceHolder {
        private static EditFrame instance;

        static {
            try {
                instance = new EditFrame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static EditFrame getInstance() {
        return EditFrame.InstanceHolder.instance;
    }
}
