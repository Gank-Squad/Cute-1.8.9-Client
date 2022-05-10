
## How To Setup 

- Open the [Eclipse](./eclipse) folder as the workspace directory in Eclipse
- Hit **Window > Preferences > Java > Installed JREs > Add > Standard VM > 'Path to java 8'** and then choose the checkbox next to java 8 to use it
- Delete the **Server** option on the left if it shows up (you only need client)
- Drop down **Client**, right click **Referenced Libraries > Build Path > Configure Build Path** select 1.8.9.jar and hit **Edit**, then choose [jars/versions/1.8.9/1.8.9.jar](./jars/versions/1.8.9/1.8.9.jar), then apply and close
- Try running using the green arrow at the top, I think everything should work

