# ApricotSharePluging para Godot 4.7.2

Plugin nativo de Android (v2) para Godot 4.7.2 que permite compartir texto e imágenes mediante el diálogo nativo de Android (`Intent.ACTION_SEND` / `Intent.createChooser`).

Desarrollado originalmente por **David "shiro" Beltran** (Apricot Studios).

---

## 🚀 Características

- Compatible con la arquitectura **Android Plugin v2** de Godot 4.7.2.
- Comparte texto y enlaces de forma nativa.
- Comparte imágenes utilizando un `FileProvider` dedicado (`ApricotShareFileProvider`), evitando conflictos con el `FileProvider` interno de Godot durante el merger de Android Manifests.
- Mantiene compatibilidad con el singleton `ApricotSharePluging` y los métodos originales en GDScript.

---

## 🛠️ Compilación

Para compilar las variantes `debug` y `release` del archivo `.aar`, ejecuta el wrapper de Gradle en la raíz del proyecto:

```powershell
.\gradlew.bat :app:assemble
```

Al compilar, los binarios `.aar` generados se copiarán automáticamente en la ruta:
`godot-addon/addons/apricot_share/bin/`

---

## 📦 Instalación en Godot

1. Copia la carpeta `godot-addon/addons/apricot_share` dentro de la carpeta `res://addons/` de tu proyecto de Godot.
2. Abre tu proyecto en Godot y ve a:
   **Proyecto** > **Ajustes del Proyecto** > **Plugins** y activa **Apricot Share**.
3. En los ajustes de exportación de Android:
   - Asegúrate de habilitar **Use Gradle Build** (Compilación Gradle).
   - En la sección **Plugins**, verifica que **ApricotSharePluging** esté marcado.

---

## 💻 Uso en GDScript

### Obtener el Singleton

```gdscript
var share_plugin = null

func _ready() -> void:
    if Engine.has_singleton("ApricotSharePluging"):
        share_plugin = Engine.get_singleton("ApricotSharePluging")
    else:
        print("ApricotSharePluging no está disponible en esta plataforma.")
```

### Compartir Texto

```gdscript
func compartir_texto() -> void:
    if share_plugin:
        var titulo = "Compartir puntuación"
        var asunto = "¡Nuevo récord!"
        var mensaje = "¡He conseguido 1000 puntos en mi juego favorito! Pruébalo tú también."
        
        var exito: bool = share_plugin.apricotShareText(titulo, asunto, mensaje)
        if not exito:
            print("No se pudo iniciar el diálogo de compartir texto.")
```

### Compartir Imagen

> **Nota:** La ruta de la imagen debe ser una ruta absoluta válida y accesible en el almacenamiento del dispositivo (por ejemplo, en `OS.get_user_data_dir()`).

```gdscript
func compartir_captura(ruta_absoluta_imagen: String) -> void:
    if share_plugin:
        var titulo = "Compartir captura"
        var asunto = "Mira esta captura"
        var mensaje = "¡Mira lo que acabo de lograr!"
        
        # Ejemplo: ruta_absoluta_imagen = ProjectSettings.globalize_path("user://screenshot.png")
        var exito: bool = share_plugin.apricotShareImg(ruta_absoluta_imagen, titulo, asunto, mensaje)
        if not exito:
            print("No se pudo compartir la imagen.")
```

---

## 📄 Métodos Disponibles

| Método | Argumentos | Retorno | Descripción |
| :--- | :--- | :--- | :--- |
| `apricotShareText` | `title: String, subject: String, text: String` | `bool` | Abre el menú nativo para compartir texto. |
| `apricotShareImg` | `pathImg: String, title: String, subject: String, text: String` | `bool` | Abre el menú nativo para compartir una imagen junto con texto opcional. |

---

## 📝 Licencia / Autor

- **Autor:** David "shiro" Beltran
- **Versión:** 2.0.0
