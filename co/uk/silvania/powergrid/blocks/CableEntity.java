package co.uk.silvania.powergrid.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import co.uk.silvania.powergrid.ITransporter;

public class CableEntity extends TileEntity implements ITransporter {
	
	public int powerValue;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.powerValue = nbt.getInteger("powerValue");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("powerValue", this.powerValue);
	}

}
