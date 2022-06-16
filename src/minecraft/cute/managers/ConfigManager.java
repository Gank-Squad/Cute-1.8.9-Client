package cute.managers;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import cute.modules.Module;
import cute.modules.render.ESPBlocks;
import cute.modules.render.XRay;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Keybind;
import cute.settings.Mode;
import cute.settings.Setting;
import cute.settings.Slider;
import cute.settings.SubSetting;
import cute.settings.enums.ListType;
import cute.util.Cache;
import cute.util.types.VirtualBlock;
import cute.settings.ListSelection;

public class ConfigManager extends BaseManager
{
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void createDirectory() throws IOException 
    {
        if (!Files.exists(Paths.get("cute/")))
            Files.createDirectories(Paths.get("cute/"));

        if (!Files.exists(Paths.get("cute/modules")))
            Files.createDirectories(Paths.get("cute/modules"));

        if (!Files.exists(Paths.get("cute/gui")))
            Files.createDirectories(Paths.get("cute/gui"));

        if (!Files.exists(Paths.get("cute/social")))
            Files.createDirectories(Paths.get("cute/social"));
    }

    public static void registerFiles(String name, String path) throws IOException 
    {
    	String _path = "cute/" + path + "/" + name + ".json";
    	
        if (!Files.exists(Paths.get(_path))) 
        {
            Files.createFile(Paths.get(_path));
            return;
        }
        
        
        File file = new File(_path);
        file.delete();
        Files.createFile(Paths.get(_path));
    }

    public static void saveConfig() 
    {
        try 
        {
        	createDirectory();
            saveModules();
//            saveGUI();
//            saveHUD();
//            saveFriends();
//            saveEnemies();
        }
        catch (IOException ignored) 
        {

        }
    }

    public static void loadConfig() 
    {
        try 
        {
            createDirectory();
            loadModules();
            
//            loadGUI();
//            loadHUD();
//            loadFriends();
//            loadEnemies();
        } 
        catch (IOException ignored) 
        {

        }
    }

