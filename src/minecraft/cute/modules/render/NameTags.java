package cute.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cute.eventapi.EventTarget;
import cute.events.RenderNameTagEvent;
import cute.modules.Module;
import cute.modules.bot.AntiBot;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.Slider;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class NameTags extends Module 
{
	public NameTags() 
	{
		super("Name Tags", Category.RENDER, "Shows names and hotbar through walls");
	}
	public static final Logger Logger = LogManager.getLogger();
	public static Checkbox names = new Checkbox("Names", true);

    public static Checkbox armor = new Checkbox("Armor/Hand", true);
    
    public static Checkbox enchants = new Checkbox("Enchants", true);
    
    public static Checkbox everything = new Checkbox("Everything", false);
    
    public static Slider scaleDistance = new Slider("Scale Distance", 1d, 20d, 100d, 1);
    public static Slider scale         = new Slider("Size Scale", 0.01d, 0.25d, 1d, 1);

    public static boolean globalEnabled = false;
    
    // this is used so the minecraft files mentioned at the top can check if XRay is on 
 	public static boolean isOn()
 	{
 		return NameTags.globalEnabled;
 	}
    
    @Override
    public void setup()
    {
    	this.addSetting(names);
    	this.addSetting(armor);
    	this.addSetting(enchants);
    	this.addSetting(everything);
    	this.addSetting(scaleDistance);
    	this.addSetting(scale);
    }
    
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		NameTags.globalEnabled = true;
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
		
		NameTags.globalEnabled = false;
	}
    
    public static List<String> getEnchantList(ItemStack stack) 
    {
        List<String> eList = new ArrayList<String>();
        
        if (stack == null || stack.getEnchantmentTagList() == null)
        	return eList;
        
        for(int j = stack.getEnchantmentTagList().tagCount() - 1; j >= 0; j--) 
        {
            int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(j).getInteger("lvl");
            int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(j).getInteger("id");
            Enchantment enchant = Enchantment.getEnchantmentById(enchantID);

            if (enchant == null) 
            {
                continue;
            }

            String name = StatCollector.translateToLocal(enchant.getName());            
            String disp = name.charAt(0) + String.valueOf(enchantLevel);
            
            eList.add(disp);
        }
//        Collections.sort(eList);
        return eList;
    }

    
    @EventTarget
    public void onRenderWorld(RenderNameTagEvent event) 
    {
    	// untested but i think when in pvp on hypixel the anti ka bots copy the player gear
    	// so it renders twice the armor above their head, this is supposed to avoid bots now idk if it works
    	if(!(event.entity instanceof EntityPlayer) || 
    	     event.entity.isDead ||  
    	    AntiBot.isBot((EntityLivingBase)event.entity))
    		return;
    	
    	event.setCancelled(true);
    	
    	FontRenderer fontrenderer = event.fontRenderer;
    	WorldRenderer worldrenderer = event.worldRenderer;
    	Tessellator tessellator       = event.tessellator;
    	RenderItem renderitem = mc.getRenderItem();
    	
    	String str = event.name;
    	
    	int i = 0;
        int j = fontrenderer.getStringWidth(str) / 2;
        
        // scale the size based of distance to entity 
        float distance = mc.thePlayer.getDistanceToEntity(event.entity);
        
        if(distance > scaleDistance.getValue() * (1 - scale.getValue()))
        {
        	float scale = (float)(NameTags.scale.getValue() + (distance / scaleDistance.getValue()));
        	GlStateManager.scale(scale, scale, scale);
        }
        
        GlStateManager.disableDepth();
        
        if(NameTags.names.getValue())
    	{    	
	        GlStateManager.disableTexture2D();
	        
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	        worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        tessellator.draw();
	
	        GlStateManager.enableTexture2D();
	
	        // this is here so that it goes through walls
	        fontrenderer.drawString(str, -j, i, -1);
	        
	        GlStateManager.enableDepth();
	        GlStateManager.depthMask(true);
	        // this is here so entities behind the name don't override it 
	        fontrenderer.drawString(str, -j, i, -1);
    	}
        
        
        if(!NameTags.armor.getValue()) 
        {
        	GlStateManager.enableDepth();
        	return;	
        }
        
        
        
    	EntityPlayer player = (EntityPlayer)event.entity;

		int x = -j - 10;
		
		for (int k = 0; k < 5; ++k) 
		{
            final ItemStack stack = player.getEquipmentInSlot(k);
            
            if (stack == null) 
            	continue;
            
            // render item and overlay above entities behind it
            GlStateManager.depthMask(true);
            renderitem.renderitemForNameTag(stack, x, -20, 0);
            renderitem.renderItemOverlayIntoGUIForNameTags(fontrenderer, stack, x, -20, null);
            
            // render item and overlay through walls  
            GlStateManager.disableDepth();        
            renderitem.renderitemForNameTag(stack, x, -20, 0);
            renderitem.renderItemOverlayIntoGUIForNameTags(fontrenderer, stack, x, -20, null);
            GlStateManager.enableDepth();
            
            if(!enchants.getValue())
            {
            	x += 16;
            	continue;
            }
            
            int y = 0;
            for(String enchant : getEnchantList(stack))
            {                
                // note - when I removed the push matrix and translate I added +8 to _x and _y
                // this is becase renderitemForNameTag translates by +8 in x and y,
            	float _x = x  - fontrenderer.getStringWidth(enchant) + 14f;
            	float _y = -30f + y;
            	
            	GlStateManager.disableLighting();
            	GlStateManager.depthMask(true);
            	
            	// render enchant above entities behind it
            	 fontrenderer.drawStringWithShadow(enchant, _x, _y, -1);
            	
            	// render enchant through walls 
            	GlStateManager.disableDepth();
            	fontrenderer.drawStringWithShadow(enchant, _x, _y, -1);
            	
            	GlStateManager.enableDepth();
            	GlStateManager.enableLighting();

            	y -= 10;
            }

            x += 16;
        }	
		
		GlStateManager.enableDepth();
    }
    
    
    
    
    
    
    
    
    
    
}














