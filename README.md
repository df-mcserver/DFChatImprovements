# DFChatImprovements
A small PaperMC (1.20+) plugin which adds minor chat improvements, and allows for message forwarding to [DFProxyPlugin](https://github.com/df-mcserver/DFProxyPlugin).  
This means that the messages being sent on the backend server is more accurately forwarded to the proxy, and thus to the discord bot functionality. This plugin is required for forwarding death/advancement messages.

Adheres to the [v0.0.2-INDEV](https://github.com/df-mcserver/DFProxyPlugin/blob/92a3598c6090757cfba7aaed662f5478b248a2c5/PLUGIN_SPEC.md) spec of [DFProxyPlugin](https://github.com/df-mcserver/DFProxyPlugin)

- Emojis
   - Requires [DFJavaResources](https://github.com/df-mcserver/DFJavaResources) (or [DFBedrockResources](https://github.com/df-mcserver/DFBedrockResources) for bedrock) to work
   - Syntax: `hello :smile:` -> `hello 😄`
- Pinging
   - Plays a sound in the mentioned player's game
   - Syntax: `hello @[player-username]` -> plays a sound in `player-username`'s client
- Flex
   - /flex command
   - Shows your currently held item in chat, for others to see.