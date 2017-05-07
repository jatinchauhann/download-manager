package downloadmanager;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

//this class renders a JProgressBar in a table cell
class ProgressRenderer extends JProgressBar implements TableCellRenderer {
	// constructor for progress renderer
	public ProgressRenderer(int min, int max) {
		super(min, max);
	}

	// returns this JProgressBar as a renderer for a given table cell
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// set JProgressBar's percentage complete value
		setValue((int) ((Float) value).floatValue());
		return this;
	}
}
