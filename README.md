# LudumDare33
Ludum Dare 33 entry

## Setup
### Depends on:

#### Gradle
1. download [gradle.zip](https://services.gradle.org/distributions/gradle-2.5-bin.zip)
2. extract zip and put in C:/Programs/gradle or /Applications/gradle
3. add GRADLE_HOME/bin to your path (...;C:/Programs/gradle/bin)
4. test by opening a shell and type 'gradle --version'

#### GitHub and Intellij
1. open Intellij
2. go to Settings->VersionControl->GitHub
3. host: https://github.com
4. authType: Password
5. put in your username and password for GitHub
6. click the 'Test' button

### Project setup
    NOTE: after going through this process you might have to enable VersionControl
    on the projects if it doesn't happen automatically.  It is kind of a pain,
    but you have to go to Settings->VersionControl and then there is an option in
    that menu for each project to set the version control to Git.  Not sure why,
    but this only seems to happen SOME of the time.
LINK: (https://www.jetbrains.com/idea/help/associating-a-project-root-with-a-version-control-system.html)

1. Create directory LD33 (I put mine in ~/Documents)
2. Select 'Check out from Version Control'->'GitHub'
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/01.png)
3. Type in your version of paths, but keep names the same
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/02.png)
4. Click 'OK'
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/03.png)
5. Put in relative paths for your gradle and jdk setup
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/04.png)
6. Project should open and project structure should look like:
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/05.png)
7. Close that project window and open up the intellij app and repeat step 2, 3, 4, and 5
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/06.png)
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/07.png)
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/08.png)
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/09.png)
8. Project should open and project structure should look like:
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/10.png)
9. Navigate to location in picture and right click, select 'Run Application.main()'
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/11.png)
10. The game should build, compile, and run and you should see this (or at least something):
![alt tag](https://github.com/Kenoshen/LudumDare33/blob/master/help/project/12.png)
