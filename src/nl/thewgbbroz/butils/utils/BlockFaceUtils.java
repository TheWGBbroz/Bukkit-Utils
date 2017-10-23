package nl.thewgbbroz.butils.utils;

import org.bukkit.block.BlockFace;

public class BlockFaceUtils {
	private BlockFaceUtils() {
	}
	
	private static final double HALFROOTOFTWO = 0.707106781;
	private static final BlockFace[] AXIS = new BlockFace[4];
	private static final BlockFace[] RADIAL = { BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH,
			BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST };
	
	public static double cos(BlockFace face) {
		switch (face) {
		case SOUTH_WEST:
		case NORTH_WEST:
			return -HALFROOTOFTWO;
		case SOUTH_EAST:
		case NORTH_EAST:
			return HALFROOTOFTWO;
		case EAST:
			return 1;
		case WEST:
			return -1;
		default:
			return 0;
		}
	}
	
	public static double sin(BlockFace face) {
		switch (face) {
		case NORTH_EAST:
		case NORTH_WEST:
			return -HALFROOTOFTWO;
		case SOUTH_WEST:
		case SOUTH_EAST:
			return HALFROOTOFTWO;
		case NORTH:
			return -1;
		case SOUTH:
			return 1;
		default:
			return 0;
		}
	}
	
	public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
		if (useSubCardinalDirections) {
			return RADIAL[Math.round(yaw / 45f) & 0x7];
		} else {
			return AXIS[Math.round(yaw / 90f) & 0x3];
		}
	}
}
