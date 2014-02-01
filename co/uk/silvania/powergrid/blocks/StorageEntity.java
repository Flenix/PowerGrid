package co.uk.silvania.powergrid.blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.silvania.powergrid.IStorage;
import co.uk.silvania.powergrid.PowerGrid;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class StorageEntity extends TileEntity implements IStorage {
	
	public double storedPower;
	public double maxPower = 10000;
	
	public void setStoredPower(double i) {
		storedPower = i;
	}
	
	public double getStoredPower() {
		return storedPower;
	}
	
	public double getMaxPower() {
		return maxPower;
	}
	
	//public boolean canAcceptPower();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.storedPower = nbt.getDouble("powerValue");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("powerValue", storedPower);
	}
	
	@Override
	public void updateEntity() {		
		//Send packet with stored power amount!
		ByteArrayOutputStream bt = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bt);
        try {
                out.writeUTF("storageEntityPowerValue");
                out.writeDouble(storedPower);
                
                Packet250CustomPayload packet = new Packet250CustomPayload("PowerGrid", bt.toByteArray());
                
                PacketDispatcher.sendPacketToAllPlayers(packet);
                //System.out.println("Balance Packet sent! Value: " + storedPower);
        }
        catch (IOException ex) {
                System.out.println("Packet Failed!");
        }
        
        this.worldObj.notifyBlockChange(xCoord, yCoord, zCoord, PowerGrid.blockStorage.blockID);
	}

	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

}
