package com.fjxokt.lolclient.ui.chat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import com.fjxokt.lolclient.messaging.MessagingManager;
import com.fjxokt.lolclient.utils.ResourcesManager;

public class ChatWin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final MessagingManager manager = MessagingManager.getInst();
	
	private List<ChatItem> buddiesList;
	private JList contact;
	
	// wether a user or a group, group will have a specific behavior when clicking (how/hide)
	private class ChatItem {
		String groupName;
		List<ChatItem> buddies; // for group
		RosterEntry buddy;
		boolean isExpended = true;
	}
	
	private class BuddyListRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getListCellRendererComponent(JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			
			ChatItem item = (ChatItem)value;
			
			RosterEntry buddy = item.buddy;
			
			if (buddy != null) {
				Icon statusIcon = null;
				if (manager.isBuddyAvailable(buddy)) {
					statusIcon = ResourcesManager.getInst().getIcon("images" + File.separator + "circle_green.jpg");
				}
				else if (manager.isBuddyAway(buddy)) {
					statusIcon = ResourcesManager.getInst().getIcon("images" + File.separator + "circle_yellow.jpg");
				}
				else if (manager.isBuddyBusy(buddy)) {
					statusIcon = ResourcesManager.getInst().getIcon("images" + File.separator + "circle_red.jpg");
				}
				else {
					statusIcon = ResourcesManager.getInst().getIcon("images" + File.separator + "circle_gray.jpg");
				}
				label.setIcon(statusIcon);
				label.setText(buddy.getName());
			}
			else {
				String groupStr = (item.isExpended ? " - " : " + ") + item.groupName + " (" + item.buddies.size() + ")";
				label.setText(groupStr);
				label.setFont(label.getFont().deriveFont(Font.BOLD));
			}
			
			return label;
		}
	}
	
	public boolean isGroupExpanded(String group) {
		for (ChatItem item : buddiesList) {
			if (item.groupName.equals(group)) {
				return item.isExpended;
			}
		}
		// should never happen
		return false;
	}
	
	public void expandGroup(String group, boolean state) {
		for (int i=0; i<buddiesList.size(); i++) {
			ChatItem item = buddiesList.get(i);
			// nothing to do
			if (item.groupName.equals(group) && item.isExpended == state) {
				return;
			}
			if (item.groupName.equals(group) && item.isExpended != state) {
				// need to add the sub items
				if (state == true) {
					for (int j=item.buddies.size(); j>0; j--) {
						buddiesList.add(i+1, item.buddies.get(j-1));
					}
				}
				// remove sub items
				else {
					for (int j=i+1; j<buddiesList.size(); j++) {
						for (ChatItem c : item.buddies) {
							buddiesList.remove(c);
						}
					}
				}
				item.isExpended = state;
				// bye
				return;
			}
		}
	}
	
	
	public void updateListData() {
		for (int i=0; i<buddiesList.size(); i++) {
			ChatItem item = buddiesList.get(i);
			if (item.buddy == null) {
				expandGroup(item.groupName, item.isExpended);
			}
		}
	}
	
	public void updateListModel() {
		DefaultListModel model = new DefaultListModel();
		for (ChatItem it : buddiesList) {
			model.addElement(it);
		}
		contact.setModel(model);
	}
	
	public void refreshList() {
		
		Map<String, Boolean> states = new HashMap<String, Boolean>();
		for (ChatItem it : buddiesList) {
			if (it.buddy == null) {
				states.put(it.groupName, it.isExpended);
			}
		}
		
		buddiesList = buildList();
		for (ChatItem it : buddiesList) {
			if (it.buddy == null && states.containsKey(it.groupName)) {
				expandGroup(it.groupName, states.get(it.groupName));
			}
		}
				
		updateListData();
		updateListModel();
	}
	
	public List<ChatItem> buildList() {
		List<ChatItem> list = new ArrayList<ChatItem>();
		
		Collection<RosterEntry> buddies = manager.getBuddyList();
		
		// create our map <group, list of buddies for the group>
		SortedMap<String, List<RosterEntry>> map = new TreeMap<String, List<RosterEntry>>();
		for (RosterEntry bud : buddies) {
			// skip buddies without group (what does this mean ? need to be added ?)
			if (bud.getGroups().isEmpty()) {
				continue;
			}
			Object o = (bud.getGroups().toArray())[0];
			String groupN = ((RosterGroup)o).getName();
			List<RosterEntry> entries = map.get(groupN);
			if (entries == null) {
				entries = new ArrayList<RosterEntry>();
				map.put(groupN, entries);
			}
			entries.add(bud);
		}
		
		for (String group : map.keySet()) {
			List<RosterEntry> lst = map.get(group);
			// filter group members
			Collections.sort(lst, new Comparator<RosterEntry>() {
				@Override
				public int compare(RosterEntry o1, RosterEntry o2) {
					// connected one are on top
					if (manager.isBuddyAvailable(o1) && (!manager.isBuddyAvailable(o2) || manager.isBuddyAway(o2))) {
						return -1;
					}
					if (manager.isBuddyAvailable(o2) && (!manager.isBuddyAvailable(o1) || manager.isBuddyAway(o1))) {
						return 1;
					}
					return o1.getName().compareTo(o2.getName());
				}
			});
			// create group object
			ChatItem it = new ChatItem();
			it.groupName = group;
			it.buddies = new ArrayList<ChatItem>();
			list.add(it);
			
			for (RosterEntry ent : lst) {
				ChatItem ite = new ChatItem();
				ite.groupName = group;
				ite.buddy = ent;
				it.buddies.add(ite);
				list.add(ite);
			}
		}
		
		return list;
	}
	
	public ChatWin() {
		super("Chat");
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        
        System.out.println("Creating chat window");
        
        contact = new JList();
        contact.setCellRenderer(new BuddyListRenderer());
        contact.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() > 1) {
                    ChatItem it = (ChatItem)contact.getSelectedValue();
                    if (it != null) {
	                    // click on a group
	                    if (it.buddy == null) {
	                    	expandGroup(it.groupName, !it.isExpended);
	                    	updateListModel();
	                    }
	                    // click on a buddy
	                    else {
	                    	ChatWinManager.getInst().getWindow(it.buddy.getUser());
	                    }
                    }
                }
            }
        });

        DefaultListModel model = new DefaultListModel();
        buddiesList = buildList();
        for (ChatItem ite : buddiesList) {
        	model.addElement(ite);
        }
        contact.setModel(model);
        
        JScrollPane scroll = new JScrollPane(contact);
        add(scroll);
        
        pack();
        setVisible(true);
	}

}