    public static void saveModules() throws IOException 
    {
        for (Module module : ModuleManager.getModules()) 
        {
            registerFiles(module.getName(), "modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(module.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
            moduleObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
            moduleObject.add("Bind", new JsonPrimitive(module.getKeybind().getKeyCode()));

            for (Setting setting : module.getSettings()) 
            {
                if (setting instanceof Checkbox) 
                {
                    settingObject.add(((Checkbox) setting).getName(), new JsonPrimitive(((Checkbox) setting).getValue()));

                    if (((Checkbox) setting).hasSubSettings()) 
                    {
                        for (SubSetting subSetting : ((Checkbox) setting).getSubSettings()) {

                            if (subSetting instanceof ColorPicker) {
                                JsonObject subColorObject = new JsonObject();

                                subColorObject.add("Red", new JsonPrimitive(((ColorPicker) subSetting).getRed()));
                                subColorObject.add("Green", new JsonPrimitive(((ColorPicker) subSetting).getGreen()));
                                subColorObject.add("Blue", new JsonPrimitive(((ColorPicker) subSetting).getBlue()));
                                subColorObject.add("Alpha", new JsonPrimitive(((ColorPicker) subSetting).getAlpha()));

                                subSettingObject.add(((ColorPicker) subSetting).getName(), subColorObject);
                            }
                        }
                    }
                }

                else if (setting instanceof Slider) 
                {
                    settingObject.add(((Slider) setting).getName(), new JsonPrimitive(((Slider) setting).getValue()));
                }

                else if (setting instanceof Mode) 
                {
                    settingObject.add(((Mode) setting).getName(), new JsonPrimitive(((Mode) setting).getValue()));
                }

                else if (setting instanceof Keybind) 
                {
                    settingObject.add(((Keybind) setting).getName(), new JsonPrimitive(((Keybind) setting).getKey()));
                }
                else if (setting instanceof ListSelection)
                {
                	ListSelection ls = (ListSelection)setting;
                	JsonArray items = new JsonArray();
                	
                	switch(ls.getListType())
                	{
	                	case BLOCK:
	                		
	                		for(Object _ : ls.getEnabledItems()) 
	                        {
	                        	VirtualBlock b = (VirtualBlock)_;
	                        	
	                        	JsonObject jo = new JsonObject();
	                        	jo.add("Id",  new JsonPrimitive(b.blockID));
	                        	jo.add("Red",  new JsonPrimitive(b.getRed()));
	                        	jo.add("Green",  new JsonPrimitive(b.getGreen()));
	                        	jo.add("Blue",  new JsonPrimitive(b.getBlue()));
	                        	jo.add("Alpha",  new JsonPrimitive(b.getAlpha()));
	                        	jo.add("Meta",  new JsonPrimitive(b.meta));
	                        	jo.add("Enabled",  new JsonPrimitive(b.enabled));
	                        	jo.add("DisplayName",  new JsonPrimitive(b.displayName));
	                        	
	                        	items.add(jo);
	                        }
	                	
	                		break;
	                		
	                	case PLAYERNAME:
	                		
	                		for(Object _ : ls.getEnabledItems()) 
	                        {
	                        	String b = (String)_;
	                        	
	                        	items.add(new JsonPrimitive(b));
	                        }
	                		
	                		break;
	                		
	                	case POTION:
	                		continue;
                	}
                	
                	
                	settingObject.add(ls.getName(), items);
                }
            }

            settingObject.add("SubSettings", subSettingObject);
            moduleObject.add("Settings", settingObject);
            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    @SuppressWarnings("resource")
	public static void loadModules() throws IOException 
    {
        for (Module module : ModuleManager.modules) 
        {
        	String modulePath = "cute/modules/" + module.getName() + ".json";
        	
            if (!Files.exists(Paths.get(modulePath)))
                continue;

            InputStream inputStream = Files.newInputStream(Paths.get(modulePath));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();

            if (moduleObject.get("Name") == null || 
        		moduleObject.get("Enabled") == null || 
        		moduleObject.get("Drawn") == null || 
        		moduleObject.get("Bind") == null)
                continue;

            JsonObject settingObject    = moduleObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            for (Setting setting : module.getSettings()) 
            {
                JsonElement settingValueObject = null;

                if (setting instanceof Checkbox) 
                {
                    settingValueObject = settingObject.get(((Checkbox) setting).getName());

                    for (SubSetting subSetting : ((Checkbox) setting).getSubSettings()) 
                    {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

                        if (subSetting instanceof ColorPicker) 
                        {
                            try 
                            {
                                if (subSettingObject.get(((ColorPicker) subSetting).getName()).getAsJsonObject() == null)
                                    return;

                                JsonObject subColorObject = subSettingObject.get(((ColorPicker) subSetting).getName()).getAsJsonObject();

                                if (subColorObject.get("Red") == null || subColorObject.get("Green") == null || subColorObject.get("Blue") == null || subColorObject.get("Alpha") == null)
                                	return;

                                redValueObject   = subColorObject.get("Red");
                                greenValueObject = subColorObject.get("Green");
                                blueValueObject  = subColorObject.get("Blue");
                                alphaValueObject = subColorObject.get("Alpha");
                            } 
                            catch (Exception ignored) 
                            {

                            }
                        }

                        if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null)
                            ((ColorPicker) subSetting).setColor(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                    }
                }

                else if (setting instanceof Slider) 
                {
                    settingValueObject = settingObject.get(((Slider) setting).getName());
                }

                else if (setting instanceof Mode) 
                {
                    settingValueObject = settingObject.get(((Mode) setting).getName());
                }

                else if (setting instanceof Keybind) 
                {
                    settingValueObject = settingObject.get(((Keybind) setting).getName());
                }
                
                else if (setting instanceof ListSelection)
                {
                	settingValueObject = settingObject.get(((ListSelection) setting).getName());
                }

                if (settingValueObject != null) 
                {
                    if (setting instanceof Checkbox)
                        ((Checkbox) setting).setChecked(settingValueObject.getAsBoolean());

                    else if (setting instanceof Slider)
                        ((Slider) setting).setValue(settingValueObject.getAsDouble());

                    else if (setting instanceof Mode)
                        ((Mode) setting).setMode(settingValueObject.getAsInt());

                    else if (setting instanceof Keybind)
                        ((Keybind) setting).setKey(settingValueObject.getAsInt());
                    
                    else if (setting instanceof ListSelection)
                    {
                    	ListSelection ls = (ListSelection)setting;
                    	JsonArray items = (JsonArray)settingValueObject;
                    	
                    	switch(ls.getListType())
                    	{
    	                	case BLOCK:
    	                		
    	                		for (int i = 0; i < items.size(); i++) 
    	                        {
    	                			int id;
    	                			int r;
    	                			int g;
    	                			int b;
    	                			int a;
    	                			int meta;
    	                			boolean enabled;
    	                			String displayName;
    	                			
    	                			try
    	                			{
    	                				JsonObject jsonObj = items.get(i).getAsJsonObject();
        	                        	
        	                        	id = jsonObj.get("Id").getAsInt();
        	                        	r  = jsonObj.get("Red").getAsInt();
        	                        	g  = jsonObj.get("Green").getAsInt();
        	                        	b  = jsonObj.get("Blue").getAsInt();
        	                        	a  = jsonObj.get("Alpha").getAsInt();
        	                        	meta = jsonObj.get("Meta").getAsInt();
        	                        	enabled = jsonObj.get("Enabled").getAsBoolean();
        	                        	displayName = jsonObj.get("DisplayName").getAsString();
    	                			}
    	                        	catch(Exception ignored)
    	                        	{
    	                        		continue;
    	                        	}
    	                		
    	                			Stream<VirtualBlock> s = Cache.BLOCKS.stream().filter(x -> x.blockID == id);
    	                			
    	                			s.forEach(x -> 
    	                			{
    	                				try 
    	                				{
    	                					x.setRed(r);
    	                					x.setGreen(g);
    	                					x.setBlue(b);
    	                					x.setAlpha(a);
    	                					x.meta    =   meta;
    	                					x.displayName = displayName;
    	                					x.enabled = enabled;
    	                					
    	                					ls.enableItem(x);
    	                				}
    	                				catch(Exception ignored) {}
    	                			});	
    	                        }
    	                		break;
    	                		
    	                	case PLAYERNAME:
    	                		
    	                		for (int i = 0; i < items.size(); i++) 
    	                        {
    	                			String name;
    	                			
    	                			try
    	                			{
    	                				name = items.get(i).getAsString();
    	                				System.out.println(name);
    	                			}
    	                        	catch(Exception ignored)
    	                        	{
    	                        		continue;
    	                        	}
    	                		
    	                			ls.enableItem(name);	
    	                        }
    	                		break;
    	                		
    	                	case POTION:
    	                		continue;
                    	}
                    }
                }
            }

            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            module.setDrawn(moduleObject.get("Drawn").getAsBoolean());
            module.setKeyCode(moduleObject.get("Bind").getAsInt());
        }
    }
    
    public static void saveBlockList(String name) throws IOException
    {
    	if(name != "xray" && name != "esp")
    		return;
    	
    	String path = "cute/modules/Virtual Block List " + name + ".json";
    	
    	registerFiles("Virtual Block List " + name, "modules");
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8);

        JsonArray moduleObject = new JsonArray();
        
        ArrayList<Object> _enabled = null;
        
        if(name == "xray")
        {
        	_enabled = XRay.vblocks.getEnabledItems();
        }
        else if(name == "esp")
        {
        	_enabled = ESPBlocks.blocks.getEnabledItems();
        }
        
        for(Object _ : _enabled) 
        {
        	VirtualBlock b = (VirtualBlock)_;
        	
        	JsonObject jo = new JsonObject();
        	jo.add("Id",  new JsonPrimitive(b.blockID));
        	jo.add("Red",  new JsonPrimitive(b.getRed()));
        	jo.add("Green",  new JsonPrimitive(b.getGreen()));
        	jo.add("Blue",  new JsonPrimitive(b.getBlue()));
        	jo.add("Alpha",  new JsonPrimitive(b.getAlpha()));
        	jo.add("Meta",  new JsonPrimitive(b.meta));
        	jo.add("Enabled",  new JsonPrimitive(b.enabled));
        	jo.add("DisplayName",  new JsonPrimitive(b.displayName));
        	
        	moduleObject.add(jo);
        }
        
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    
    public static void LoadBlockList(String name) throws IOException 
    {
    	String path = "cute/modules/Virtual Block List " + name + ".json";
    	
    	if (!Files.exists(Paths.get(path)))
            return;

        InputStream inputStream = Files.newInputStream(Paths.get(path));
        JsonArray blockListObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonArray();

        for (int i = 0; i < blockListObject.size(); i++) 
        {
        	JsonObject jsonObj = blockListObject.get(i).getAsJsonObject();
        	
        	int id = jsonObj.get("Id").getAsInt();
        	int r  = jsonObj.get("Red").getAsInt();
        	int g  = jsonObj.get("Green").getAsInt();
        	int b  = jsonObj.get("Blue").getAsInt();
        	int a  = jsonObj.get("Alpha").getAsInt();
        	int meta = jsonObj.get("Meta").getAsInt();
        	boolean enabled = jsonObj.get("Enabled").getAsBoolean();
        	String displayName = jsonObj.get("DisplayName").getAsString();
    	
			Stream<VirtualBlock> s = Cache.BLOCKS.stream().filter(x -> x.blockID == id);
			
			s.forEach(x -> 
			{
				try 
				{
					x.setRed(r);
					x.setGreen(g);
					x.setBlue(b);
					x.setAlpha(a);
					x.meta    =   meta;
					x.displayName = displayName;
					x.enabled = enabled;
					
					if(name == "xray")
			    	{
						XRay.vblocks.enableItem(x);
			    	}
					else if(name == "esp") 
					{
						ESPBlocks.blocks.enableItem(x);	
					}
				}
				catch(NumberFormatException e) {}
			});	
        }
    }
    
    
//    public static void saveGUI() throws IOException 
//    {
//        registerFiles("GUI", "gui");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/gui/GUI.json"), StandardCharsets.UTF_8);
//        JsonObject guiObject = new JsonObject();
//        JsonObject windowObject = new JsonObject();
//
//        for (Window window : Window.windows) 
//        {
//            JsonObject positionObject = new JsonObject();
//
//            positionObject.add("x", new JsonPrimitive(window.x));
//            positionObject.add("y", new JsonPrimitive(window.y));
//            positionObject.add("open", new JsonPrimitive(window.opened));
//
//            windowObject.add(window.category.getName(), positionObject);
//        }
//
//        guiObject.add("Windows", windowObject);
//        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadGUI() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/gui/GUI.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/gui/GUI.json"));
//        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
//
//        if (guiObject.get("Windows") == null)
//            return;
//
//        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();
//        for (Window window : Window.windows) 
//        {
//            if (windowObject.get(window.category.name()) == null)
//                return;
//
//            JsonObject categoryObject = windowObject.get(window.category.name()).getAsJsonObject();
//
//            JsonElement windowXObject = categoryObject.get("x");
//            if (windowXObject != null && windowXObject.isJsonPrimitive())
//                window.x = windowXObject.getAsInt();
//
//            JsonElement windowYObject = categoryObject.get("y");
//            if (windowYObject != null && windowYObject.isJsonPrimitive())
//                window.y = windowYObject.getAsInt();
//
//            JsonElement windowOpenObject = categoryObject.get("open");
//            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive())
//                window.opened = windowOpenObject.getAsBoolean();
//        }
//
//        inputStream.close();
//    }

//    public static void saveHUD() throws IOException 
//    {
//        registerFiles("HUD", "gui");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/gui/HUD.json"), StandardCharsets.UTF_8);
//        JsonObject guiObject = new JsonObject();
//        JsonObject hudObject = new JsonObject();
//
//        for (HUDComponent component : HUDComponentManager.getComponents()) 
//        {
//            JsonObject positionObject = new JsonObject();
//
//            positionObject.add("x", new JsonPrimitive(component.x));
//            positionObject.add("y", new JsonPrimitive(component.y));
//            positionObject.add("enabled", new JsonPrimitive(component.isEnabled()));
//
//            hudObject.add(component.getName(), positionObject);
//        }
//
//        guiObject.add("Components", hudObject);
//        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadHUD() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/gui/HUD.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/gui/HUD.json"));
//        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
//
//        if (guiObject.get("Components") == null)
//            return;
//
//        JsonObject windowObject = guiObject.get("Components").getAsJsonObject();
//        for (HUDComponent component : HUDComponentManager.getComponents()) 
//        {
//            if (windowObject.get(component.getName()) == null)
//                return;
//
//            JsonObject categoryObject = windowObject.get(component.getName()).getAsJsonObject();
//
//            JsonElement hudXObject = categoryObject.get("x");
//            if (hudXObject != null && hudXObject.isJsonPrimitive())
//                component.x = hudXObject.getAsInt();
//
//            JsonElement hudYObject = categoryObject.get("y");
//            if (hudYObject != null && hudYObject.isJsonPrimitive())
//                component.y = hudYObject.getAsInt();
//
//            JsonElement hudEnabledObject = categoryObject.get("enabled");
//            if (hudEnabledObject != null && hudEnabledObject.isJsonPrimitive())
//                if (hudEnabledObject.getAsBoolean())
//                    component.toggle();
//        }
//
//        inputStream.close();
//    }

//    public static void saveFriends() throws IOException 
//    {
//        registerFiles("Friends", "Social");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/social/Friends.json"), StandardCharsets.UTF_8);
//        JsonObject mainObject = new JsonObject();
//        JsonArray friendArray = new JsonArray();
//
//        for (Friend friend : FriendManager.getFriends()) 
//        {
//            friendArray.add(friend.getName());
//        }
//
//        mainObject.add("Friends", friendArray);
//        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadFriends() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/social/Friends.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/social/Friends.json"));
//        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
//
//        if (mainObject.get("Friends") == null)
//            return;
//
//        JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();
//
//        friendObject.forEach(object -> FriendManager.addFriend(object.getAsString()));
//
//        inputStream.close();
//    }

//    public static void saveEnemies() throws IOException 
//    {
//        registerFiles("Enemies", "social");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/social/Enemies.json"), StandardCharsets.UTF_8);
//        JsonObject mainObject = new JsonObject();
//        JsonArray enemyArray = new JsonArray();
//
//        for (Enemy enemy : EnemyManager.getEnemies()) 
//        {
//            enemyArray.add(enemy.getName());
//        }
//
//        mainObject.add("Enemies", enemyArray);
//        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadEnemies() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/social/Enemies.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/social/Enemies.json"));
//        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).getAsJsonObject();
//
//        if (mainObject.get("Enemies") == null)
//            return;
//
//        JsonArray enemyObject = mainObject.get("Enemies").getAsJsonArray();
//
//        enemyObject.forEach(object -> EnemyManager.addEnemy(object.getAsString()));
//
//        inputStream.close();
//    }
}