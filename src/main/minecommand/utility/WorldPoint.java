/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecommand.utility;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

/**
 * Simple representation of Minecraft locations.
 * @author RlonRyan
 */
public class WorldPoint {
    
    public final int x, y, z, dim;

    public WorldPoint(int x, int y, int z, int dim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }
    
    public WorldPoint(BlockPos pos, int dim) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.dim = dim;
    }
    
    public WorldPoint(MovingObjectPosition pos, int dim) {
        this(pos.getBlockPos(), dim);
    }
    
    public WorldPoint(Entity ent) {
        this(ent.getPosition(), ent.dimension);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utilities.getDimensionName(dim)).append(": ");
        sb.append('[').append(this.x).append(',').append(this.y).append(',').append(this.z).append(']');
        return sb.toString();
    }
    
}
