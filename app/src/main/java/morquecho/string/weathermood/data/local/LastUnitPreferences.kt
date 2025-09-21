package morquecho.string.weathermood.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastUnitPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("last_unit_prefs", Context.MODE_PRIVATE)

    fun saveLastUnit(unit: String) {
        sharedPreferences.edit { putString("last_unit", unit) }
    }

    fun getLastUnit(): String? {
        return sharedPreferences.getString("last_unit", null)
    }
}