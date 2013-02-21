package com.fjxokt.lolclient.ui;

import com.fjxokt.lolclient.ResourceConstants;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.fjxokt.lolclient.lolrtmps.model.PracticeGameSearchResult;
import com.fjxokt.lolclient.utils.ResourcesManager;

public class PracticeTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	private PracticeTableModel model;
	
	public  PracticeTable() {
		this(new ArrayList<PracticeGameSearchResult>());
	}
	
	public PracticeTable(List<PracticeGameSearchResult> games) {
		super();
		model = new PracticeTableModel(games);
		this.setModel(model);
		this.setAutoCreateRowSorter(true);
		getColumnModel().getColumn(0).setPreferredWidth(18);
		getColumnModel().getColumn(0).setMaxWidth(18);
		getColumnModel().getColumn(1).setMinWidth(200);
		getColumnModel().getColumn(3).setPreferredWidth(50);
		getColumnModel().getColumn(3).setMaxWidth(50);
		getColumnModel().getColumn(4).setPreferredWidth(50);
		getColumnModel().getColumn(4).setMaxWidth(50);
		getColumnModel().getColumn(5).setMinWidth(100);
		
		// customize private game column
	    TableColumnModel columnModel = this.getColumnModel();
	    TableColumn lock = columnModel.getColumn(0);
	    JLabel iconLb = new JLabel(ResourcesManager.getInst().getIcon(ResourceConstants.genericClientImagesPath +  "lock.png", false));
	    iconLb.setAlignmentX(Component.CENTER_ALIGNMENT);
	    iconLb.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	    lock.setHeaderRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				return (JComponent)value;
			}
		});
	    lock.setHeaderValue(iconLb);
	}
	
	public PracticeGameSearchResult getObjectAtRow(int row) {
		row = this.convertRowIndexToModel(row);
		if (row != -1) {
			return model.getRow(row);
		}
		return null;
	}
	
	public PracticeGameSearchResult getSelectedObject() {
		int row = getSelectedRow();
		if (row != -1) {
			row = this.convertRowIndexToModel(row);
			return model.getRow(row);
		}
		return null;
	}


}
