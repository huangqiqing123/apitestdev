package test.study.swing.jtree;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

public class SelectTree {
	
	JFrame jf = new JFrame("监听树的选择事件");
	JTree tree;
	
	//定义初始节点
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
	DefaultMutableTreeNode sd = new DefaultMutableTreeNode("山东");
	DefaultMutableTreeNode jn = new DefaultMutableTreeNode("济南");
	DefaultMutableTreeNode qd = new DefaultMutableTreeNode("青岛");
	DefaultMutableTreeNode lx = new DefaultMutableTreeNode("历下区");
	DefaultMutableTreeNode lc = new DefaultMutableTreeNode("历城区");
	
	JTextArea eventTxt = new JTextArea(5,20);
	
	public void init(){
		
		//通过add方法建立树之间的关联
		jn.add(lc);
		jn.add(lx);
		sd.add(jn);
		sd.add(qd);
		root.add(sd);
		
		//以根节点创建树
		tree = new JTree(root);
		
		//设置只能选择一个treepath
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.addTreeSelectionListener(new TreeSelectionListener(){

			//当Jtree中被选择的节点发生改变时，将触发该方法。
			public void valueChanged(TreeSelectionEvent e) {
				if(e.getOldLeadSelectionPath()!=null){
					eventTxt.append("原选中的节点路径："+e.getOldLeadSelectionPath()+"\n");
					eventTxt.append("新选中的节点路径："+e.getNewLeadSelectionPath()+"\n");
				}
			}		
		});
		
		//设置是否显示根节点的展开、折叠图标,默认是false。
		tree.setShowsRootHandles(true);
		
		//设置Jtree的windows外观，展开折叠图标会变成+  -号
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			SwingUtilities.updateComponentTreeUI(tree);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} 
		
		//设置根节点是否可见，默认是true
		tree.setRootVisible(true);
		
		//设置布局 box之横向布局
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JScrollPane(tree));
		box.add(new JScrollPane(eventTxt));
		jf.add(box);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	public static void main(String[] args) {
		if (System.getProperty("substancelaf.useDecorations") == null) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}
		try {
			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());// 设置主题色调 
		SubstanceLookAndFeel.setCurrentTitlePainter(new org.jvnet.substance.title.Glass3DTitlePainter()); //设置标题  	
		SubstanceLookAndFeel.setCurrentGradientPainter(new org.jvnet.substance.painter.StandardGradientPainter()); //设置渲染方式  
		SubstanceLookAndFeel.setCurrentButtonShaper(new org.jvnet.substance.button.StandardButtonShaper()); //设置按钮外观   

		SelectTree st = new SelectTree();
		st.init();
	}
}
