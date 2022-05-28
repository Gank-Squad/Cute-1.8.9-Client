### How To Install 

- Download the release you want (cute.zip not the source) 

- Extract the `cute` folder into `/.minecraft/versions/` or extract the content of the `cute` folder into `/.minecraft/version/cute/`
 
- Add the new version in the minecraft launcher 

- Then just run the release like normal, if the game crashes and you have updated the version try deleting the `/.minecraft/cute/` folder, it's likely a settings related crash


### How To Setup For Eclipse 

- Open the [Eclipse](./eclipse) folder as the workspace directory in Eclipse

- Hit `Window > Preferences > Java > Installed JREs > Add > Standard VM > 'Path to java 8'` and then choose the checkbox next to java 8 to use it

- Delete the **Server** option on under package manager if it shows up (you only need client)

- Drop down **Client**, right click `Referenced Libraries > Build Path > Configure Build Path` select 1.8.9.jar and hit `Edit`, then choose [jars/versions/1.8.9/1.8.9.jar](./jars/versions/1.8.9/1.8.9.jar), then apply and close

- Same as above but scroll down until you see `Realms`, hit edit, and select [jars/libraries/com/mojang/realms/realms-1.7.59.jar](./jars/libraries/com/mojang/realms/realms-1.7.59.jar)

- Try running using the green arrow at the top, I think everything should work

- If the step above doesn't run the game, click the little dropdown arrow next to that green button, double click `Java Application`, then set project to `Client`, and main class to `Start`, then under the arguments tab put `-Xmx1024M -Xms1024M` under `VM arguments`. You should then set working directory to `other` and paste `${workspace_loc:Client/jars}`. You're then good to hit save and run.


### How To Compile From Eclipse 

- Assuming you've done the setup for Eclipse and can run minecraft from it, hit `File > Export > Java > Jar File > *Drop Down Client* > Check 'src' > *Uncheck Everything Else* > Finish` this will get you a jar file

- Once you've done the above step, open the jar file with 7zip/winrar and delete `start.class` and `META-INF`

- Then you need to find and open the 1.8.9.jar file with 7zip/winrar, should be located somewhere in the `/.minecraft/versions/1.8.9` folder, and copy the `assets` folder, `log4j2.xml` and `pack.png` into the jar file you made

- Lastly, in the repo you need `jars\libraries\io\netty\netty-all\4.0.23.Final\netty-all-4.0.23.Final.jar`, open this with 7zip/winrar and copy `io` into the jar you made with Eclipse, once you've done that you're done, and it should work following the install guide above
