# INF1009 OOP Team Project - Setup Guide

Multi-module libGDX game project configured for Eclipse, IntelliJ IDEA, and VSCode.

---

## 📋 Prerequisites

### Required Software

1. **Java 17 or Java 21** (NOT Java 8 or Java 25!)
   - Download: [Eclipse Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21)
   - Verify installation: `java -version`

2. **Git** (for version control)
   - Download: [Git for Windows](https://git-scm.com/downloads)

3. **IDE** (Choose one or use multiple):
   - Eclipse IDE for Java Developers
   - IntelliJ IDEA (Community or Ultimate)
   - Visual Studio Code with Java extensions

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository

```bash
git clone <your-repository-url>
cd INF1009-OOP-P10-Team-8-Project
```

### 2️⃣ Verify Java Version

```bash
java -version
# Should show: Java 17 or 21 (NOT 8 or 25!)
```

### 3️⃣ Test Build (Optional - verify setup works)

```bash
# Windows
.\gradlew.bat build

# Linux/Mac
./gradlew build
```

Expected output: `BUILD SUCCESSFUL`

---

## 🔧 IDE Setup Instructions

## Eclipse Setup

### Import Project

1. **Open Eclipse**
2. **File → Import**
3. Expand **Gradle** folder
4. Select **Existing Gradle Project**
5. Click **Next**
6. Browse to project root folder
7. Click **Finish**

Eclipse will automatically:
- Import `core` and `desktop` modules
- Download dependencies
- Configure build paths

### Verify Import

You should see two projects in Project Explorer:
- `core` - Game logic module
- `desktop` - Desktop launcher module

### Common Issues

**Problem:** "Gradle nature not recognized"
**Solution:** Install Buildship plugin:
1. **Help → Eclipse Marketplace**
2. Search "Buildship"
3. Install **Buildship Gradle Integration**
4. Restart Eclipse

**Problem:** Wrong Java version
**Solution:**
1. Right-click project → **Properties**
2. **Java Build Path → Libraries**
3. Edit JRE to Java 17 or 21

---

## IntelliJ IDEA Setup

### Import Project

1. **Open IntelliJ IDEA**
2. **File → Open**
3. Select `build.gradle` in project root
4. Click **OK**
5. Choose **Open as Project**
6. Wait for Gradle sync to complete

IntelliJ will automatically:
- Recognize multi-module structure
- Download dependencies
- Index project files

### Verify Import

Check Project view (left sidebar):
```
INF1009-OOP-P10-Team-8-Project
├── core
│   └── src/
└── desktop
    └── src/
```

### Configure SDK

1. **File → Project Structure → Project**
2. Set **SDK** to Java 17 or 21
3. Set **Language Level** to 11
4. Click **OK**

### Refresh Gradle

If dependencies don't load:
1. Open **Gradle** panel (right sidebar)
2. Click **Refresh** icon 🔄

---

## Visual Studio Code Setup

### Install Extensions

VSCode should prompt to install recommended extensions. If not:

1. Open **Extensions** (Ctrl+Shift+X)
2. Install:
   - **Extension Pack for Java** (by Microsoft)
   - **Gradle for Java** (by Microsoft)

### Open Project

1. **File → Open Folder**
2. Select project root folder
3. Wait for Java/Gradle extensions to activate

### Verify Setup

- Bottom status bar should show: **Java 17** or **Java 21**
- **Explorer** view shows `core` and `desktop` folders
- No red error squiggles in `build.gradle`

### Configure Java Home (if needed)

If VSCode uses wrong Java version:

1. **Ctrl+Shift+P** → **Preferences: Open Settings (JSON)**
2. Add:
   ```json
   {
       "java.configuration.runtimes": [
           {
               "name": "JavaSE-21",
               "path": "C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.x",
               "default": true
           }
       ]
   }
   ```

---

## 📂 Project Structure

```
INF1009-OOP-P10-Team-8-Project/
├── core/                      # Core game logic
│   ├── src/                   # ✏️ Add your game classes here
│   └── assets/                # ✏️ Add images, sounds, etc. here
├── desktop/                   # Desktop launcher
│   └── src/                   # ✏️ Add desktop launcher here
├── gradle/                    # Gradle wrapper files
├── build.gradle               # Main build configuration
├── settings.gradle            # Module definitions
├── gradle.properties          # Gradle settings
├── gradlew / gradlew.bat     # Build scripts
└── .gitignore                 # Git ignore rules
```

---

## 🛠️ Gradle Commands

### Build Project
```bash
.\gradlew.bat build        # Windows
./gradlew build            # Linux/Mac
```

### Clean Build
```bash
.\gradlew.bat clean build
```

### Run Application (after creating main class)
```bash
.\gradlew.bat desktop:run
```

### Generate IDE Files
```bash
# Eclipse project files
.\gradlew.bat eclipse

# IntelliJ project files
.\gradlew.bat idea
```

### Create Distribution JAR
```bash
.\gradlew.bat desktop:dist
# Output: desktop/build/libs/desktop-1.0.jar
```

---

## 📝 Development Workflow

### 1. Create Your Main Class

Update `build.gradle` line 71:
```gradle
mainClass = 'com.yourpackage.desktop.DesktopLauncher'
```

### 2. Add Source Files

- **Core game logic** → `core/src/com/yourpackage/`
- **Desktop launcher** → `desktop/src/com/yourpackage/desktop/`
- **Assets** → `core/assets/`

### 3. Build and Test

```bash
.\gradlew.bat desktop:run
```

---

## 🔒 Git Commit Guidelines

### Before Committing

**✅ DO:**
- Test your code compiles: `.\gradlew.bat build`
- Write meaningful commit messages
- Commit source files in `core/src/` and `desktop/src/`
- Commit assets in `core/assets/`
- Commit changes to `build.gradle` if you add dependencies

**❌ DON'T COMMIT:**
- IDE-specific files (`.classpath`, `.project`, `.settings/`, `.idea/`, `*.iml`)
- Build outputs (`build/`, `*.class`, `*.jar`)
- Gradle cache (`.gradle/`)
- Personal settings (already in `.gitignore`)

### Commit Best Practices

```bash
# Check what you're committing
git status

# Stage only source files
git add core/src/
git add desktop/src/
git add core/assets/

# Write descriptive commit message
git commit -m "feat: Add player movement system"

# Push to remote
git push origin main
```

### Commit Message Format

Use clear, descriptive messages:

```
feat: Add new feature
fix: Fix bug in player movement
docs: Update README
refactor: Restructure game loop
test: Add unit tests for collision detection
```

### Before Pushing

1. **Pull latest changes:**
   ```bash
   git pull origin main
   ```

2. **Resolve conflicts** (if any)

3. **Test build:**
   ```bash
   .\gradlew.bat clean build
   ```

4. **Push:**
   ```bash
   git push origin main
   ```

---

## 🐛 Troubleshooting

### "Unsupported class file major version 69"

**Problem:** You have Java 25 installed
**Solution:** Install Java 17 or 21

**Automatic Installation (Windows):**
```powershell
# Using winget
winget install --id EclipseAdoptium.Temurin.21.JDK --silent

# Verify
java -version
```

**Manual Installation:**
1. Download: [Eclipse Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21)
2. Install and restart terminal/IDE
3. Verify: `java -version`

---

### "Could not find or load main class org.gradle.wrapper.GradleWrapperMain"

**Problem:** Missing `gradle-wrapper.jar`
**Solution:** Already included in project. If missing, run:
```bash
gradle wrapper --gradle-version 8.11.1
```

---

### "Could not resolve com.badlogicgames.gdx"

**Problem:** Network issues or missing dependencies
**Solution:**
```bash
# Clear Gradle cache and rebuild
.\gradlew.bat clean build --refresh-dependencies
```

---

### Eclipse/IntelliJ Not Recognizing Gradle

**Eclipse Solution:**
1. Install Buildship: **Help → Eclipse Marketplace → Search "Buildship"**
2. Restart Eclipse
3. Re-import project

**IntelliJ Solution:**
1. **File → Invalidate Caches / Restart**
2. Re-open project

---

### VSCode Java Extension Not Working

**Solution:**
1. **Ctrl+Shift+P** → **Java: Clean Java Language Server Workspace**
2. Reload window
3. Verify Java version: Check bottom status bar

---

## 📦 Dependencies

This project uses the following libraries (automatically downloaded by Gradle):

| Library | Version | Purpose |
|---------|---------|---------|
| **libGDX** | 1.12.1 | Game framework |
| **LWJGL3** | (via libGDX) | Desktop backend |
| **JUnit Jupiter** | 5.9.3 | Unit testing |

### Adding New Dependencies

Edit `build.gradle` in the `project(":core")` or `project(":desktop")` section:

```gradle
project(":core") {
    dependencies {
        api "com.badlogicgames.gdx:gdx:$gdxVersion"
        api "com.example:new-library:1.0.0"  // Add here
    }
}
```

Then refresh Gradle:
- **Eclipse:** Right-click project → **Gradle → Refresh Gradle Project**
- **IntelliJ:** Click Gradle refresh icon 🔄
- **VSCode:** Reload window (Ctrl+Shift+P → Reload Window)

---

## 🤝 Team Collaboration Tips

1. **Pull before you code:**
   ```bash
   git pull origin main
   ```

2. **Use different packages** to avoid conflicts:
   ```
   core/src/com/yourteam/
   ├── player/    # Team member 1
   ├── enemy/     # Team member 2
   └── ui/        # Team member 3
   ```

3. **Communicate about `build.gradle` changes**
   - Coordinate before adding dependencies
   - Test build after pulling changes

4. **Use branches for features:**
   ```bash
   git checkout -b feature/player-movement
   # Make changes
   git commit -m "feat: Add player movement"
   git push origin feature/player-movement
   # Create pull request
   ```

---

## 📞 Getting Help

1. **Build fails?** Check Troubleshooting section above
2. **IDE issues?** Verify Java version and refresh Gradle
3. **Git conflicts?** Ask teammate before force-pushing

---

## ✅ Quick Checklist

- [ ] Java 17 or 21 installed (`java -version`)
- [ ] Project imported into IDE
- [ ] Build succeeds (`.\gradlew.bat build`)
- [ ] Can see `core` and `desktop` modules
- [ ] `.gitignore` working (no IDE files in `git status`)
- [ ] Can commit and push changes

---

**Ready to start coding!** 🚀
