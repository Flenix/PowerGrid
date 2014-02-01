package co.uk.silvania.powergrid.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.silvania.powergrid.IGenerator;
import co.uk.silvania.powergrid.IStorage;
import co.uk.silvania.powergrid.ITransporter;
import co.uk.silvania.powergrid.PowerGrid;

public class CableEntity extends TileEntity implements ITransporter {
	
	public double powerValue;
	public double maxPowerValue = 10;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.powerValue = nbt.getInteger("powerValue");
	}
	
	public boolean isPowerEmittingBlock(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		int blockId = world.getBlockId(x, y, z);
		Block block = Block.blocksList[blockId];
		
		if (te instanceof IGenerator || te instanceof IStorage || te instanceof ITransporter) {
			return true;
		}
		return false;
	}
	
	public void sendPowerToPowerReciever(World world, int x, int y, int z) {
		if (world.getBlockTileEntity(x, y, z) instanceof IStorage) {
			StorageEntity te = (StorageEntity)world.getBlockTileEntity(x, y, z);
			//Check there is space in the receiving entity
			if (te.getStoredPower() < te.maxPower) {
				//Check we have power to send
				if (powerValue > 0) {
					//"Send" the power
					te.setStoredPower(te.getStoredPower() + 1);
					powerValue--;
				}
			}
		}
	}
	
	public void transferPowerToCable(World world, int x, int y, int z) {
		if (world.getBlockTileEntity(x, y, z) instanceof ITransporter) {
			CableEntity te = (CableEntity)world.getBlockTileEntity(x, y, z);
			
			if (powerValue > te.getPower()) {
				te.setPower(te.getPower() + 1);
				powerValue--;
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setDouble("powerValue", this.powerValue);
	}
	
	@Override
	public void updateEntity() {
		if (powerValue > maxPowerValue) {
			powerValue = maxPowerValue;
		}
		//System.out.println("Power Value of cable: " + powerValue);
		World world = this.worldObj;
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		int rotation = 0;
		boolean top = isPowerEmittingBlock(world, x, y + 1, z);
		boolean bottom = isPowerEmittingBlock(world, x, y - 1, z);
		boolean left = isPowerEmittingBlock(world, x + 1, y, z);
		boolean right = isPowerEmittingBlock(world, x - 1, y, z);
		boolean front = isPowerEmittingBlock(world, x, y, z + 1);
		boolean back = isPowerEmittingBlock(world, x, y, z - 1);
		
		if (front) {
			//System.out.println("Sending power from front");
			sendPowerToPowerReciever(world, x, y, z + 1);
			transferPowerToCable(world, x, y, z + 1);
		}
		if (back) {
			//System.out.println("Sending power from back");
			sendPowerToPowerReciever(world, x, y, z - 1);
			transferPowerToCable(world, x, y, z - 1);
		}
		
		if (top) {
			//System.out.println("Sending power from top");
			sendPowerToPowerReciever(world, x, y + 1, z);
			transferPowerToCable(world, x, y + 1, z);
		}
		if (bottom) {
			//System.out.println("Sending power from bottom");
			sendPowerToPowerReciever(world, x, y - 1, z);
			transferPowerToCable(world, x, y - 1, z);
		}
		
		if (left) {
			//System.out.println("Sending power from left");
			sendPowerToPowerReciever(world, x + 1, y, z);
			transferPowerToCable(world, x + 1, y, z);
		}
		if (right) {
			//System.out.println("Sending power from right");
			sendPowerToPowerReciever(world, x - 1, y, z);
			transferPowerToCable(world, x - 1, y, z);
		}
	}
	
	public void setPower(double i) {
		powerValue = i;
	}
	
	public double getPower() {
		return powerValue;
	}
}
