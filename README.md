# Aether Client

A Fabric client-side mod starter for Minecraft Java 1.21.11, plus the matching Aether website.

## Important status
This is a working project foundation, not a finished 160-module client. It includes the module framework, ClickGUI, settings examples, several real client-side utilities, a polished static website, and a GitHub Actions build workflow. Advanced combat automation and base-finding features are not implemented in this starter and must be developed and tested individually.

## Requirements
- Minecraft Java Edition 1.21.11
- Java 21
- Fabric Loader
- Gradle (GitHub Actions builds it in the cloud)

## Build in GitHub
1. Create a GitHub repository and upload the contents of this ZIP.
2. Open **Actions** and run **Build Aether Client** (or push a commit).
3. Download the `aether-client-jar` artifact from the completed workflow run.
4. Put the JAR in your Minecraft `mods` folder alongside Fabric API.

## Local build
Install JDK 21 and Gradle, then run:
```bash
gradle build
```
The JAR will be in `fabric-mod/build/libs/`.

## Open the website
Open `site/index.html` in a browser, or publish the `site/` directory using GitHub Pages. The download button points to the repository's GitHub Releases page by default. Change the repository URL in `site/index.html` and `site/script.js` to your actual repository before publishing.

## Included modules
- ClickGUI (Right Shift)
- Fullbright (restores the previous gamma value when disabled)
- Sprint (holds sprint while moving forward)
- Coordinates HUD
- FPS HUD
- Armor HUD (basic durability display)

The settings framework supports booleans, numeric values, and selectable options. Each module owns its own settings. More modules can be added under `dev.aether.client.module.modules`.

## Safety and compatibility
Use client-side visual features only where permitted by the server. Multiplayer servers may prohibit combat automation, entity tracking, or world scanning. This starter does not include x-ray/base scanning, anti-cheat bypasses, or automated combat.
