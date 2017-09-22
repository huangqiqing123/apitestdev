package test.study.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class UnDo extends JFrame {

	private static final long serialVersionUID = -2397593626990759111L;

	private JPanel pane = null;

	private JButton undo = null, redo = null;

	private JScrollPane sPane = null;

	private JTextArea text = null;

	private Document doc = null;

	private UndoManager undomang = null;

	public UnDo() {
		super("Redo   and   Undo ");
		undomang = new UndoManager() {
			private static final long serialVersionUID = -5960092671497318496L;

			public void undoableEditHappened(UndoableEditEvent e) {
				this.addEdit(e.getEdit());
			}
		};
		text = new JTextArea();
		doc = text.getDocument();
		redo = new JButton("> > ");
		undo = new JButton(" < < ");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (undomang.canUndo())
					undomang.undo();
			}
		});
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (undomang.canRedo())
					undomang.redo();
			}
		});
		pane = new JPanel();
		pane.add(undo);
		pane.add(redo);
		doc.addUndoableEditListener(undomang);
		sPane = new JScrollPane(text);
		this.getContentPane().add(sPane);
		this.getContentPane().add(pane, BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		new UnDo();
	}

}