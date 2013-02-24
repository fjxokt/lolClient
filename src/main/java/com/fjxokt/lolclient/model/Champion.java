package com.fjxokt.lolclient.model;

import java.util.Collections;
import java.util.List;

public class Champion {
	
	private Integer id;
	private String name;
	private String displayName;
	private String title;
	private String iconPath;
	private String portraitPath;
	private String splashPath;
	private String selectSoundPath;
	private String tags;
	private String description;
	private Double range;
	private Double moveSpeed;
	private Double armorBase;
	private Double armorLevel;
	private Double manaBase;
	private Double manaLevel;
	private Double criticalChanceBase;
	private Double criticalChanceLevel;
	private Double manaRegenBase;
	private Double manaRegenLevel;
	private Double healthRegenBase;
	private Double healthRegenLevel;
	private Double magicResistBase;
	private Double magicResistLevel;
	private Double healthBase;
	private Double healthLevel;
	private Double attackBase;
	private Double attackLevel;
	private Integer ratingDefense;
	private Integer ratingMagic;
	private Integer ratingDifficulty;
	private Integer ratingAttack;
	private List<String> tips;
	private List<String> opponentTips;
	private List<ChampionSkin> skins;
	private List<ChampionAbility> abilities;
	
	public Champion(/* ??? */) {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getPortraitPath() {
		return portraitPath;
	}
	public void setPortraitPath(String portraitPath) {
		this.portraitPath = portraitPath;
	}
	public String getSplashPath() {
		return splashPath;
	}
	public void setSplashPath(String splashPath) {
		this.splashPath = splashPath;
	}
	public String getSelectSoundPath() {
		return selectSoundPath;
	}
	public void setSelectSoundPath(String selectSoundPath) {
		this.selectSoundPath = selectSoundPath;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getRange() {
		return range;
	}
	public void setRange(Double range) {
		this.range = range;
	}
	public Double getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(Double moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public Double getArmorBase() {
		return armorBase;
	}
	public void setArmorBase(Double armorBase) {
		this.armorBase = armorBase;
	}
	public Double getArmorLevel() {
		return armorLevel;
	}
	public void setArmorLevel(Double armorLevel) {
		this.armorLevel = armorLevel;
	}
	public Double getManaBase() {
		return manaBase;
	}
	public void setManaBase(Double manaBase) {
		this.manaBase = manaBase;
	}
	public Double getManaLevel() {
		return manaLevel;
	}
	public void setManaLevel(Double manaLevel) {
		this.manaLevel = manaLevel;
	}
	public Double getCriticalChanceBase() {
		return criticalChanceBase;
	}
	public void setCriticalChanceBase(Double criticalChanceBase) {
		this.criticalChanceBase = criticalChanceBase;
	}
	public Double getCriticalChanceLevel() {
		return criticalChanceLevel;
	}
	public void setCriticalChanceLevel(Double criticalChanceLevel) {
		this.criticalChanceLevel = criticalChanceLevel;
	}
	public Double getManaRegenBase() {
		return manaRegenBase;
	}
	public void setManaRegenBase(Double manaRegenBase) {
		this.manaRegenBase = manaRegenBase;
	}
	public Double getManaRegenLevel() {
		return manaRegenLevel;
	}
	public void setManaRegenLevel(Double manaRegenLevel) {
		this.manaRegenLevel = manaRegenLevel;
	}
	public Double getHealthRegenBase() {
		return healthRegenBase;
	}
	public void setHealthRegenBase(Double healthRegenBase) {
		this.healthRegenBase = healthRegenBase;
	}
	public Double getHealthRegenLevel() {
		return healthRegenLevel;
	}
	public void setHealthRegenLevel(Double healthRegenLevel) {
		this.healthRegenLevel = healthRegenLevel;
	}
	public Double getMagicResistBase() {
		return magicResistBase;
	}
	public void setMagicResistBase(Double magicResistBase) {
		this.magicResistBase = magicResistBase;
	}
	public Double getMagicResistLevel() {
		return magicResistLevel;
	}
	public void setMagicResistLevel(Double magicResistLevel) {
		this.magicResistLevel = magicResistLevel;
	}
	public Double getHealthBase() {
		return healthBase;
	}
	public void setHealthBase(Double healthBase) {
		this.healthBase = healthBase;
	}
	public Double getHealthLevel() {
		return healthLevel;
	}
	public void setHealthLevel(Double healthLevel) {
		this.healthLevel = healthLevel;
	}
	public Double getAttackBase() {
		return attackBase;
	}
	public void setAttackBase(Double attackBase) {
		this.attackBase = attackBase;
	}
	public Double getAttackLevel() {
		return attackLevel;
	}
	public void setAttackLevel(Double attackLevel) {
		this.attackLevel = attackLevel;
	}
	public Integer getRatingDefense() {
		return ratingDefense;
	}
	public void setRatingDefense(Integer ratingDefense) {
		this.ratingDefense = ratingDefense;
	}
	public Integer getRatingMagic() {
		return ratingMagic;
	}
	public void setRatingMagic(Integer ratingMagic) {
		this.ratingMagic = ratingMagic;
	}
	public Integer getRatingDifficulty() {
		return ratingDifficulty;
	}
	public void setRatingDifficulty(Integer ratingDifficulty) {
		this.ratingDifficulty = ratingDifficulty;
	}
	public Integer getRatingAttack() {
		return ratingAttack;
	}
	public void setRatingAttack(Integer ratingAttack) {
		this.ratingAttack = ratingAttack;
	}
	public List<String> getTips() {
		return Collections.unmodifiableList(tips);
	}
	public void setTips(List<String> tips) {
		this.tips = tips;
	}
	public List<String> getOpponentTips() {
		return Collections.unmodifiableList(opponentTips);
	}
	public void setOpponentTips(List<String> opponentTips) {
		this.opponentTips = opponentTips;
	}

	public List<ChampionSkin> getSkins() {
		return Collections.unmodifiableList(skins);
	}

	public void setSkins(List<ChampionSkin> skins) {
		this.skins = skins;
	}

	public List<ChampionAbility> getAbilities() {
		return Collections.unmodifiableList(abilities);
	}

	public void setAbilities(List<ChampionAbility> abilities) {
		this.abilities = abilities;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Champion [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append(", title=");
		builder.append(title);
		builder.append(", abilities=");
		builder.append(abilities);
		builder.append(", armorBase=");
		builder.append(armorBase);
		builder.append(", armorLevel=");
		builder.append(armorLevel);
		builder.append(", attackBase=");
		builder.append(attackBase);
		builder.append(", attackLevel=");
		builder.append(attackLevel);
		builder.append(", criticalChanceBase=");
		builder.append(criticalChanceBase);
		builder.append(", criticalChanceLevel=");
		builder.append(criticalChanceLevel);
		builder.append(", description=");
		builder.append(description);
		builder.append(", healthBase=");
		builder.append(healthBase);
		builder.append(", healthLevel=");
		builder.append(healthLevel);
		builder.append(", healthRegenBase=");
		builder.append(healthRegenBase);
		builder.append(", healthRegenLevel=");
		builder.append(healthRegenLevel);
		builder.append(", iconPath=");
		builder.append(iconPath);
		builder.append(", magicResistBase=");
		builder.append(magicResistBase);
		builder.append(", magicResistLevel=");
		builder.append(magicResistLevel);
		builder.append(", manaBase=");
		builder.append(manaBase);
		builder.append(", manaLevel=");
		builder.append(manaLevel);
		builder.append(", manaRegenBase=");
		builder.append(manaRegenBase);
		builder.append(", manaRegenLevel=");
		builder.append(manaRegenLevel);
		builder.append(", moveSpeed=");
		builder.append(moveSpeed);
		builder.append(", opponentTips=");
		builder.append(opponentTips);
		builder.append(", portraitPath=");
		builder.append(portraitPath);
		builder.append(", range=");
		builder.append(range);
		builder.append(", ratingAttack=");
		builder.append(ratingAttack);
		builder.append(", ratingDefense=");
		builder.append(ratingDefense);
		builder.append(", ratingDifficulty=");
		builder.append(ratingDifficulty);
		builder.append(", ratingMagic=");
		builder.append(ratingMagic);
		builder.append(", selectSoundPath=");
		builder.append(selectSoundPath);
		builder.append(", skins=");
		builder.append(skins);
		builder.append(", splashPath=");
		builder.append(splashPath);
		builder.append(", tags=");
		builder.append(tags);
		builder.append(", tips=");
		builder.append(tips);
		builder.append("]");
		return builder.toString();
	}

}
