package co.uk.silvania.powergrid;

import co.uk.silvania.powergrid.blocks.ContainerPowerStorage;
import co.uk.silvania.powergrid.blocks.GuiPowerStorage;
import co.uk.silvania.powergrid.blocks.StorageEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: {
				TileEntity te = world.getBlockTileEntity(x,y,z);
				if (te instanceof StorageEntity) {
					return new ContainerPowerStorage(player.inventory, (StorageEntity) te);
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: {
				TileEntity te = world.getBlockTileEntity(x,y,z);
				if (te instanceof StorageEntity) {
					return new GuiPowerStorage(player.inventory, (StorageEntity) te);
				}
			}
		}
		return null;
	}

}
