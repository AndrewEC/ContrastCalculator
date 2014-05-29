Contrast Calculator V1.3
=====

The project was created for use within the ACOE and other I&IT clusters for checking colour contrast ratios between specified colours. It supports multiple monitors, multiple colour blind settings, and use of JNA for manipulating specific windows in the windows environment.

To view previous changes to the program look at the Changelog.txt in the root of the repository.

Current Features
---
The current list of available features are as follows:
* Multi-monitor support
* Multiple colour blind filter settings
* Dropper tool for selecting individual on screen pixels
* Take screenshots of a single monitor, multiple monitors, or an individual program window
* Save and export results into html table or textual format
* Apply colour blind filters to screenshots or dropper tool
* Magnification tool for magnifying individual windows or user defined portions of the screen

Known Issues - Tested with JRE 1.7
---
* When using a multi monitor setup the main monitor must be the top left most monitor (in terms of virtual coordinates)
* May have some issues on lower memory machines with many monitors
* Dropper tool will not update when at the very edge of the screen, though can still select pixel
* Only accepts a 6 digit hex based colour code for entering colours manually

Running the Program
---

```
1. Make sure to have at least JRE 1.5 or newer. Version 1.7 is the recommended version.
2. Download the runnable jar or exe from the build directory
	2a. The Contrast Calculator - WebLaF uses the 3rd party WebLaF look and feel
	2b. The Contrast Calculator - Steel uses Java's build in Blue Steel look and feel.
		(WebLaF looks better but Blue Steel has a much faster load time and is a bit easier on memory)
3. Run the program.
```

3rd Party Libraries
---

The project implements the following 3rd party libraries:

* JNA for native window manipulation on the Windows OS
* WebLaF look and feel for Java swing GUI

The executable version of the program was created using launch4j. The launch4j configuration file can be found in the build directory. Though it currently uses absolute paths.

Project Designer: Andrew Cumming