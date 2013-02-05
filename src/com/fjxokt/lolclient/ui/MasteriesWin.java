package com.fjxokt.lolclient.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.fjxokt.lolclient.lolrtmps.model.dto.MasteryBookPageDTO;
import com.fjxokt.lolclient.lolrtmps.model.Talent;
import com.fjxokt.lolclient.lolrtmps.model.TalentEntry;
import com.fjxokt.lolclient.lolrtmps.model.TalentGroup;
import com.fjxokt.lolclient.lolrtmps.model.TalentRow;
import com.fjxokt.lolclient.lolrtmps.LoLClient;

public class MasteriesWin extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private class TalentRowPan extends JPanel {
		private static final long serialVersionUID = 1L;
		private TalentRow row;
		public TalentRowPan(TalentRow row) {
			super(new GridLayout(1, 4));
			this.setOpaque(false);
			this.row = row;
		}
		public TalentRow getTalentRow() {
			return row;
		}
	}
	
	private class TalentEntryLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		private Talent talent;
		private Integer rank;
		public TalentEntryLabel(final Talent talent) {
			super("");
			this.talent = talent;
			this.rank = 0;
			setIcon(icons.get(talent.getTltId()));
			setPreferredSize(new Dimension(50, 50));
			setForeground(Color.white);
			setAlignmentX(Component.CENTER_ALIGNMENT);
			setVerticalTextPosition(JLabel.CENTER);
			setHorizontalTextPosition(JLabel.CENTER);
			addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent arg0) {}
				public void mousePressed(MouseEvent arg0) {}
				public void mouseExited(MouseEvent arg0) {}
				public void mouseEntered(MouseEvent arg0) {}
				public void mouseClicked(MouseEvent arg0) {
					// check if the row is enabled
					TalentRowPan row = (TalentRowPan)TalentEntryLabel.this.getParent();
					System.out.println("ROW ENABLED ? : " + row.isEnabled());
					if (!row.isEnabled()) {
						return;
					}
					// this talent need another talent to be unlocked
					if (talent.getPrereqTalentGameCode() != null) {
						TalentEntryLabel l = labels.get(talent.getPrereqTalentGameCode());
						// the other talent is fully set, condition ok
						if (l.getRank().equals(l.getTalent().getMaxRank())) {
							// we can add a point
							if (rank < talent.getMaxRank()) {						
								update(rank + 1);
								TalentGroupPan gp = (TalentGroupPan)row.getParent();
								gp.update();
							}
						}
					}
					// no prerequis
					else {
						if (rank < talent.getMaxRank()) {						
							update(rank + 1);
							TalentGroupPan gp = (TalentGroupPan)row.getParent();
							gp.update();
						}
					}
				}
			});
		}
		public Talent getTalent() {
			return talent;
		}
		public Integer getRank() {
			return rank;
		}
		public void update(Integer rank) {
			this.rank = rank;
			//setText("" + talent.getTltId() + ":" + rank);
			setText("" + rank);
		}
	}
	
	private class TalentGroupPan extends JPanel {
		private static final long serialVersionUID = 1L;
		public TalentGroupPan(TalentGroup group) {
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setOpaque(false);
			// get and sort the rows
			List<TalentRow> rows = group.getTalentRows();
			Collections.sort(rows, new Comparator<TalentRow>() {
				public int compare(TalentRow o1, TalentRow o2) {
					return o1.getIndex().compareTo(o2.getIndex());
				}
			});
			for (int i=0; i<rows.size(); i++) {
				TalentRow row = rows.get(i);
				// a talent row
				TalentRowPan p = new TalentRowPan(row);
				// get and sort talents
				List<Talent> talents = row.getTalents();
				Collections.sort(talents, new Comparator<Talent>() {
					public int compare(Talent o1, Talent o2) {
						return o1.getIndex().compareTo(o2.getIndex());
					}
				});
				for (int j=0; j<4; j++) {
					Talent t = null;
					for (Talent ta : talents) {
						if (ta.getIndex().equals(j)) {
							t = ta;
							break;
						}
					}
					JLabel lb = null;
					if (t == null) {
						lb = new JLabel();
					}
					else {
						lb = new TalentEntryLabel(t);
						labels.put(t.getTltId(), (TalentEntryLabel)lb);
					}
					lb.setOpaque(false);
//				    lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					p.add(lb);
				}
				add(p);
			}
		}
		public void update() {
			// get total nb of points for this group
			Integer totalPoints = 0;
			for (Component c : getComponents()) {
				TalentRowPan row = (TalentRowPan)c;
				for (Component co : row.getComponents()) {
					if (co instanceof TalentEntryLabel) {
						TalentEntryLabel lb = (TalentEntryLabel)co;
						totalPoints += lb.rank;
					}
				}
			}
			for (Component c : getComponents()) {
				TalentRowPan row = (TalentRowPan)c;
				System.out.println("POINTS: " + totalPoints + " : " + row.getTalentRow().getPointsToActivate());
				if (totalPoints >= row.getTalentRow().getPointsToActivate()) {
					row.setEnabled(true);
				}
				else {
					row.setEnabled(false);
				}
			}
		}
	}
	
	private JPanel groupsPan;
	private Map<Integer, TalentEntryLabel> labels;
	private Map<Integer, Icon> icons;

	public MasteriesWin() {
		this.setTitle("NoAir pvp.net - Masteries");
        this.setSize(750, 450);
        this.setPreferredSize(new Dimension(750, 450));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        JPanel mainPan = new JPanel(new BorderLayout());
        
        final List<MasteryBookPageDTO> pages = LoLClient.getInst().getMasteryBook().getBookPages();
        JPanel pagesBts = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        for (int i=0; i<pages.size(); i++) {
        	final JButton b = new JButton("" + (i+1));
        	b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			        update(pages.get(Integer.parseInt(b.getText())-1));
				}
			});
        	pagesBts.add(b);
        }
