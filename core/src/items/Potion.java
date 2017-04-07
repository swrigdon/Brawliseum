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
    private float value;

    public Potion(String potionName, float value)
    {
        this.potionName = potionName;
        this.value = value;
    }
    
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
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }
        
}
