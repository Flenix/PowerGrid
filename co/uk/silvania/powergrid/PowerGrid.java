package co.uk.silvania.powergrid;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import co.uk.silvania.powergrid.blocks.BlockCable;
import co.uk.silvania.powergrid.blocks.BlockSolarPanel;
import co.uk.silvania.powergrid.blocks.BlockStorage;
import co.uk.silvania.powergrid.blocks.CableEntity;
import co.uk.silvania.powergrid.blocks.SolarPanelEntity;
import co.uk.silvania.powergrid.blocks.StorageEntity;
import co.uk.silvania.powergrid.network.ClientPacketHandler;
import co.uk.silvania.powergrid.network.ServerPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid=PowerGrid.modid, dependencies="after:flenixcities", name="PowerGrid", version="0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, 
	clientPacketHandlerSpec = @SidedPacketHandler(channels={"PowerGrid"}, packetHandler = ClientPacketHandler.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels={"PowerGrid"}, packetHandler = ServerPacketHandler.class))
public class PowerGrid { 
	
	public static final String modid = "PowerGrid";
	
	public static Block solarPanel;
	public static Block blockStorage;
	public static Block cableBlock;
	
    @Instance(PowerGrid.modid)
    public static PowerGrid instance;
    //TODO public static GuiHandler cityGuiHandler = new GuiHandler();

    @SidedProxy(clientSide="co.uk.silvania.powergrid.client.ClientProxy", serverSide="co.uk.silvania.powergrid.CommonProxy")
    public static CommonProxy proxy;
    
	public static CreativeTabs tabPowerGrid = new CreativeTabs("tabPowerGrid") {
		public ItemStack getIconItemStack() {
			return new ItemStack(Block.anvil, 1, 0);
		}
	};
	
	public static String configPath;
	
	//TODO public static WorldGen worldGen = new WorldGen();

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		MinecraftServer server = MinecraftServer.getServer();
	}
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    	
    	//configPath = event.getModConfigurationDirectory() + "/FlenixCities/";
    	//PowerGridConfig.init(configPath);
        
    	//TODO NetworkRegistry.instance().registerGuiHandler(this, cityGuiHandler);
       
    	solarPanel = new BlockSolarPanel(500);
    	blockStorage = new BlockStorage(501);
    	cableBlock = new BlockCable(502);
    	GameRegistry.registerBlock(solarPanel, "solarPanel");
    	GameRegistry.registerBlock(blockStorage, "storageBlock");
		GameRegistry.registerBlock(cableBlock, "cableBlock");
    	LanguageRegistry.addName(solarPanel, "Solar Panel");

        
        //MinecraftForge.EVENT_BUS.register(new EventListener());
        
    	GameRegistry.registerTileEntity(SolarPanelEntity.class, "solarPanel");
    	GameRegistry.registerTileEntity(StorageEntity.class, "storage");
    	GameRegistry.registerTileEntity(CableEntity.class, "cable");
        
        LanguageRegistry.instance().addStringLocalization("itemGroup.tabPowerGrid", "en_US", "PowerGrid");            
        
        /*OreDictionary.registerOre("oreCopper", new ItemStack(CoreBlocks.copperOre));
        OreDictionary.registerOre("oreTin", new ItemStack(CoreBlocks.tinOre));
        OreDictionary.registerOre("oreSilver", new ItemStack(CoreBlocks.silverOre));
        OreDictionary.registerOre("oreTitanium", new ItemStack(CoreBlocks.titaniumOre));
        OreDictionary.registerOre("oreRuby", new ItemStack(CoreBlocks.rubyOre));
        OreDictionary.registerOre("oreTecmonium", new ItemStack(CoreBlocks.tecmoniumOre));
        OreDictionary.registerOre("oreCrystal", new ItemStack(CoreBlocks.crystalOre));
        OreDictionary.registerOre("oreSapphire", new ItemStack(CoreBlocks.sapphireOre));
        OreDictionary.registerOre("ingotCopper", new ItemStack(CoreItems.copperIngot));
        OreDictionary.registerOre("ingotTin", new ItemStack(CoreItems.tinIngot));
        OreDictionary.registerOre("ingotSilver", new ItemStack(CoreItems.silverIngot));
        OreDictionary.registerOre("ingotTitanium", new ItemStack(CoreItems.titaniumIngot));
        OreDictionary.registerOre("ingotTecmonium", new ItemStack(CoreItems.tecmoniumIngot));
        OreDictionary.registerOre("gemRuby", new ItemStack(CoreItems.rubyItem));
        OreDictionary.registerOre("gemCrystal", new ItemStack(CoreItems.crystalItem));
        OreDictionary.registerOre("gemSapphire", new ItemStack(CoreItems.sapphireItem));*/
    }
               
    @EventHandler
    public void Init(FMLInitializationEvent event) {
        //proxy.addRecipes();
    }


    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
};