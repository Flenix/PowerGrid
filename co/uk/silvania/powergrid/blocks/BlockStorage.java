package co.uk.silvania.powergrid.blocks;

import java.util.Random;

import co.uk.silvania.powergrid.PowerGrid;
import co.uk.silvania.powergrid.network.ClientPacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockStorage extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private Icon iconTop;
	@SideOnly(Side.CLIENT)
	private Icon[] iconSides;
	
	int finalAmount;
	int storageAmount = (int) Math.round(ClientPacketHandler.storageValue / 1000);

	public BlockStorage(int id) {
		super(id, Material.iron);
		this.setCreativeTab(PowerGrid.tabPowerGrid);
		this.setUnlocalizedName("blockStorage");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new StorageEntity();
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		//System.out.println("Randomly displaying! Power: " + storageAmount);
		//System.out.println("Original value: " + ClientPacketHandler.storageValue);
		int roundedAmount = (int) Math.round(ClientPacketHandler.storageValue);
		//System.out.println("Rounded up to: " + roundedAmount);
		int storageAmount = roundedAmount / 1000;
		//System.out.println("Then divided by 1000 to get: " + storageAmount);
		finalAmount = storageAmount;
		//System.out.println("Finally, set the finalAmount variable to match: " + finalAmount);
		world.markBlockForRenderUpdate(x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float j, float k, float l) {
		StorageEntity tileEntity = (StorageEntity) world.getBlockTileEntity(x, y, z);
		//storageAmount = (int) Math.round(tileEntity.storedPower / 1000);
		if (!world.isRemote) {
			if (tileEntity != null) {
        		if (player.isSneaking()) {
        			tileEntity.storedPower = 0;
        			System.out.println("Storage cleared!");
        		}
        		player.openGui(PowerGrid.instance, 0, world, x, y, z);
        		//System.out.println("Stored power: " + tileEntity.storedPower + " set to " + ((int) Math.round(tileEntity.storedPower / 1000)));
        	}
        }
		world.markBlockForRenderUpdate(x, y, z);
        return true;
	}
	
	public void registerIcons(IconRegister icon) {
		iconSides = new Icon[11];
		
		for (int i = 0; i < iconSides.length; i++) {
			iconTop = icon.registerIcon(PowerGrid.modid + ":" + (this.getUnlocalizedName().substring(5)) + "_top");
			iconSides[i] = icon.registerIcon(PowerGrid.modid + ":" + (this.getUnlocalizedName().substring(5)) + "_sides_" + i);
		}
	}
	
	public Icon getIcon(int side, int meta) {
		if (side == 1) {
			return iconTop;
		}
		return iconSides[storageAmount];
	}
}
