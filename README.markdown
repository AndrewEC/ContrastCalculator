Contrast Calculator V1.4.2
=====

The project was created for use within the ACOE and other I&IT clusters for checking colour contrast ratios between specified colours. It supports multiple monitors, multiple colour blind settings, and use of JNA for manipulating specific windows in the windows environment.

To view previous changes to the program look at the Changelog.txt in the root of the repository.

Current Features
---
The current list of available features are as follows:
* Multi-monitor support
* Multiple colour blind filter settings
* Dropper tool for selecting the colour of an individual pixel
* Take screenshots of a single monitor, multiple monitors, or an individual program window
* Capture individual portions of the screen with the built in snipping tool
* Apply colour blind filters to any of the screen capturing tools

Known Issues - Tested with JRE 1.7
---
* When using a multi monitor setup the main monitor must be the top left most monitor (in terms of virtual coordinates)
* May have some issues on lower memory machines with many monitors
* Dropper tool will not update when at the very edge of the screen, making it difficult to indentify which pixel is currently in focus
* When using the area snipper tool you must select an area by dragging from the top left most portion to the bottom right most portion, no other method of selection is currently viable.

Running the Program
---

```
1. Have JRE version 1.7 or newer installed. This program will work with as old as 1.5 but you may experience issues using this old of a version.
2. Download the runnable jar or exe from the build directory
	2a. The Contrast Calculator - WebLaF uses the 3rd party WebLaF look and feel
	2b. The Contrast Calculator - Steel uses Java's build in Blue Steel look and feel.
		(WebLaF looks better but Blue Steel has a much faster load time and is a bit lighter on memory)
3. Run the program.
```

Note: This program requires that your computer and Java version support per-pixel translucency/transparency in order for the snipping tool to properly function.

3rd Party Libraries
---

The project implements the following 3rd party libraries:

* JNA for native window manipulation on the Windows OS
* WebLaF look and feel for Java Swing GUI

The executable version of the program was created using launch4j. The launch4j configuration file can be found in the build directory but they use absolute paths.

This project was created using Eclipse for Java Developers - Luna M6 Release (4.4.0M6)
Later moved to Intellij IDEA.

Email: andrew.e.cumming@gmail.com

Project Page: https://portfolio-andrewsstuff.rhcloud.com/page/contrast_calculator