//        mainPan.add(new JLabel("Number of pages: " + pages.size()));
        mainPan.add(pagesBts, BorderLayout.BEFORE_FIRST_LINE);
     
		// create map
		labels = new HashMap<Integer, TalentEntryLabel>();
        
        // get and sort groups
        List<TalentGroup> groups = LoLClient.getInst().getLoginDataPacketForUser().getSummonerCatalog().getTalentTree();
        Collections.sort(groups, new Comparator<TalentGroup>() {
			public int compare(TalentGroup o1, TalentGroup o2) {
				return o1.getIndex().compareTo(o2.getIndex());
			}
		});
             
        // create icon map
        icons = new HashMap<Integer, Icon>();
        // prepare icons
        List<Integer> ids = new ArrayList<Integer>();
        for (TalentGroup group : groups) {
        	for (TalentRow row : group.getTalentRows()) {
        		for (Talent talent : row.getTalents()) {
        			ids.add(talent.getTltId());
        		}
        	}
        }
        Collections.sort(ids);
        // create images
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("images" + File.separator + "masteries.png"));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        for (int i=0; i<ids.size(); i++) {
        	int size = 48;
        	int x = (i%10) * size;
        	int y = ((int)(i/10)) * size;
        	BufferedImage sub = img.getSubimage(x, y, size, size);
        	ImageIcon icon = new ImageIcon(sub);
        	icons.put(ids.get(i), icon);
        }
        
        groupsPan = new MasteryPanel();
        groupsPan.setBorder(new EmptyBorder(5, 15, 0, 0));
        
        for (int i=0; i<groups.size(); i++) {
        	TalentGroupPan pan = new TalentGroupPan(groups.get(i));
        	groupsPan.add(pan);
        }
        
        mainPan.add(groupsPan, BorderLayout.CENTER);
        
        this.setContentPane(mainPan);
        this.pack();
        this.setVisible(true);
        
//        MasteryBookPageDTO page = pages.get(0);
//        update(page);
	}
	
	private class MasteryPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image background;
		private Image backgroundOriginal;
		public MasteryPanel() {
			super(new GridLayout(1, 3, 15, 0));
	        try {
	        	backgroundOriginal = ImageIO.read(new File("images" + File.separator + "masteries-background.jpg"));
	        	background = backgroundOriginal;
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        addComponentListener(new ComponentAdapter() { 
	            public void componentResized(ComponentEvent e) { 
	                int w = MasteryPanel.this.getWidth(); 
	                int h = MasteryPanel.this.getHeight(); 
	                background = w>0 && h> 0 ? backgroundOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH) : backgroundOriginal; 
	                MasteryPanel.this.revalidate(); 
	            } 
	        }); 
		}
	   @Override
	    protected void paintComponent(Graphics g) {
	        g.drawImage(background, 0, 0, null);
	    }
	}
	
	public void update(MasteryBookPageDTO page) {
		System.out.println("page: " + page.getName() + " entries: " + page.getTalentEntries().size()
				+ " total pts: " + page.getTotalSetPoints());
		// update all talents
		for (TalentEntry te : page.getTalentEntries()) {
			System.out.println("talentid: " + te.getTalentId());
			if (labels.containsKey(te.getTalentId())) {
				System.out.println("updated talentid " + te.getTalentId() + " rank: " + te.getRank());
				TalentEntryLabel lb = labels.get(te.getTalentId());
				lb.update(te.getRank());
			}
		}
		// update rows
		for (Component c : groupsPan.getComponents()) {
			TalentGroupPan pan = (TalentGroupPan)c;
			pan.update();
		}
		this.repaint();
	}

}
