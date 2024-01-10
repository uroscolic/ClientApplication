package com.raf.clientapplication;

import java.awt.*;

import javax.swing.*;

import com.raf.clientapplication.view.LoginView;
import com.raf.clientapplication.view.ReservationView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientApplication extends JFrame {

	private String token;
	private LoginView loginView;
	private String role;
	private Long id;

	private ClientApplication() throws IllegalAccessException, NoSuchMethodException {
		this.setTitle("Training Reservation Application");
		this.setSize(1200, 1200);

		this.setLayout(new BorderLayout());

		loginView = new LoginView();
		this.add(loginView, BorderLayout.CENTER);


		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static class InstanceHolder {
		private static ClientApplication instance;

		static {
			try {
				instance = new ClientApplication();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}


	public static ClientApplication getInstance() {
		return InstanceHolder.instance;
	}
}
