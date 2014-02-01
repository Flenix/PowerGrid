package co.uk.silvania.powergrid.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerPowerStorage extends Container {
	
	protected StorageEntity tile;
	private IInventory storaveInv;

	public ContainerPowerStorage(InventoryPlayer inventory, StorageEntity te) {
		tile = te;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tile.isUsableByPlayer(entityplayer);
	}

}
