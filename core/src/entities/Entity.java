/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Jason
 */
public abstract class Entity extends Rectangle
{
   private float x;
   private float y;
   private float speed;
   private Texture textureSomethingElse;
   
   public abstract void move();
   
}
