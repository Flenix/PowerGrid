package co.uk.silvania.powergrid.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientPacketHandler implements IPacketHandler {
	
	public static double storageValue;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("PowerGrid")) {
			handlePowerGridPacket(packet, player);
		}
	}
	
	private void handlePowerGridPacket(Packet250CustomPayload packet, Player player) {
		//System.out.println("Packet get!");
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			String pktID = dis.readUTF();
			double value = dis.readDouble();
			System.out.println("Packet values: " + pktID + ", " + value);
			if (pktID.equalsIgnoreCase("storageEntityPowerValue")) {
				storageValue = value;
			}
		} catch (IOException e) {
			System.out.println("[PowerGrid] Failed to read packet");
		}
	}
}
