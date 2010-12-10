package com.kanasansoft.WSBroadcaster;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WSBroadcaster {

	public static void main(String[] args) throws Exception {
		new WSBroadcaster();
	}

	public WSBroadcaster() throws Exception {

		MenuItem quitMenuItem = new MenuItem("Quit");
		quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(quitMenuItem);

		URL imageUrl = this.getClass().getClassLoader().getResource("images/icon.png");
		TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(imageUrl));
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("WSBroadcaster");
		trayIcon.setPopupMenu(popupMenu);

		SystemTray systemTray = java.awt.SystemTray.getSystemTray();
		systemTray.add(trayIcon);

		Server server = new Server(40320);

		MyWebSocketServlet wss = new MyWebSocketServlet();
		ServletHolder sh = new ServletHolder(wss);
		ServletContextHandler sch = new ServletContextHandler();
		sch.addServlet(sh, "/*");

		HandlerList hl = new HandlerList();
		hl.setHandlers(new Handler[] {sch});
		server.setHandler(hl);

		server.start();

	}

}
