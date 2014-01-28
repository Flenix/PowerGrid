package co.uk.silvania.powergrid.blocks;

import co.uk.silvania.powergrid.PowerGrid;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStorage extends BlockContainer {

	public BlockStorage(int id) {
		super(id, Material.iron);
		this.setCreativeTab(PowerGrid.tabPowerGrid);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new StorageEntity();
	}

}
