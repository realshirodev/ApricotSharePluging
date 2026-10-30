ApricotSharePluging for Godot 4.7.2

This repository contains the Android plugin source and the Godot v2 export
addon. It preserves the `ApricotSharePluging` singleton and the existing
`apricotShareText(title, subject, text)` and
`apricotShareImg(path, title, subject, text)` GDScript methods.

## Build

Build both AAR variants with the Gradle wrapper:

```powershell
.\gradlew.bat :app:assemble
```

The build copies the debug and release AARs into
`godot-addon/addons/apricot_share/bin/`. Copy that `apricot_share` folder into
the Godot project's `addons/` directory, enable `plugin.cfg`, and export for
Android. The project build also copies the AARs into the parent game's addon
folder for local testing.

The plugin compiles against `org.godotengine:godot:4.7.2.stable` and registers
through Android plugin v2 metadata. Image sharing uses a dedicated
`ApricotShareFileProvider`, separate from Godot's own provider, to avoid Android
manifest-merger conflicts.
