package com.fjxokt.lolclient.ui.champsel;

public class ChampSelection {
	private String name;
	private int spell1, spell2;
	private int champId;
	public ChampSelection(String name, int spell1, int spell2, int champId) {
		super();
		this.name = name;
		this.spell1 = spell1;
		this.spell2 = spell2;
		this.champId = champId;
	}
	public int getSpell1() {
		return spell1;
	}
	public void setSpell1(int spell1) {
		this.spell1 = spell1;
	}
	public int getSpell2() {
		return spell2;
	}
	public void setSpell2(int spell2) {
		this.spell2 = spell2;
	}
	public int getChampId() {
		return champId;
	}
	public void setChampId(int champId) {
		this.champId = champId;
	}
	public String getName() {
		return name;
	}
}