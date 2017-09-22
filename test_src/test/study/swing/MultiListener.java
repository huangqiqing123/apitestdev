package test.study.swing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiListener {

	private Frame f = new Frame("测试");
	private TextArea ta = new TextArea(6,40);
	private Button b1 = new Button("按钮一");
	private Button b2 = new Button("按钮二");
	
	public void init(){
		
		FirstListener l1 = new FirstListener();
		SecondListener l2 = new SecondListener();
		
		b1.addActionListener(l1);
		b1.addActionListener(l2);
		
		b2.addActionListener(l2);
		
		f.add(ta);
		
		Panel p = new Panel();
		p.add(b1);
		p.add(b2);
		
		f.add(p,BorderLayout.SOUTH);
		
		f.pack();
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		MultiListener multi = new MultiListener();
		multi.init();
	}
	
	//自定义监听器1
	class FirstListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {		
			ta.append("第一个事件监听器被触发，事件源是："+e.getActionCommand()+"\n");
		}		
	}
	//自定义监听器2
	class SecondListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {		
			ta.append("第二个事件监听器被触发，事件源是："+e.getActionCommand()+"\n");
		}		
	}
}
