package cute.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import cute.util.types.BlockInfo;
import cute.util.types.VirtualBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Cache 
{

	public static ArrayList<String> POTION_NAMES = new ArrayList<String>();
	
	public static void loadCache()
	{
		POTION_NAMES.clear();
		
		Set<ResourceLocation> pots = Potion.getPotionLocations();
		
		for(ResourceLocation rl : pots)
		{
//			System.out.println(rl.toString());
			POTION_NAMES.add(rl.toString());
//			potionCache.add(Potion.getPotionFromResourceLocation(rl.toString()));
		}
		
		
		
//		
//		// this is all stupid, but who cares, this function should only
//		// be called once at startup
//		Set<ResourceLocation> bloc = Block.blockRegistry.getKeys();
//		
//		// using this to sort by in game name
//		Set<String> blocs = new TreeSet<String>();
//		
//		for(ResourceLocation rl : bloc)
//		{
//			Block b = Block.blockRegistry.getObject(rl);
//			
//			if(b == Blocks.air)
//				continue;
//			
//			// adding the resource location at the end so i can use it later
//			blocs.add(b.getLocalizedName() + "|" + rl.toString());
//		}
//		
//		// now that we have the sorted block names, fill the list
//		Iterator<String> iterator = blocs.iterator();
//        while (iterator.hasNext())
//        {
//        	// this is just really good 
//        	String n = iterator.next();
//            String[] name_id = n.split("[|]");
//            
//            BLOCKS.add(new BlockInfo(new ResourceLocation(name_id[1])));
//        }
//        
        
//        for(BlockInfo bi : BLOCKS)
//        {
//        	System.out.println(bi.displayName + " | " + bi.registryName);
//        }
	}
	
	
	
	
	
	
	
	public static Object[] searchForBlock(String lookup)
	{
		Stream<VirtualBlock> s = BLOCKS.stream().
				filter(x -> x.displayName.toLowerCase().contains(lookup));
		
		return s.toArray();
		
	}
	
	
	
	public static List<VirtualBlock> BLOCKS = Arrays.asList(
//			new VirtualBlock(0  , "Air"                  , new ResourceLocation("air"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is stone, granite, polished granite, andesite, polished andesite, diorite, polished diorite
			new VirtualBlock(1  , "Stone"				 , new ResourceLocation("stone"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			new VirtualBlock(2  , "Grass"                , new ResourceLocation("grass"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(3  , "Dirt"                 , new ResourceLocation("dirt"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(4  , "Cobblestone"          , new ResourceLocation("cobblestone"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is for all wood planks 
			new VirtualBlock(5  , "Plank (All)"          , new ResourceLocation("planks"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			new VirtualBlock(6  , "Sapling"              , new ResourceLocation("sapling"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(7  , "Bedrock"              , new ResourceLocation("bedrock"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(8  , "Flowing Water"        , new ResourceLocation("flowing_water"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(9  , "Water"                , new ResourceLocation("water"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(10 , "Flowing Lava"         , new ResourceLocation("flowing_lava"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(11 , "Lava"                 , new ResourceLocation("lava"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(12 , "Sand"                 , new ResourceLocation("sand"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(13 , "Gravel"               , new ResourceLocation("gravel"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(14 , "Gold Ore"             , new ResourceLocation("gold_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(15 , "Iron Ore"             , new ResourceLocation("iron_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(16 , "Coal Ore"             , new ResourceLocation("coal_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is birch, oak, spruce and jungle logs 
			new VirtualBlock(17 , "Logs 1"             , new ResourceLocation("log"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(162, "Logs 2"             , new ResourceLocation( "log2"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is for all leaves 
			new VirtualBlock(18 , "Leaves"               , new ResourceLocation("leaves"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is for both dry and wet sponge
			new VirtualBlock(19 , "Sponge (Dry Wet)"     , new ResourceLocation("sponge"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(20 , "Glass"                , new ResourceLocation("glass"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(21 , "Lapis Ore"            , new ResourceLocation("lapis_ore"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(22 , "Lapis Block"          , new ResourceLocation("lapis_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(23 , "Dispenser"            , new ResourceLocation("dispenser"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(24 , "Sandstone"            , new ResourceLocation("sandstone"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(25 , "Noteblock"            , new ResourceLocation("noteblock"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(26 , "Bed"                  , new ResourceLocation("bed"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(27 , "Golden Rail"          , new ResourceLocation("golden_rail"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(28 , "Detector Rail"        , new ResourceLocation("detector_rail"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			new VirtualBlock(30 , "Cobweb"               , new ResourceLocation("web"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(31 , "Tallgrass"            , new ResourceLocation("tallgrass"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(32 , "Deadbush"             , new ResourceLocation("deadbush"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(33 , "Piston"               , new ResourceLocation("piston"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(29 , "Sticky Piston"        , new ResourceLocation("sticky_piston"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(34 , "Piston Head"          , new ResourceLocation("piston_head"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// all color of wool
			new VirtualBlock(35 , "Wool (All)"           , new ResourceLocation("wool"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this doesn't seem to exists
//			new VirtualBlock(36 , "Piston Extension"     , new ResourceLocation("piston_extension"               ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			new VirtualBlock(37 , "Yellow Flower"        , new ResourceLocation("yellow_flower"                  ), 0.3f, 0f, 0.3f, 0.7f, 0.6f, 0.7f),
			new VirtualBlock(38 , "Red Flower"           , new ResourceLocation("red_flower"                     ), 0.3f, 0f, 0.3f, 0.7f, 0.6f, 0.7f),
			new VirtualBlock(39 , "Brown Mushroom"       , new ResourceLocation("brown_mushroom"                 ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f),
			new VirtualBlock(40 , "Red Mushroom"         , new ResourceLocation("red_mushroom"                   ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f),
			new VirtualBlock(41 , "Gold Block"           , new ResourceLocation("gold_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(42 , "Iron Block"           , new ResourceLocation("iron_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(43 , "Double Stone Slab"    , new ResourceLocation("double_stone_slab"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// this is like all slabs 
			new VirtualBlock(44 , "Stone Slab"           , new ResourceLocation("stone_slab"                     ), 0f, 0f, 0f, 1f, 0.5f, 1f),
			
			new VirtualBlock(45 , "Brick Block"          , new ResourceLocation("brick_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(46 , "TNT"                  , new ResourceLocation("tnt"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(47 , "Bookshelf"            , new ResourceLocation("bookshelf"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(48 , "Mossy Cobblestone"    , new ResourceLocation("mossy_cobblestone"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(49 , "Obsidian"             , new ResourceLocation("obsidian"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(50 , "Torch"                , new ResourceLocation("torch"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(51 , "Fire"                 , new ResourceLocation("fire"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(52 , "Mob Spawner"          , new ResourceLocation("mob_spawner"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(53 , "Oak Staris"           , new ResourceLocation("oak_stairs"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(54 , "Chest"                , new ResourceLocation("chest"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(55 , "Redstone Wire"        , new ResourceLocation("redstone_wire"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(56 , "Diamond Ore"          , new ResourceLocation("diamond_ore"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(57 , "Diamond Block"        , new ResourceLocation("diamond_block"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(58 , "Crafting Table"       , new ResourceLocation("crafting_table"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(59 , "Wheat"                , new ResourceLocation("wheat"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(60 , "Farmland"             , new ResourceLocation("farmland"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(61 , "Furnace"              , new ResourceLocation("furnace"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(62 , "Lit Furnace"          , new ResourceLocation("lit_furnace"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(63 , "Standing Sign"        , new ResourceLocation("standing_sign"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(64 , "Wooden Door"          , new ResourceLocation("wooden_door"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(65 , "Ladder"               , new ResourceLocation("ladder"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(66 , "Rail"                 , new ResourceLocation("rail"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(67 , "Stone Stairs"         , new ResourceLocation("stone_stairs"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(68 , "Wall Sign"            , new ResourceLocation("wall_sign"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(69 , "Lever"                , new ResourceLocation("lever"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(70 , "Stone Pressure Plate" , new ResourceLocation("stone_pressure_plate"           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(71 , "Iron Door"            , new ResourceLocation("iron_door"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(72 , "Wooden Pressure Plate", new ResourceLocation("wooden_pressure_plate"          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(73 , "Redstone Ore"         , new ResourceLocation("redstone_ore"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(74 , "Lit Redstone Ore"     , new ResourceLocation("lit_redstone_ore"               ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(75 , "Unlit Redstone Torch" , new ResourceLocation("unlit_redstone_torch"           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(76 , "Redtone Torch"        , new ResourceLocation("redstone_torch"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(77 , "Stone Button"         , new ResourceLocation("stone_button"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(78 , "Snow Layer"           , new ResourceLocation("snow_layer"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(79 , "Ice"                  , new ResourceLocation("ice"                            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(80 , "Snow"                 , new ResourceLocation("snow"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(81 , "Cactus"               , new ResourceLocation("cactus"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(82 , "Clay"                 , new ResourceLocation("clay"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(83 , "Sugar Cane"           , new ResourceLocation("reeds"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(84 , "Jukebox"              , new ResourceLocation("jukebox"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(85 , "Fence"                , new ResourceLocation("fence"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(86 , "Pumpkin"              , new ResourceLocation("pumpkin"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(87 , "Netherrack"           , new ResourceLocation("netherrack"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(88 , "Soul Sand"            , new ResourceLocation("soul_sand"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(89 , "Glowstone"            , new ResourceLocation("glowstone"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(90 , "Portal"               , new ResourceLocation("portal"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(91 , "Lit Pumpkin"          , new ResourceLocation("lit_pumpkin"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(92 , "Cake"                 , new ResourceLocation("cake"                           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(93 , "Unpowered Repeater"   , new ResourceLocation("unpowered_repeater"             ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(94 , "Powered Repeater"     , new ResourceLocation("powered_repeater"               ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// all stained glass colors 
			new VirtualBlock(95 , "Stained Glass"        , new ResourceLocation("stained_glass"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(96 , "Trapdoor"             , new ResourceLocation("trapdoor"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(97 , "Monster Egg"          , new ResourceLocation("monster_egg"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(98 , "Stonebrick"           , new ResourceLocation("stonebrick"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(99 , "Brown Mushroom Block" , new ResourceLocation("brown_mushroom_block"           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(100, "Red Mushroom Block"   , new ResourceLocation( "red_mushroom_block"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(101, "Iron Bars"            , new ResourceLocation( "iron_bars"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(102, "Glass Pane"           , new ResourceLocation( "glass_pane"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(103, "Melon Block"          , new ResourceLocation( "melon_block"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(104, "Pumpkin Stem"         , new ResourceLocation( "pumpkin_stem"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(105, "Melon Stem"           , new ResourceLocation( "melon_stem"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(106, "Vine"                 , new ResourceLocation( "vine"                          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(107, "Fence Gate"           , new ResourceLocation( "fence_gate"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(108, "Brick Stairs"         , new ResourceLocation( "brick_stairs"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(109, "Stone Brick Stairs"   , new ResourceLocation( "stone_brick_stairs"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(110, "Mycelium"             , new ResourceLocation( "mycelium"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(111, "Lily Pad"             , new ResourceLocation( "waterlily"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(112, "Nether Brick"         , new ResourceLocation( "nether_brick"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(113, "Nether Brick Fence"   , new ResourceLocation( "nether_brick_fence"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(114, "Nether Brick Stairs"  , new ResourceLocation( "nether_brick_stairs"           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(115, "Nether Wart"          , new ResourceLocation( "nether_wart"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(116, "Enchanting Table"     , new ResourceLocation( "enchanting_table"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(117, "Brewing Stand"        , new ResourceLocation( "brewing_stand"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(118, "Cauldron"             , new ResourceLocation( "cauldron"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(119, "End Portal"           , new ResourceLocation( "end_portal"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(120, "End Portal Frame"     , new ResourceLocation( "end_portal_frame"              ), 0f, 0f, 0f, 1f, 0.8f, 1f),
			new VirtualBlock(121, "End Stone"            , new ResourceLocation( "end_stone"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(122, "Dragon Egg"           , new ResourceLocation( "dragon_egg"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(123, "Redstone Lamp"        , new ResourceLocation( "redstone_lamp"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(124, "Lit Redstone Lamp"    , new ResourceLocation( "lit_redstone_lamp"             ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(125, "Double Wood Slab"     , new ResourceLocation( "double_wooden_slab"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(126, "Wood Slab"            , new ResourceLocation( "wooden_slab"                   ), 0f, 0f, 0f, 1f, 0.5f, 1f),
			new VirtualBlock(127, "Cocoa"                , new ResourceLocation( "cocoa"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(128, "Sandstone Stairs"     , new ResourceLocation( "sandstone_stairs"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(129, "Emerald Ore"          , new ResourceLocation( "emerald_ore"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(130, "Ender Chest"          , new ResourceLocation( "ender_chest"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(131, "Tripwire Hook"        , new ResourceLocation( "tripwire_hook"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(132, "Tripwire"             , new ResourceLocation( "tripwire"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(133, "Emerald Block"        , new ResourceLocation( "emerald_block"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(134, "Spruce Stairs"        , new ResourceLocation( "spruce_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(135, "Birch Stairs"         , new ResourceLocation( "birch_stairs"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(136, "Jungle Stairs"        , new ResourceLocation( "jungle_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(137, "Command Block"        , new ResourceLocation( "command_block"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(138, "Beacon"               , new ResourceLocation( "beacon"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(139, "Cobblestone Wall"     , new ResourceLocation( "cobblestone_wall"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(140, "Flower Pot"           , new ResourceLocation( "flower_pot"                    ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f),
			new VirtualBlock(141, "Carrots"              , new ResourceLocation( "carrots"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(142, "Potatoes"             , new ResourceLocation( "potatoes"                      ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(143, "Wooden Button"        , new ResourceLocation( "wooden_button"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(144, "Skull"                , new ResourceLocation( "skull"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(145, "Anvil"                , new ResourceLocation( "anvil"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(146, "Trapped Chest"        , new ResourceLocation( "trapped_chest"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(147, "Light Weighted Pressure Plate", new ResourceLocation( "light_weighted_pressure_plate" ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(148, "Heavy Weighted Pressure Plate", new ResourceLocation( "heavy_weighted_pressure_plate" ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(149, "Unpowered Comparator" , new ResourceLocation( "unpowered_comparator"          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(150, "Powered Comparator"   , new ResourceLocation( "powered_comparator"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(151, "Daylight Sensor"      , new ResourceLocation( "daylight_detector"             ), 0f, 0f, 0f, 1f, 0.37f, 1f),
			new VirtualBlock(178, "Daylight Sensor On"   , new ResourceLocation( "daylight_detector_inverted"    ), 0f, 0f, 0f, 1f, 0.37f, 1f),
			
			new VirtualBlock(152, "Redstone Block"       , new ResourceLocation( "redstone_block"                ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(153, "Quartz Ore"           , new ResourceLocation( "quartz_ore"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(154, "Hopper"               , new ResourceLocation( "hopper"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(155, "Quartz Block"         , new ResourceLocation( "quartz_block"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(156, "Quartz Stairs"        , new ResourceLocation( "quartz_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(157, ""                     , new ResourceLocation( "activator_rail"                ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(158, "Dropper"              , new ResourceLocation( "dropper"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(159, "Stained Hardened Clay", new ResourceLocation( "stained_hardened_clay"         ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			// all stained glass pain colors 
			new VirtualBlock(160, "Stained Glass Pane"   , new ResourceLocation( "stained_glass_pane"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(161, "Leaves 2"             , new ResourceLocation( "leaves2"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			
			new VirtualBlock(163, "Acacia Stairs"        , new ResourceLocation( "acacia_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(164, "Dark Oak Stairs"      , new ResourceLocation( "dark_oak_stairs"               ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(165, "Slime"                , new ResourceLocation( "slime"                         ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(166, "Barrier"              , new ResourceLocation( "barrier"                       ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(167, "Iron Trapdoor"        , new ResourceLocation( "iron_trapdoor"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(168, "Prismarine"           , new ResourceLocation( "prismarine"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(169, "Sea Lantern"          , new ResourceLocation( "sea_lantern"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(170, "Hay Block"            , new ResourceLocation( "hay_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(171, "Carpet"               , new ResourceLocation( "carpet"                        ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(172, "Hardened Clay"        , new ResourceLocation( "hardened_clay"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(173, "Coal Block"           , new ResourceLocation( "coal_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(174, "Packed Ice"           , new ResourceLocation( "packed_ice"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(175, "Double Plant"         , new ResourceLocation( "double_plant"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(176, "Standing Banner"      , new ResourceLocation( "standing_banner"               ), 0f, 0f, 0f, 1f, 1.8f, 1f),
			new VirtualBlock(177, "Wall Banner"          , new ResourceLocation( "wall_banner"                   ), 0f, 0f, 0f, 1f, 1.8f, 1f),
			
			new VirtualBlock(179, "Red Sandstone"        , new ResourceLocation( "red_sandstone"                 ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(180, "Red Sandstone Stairs" , new ResourceLocation( "red_sandstone_stairs"          ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(181, "Double Stone Slab 2"  , new ResourceLocation( "double_stone_slab2"            ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(182, "Stone Slab 2"         , new ResourceLocation( "stone_slab2"                   ), 0f, 0f, 0f, 1f, 0.5f, 1f),
			new VirtualBlock(183, "Spruce Fence Gate"    , new ResourceLocation( "spruce_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(184, "Birch Fence Gate"     , new ResourceLocation( "birch_fence_gate"              ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(185, "Jungle Fence Gate"    , new ResourceLocation( "jungle_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(186, "Dark Oah Fence Gate"  , new ResourceLocation( "dark_oak_fence_gate"           ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(187, "Acacia Fence Gate"    , new ResourceLocation( "acacia_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(188, "Spruce Fence"         , new ResourceLocation( "spruce_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(189, "Birch Fence"          , new ResourceLocation( "birch_fence"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(190, "Jungle Fence"         , new ResourceLocation( "jungle_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(191, "Dark Oak Fence"       , new ResourceLocation( "dark_oak_fence"                ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(192, "Acacia Fence"         , new ResourceLocation( "acacia_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(193, "Spruce Door"          , new ResourceLocation( "spruce_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(194, "Birch Door"           , new ResourceLocation( "birch_door"                    ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(195, "Jungle Door"          , new ResourceLocation( "jungle_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(196, "Acacia Door"          , new ResourceLocation( "acacia_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f),
			new VirtualBlock(197, "Dark Oah Door"        , new ResourceLocation( "dark_oak_door"                 ), 0f, 0f, 0f, 1f, 1f, 1f)
	);
	
}







