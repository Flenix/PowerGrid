package co.uk.silvania.powergrid.blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiPowerStorage extends GuiContainer {
	
	public static final ResourceLocation texture = new ResourceLocation("powergrid", "textures/gui/storage.png");
	private StorageEntity storageTE;

	public GuiPowerStorage(InventoryPlayer inventory, StorageEntity te) {
		super(new ContainerPowerStorage(inventory, te));
	}
	
	protected int xSize;
	protected int ySize;
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		fontRenderer.drawString("Storage Unit", xSize + 2, ySize + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1, 1, 1, 1);
		this.mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(xSize, ySize, 0, 0, xSize, ySize);
		// TODO Auto-generated method stub
		
	}

}
