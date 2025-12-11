# ---------------------
# KEEP COMPOSE RUNTIME
# ---------------------
# Compose uses reflection and generated classes.
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.navigation.** { *; }

# Keep Kotlin metadata (used by Compose & coroutines)
-keep class kotlin.Metadata { *; }

# Keep generated Compose classes
-keep class **_ComposableSingletons** { *; }

# Prevent removal of Composable lambdas
-keepclassmembers class ** {
    @androidx.compose.runtime.Composable <methods>;
}

# ---------------------
# KOTLINX SERIALIZATION
# ---------------------
# Keep serializers and generated metadata
-keepclassmembers class kotlinx.serialization.** { *; }
-keepclassmembers class **$$serializer { *; }
-keepclassmembers class **$serializer { *; }
-keep class kotlinx.serialization.json.** { *; }

# Ensure polymorphic serialization works
-keep class * implements kotlinx.serialization.KSerializer

# ---------------------
# DATASTORE
# ---------------------
# Keep DataStore types and serializers
-keep class androidx.datastore.** { *; }
-keep class androidx.datastore.preferences.** { *; }

# ---------------------
# COROUTINES
# ---------------------
# Keep coroutine machinery
-keep class kotlinx.coroutines.** { *; }

# ---------------------
# OKIO (used by DataStore core)
# ---------------------
-keep class okio.** { *; }

# ---------------------
# KEEP ENTRY POINTS
# ---------------------
# Your Application and main Activity
-keep class com.autogenie.inhaleexhale.** { *; }

# ---------------------
# REMOVE LOGGING
# ---------------------
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
