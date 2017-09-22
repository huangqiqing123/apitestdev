package test.study.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Server extends JFrame {

	private static  ServerSocket serverSocket;
	private static Socket socket;
	private static  String port="8888";
	private static JTextArea textArea_1;
	private static JButton button;
	private static ObjectOutputStream out; 
	private static ObjectInputStream in;
	private static String information;
	private static JTextArea textArea;
	public Server() {
		super();
		setTitle("服务器端");
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 64));
		panel.setLayout(null);
		panel.setBounds(0, 0, 492, 341);
		getContentPane().add(panel);

		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(26, 248, 340, 67);
		panel.add(scrollPane_1);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 26, 427, 189);
		panel.add(scrollPane);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);

	    textArea_1 = new JTextArea();
	    textArea_1.setLineWrap(true);
		scrollPane_1.setViewportView(textArea_1);

		button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String send=textArea_1.getText();
				try{
				     out.writeObject(send);
				     textArea.append("发送:"+send+"\n");
				}catch(Exception e){e.getStackTrace();
				 textArea.append("提示：发送信息失败！！！\n");
				}
			}
		});
		button.setText("发送");
		button.setBounds(372, 249, 99, 23);
		panel.add(button);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
			}
		});
		button_1.setText("清除");
		button_1.setToolTipText("点击清除发送信息窗口的文字");
		button_1.setBounds(372, 278, 99, 23);
		panel.add(button_1);
        //导出记录
		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter fw=new FileWriter("c:\\memo.txt");
					BufferedWriter bw=new BufferedWriter(fw);
					bw.write (textArea.getText());             
					bw.flush();
					fw.close();
					javax.swing.JOptionPane.showMessageDialog(null,"导出聊天信息成功=>c:\\memo.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_2.setText("导出记录");
		button_2.setBounds(372, 307, 99, 23);
		panel.add(button_2);

		//
	}

	public static void main(String args[]) throws NumberFormatException, IOException {
		try {
			Server frame = new Server();
			frame.setVisible(true);
			serverSocket = new ServerSocket(Integer.parseInt(port));
			StringBuffer msgStrBuf = null;
			   msgStrBuf = new StringBuffer();
	           msgStrBuf.append("服务器准备启动 [");
	           //取得IP信息
	           msgStrBuf.append(" IP地址: "+InetAddress.getLocalHost());
	        
	           //取得端口号信息
	           msgStrBuf.append(" 端口号: ");
	           msgStrBuf.append(port);
	       
	           msgStrBuf.append("]\n");
	           msgStrBuf.append("服务器成功启动！\n正在监听端口.......\n");
	           textArea.setText(msgStrBuf.toString());
	           
	           socket = serverSocket.accept();
	           //socket.getInetAddress()获取对方IP
	           textArea.append("用户"+socket.getInetAddress()+"连接到服务器"+"\n");
	           in = new ObjectInputStream(socket.getInputStream());
               out = new ObjectOutputStream( socket.getOutputStream());
			   do{
		    	    try {  
		                   information = (String)in.readObject();
		                   textArea.append("收到："+information+"\n");
		              } catch (Exception e) {
			                                        e.printStackTrace();
		                                          }
	          }while(!information.equalsIgnoreCase("bye"));
			   textArea.append("客户端已经下线！！！\n");
			   out.close();
			   in.close();
			   socket.close();
			  // serverSocket.close();
		}finally{}		
}
}
