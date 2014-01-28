package co.uk.silvania.powergrid.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer {

	protected BlockCable(int id) {
		super(id, Material.cloth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
