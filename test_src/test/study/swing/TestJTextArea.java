package test.study.swing;

import javax.swing.JFrame;

public class TestJTextArea extends JFrame {
 private javax.swing.JScrollPane jScrollPane1;
 private javax.swing.JScrollPane jScrollPane2;
 private javax.swing.JSplitPane jSplitPane1;
 private javax.swing.JTextArea jTextArea1;
 private javax.swing.JTextArea jTextArea2;

 public TestJTextArea() {
  initComponents();
 }

 /**
  * @param args
  */
 public static void main(String[] args) {
  // TODO Auto-generated method stub
  TestJTextArea ta = new TestJTextArea();
  ta.setSize(400, 300);
  ta.setVisible(true);
 }

 private void initComponents() {

  jSplitPane1 = new javax.swing.JSplitPane();
  jScrollPane2 = new javax.swing.JScrollPane();
  jTextArea2 = new javax.swing.JTextArea();
  jScrollPane1 = new javax.swing.JScrollPane();
  jTextArea1 = new javax.swing.JTextArea();

  setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

  jTextArea2.setColumns(20);
  jTextArea2.setRows(5);
  jTextArea2.setDragEnabled(true);
  jScrollPane2.setViewportView(jTextArea2);

  jSplitPane1.setLeftComponent(jScrollPane2);

  jTextArea1.setColumns(20);
  jTextArea1.setRows(5);
  jTextArea1.setDragEnabled(true);
  jScrollPane1.setViewportView(jTextArea1);

  jSplitPane1.setRightComponent(jScrollPane1);

  getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

  pack();
 }
}