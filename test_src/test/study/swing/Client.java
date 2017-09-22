package test.study.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Client extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Socket socket;
	private static String ip="127.0.0.1";
	private static  String port="8888";
	private static ObjectInputStream in;
	private static ObjectOutputStream out;
	private static JTextArea textArea;
	private static String information;
	
	public static void main(String args[]) {
			Client frame = new Client();
			frame.setVisible(true);
			 try {
					
					socket = new Socket(ip,Integer.parseInt(port));
					out = new ObjectOutputStream(socket.getOutputStream());
		            in = new ObjectInputStream(socket.getInputStream());
		            textArea.append("连接服务器成功！！n");
		            do{
			    	    try {  
			                   information = (String)in.readObject();
			                   textArea.append("收到:"+information+"\n");
			            } catch (Exception e) { e.printStackTrace(); }
		           }while(!information.equalsIgnoreCase("bye"));
		            textArea.append("服务器已停止服务！！！！！\n");
		            out.close();
		            in.close();
		            socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
	}

	/**
	 * Create the frame
	 */
	public Client() {
		super();
		setTitle("客户端");
		getContentPane().setLayout(null);
		setBounds(100, 100, 429, 397);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 64));
		panel.setLayout(null);
		panel.setBounds(0, 0, 425, 342);
		getContentPane().add(panel);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 387, 170);
		panel.add(scrollPane);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setToolTipText("接收信息窗口");
		scrollPane.setViewportView(textArea);

		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 218, 394, 67);
		panel.add(scrollPane_1);

		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setToolTipText("发送信息窗口");
		textArea_1.setLineWrap(true);
		textArea_1.setFont(new Font("", Font.PLAIN, 12));
		scrollPane_1.setViewportView(textArea_1);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String send=textArea_1.getText();
				try{
				out.writeObject(send);
				textArea.append("发送:"+send+"\n");
				}catch(Exception e){e.getStackTrace();}
			}
		});
		button.setText("发送");
		button.setBounds(288, 308, 99, 23);
		panel.add(button);

		final JButton button_clear = new JButton();
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
			}
		});
		button_clear.setText("清除");
		button_clear.setBounds(144, 308, 99, 23);
		panel.add(button_clear);

		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu menu = new JMenu();
		menu.setText("文件");
		menuBar.add(menu);

		final JMenuItem menuItem_1 = new JMenuItem();
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menuItem_1.setText("关闭");
		menu.add(menuItem_1);

		final JMenu menu_1 = new JMenu();
		menu_1.setText("工具");
		menuBar.add(menu_1);

		final JMenuItem menuItem = new JMenuItem();
		menuItem.setText("复制");
		menu_1.add(menuItem);
		//
	}

}
