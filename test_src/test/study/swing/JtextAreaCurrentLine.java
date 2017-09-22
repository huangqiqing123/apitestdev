package test.study.swing;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 * JTextArea中获得光标所处的行数
 * @author 五斗米 <如转载请保留作者和出处>
 * @blog http://blog.csdn.net/mq612
 */

public class JtextAreaCurrentLine extends JFrame {

 private static final long serialVersionUID = -2397593626990759111L;

 private JScrollPane scrollPane = null;

 private JTextArea text = null;

 private JButton button = null;

 public JtextAreaCurrentLine() {
  super("JTextArea Row Test");
  text = new JTextArea(
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
  text.setLineWrap(true);
  scrollPane = new JScrollPane(text);
  this.getContentPane().add(scrollPane);
  button = new JButton("获取光标所处的行数");
  button.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    try {
     Rectangle rec = text.modelToView(text.getCaretPosition());
     System.out.println(rec.y / rec.height + 1);
    } catch (BadLocationException e) {
     e.printStackTrace();
    }
   }
  });
  this.getContentPane().add(button, BorderLayout.NORTH);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setSize(300, 200);
  this.setVisible(true);
 }

 public static void main(String args[]) {
  new JtextAreaCurrentLine();
 }

}
 
