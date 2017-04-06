/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Jason
 */
public class Potion 
{
    private String potionName;
    private String potionType;
    private float healAmount;
    private float energyAmount;
    private int quantity;

    /**
     * @return the potionName
     */
    public String getPotionName() {
        return potionName;
    }

    /**
     * @param potionName the potionName to set
     */
    public void setPotionName(String potionName) {
        this.potionName = potionName;
    }

    /**
     * @return the potionType
     */
    public String getPotionType() {
        return potionType;
    }

    /**
     * @param potionType the potionType to set
     */
    public void setPotionType(String potionType) {
        this.potionType = potionType;
    }

    /**
     * @return the healAmount
     */
    public float getHealAmount() {
        return healAmount;
    }

    /**
     * @param healAmount the healAmount to set
     */
    public void setHealAmount(float healAmount) {
        this.healAmount = healAmount;
    }

    /**
     * @return the energyAmount
     */
    public float getEnergyAmount() {
        return energyAmount;
    }

    /**
     * @param energyAmount the energyAmount to set
     */
    public void setEnergyAmount(float energyAmount) {
        this.energyAmount = energyAmount;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
