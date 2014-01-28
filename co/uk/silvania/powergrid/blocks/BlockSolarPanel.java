package co.uk.silvania.powergrid.blocks;

import java.util.Random;

import co.uk.silvania.powergrid.PowerGrid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockSolarPanel extends BlockContainer {

	public BlockSolarPanel(int id) {
		super(id, Material.iron);
		this.setCreativeTab(PowerGrid.tabPowerGrid);
		this.setUnlocalizedName("solarPanel");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SolarPanelEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float j, float k, float l) {
		SolarPanelEntity tileEntity = (SolarPanelEntity) world.getBlockTileEntity(x, y, z);
		if (!world.isRemote) {
			if (tileEntity != null) {
        		if (player.isSneaking()) {
        			tileEntity.powerValue = 0;
        			System.out.println("Storage cleared!");
        		}
        		System.out.println("Light Level:" + tileEntity.solarValue);
        		System.out.println("Stored power: " + tileEntity.powerValue);
        	}
        }
        
        return true;
	}
	
}
