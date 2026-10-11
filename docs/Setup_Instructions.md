# Setup Instructions

This project uses Java 21.

## Check Java
```powershell
java -version
javac -version
```

## Run Hello World
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile:
```powershell
javac HelloWorld.java
```

Run:
```powershell
java HelloWorld
```

## Run this project
```powershell
cd "C:\Users\sharm\OneDrive\Desktop\Airtribe_assignment\LearnTrack-Student-Course-Management-System"
javac -d out (Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
java -cp out com.airtribe.learntrack.Main
```
