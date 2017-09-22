package test.study.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class JPopupMenuTest extends JFrame{

    public JPopupMenuTest()
    {
        super();
        setTitle("MenuTest");
        setBounds(100,100,350,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final   JLabel   jLabel=new   JLabel("JPopupMenu",JLabel.CENTER); 
        final JPopupMenu popupMenu = new JPopupMenu();  //弹出式菜单
        JMenuItem menuItem1 = new JMenuItem("菜单项名称1");
        JMenuItem menuItem2 = new JMenuItem("菜单项名称2");
        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        getContentPane().addMouseListener(new MouseAdapter(){   //鼠标事件
            /*public void mouseRelease(MouseEvent e){  //释放鼠标事件
                if(e.isPopupTrigger()){
                    //popupMenu.show(e.getComponent(),e.getX(),e.getY());
                    popupMenu.show(jLabel,e.getX(),e.getY());
                }
            }*/
            //public void mouseRelease(MouseEvent e){  //释放鼠标事件
            //if(e.isPopupTrigger()){
                //popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //popupMenu.show(jLabel,e.getX(),e.getY());
            //}
            //}
            public   void   mousePressed(MouseEvent   e) 
            { 
                //popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //popupMenu.show(jLabel,e.getX(),e.getY()); 
            }
        });
        jLabel.addMouseListener(new MouseAdapter(){   //鼠标事件
            
            public   void   mousePressed(MouseEvent   e) 
            { 
                //if(e.getButton()==3)   //1左键,2中键，在这里可以设置键值，这里可设置的不正确，请核实下
                //{
                popupMenu.show(e.getComponent(),e.getX(),e.getY());
                //}
                //popupMenu.show(jLabel,e.getX(),e.getY()); 
            }
        });
        this.getContentPane().add(jLabel); 
        //popupMenu.show(jLabel,e.getX(),e.getY());
        //getContentPane().add(popupMenu);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JPopupMenuTest jPopupMenuTest= new JPopupMenuTest();
        jPopupMenuTest.setVisible(true);
    }

}
