# AnimeBracketEliminationVoteCountsScraper
AnimeBracket elimination vote counts scraper

Regarding the code(/base) - it's messy and looks like garbage and I think the resultant xlsx file is not properly made since it throws an error when you try to open it with Excel, but it does what I need it to do so I don't care.

Instructions for the user:
Go to the elimination votes count page (i.e. "Current Vote Counts" in the menu), right-click and choose "Save Page As...", and choose some place to save it.
If you wanna compile this program yourself, go ahead - easiest way atm is through opening it up in Intellij and hitting run (on "Scraper"), but sbt should be able to do the trick itself (though I haven't tested that).
Currently I have an executable jar file in out\artifacts\animebracketeliminationvotecountsscraper_jar, so all you need to do is move that jar to wherever you saved the webpage and double click it.  (The moving part isn't technically necessary, but excel had some issues not being able to find the resultant spreadsheet due to I think errors with the length of the path.)  You're gonna need to have a recent version of Java (i.e. 1.8.x) installed on your system.
It'll then ask you to identify the file you want to run it on (so choose the webpage), and after a few seconds (maybe more depending on your computer's specs) it'll spit out a spreadsheet.  
When you open up the spreadsheet in Excel, it'll probably spit some errors at you - ignore those (i.e. "yes" to the prompt asking if you want to recover, and "close" to the prompt showing the logging info) and you should have a table waiting there.  
If you want the order to be how AnimeBracket does it, left-click on the arrow in the cell called "Weighted" and choose "Sort Descending".  If you want it to be in terms of raw numbers, left-click on the arrow in the cell called "Actual" and choose "Sort Descending".  