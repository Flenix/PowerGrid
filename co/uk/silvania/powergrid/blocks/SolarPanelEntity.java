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

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("powerValue", powerValue);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.powerValue = nbt.getDouble("powerValue");
	}
	
	@Override
	public void updateEntity() {
		long time = getWorldObj().getWorldTime();
		solarValue = getWorldObj().getSkyBlockTypeBrightness(EnumSkyBlock.Sky, xCoord, yCoord + 1, zCoord) + 1;
		
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
	}
}
