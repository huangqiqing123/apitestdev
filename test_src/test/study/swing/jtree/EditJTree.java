package test.study.swing.jtree;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class EditJTree {

	JFrame jf;
	JTree tree;
	
	//定义Jtree对象对应的model
	DefaultTreeModel model;
	
	//定义初始节点
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
	DefaultMutableTreeNode sd = new DefaultMutableTreeNode("山东");
	DefaultMutableTreeNode jn = new DefaultMutableTreeNode("济南");
	DefaultMutableTreeNode qd = new DefaultMutableTreeNode("青岛");
	DefaultMutableTreeNode lx = new DefaultMutableTreeNode("历下区");
	DefaultMutableTreeNode lc = new DefaultMutableTreeNode("历城区");
	
	//定义需要拖动的TreePath
	TreePath movepath;
	JButton addSiblingButton = new JButton("添加兄弟节点");
	JButton addChildButton = new JButton("添加子节点");
	JButton deleteButton = new JButton("删除节点");
	JButton editButton = new JButton("编辑当前节点");
	
	public void init(){
	
		jn.add(lc);
		jn.add(lx);
		sd.add(jn);
		sd.add(qd);
		root.add(sd);
		tree = new JTree(root);
		
		jf = new JFrame("可编辑的树");
		
		//获取Jtree对应的TreeModel对象
		model = (DefaultTreeModel)tree.getModel();
		
		//设置Jtree可编辑
		tree.setEditable(true);
		
		//为Jtree添加鼠标监听器
		tree.addMouseListener(new MouseAdapter(){

			//鼠标按下时获得被拖动的节点。
			public void mousePressed(MouseEvent e) {
				
				//如果需要唯一确定某个节点，必须通过TreePath来获取
				TreePath tp = tree.getPathForLocation(e.getX(), e.getY());//返回指定位置处的节点路径。
				if(tp!=null){
					movepath = tp;
				}
			}

			//鼠标松开时获得需要拖到哪个父节点下
			public void mouseReleased(MouseEvent e) {
			
				TreePath tp = tree.getPathForLocation(e.getX(), e.getY());tree.setSelectionPath(tp);
				if(tp!=null&&movepath!=null){
					
					//阻止向子节点拖动
					if(movepath.isDescendant(tp)&&movepath!=tp){//movepath.isDescendant(tp)//movepath如果是tp的后代则返回true
						JOptionPane.showMessageDialog(jf, "目标节点时被移动节点的子节点，无法移动！","非法操作",JOptionPane.ERROR_MESSAGE);
						return;
					}
					//既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
					else if(movepath!=tp){
						
						//add方法可以先将原节点从原父节点删除，再添加到新父节点中。
						((DefaultMutableTreeNode)tp.getLastPathComponent()).add((DefaultMutableTreeNode)movepath.getLastPathComponent());
						movepath=null;
						tree.updateUI();
					}
				}
			}
			
		});
		
		JPanel panel = new JPanel();
		
		//实现添加兄弟节点的监听器
		addSiblingButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				//获取选中节点
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				
				//如果节点为空，则直接返回。
				if(selectNode==null){
					return;
				}
				//获取该选中节点的父节点
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectNode.getParent();
				
				//如果父节点为空，则直接返回。
				if(parentNode==null){
					return;
				}
				
				//创建一个新节点
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("新节点");
				//获取选中节点的选中索引
				int selectedIndex = parentNode.getIndex(selectNode);//public int getIndex(TreeNode aChild)返回此节点的子节点数组中指定子节点的索引。如果指定节点不是此节点的子节点，则返回 -1。
				//在选中位置插入新节点
				model.insertNodeInto(newNode, parentNode, selectedIndex+1);
				
				//------------显示新节点，自动展开父节点---------------------
				//获取从根节点到新节点的所有节点
				TreeNode[] nodes = model.getPathToRoot(newNode);
				//使用指定的节点数组来创建TreePath
				TreePath path = new TreePath(nodes);
				//显示指定的treepath
				tree.scrollPathToVisible(path);			
			}
			
		});
		
		panel.add(addSiblingButton);
		
		//实现添加子节点的监听器
		addChildButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				//获取选中节点
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				
				//如果节点为空，则直接返回。
				if(selectNode==null){
					return;
				}
				//创建一个新节点
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("新节点");
				
				//直接通过model来添加新节点，则无需通过调用Jtree的updateUI方法
				//model.insertNodeInto(newNode, selectNode, selectNode.getChildCount());
				
				//直接通过节点添加新节点，则需要调用tree的updateUI方法
				selectNode.add(newNode);
				
				//------------显示新节点，自动展开父节点---------------------
				//获取从根节点到新节点的所有节点
				TreeNode[] nodes = model.getPathToRoot(newNode);
				//使用指定的节点数组来创建TreePath
				TreePath path = new TreePath(nodes);
				//显示指定的treepath
				tree.scrollPathToVisible(path);		
				
				tree.updateUI();
			}		
		});
		
		panel.add(addChildButton);
		
		//实现删除节点的监听器
		deleteButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				//获取选中节点
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if(selectNode!=null&&selectNode.getParent()!=null){
					
					//删除指定节点
					model.removeNodeFromParent(selectNode);
				}
			}		
		});
		
		panel.add(deleteButton);
		
		//实现编辑节点的监听器
		editButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				TreePath selectedpath = tree.getSelectionPath();
				if(selectedpath!=null){}
				tree.startEditingAtPath(selectedpath);
			}	
		});
		
		panel.add(editButton);
		jf.add(new JScrollPane(tree));
		jf.add(new JScrollPane(panel),BorderLayout.SOUTH);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	public static void main(String[] args) {
		new EditJTree().init();
	}
}
