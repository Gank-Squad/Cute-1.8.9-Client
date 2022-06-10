package cute.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import cute.util.types.VirtualBlock;
import cute.util.types.VirtualItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Cache 
{
	public static List<VirtualBlock> BLOCKS = new ArrayList();
	public static List<VirtualItem> ITEMS = new ArrayList();
	
	public static Object[] searchForBlock(String lookup)
	{
		Stream<VirtualBlock> s = BLOCKS.stream().
				filter(x -> x.displayName.toLowerCase().contains(lookup));
		
		return s.toArray();
		
	}
	

	public static Object[] searchForItem(String lookup)
	{
		Stream<VirtualItem> s = ITEMS.stream().
				filter(x -> x.displayName.toLowerCase().contains(lookup));
		
		return s.toArray();
		
	}
	
	
	public static void loadCache()
	{
		// BLOCKS.add(new VirtualBlock(0  , "Air"                  , new ResourceLocation("air"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is stone, granite, polished granite, andesite, polished andesite, diorite, polished diorite
	    BLOCKS.add(new VirtualBlock(1  , "Stone"				 , new ResourceLocation("stone"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(2  , "Grass"                , new ResourceLocation("grass"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(3  , "Dirt"                 , new ResourceLocation("dirt"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(4  , "Cobblestone"          , new ResourceLocation("cobblestone"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is for all wood planks 
	    BLOCKS.add(new VirtualBlock(5  , "Plank (All)"          , new ResourceLocation("planks"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(6  , "Sapling"              , new ResourceLocation("sapling"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(7  , "Bedrock"              , new ResourceLocation("bedrock"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(8  , "Flowing Water"        , new ResourceLocation("flowing_water"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(9  , "Water"                , new ResourceLocation("water"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(10 , "Flowing Lava"         , new ResourceLocation("flowing_lava"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(11 , "Lava"                 , new ResourceLocation("lava"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(12 , "Sand"                 , new ResourceLocation("sand"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(13 , "Gravel"               , new ResourceLocation("gravel"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(14 , "Gold Ore"             , new ResourceLocation("gold_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(15 , "Iron Ore"             , new ResourceLocation("iron_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(16 , "Coal Ore"             , new ResourceLocation("coal_ore"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is birch, oak, spruce and jungle logs 
	    BLOCKS.add(new VirtualBlock(17 , "Logs 1"             , new ResourceLocation("log"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(162, "Logs 2"             , new ResourceLocation( "log2"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is for all leaves 
	    BLOCKS.add(new VirtualBlock(18 , "Leaves"               , new ResourceLocation("leaves"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is for both dry and wet sponge
	    BLOCKS.add(new VirtualBlock(19 , "Sponge (Dry Wet)"     , new ResourceLocation("sponge"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(20 , "Glass"                , new ResourceLocation("glass"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(21 , "Lapis Ore"            , new ResourceLocation("lapis_ore"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(22 , "Lapis Block"          , new ResourceLocation("lapis_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(23 , "Dispenser"            , new ResourceLocation("dispenser"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(24 , "Sandstone"            , new ResourceLocation("sandstone"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(25 , "Noteblock"            , new ResourceLocation("noteblock"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(26 , "Bed"                  , new ResourceLocation("bed"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(27 , "Golden Rail"          , new ResourceLocation("golden_rail"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(28 , "Detector Rail"        , new ResourceLocation("detector_rail"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(30 , "Cobweb"               , new ResourceLocation("web"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(31 , "Tallgrass"            , new ResourceLocation("tallgrass"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(32 , "Deadbush"             , new ResourceLocation("deadbush"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(33 , "Piston"               , new ResourceLocation("piston"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(29 , "Sticky Piston"        , new ResourceLocation("sticky_piston"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(34 , "Piston Head"          , new ResourceLocation("piston_head"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // all color of wool
	    BLOCKS.add(new VirtualBlock(35 , "Wool (All)"           , new ResourceLocation("wool"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this doesn't seem to exists
	    // BLOCKS.add(new VirtualBlock(36 , "Piston Extension"     , new ResourceLocation("piston_extension"               ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(37 , "Yellow Flower"        , new ResourceLocation("yellow_flower"                  ), 0.3f, 0f, 0.3f, 0.7f, 0.6f, 0.7f));
	    BLOCKS.add(new VirtualBlock(38 , "Red Flower"           , new ResourceLocation("red_flower"                     ), 0.3f, 0f, 0.3f, 0.7f, 0.6f, 0.7f));
	    BLOCKS.add(new VirtualBlock(39 , "Brown Mushroom"       , new ResourceLocation("brown_mushroom"                 ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f));
	    BLOCKS.add(new VirtualBlock(40 , "Red Mushroom"         , new ResourceLocation("red_mushroom"                   ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f));
	    BLOCKS.add(new VirtualBlock(41 , "Gold Block"           , new ResourceLocation("gold_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(42 , "Iron Block"           , new ResourceLocation("iron_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(43 , "Double Stone Slab"    , new ResourceLocation("double_stone_slab"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // this is like all slabs 
	    BLOCKS.add(new VirtualBlock(44 , "Stone Slab"           , new ResourceLocation("stone_slab"                     ), 0f, 0f, 0f, 1f, 0.5f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(45 , "Brick Block"          , new ResourceLocation("brick_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(46 , "TNT"                  , new ResourceLocation("tnt"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(47 , "Bookshelf"            , new ResourceLocation("bookshelf"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(48 , "Mossy Cobblestone"    , new ResourceLocation("mossy_cobblestone"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(49 , "Obsidian"             , new ResourceLocation("obsidian"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(50 , "Torch"                , new ResourceLocation("torch"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(51 , "Fire"                 , new ResourceLocation("fire"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(52 , "Mob Spawner"          , new ResourceLocation("mob_spawner"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(53 , "Oak Staris"           , new ResourceLocation("oak_stairs"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(54 , "Chest"                , new ResourceLocation("chest"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(55 , "Redstone Wire"        , new ResourceLocation("redstone_wire"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(56 , "Diamond Ore"          , new ResourceLocation("diamond_ore"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(57 , "Diamond Block"        , new ResourceLocation("diamond_block"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(58 , "Crafting Table"       , new ResourceLocation("crafting_table"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(59 , "Wheat"                , new ResourceLocation("wheat"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(60 , "Farmland"             , new ResourceLocation("farmland"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(61 , "Furnace"              , new ResourceLocation("furnace"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(62 , "Lit Furnace"          , new ResourceLocation("lit_furnace"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(63 , "Standing Sign"        , new ResourceLocation("standing_sign"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(64 , "Wooden Door"          , new ResourceLocation("wooden_door"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(65 , "Ladder"               , new ResourceLocation("ladder"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(66 , "Rail"                 , new ResourceLocation("rail"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(67 , "Stone Stairs"         , new ResourceLocation("stone_stairs"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(68 , "Wall Sign"            , new ResourceLocation("wall_sign"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(69 , "Lever"                , new ResourceLocation("lever"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(70 , "Stone Pressure Plate" , new ResourceLocation("stone_pressure_plate"           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(71 , "Iron Door"            , new ResourceLocation("iron_door"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(72 , "Wooden Pressure Plate", new ResourceLocation("wooden_pressure_plate"          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(73 , "Redstone Ore"         , new ResourceLocation("redstone_ore"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(74 , "Lit Redstone Ore"     , new ResourceLocation("lit_redstone_ore"               ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(75 , "Unlit Redstone Torch" , new ResourceLocation("unlit_redstone_torch"           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(76 , "Redtone Torch"        , new ResourceLocation("redstone_torch"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(77 , "Stone Button"         , new ResourceLocation("stone_button"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(78 , "Snow Layer"           , new ResourceLocation("snow_layer"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(79 , "Ice"                  , new ResourceLocation("ice"                            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(80 , "Snow"                 , new ResourceLocation("snow"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(81 , "Cactus"               , new ResourceLocation("cactus"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(82 , "Clay"                 , new ResourceLocation("clay"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(83 , "Sugar Cane"           , new ResourceLocation("reeds"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(84 , "Jukebox"              , new ResourceLocation("jukebox"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(85 , "Fence"                , new ResourceLocation("fence"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(86 , "Pumpkin"              , new ResourceLocation("pumpkin"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(87 , "Netherrack"           , new ResourceLocation("netherrack"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(88 , "Soul Sand"            , new ResourceLocation("soul_sand"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(89 , "Glowstone"            , new ResourceLocation("glowstone"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(90 , "Portal"               , new ResourceLocation("portal"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(91 , "Lit Pumpkin"          , new ResourceLocation("lit_pumpkin"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(92 , "Cake"                 , new ResourceLocation("cake"                           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(93 , "Unpowered Repeater"   , new ResourceLocation("unpowered_repeater"             ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(94 , "Powered Repeater"     , new ResourceLocation("powered_repeater"               ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // all stained glass colors 
	    BLOCKS.add(new VirtualBlock(95 , "Stained Glass"        , new ResourceLocation("stained_glass"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(96 , "Trapdoor"             , new ResourceLocation("trapdoor"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(97 , "Monster Egg"          , new ResourceLocation("monster_egg"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(98 , "Stonebrick"           , new ResourceLocation("stonebrick"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(99 , "Brown Mushroom Block" , new ResourceLocation("brown_mushroom_block"           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(100, "Red Mushroom Block"   , new ResourceLocation( "red_mushroom_block"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(101, "Iron Bars"            , new ResourceLocation( "iron_bars"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(102, "Glass Pane"           , new ResourceLocation( "glass_pane"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(103, "Melon Block"          , new ResourceLocation( "melon_block"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(104, "Pumpkin Stem"         , new ResourceLocation( "pumpkin_stem"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(105, "Melon Stem"           , new ResourceLocation( "melon_stem"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(106, "Vine"                 , new ResourceLocation( "vine"                          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(107, "Fence Gate"           , new ResourceLocation( "fence_gate"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(108, "Brick Stairs"         , new ResourceLocation( "brick_stairs"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(109, "Stone Brick Stairs"   , new ResourceLocation( "stone_brick_stairs"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(110, "Mycelium"             , new ResourceLocation( "mycelium"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(111, "Lily Pad"             , new ResourceLocation( "waterlily"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(112, "Nether Brick"         , new ResourceLocation( "nether_brick"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(113, "Nether Brick Fence"   , new ResourceLocation( "nether_brick_fence"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(114, "Nether Brick Stairs"  , new ResourceLocation( "nether_brick_stairs"           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(115, "Nether Wart"          , new ResourceLocation( "nether_wart"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(116, "Enchanting Table"     , new ResourceLocation( "enchanting_table"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(117, "Brewing Stand"        , new ResourceLocation( "brewing_stand"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(118, "Cauldron"             , new ResourceLocation( "cauldron"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(119, "End Portal"           , new ResourceLocation( "end_portal"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(120, "End Portal Frame"     , new ResourceLocation( "end_portal_frame"              ), 0f, 0f, 0f, 1f, 0.8f, 1f));
	    BLOCKS.add(new VirtualBlock(121, "End Stone"            , new ResourceLocation( "end_stone"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(122, "Dragon Egg"           , new ResourceLocation( "dragon_egg"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(123, "Redstone Lamp"        , new ResourceLocation( "redstone_lamp"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(124, "Lit Redstone Lamp"    , new ResourceLocation( "lit_redstone_lamp"             ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(125, "Double Wood Slab"     , new ResourceLocation( "double_wooden_slab"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(126, "Wood Slab"            , new ResourceLocation( "wooden_slab"                   ), 0f, 0f, 0f, 1f, 0.5f, 1f));
	    BLOCKS.add(new VirtualBlock(127, "Cocoa"                , new ResourceLocation( "cocoa"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(128, "Sandstone Stairs"     , new ResourceLocation( "sandstone_stairs"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(129, "Emerald Ore"          , new ResourceLocation( "emerald_ore"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(130, "Ender Chest"          , new ResourceLocation( "ender_chest"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(131, "Tripwire Hook"        , new ResourceLocation( "tripwire_hook"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(132, "Tripwire"             , new ResourceLocation( "tripwire"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(133, "Emerald Block"        , new ResourceLocation( "emerald_block"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(134, "Spruce Stairs"        , new ResourceLocation( "spruce_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(135, "Birch Stairs"         , new ResourceLocation( "birch_stairs"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(136, "Jungle Stairs"        , new ResourceLocation( "jungle_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(137, "Command Block"        , new ResourceLocation( "command_block"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(138, "Beacon"               , new ResourceLocation( "beacon"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(139, "Cobblestone Wall"     , new ResourceLocation( "cobblestone_wall"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(140, "Flower Pot"           , new ResourceLocation( "flower_pot"                    ), 0.3f, 0f, 0.3f, 0.7f, 0.4f, 0.7f));
	    BLOCKS.add(new VirtualBlock(141, "Carrots"              , new ResourceLocation( "carrots"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(142, "Potatoes"             , new ResourceLocation( "potatoes"                      ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(143, "Wooden Button"        , new ResourceLocation( "wooden_button"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(144, "Skull"                , new ResourceLocation( "skull"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(145, "Anvil"                , new ResourceLocation( "anvil"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(146, "Trapped Chest"        , new ResourceLocation( "trapped_chest"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(147, "Light Weighted Pressure Plate", new ResourceLocation( "light_weighted_pressure_plate" ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(148, "Heavy Weighted Pressure Plate", new ResourceLocation( "heavy_weighted_pressure_plate" ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(149, "Unpowered Comparator" , new ResourceLocation( "unpowered_comparator"          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(150, "Powered Comparator"   , new ResourceLocation( "powered_comparator"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(151, "Daylight Sensor"      , new ResourceLocation( "daylight_detector"             ), 0f, 0f, 0f, 1f, 0.37f, 1f));
	    BLOCKS.add(new VirtualBlock(178, "Daylight Sensor On"   , new ResourceLocation( "daylight_detector_inverted"    ), 0f, 0f, 0f, 1f, 0.37f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(152, "Redstone Block"       , new ResourceLocation( "redstone_block"                ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(153, "Quartz Ore"           , new ResourceLocation( "quartz_ore"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(154, "Hopper"               , new ResourceLocation( "hopper"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(155, "Quartz Block"         , new ResourceLocation( "quartz_block"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(156, "Quartz Stairs"        , new ResourceLocation( "quartz_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(157, ""                     , new ResourceLocation( "activator_rail"                ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(158, "Dropper"              , new ResourceLocation( "dropper"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(159, "Stained Hardened Clay", new ResourceLocation( "stained_hardened_clay"         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    // all stained glass pain colors 
	    BLOCKS.add(new VirtualBlock(160, "Stained Glass Pane"   , new ResourceLocation( "stained_glass_pane"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(161, "Leaves 2"             , new ResourceLocation( "leaves2"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(163, "Acacia Stairs"        , new ResourceLocation( "acacia_stairs"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(164, "Dark Oak Stairs"      , new ResourceLocation( "dark_oak_stairs"               ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(165, "Slime"                , new ResourceLocation( "slime"                         ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(166, "Barrier"              , new ResourceLocation( "barrier"                       ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(167, "Iron Trapdoor"        , new ResourceLocation( "iron_trapdoor"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(168, "Prismarine"           , new ResourceLocation( "prismarine"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(169, "Sea Lantern"          , new ResourceLocation( "sea_lantern"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(170, "Hay Block"            , new ResourceLocation( "hay_block"                     ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(171, "Carpet"               , new ResourceLocation( "carpet"                        ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(172, "Hardened Clay"        , new ResourceLocation( "hardened_clay"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(173, "Coal Block"           , new ResourceLocation( "coal_block"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(174, "Packed Ice"           , new ResourceLocation( "packed_ice"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(175, "Double Plant"         , new ResourceLocation( "double_plant"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(176, "Standing Banner"      , new ResourceLocation( "standing_banner"               ), 0f, 0f, 0f, 1f, 1.8f, 1f));
	    BLOCKS.add(new VirtualBlock(177, "Wall Banner"          , new ResourceLocation( "wall_banner"                   ), 0f, 0f, 0f, 1f, 1.8f, 1f));
	    
	    BLOCKS.add(new VirtualBlock(179, "Red Sandstone"        , new ResourceLocation( "red_sandstone"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(180, "Red Sandstone Stairs" , new ResourceLocation( "red_sandstone_stairs"          ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(181, "Double Stone Slab 2"  , new ResourceLocation( "double_stone_slab2"            ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(182, "Stone Slab 2"         , new ResourceLocation( "stone_slab2"                   ), 0f, 0f, 0f, 1f, 0.5f, 1f));
	    BLOCKS.add(new VirtualBlock(183, "Spruce Fence Gate"    , new ResourceLocation( "spruce_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(184, "Birch Fence Gate"     , new ResourceLocation( "birch_fence_gate"              ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(185, "Jungle Fence Gate"    , new ResourceLocation( "jungle_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(186, "Dark Oah Fence Gate"  , new ResourceLocation( "dark_oak_fence_gate"           ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(187, "Acacia Fence Gate"    , new ResourceLocation( "acacia_fence_gate"             ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(188, "Spruce Fence"         , new ResourceLocation( "spruce_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(189, "Birch Fence"          , new ResourceLocation( "birch_fence"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(190, "Jungle Fence"         , new ResourceLocation( "jungle_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(191, "Dark Oak Fence"       , new ResourceLocation( "dark_oak_fence"                ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(192, "Acacia Fence"         , new ResourceLocation( "acacia_fence"                  ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(193, "Spruce Door"          , new ResourceLocation( "spruce_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(194, "Birch Door"           , new ResourceLocation( "birch_door"                    ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(195, "Jungle Door"          , new ResourceLocation( "jungle_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(196, "Acacia Door"          , new ResourceLocation( "acacia_door"                   ), 0f, 0f, 0f, 1f, 1f, 1f));
	    BLOCKS.add(new VirtualBlock(197, "Dark Oak Door"        , new ResourceLocation( "dark_oak_door"                 ), 0f, 0f, 0f, 1f, 1f, 1f));
	    
	    
	    
	    
	    
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(0))  , "Air"                  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(1))  , "Stone"				 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2))  , "Grass"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(3))  , "Dirt"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(4))  , "Cobblestone"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(5))  , "Plank (All)"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(6))  , "Sapling"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(7))  , "Bedrock"              ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(8))  , "Flowing Water"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(9))  , "Water"                ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(10)) , "Flowing Lava"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(11)) , "Lava"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(12)) , "Sand"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(13)) , "Gravel"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(14)) , "Gold Ore"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(15)) , "Iron Ore"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(16)) , "Coal Ore"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(17)) , "Logs 1"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(162)), "Logs 2"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(18)) , "Leaves"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(19)) , "Sponge (Dry Wet)"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(20)) , "Glass"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(21)) , "Lapis Ore"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(22)) , "Lapis Block"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(23)) , "Dispenser"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(24)) , "Sandstone"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(25)) , "Noteblock"            ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(26)) , "Bed"                  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(27)) , "Golden Rail"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(28)) , "Detector Rail"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(30)) , "Cobweb"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(31)) , "Tallgrass"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(32)) , "Deadbush"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(33)) , "Piston"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(29)) , "Sticky Piston"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(34)) , "Piston Head"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(35)) , "Wool (All)"           ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(36)) , "Piston Extension"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(37)) , "Yellow Flower"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(38)) , "Red Flower"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(39)) , "Brown Mushroom"       ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(40)) , "Red Mushroom"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(41)) , "Gold Block"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(42)) , "Iron Block"           ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(43)) , "Double Stone Slab"    ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(44)) , "Stone Slab"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(45)) , "Brick Block"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(46)) , "TNT"                  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(47)) , "Bookshelf"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(48)) , "Mossy Cobblestone"    ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(49)) , "Obsidian"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(50)) , "Torch"                ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(51)) , "Fire"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(52)) , "Mob Spawner"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(53)) , "Oak Staris"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(54)) , "Chest"                ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(55)) , "Redstone Wire"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(56)) , "Diamond Ore"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(57)) , "Diamond Block"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(58)) , "Crafting Table"       ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(59)) , "Wheat"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(60)) , "Farmland"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(61)) , "Furnace"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(62)) , "Lit Furnace"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(63)) , "Standing Sign"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(64)) , "Wooden Door"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(65)) , "Ladder"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(66)) , "Rail"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(67)) , "Stone Stairs"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(68)) , "Wall Sign"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(69)) , "Lever"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(70)) , "Stone Pressure Plate" ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(71)) , "Iron Door"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(72)) , "Wooden Pressure Plate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(73)) , "Redstone Ore"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(74)) , "Lit Redstone Ore"     ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(75)) , "Unlit Redstone Torch" ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(76)) , "Redtone Torch"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(77)) , "Stone Button"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(78)) , "Snow Layer"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(79)) , "Ice"                  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(80)) , "Snow"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(81)) , "Cactus"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(82)) , "Clay"                 ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(83)) , "Sugar Cane"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(84)) , "Jukebox"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(85)) , "Fence"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(86)) , "Pumpkin"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(87)) , "Netherrack"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(88)) , "Soul Sand"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(89)) , "Glowstone"            ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(90)) , "Portal"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(91)) , "Lit Pumpkin"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(92)) , "Cake"                 ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(93)) , "Unpowered Repeater"   ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(94)) , "Powered Repeater"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(95)) , "Stained Glass"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(96)) , "Trapdoor"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(97)) , "Monster Egg"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(98)) , "Stonebrick"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(99)) , "Brown Mushroom Block" ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(100)), "Red Mushroom Block"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(101)), "Iron Bars"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(102)), "Glass Pane"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(103)), "Melon Block"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(104)), "Pumpkin Stem"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(105)), "Melon Stem"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(106)), "Vine"                 ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(107)), "Fence Gate"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(108)), "Brick Stairs"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(109)), "Stone Brick Stairs"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(110)), "Mycelium"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(111)), "Lily Pad"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(112)), "Nether Brick"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(113)), "Nether Brick Fence"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(114)), "Nether Brick Stairs"  ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(115)), "Nether Wart"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(116)), "Enchanting Table"     ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(117)), "Brewing Stand"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(118)), "Cauldron"             ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(119)), "End Portal"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(120)), "End Portal Frame"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(121)), "End Stone"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(122)), "Dragon Egg"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(123)), "Redstone Lamp"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(124)), "Lit Redstone Lamp"    ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(125)), "Double Wood Slab"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(126)), "Wood Slab"            ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(127)), "Cocoa"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(128)), "Sandstone Stairs"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(129)), "Emerald Ore"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(130)), "Ender Chest"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(131)), "Tripwire Hook"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(132)), "Tripwire"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(133)), "Emerald Block"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(134)), "Spruce Stairs"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(135)), "Birch Stairs"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(136)), "Jungle Stairs"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(137)), "Command Block"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(138)), "Beacon"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(139)), "Cobblestone Wall"     ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(140)), "Flower Pot"           ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(141)), "Carrots"              ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(142)), "Potatoes"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(143)), "Wooden Button"        ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(144)), "Skull"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(145)), "Anvil"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(146)), "Trapped Chest"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(147)), "Light Weighted Pressure Plate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(148)), "Heavy Weighted Pressure Plate"));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(149)), "Unpowered Comparator" ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(150)), "Powered Comparator"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(151)), "Daylight Sensor"      ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(178)), "Daylight Sensor On"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(152)), "Redstone Block"       ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(153)), "Quartz Ore"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(154)), "Hopper"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(155)), "Quartz Block"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(156)), "Quartz Stairs"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(157)), ""                     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(158)), "Dropper"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(159)), "Stained Hardened Clay"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(160)), "Stained Glass Pane"   ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(161)), "Leaves 2"             ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(163)), "Acacia Stairs"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(164)), "Dark Oak Stairs"      ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(165)), "Slime"                ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(166)), "Barrier"              ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(167)), "Iron Trapdoor"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(168)), "Prismarine"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(169)), "Sea Lantern"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(170)), "Hay Block"            ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(171)), "Carpet"               ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(172)), "Hardened Clay"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(173)), "Coal Block"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(174)), "Packed Ice"           ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(175)), "Double Plant"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(176)), "Standing Banner"      ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(177)), "Wall Banner"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(179)), "Red Sandstone"        ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(180)), "Red Sandstone Stairs" ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(181)), "Double Stone Slab 2"  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(182)), "Stone Slab 2"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(183)), "Spruce Fence Gate"    ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(184)), "Birch Fence Gate"     ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(185)), "Jungle Fence Gate"    ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(186)), "Dark Oah Fence Gate"  ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(187)), "Acacia Fence Gate"    ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(188)), "Spruce Fence"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(189)), "Birch Fence"          ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(190)), "Jungle Fence"         ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(191)), "Dark Oak Fence"       ));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(192)), "Acacia Fence"         ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(193)), "Spruce Door"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(194)), "Birch Door"           ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(195)), "Jungle Door"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(196)), "Acacia Door"          ));
//	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(197)), "Dark Oak Door"        ));

	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(256)), "Iron Shovel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(257)), "Iron Pickaxe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(258)), "Iron Axe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(259)), "Flint And Steel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(260)), "Apple"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(261)), "Bow"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(262)), "Arrow"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(263)), "Coal"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(264)), "Diamond"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(265)), "Iron Ingot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(266)), "Gold Ingot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(267)), "Iron Sword"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(268)), "Wooden Sword"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(269)), "Wooden Shovel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(270)), "Wooden Pickaxe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(271)), "Wooden Axe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(272)), "Stone Sword"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(273)), "Stone Shovel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(274)), "Stone Pickaxe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(275)), "Stone Axe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(276)), "Diamond Sword"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(277)), "Diamond Shovel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(278)), "Diamond Pickaxe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(279)), "Diamond Axe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(280)), "Stick"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(281)), "Bowl"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(282)), "Mushroom Stew"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(283)), "Golden Sword"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(284)), "Golden Shovel"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(285)), "Golden Pickaxe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(286)), "Golden Axe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(287)), "String"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(288)), "Feather"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(289)), "Gunpowder"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(290)), "Wooden Hoe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(291)), "Stone Hoe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(292)), "Iron Hoe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(293)), "Diamond Hoe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(294)), "Golden Hoe"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(295)), "Wheat Seeds"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(296)), "Wheat"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(297)), "Bread"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(298)), "Leather Helmet"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(299)), "Leather Chestplate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(300)), "Leather Leggings"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(301)), "Leather Boots"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(302)), "Chainmail Helmet"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(303)), "Chainmail Chestplate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(304)), "Chainmail Leggings"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(305)), "Chainmail Boots"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(306)), "Iron Helmet"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(307)), "Iron Chestplate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(308)), "Iron Leggings"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(309)), "Iron Boots"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(310)), "Diamond Helmet"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(311)), "Diamond Chestplate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(312)), "Diamond Leggings"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(313)), "Diamond Boots"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(314)), "Golden Helmet"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(315)), "Golden Chestplate"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(316)), "Golden Leggings"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(317)), "Golden Boots"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(318)), "Flint"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(319)), "Porkchop"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(320)), "Cooked Porkchop"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(321)), "Painting"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(322)), "Golden Apple"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(323)), "Sign"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(324)), "Wooden Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(325)), "Bucket"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(326)), "Water Bucket"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(327)), "Lava Bucket"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(328)), "Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(329)), "Saddle"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(330)), "Iron Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(331)), "Redstone"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(332)), "Snowball"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(333)), "Boat"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(334)), "Leather"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(335)), "Milk Bucket"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(336)), "Brick"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(337)), "Clay Ball"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(338)), "Reeds"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(339)), "Paper"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(340)), "Book"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(341)), "Slime Ball"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(342)), "Chest Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(343)), "Furnace Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(344)), "Egg"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(345)), "Compass"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(346)), "Fishing Rod"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(347)), "Clock"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(348)), "Glowstone Dust"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(349)), "Fish"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(350)), "Cooked Fish"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(351)), "Dye"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(352)), "Bone"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(353)), "Sugar"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(354)), "Cake"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(355)), "Bed"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(356)), "Repeater"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(357)), "Cookie"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(358)), "Filled Map"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(359)), "Shears"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(360)), "Melon"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(361)), "Pumpkin Seeds"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(362)), "Melon Seeds"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(363)), "Beef"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(364)), "Cooked Beef"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(365)), "Chicken"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(366)), "Cooked Chicken"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(367)), "Rotten Flesh"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(368)), "Ender Pearl"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(369)), "Blaze Rod"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(370)), "Ghast Tear"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(371)), "Gold Nugget"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(372)), "Nether Wart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(373)), "Potion"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(374)), "Glass Bottle"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(375)), "Spider Eye"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(376)), "Fermented Spider Eye"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(377)), "Blaze Powder"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(378)), "Magma Cream"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(379)), "Brewing Stand"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(380)), "Cauldron"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(381)), "Ender Eye"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(382)), "Speckled Melon"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(383)), "Spawn Egg"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(384)), "Experience Bottle"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(385)), "Fire Charge"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(386)), "Writable Book"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(387)), "Written Book"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(388)), "Emerald"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(389)), "Item Frame"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(390)), "Flower Pot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(391)), "Carrot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(392)), "Potato"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(393)), "Baked Potato"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(394)), "Poisonous Potato"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(395)), "Map"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(396)), "Golden Carrot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(397)), "Skull"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(398)), "Carrot On A Stick"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(399)), "Nether Star"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(400)), "Pumpkin Pie"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(401)), "Fireworks"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(402)), "Firework Charge"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(403)), "Enchanted Book"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(404)), "Comparator"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(405)), "Netherbrick"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(406)), "Quartz"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(407)), "Tnt Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(408)), "Hopper Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(409)), "Prismarine Shard"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(410)), "Prismarine Crystals"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(411)), "Rabbit"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(412)), "Cooked Rabbit"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(413)), "Rabbit Stew"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(414)), "Rabbit Foot"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(415)), "Rabbit Hide"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(416)), "Armor Stand"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(417)), "Iron Horse Armor"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(418)), "Golden Horse Armor"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(419)), "Diamond Horse Armor"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(420)), "Lead"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(421)), "Name Tag"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(422)), "Command Block Minecart"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(423)), "Mutton"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(424)), "Cooked Mutton"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(425)), "Banner"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(427)), "Spruce Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(428)), "Birch Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(429)), "Jungle Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(430)), "Acacia Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(431)), "Dark Oak Door"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2256)), "Record 13"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2257)), "Record Cat"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2258)), "Record Blocks"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2259)), "Record Chirp"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2260)), "Record Far"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2261)), "Record Mall"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2262)), "Record Mellohi"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2263)), "Record Stal"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2264)), "Record Strad"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2265)), "Record Ward"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2266)), "Record 11"));
	    ITEMS.add(new VirtualItem(new ItemStack(Item.itemRegistry.getObjectById(2267)), "Record Wait"));
	}
	
	
	
	
}








