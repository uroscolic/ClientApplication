package com.raf.clientapplication.view;

import com.raf.clientapplication.ClientApplication;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Getter
@Setter
public class ReservationFrame extends JFrame {
    private ReservationView reservationView;
    private ReservationFrame() throws IllegalAccessException, NoSuchMethodException, IOException {
        this.setTitle("Reservation");
        this.setSize(1200, 1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        reservationView = new ReservationView();
        setLayout(new BorderLayout());
        add(reservationView, BorderLayout.CENTER);
        reservationView.setVisible(true);
    }
    private static class InstanceHolder {
        private static ReservationFrame instance;

        static {
            try {
                instance = new ReservationFrame();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static ReservationFrame getInstance() {
        return ReservationFrame.InstanceHolder.instance;
    }

    public static void reload() throws IOException {
        ReservationFrame.getInstance().getReservationView().reload();
    }

}
