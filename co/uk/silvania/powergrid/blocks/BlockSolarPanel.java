package co.uk.silvania.powergrid.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import co.uk.silvania.powergrid.PowerGrid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSolarPanel extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private Icon iconTop;
	@SideOnly(Side.CLIENT)
	private Icon iconSides;

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
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack item) {
		int l = MathHelper.floor_double((double)(entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		SolarPanelEntity tileEntity = (SolarPanelEntity) world.getBlockTileEntity(x, y, z);
		if (l == 0) {
			tileEntity.rotation = 1;
		}
		if (l == 1) {
			tileEntity.rotation = 2;
		}
		if (l == 2) {
			tileEntity.rotation = 3;
		}
		if (l == 3) {
			tileEntity.rotation = 4;
		}
	}
	
	public void registerIcons(IconRegister icon) {
		iconTop = icon.registerIcon(PowerGrid.modid + ":" + (this.getUnlocalizedName().substring(5)) + "_top");
		iconSides = icon.registerIcon(PowerGrid.modid + ":" + (this.getUnlocalizedName().substring(5)) + "_sides");
	}
	
	public Icon getIcon(int side, int meta) {
		if (side == 1) {
			return iconTop;
		}
		return iconSides;
	}
	
}
