# Please find the runnable jar and screenshots in the following google drive folder 
https://drive.google.com/drive/folders/1JnUsZzQFc-kqMhUiu_-cMOfWpzRz8ltm

# Source code:
	- Kindly find the below github repository 
	https://github.com/S-Sayed/random-number-game.git
		
# Running instructions
	- Please find the packaged spring boot application "random-number-game.jar" in the above google drive location

		- To start the applicaton, please run below commands 
			- For player No. 1
				- Manual Typing
					java -jar random-number-game.jar --initiator=true --isManualTyping=true --playerName=JustEat --player.no=1 --server.port=8081
					
				- Automatic Typing
					java -jar random-number-game.jar --initiator=true --isManualTyping=false --playerName=JustEat --player.no=1 --server.port=8081 
			
			- For player No. 2
				java -jar random-number-game.jar --playerName=Takeaway --player.no=2 --server.port=8082