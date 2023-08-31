package divisas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyComboBoxEditor implements ComboBoxEditor {
	protected JPanel panel = new JPanel();
	protected JLabel label = new JLabel();
	protected JTextField editor = new JTextField();
	private Object oldValue;

	public MyComboBoxEditor() {
		panel.setLayout(new BorderLayout());
		panel.add(label, BorderLayout.WEST);
		panel.add(editor, BorderLayout.CENTER);
	}

	@Override
	public Component getEditorComponent() {
		return panel;
	}

	@Override
	public void setItem(Object anObject) {
		if (anObject != null) {
			oldValue = anObject;
			editor.setText(anObject.toString());
			if (anObject instanceof Divisas) {
				Divisas divisa = (Divisas) anObject;
				ImageIcon banderaIcon = new ImageIcon(divisa.bandera());
				Image image = banderaIcon.getImage();
				Image scaledImage = image.getScaledInstance(BanderaRenderer.ICON_SIZE, BanderaRenderer.ICON_SIZE,
						Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(scaledImage));
			}
		} else {
			editor.setText("");
		}
	}

	@Override
	public Object getItem() {
		Object newValue = editor.getText();
		if (oldValue != null && !(oldValue instanceof String)) {
			if (newValue.equals(oldValue.toString())) {
				return oldValue;
			} else {
				// Implementación personalizada para el cast a un nuevo tipo.
			}
		}
		return newValue;
	}

	@Override
	public void selectAll() {
		editor.selectAll();
		editor.requestFocus();
	}

	@Override
	public void addActionListener(ActionListener l) {
		editor.addActionListener(l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		editor.removeActionListener(l);
	}
}
