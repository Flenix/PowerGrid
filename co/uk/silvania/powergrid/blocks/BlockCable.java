package co.uk.silvania.powergrid.blocks;

import co.uk.silvania.powergrid.PowerGrid;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockContainer {

	public BlockCable(int id) {
		super(id, Material.cloth);
		this.setCreativeTab(PowerGrid.tabPowerGrid);
		this.setUnlocalizedName("blockCable");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new CableEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float j, float k, float l) {
		CableEntity tileEntity = (CableEntity) world.getBlockTileEntity(x, y, z);
		if (!world.isRemote) {
			if (tileEntity != null) {
        		if (player.isSneaking()) {
        			tileEntity.powerValue = 0;
        			System.out.println("Storage cleared!");
        		}
        		System.out.println("Cable currently buffering: " + tileEntity.powerValue);
        	}
        }
        return true;
	}
	
	public void registerIcons(IconRegister icon) {
		blockIcon = icon.registerIcon(PowerGrid.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}	
}
