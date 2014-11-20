#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.8.1
 Author:         myName

 Script Function:
	Template AutoIt script.

#ce ----------------------------------------------------------------------------

#include <Process.au3>

Run('C:\Program Files\CCleaner\CCleaner64.exe')
WinWaitActive("Piriform CCleaner")

For $i = 1 to 8
Sleep (1000)
Send("{TAB}")
Next

For $i = 1 to 2
Sleep (2000)
Send("{ENTER}")
Next

Sleep (20000)

_RunDos("taskkill /f /im CCleaner64.exe")

WinWaitActive("Piriform CCleaner")






