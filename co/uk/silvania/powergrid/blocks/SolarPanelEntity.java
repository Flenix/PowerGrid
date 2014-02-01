package co.uk.silvania.powergrid.blocks;

import co.uk.silvania.powergrid.IGenerator;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;

public class SolarPanelEntity extends TileEntity implements IGenerator {
	
	public double powerValue;
	public double solarValue;
	public double maxPower;
	public int rotation;
	int tickCounter = 0;
	
	public double getStoredPower() {
		return powerValue;
	}
	
	public void setStoredPower(double i) {
		powerValue = i;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("powerValue", powerValue);
		nbt.setInteger("tickCounter", tickCounter);
		nbt.setInteger("rotation", rotation);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.powerValue = nbt.getDouble("powerValue");
		this.tickCounter = nbt.getInteger("tickCounter");
		this.rotation = nbt.getInteger("rotation");
	}
	
	@Override
	public void updateEntity() {
		long time = getWorldObj().getWorldTime();
		solarValue = getWorldObj().getSkyBlockTypeBrightness(EnumSkyBlock.Sky, xCoord, yCoord + 1, zCoord) + 1;
		int tick = 5;
		
		//If it's daytime
		if (time <= 12000) {
			if (solarValue >= 8) {
				if (!worldObj.isRaining()) { //Clouds block the sun!
					powerValue = powerValue + (solarValue / 4);
				}
			}
		//If it's sunset
		} else if (time >= 12000 && time <= 13600) {
			double mathTime = time - 12000;
			double x = mathTime / 200; //Mathtime is a value between 0 and 1600. Divide by 200 to get a value between 0 and 8)
			int finalTime = (int) Math.round(x); //Round it up to an integer
			if ((solarValue - finalTime) >= 8) {
				if (!worldObj.isRaining()) {
					powerValue = powerValue + ((solarValue - finalTime) / 4);
				}
			}
		}
		//System.out.println("Tick Counter: " + tickCounter);
		
			tickCounter = 0;
			//System.out.println("Solar panel now checking for cable");
			int dir1;
			int dir2;
			int dir3;
			int dir4;
			
			if (rotation == 1) {
				dir1 = xCoord + 1;
				dir2 = xCoord - 1;
				dir3 = zCoord + 1;
				dir4 = zCoord - 1;
			} else if (rotation == 2) {
				dir1 = xCoord - 1;
				dir2 = zCoord + 1;
				dir3 = zCoord - 1;
				dir4 = xCoord + 1;
			} else if (rotation == 3) {
				dir1 = zCoord + 1;
				dir2 = zCoord - 1;
				dir3 = xCoord + 1;
				dir4 = xCoord - 1;
			} else if (rotation == 4) {
				dir1 = zCoord - 1;
				dir2 = xCoord + 1;
				dir3 = xCoord - 1;
				dir4 = zCoord + 1;
			}
			
			if (this.worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord) instanceof CableEntity) {
				//System.out.println("Solar panel has found a cable!");
				CableEntity te = (CableEntity)this.worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
				if (powerValue > 0) {
					//System.out.println("Solar panel has power,");
					if (te.getPower() < te.maxPowerValue) {
						//System.out.println("Cable has room, let's move that power over!");
						setStoredPower(getStoredPower() - 1);
						te.setPower(te.getPower() + 1);
					}
				}
			}

	}
	
	public void rotationCalculator(int rotation, int x, int z) {
		int dir1;
		int dir2;
		int dir3;
		int dir4;
		
		if (rotation == 1) {
			dir1 = x + 1;
			dir2 = x - 1;
			dir3 = z + 1;
			dir4 = z - 1;
		} else if (rotation == 2) {
			dir1 = x - 1;
			dir2 = z + 1;
			dir3 = z - 1;
			dir4 = x + 1;
		} else if (rotation == 3) {
			dir1 = z + 1;
			dir2 = z - 1;
			dir3 = x + 1;
			dir4 = x - 1;
		} else if (rotation == 4) {
			dir1 = z - 1;
			dir2 = x + 1;
			dir3 = x - 1;
			dir4 = z + 1;
		}
	}
	
	public boolean findCableOnSide(int x, int y, int z) {
		if (this.worldObj.getBlockTileEntity(x, y - 1, z) instanceof CableEntity) {
			return true;
		}
		if (this.worldObj.getBlockTileEntity(x, y + 1, z) instanceof CableEntity) {
			return true;
		}
		if (this.worldObj.getBlockTileEntity(x - 1, y, z) instanceof CableEntity) {
			return true;
		}
		if (this.worldObj.getBlockTileEntity(x + 1, y, z) instanceof CableEntity) {
			return true;
		}
		if (this.worldObj.getBlockTileEntity(x, y, z - 1) instanceof CableEntity) {
			return true;
		}
		if (this.worldObj.getBlockTileEntity(x, y, z + 1) instanceof CableEntity) {
			return true;
		}
		
	return false;	
	}
}
