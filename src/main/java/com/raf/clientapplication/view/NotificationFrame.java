package com.raf.clientapplication.view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.IOException;

@Getter
@Setter
public class NotificationFrame extends JFrame {
    private NotificationView notificationView;
    private NotificationFrame() throws IllegalAccessException, NoSuchMethodException, IOException {
        this.setTitle("Notification");
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        notificationView = new NotificationView();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(notificationView);
        notificationView.setVisible(true);
    }

    public static void reload() throws IOException {
        NotificationFrame.getInstance().getNotificationView().init();
    }

    private static class InstanceHolder {
        private static NotificationFrame instance;

        static {
            try {
                instance = new NotificationFrame();
            } catch (IllegalAccessException | IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static NotificationFrame getInstance() {
        return NotificationFrame.InstanceHolder.instance;
    }
}
