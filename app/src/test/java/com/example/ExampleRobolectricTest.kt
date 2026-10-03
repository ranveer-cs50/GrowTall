package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.datasource.GrowthNutritionPresetData
import com.example.data.model.BiologicalSex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("GrowTall", appName)
  }

  @Test
  fun `growth percentiles and adult height prediction work`() {
    val predictedHeight = GrowthNutritionPresetData.predictAdultHeight(
      sex = BiologicalSex.MALE,
      age = 14,
      currentHeightCm = 164.0f,
      weightKg = 52.0f
    )
    assertTrue("Predicted adult height should be reasonable", predictedHeight in 170.0f..190.0f)

    val percentile = GrowthNutritionPresetData.estimatePercentile(
      sex = BiologicalSex.MALE,
      age = 14,
      heightCm = 164.0f
    )
    assertEquals(50, percentile)
  }
}
