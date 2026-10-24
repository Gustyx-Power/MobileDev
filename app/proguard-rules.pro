# [Session 5] ProGuard / R8 Rules
-allowaccessmodification
-repackageclasses ''
-dontwarn org.jetbrains.annotations.**

# Keep MainActivity
-keep class com.gdgoc.luminawalls.MainActivity

# Serialization Rules
-keepattributes *Annotation*, InnerClasses
-keep,includedescriptorclasses class com.gdgoc.luminawalls.**$$serializer { *; }
-keepclassmembers class com.gdgoc.luminawalls.** {
    *** Companion;
}
-keepclasseswithmembers class com.gdgoc.luminawalls.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Strip debug logs in release
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
}


