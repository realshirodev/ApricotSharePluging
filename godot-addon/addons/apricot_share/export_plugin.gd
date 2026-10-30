@tool
extends EditorPlugin

var export_plugin: AndroidExportPlugin


func _enter_tree() -> void:
	export_plugin = AndroidExportPlugin.new()
	add_export_plugin(export_plugin)


func _exit_tree() -> void:
	remove_export_plugin(export_plugin)
	export_plugin = null


class AndroidExportPlugin extends EditorExportPlugin:
	func _supports_platform(platform: EditorExportPlatform) -> bool:
		return platform is EditorExportPlatformAndroid


	func _get_android_libraries(
		_platform: EditorExportPlatform, debug: bool
	) -> PackedStringArray:
		var build_type := "debug" if debug else "release"
		return PackedStringArray([
			"apricot_share/bin/ApricotSharePluging-%s.aar" % build_type
		])


	func _get_android_dependencies(
		_platform: EditorExportPlatform, _debug: bool
	) -> PackedStringArray:
		return PackedStringArray(["androidx.core:core:1.17.0"])


	func _get_name() -> String:
		return "ApricotSharePluging"
