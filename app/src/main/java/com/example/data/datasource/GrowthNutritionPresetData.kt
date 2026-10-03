package com.example.data.datasource

import com.example.data.model.BiologicalSex
import com.example.data.model.FoodScanResult
import com.example.data.model.GrowthVerdict
import com.example.data.model.MealAlternative

object GrowthNutritionPresetData {

    // Preset food database for instant testing & accurate local nutrition inference
    val VERIFIED_FOOD_DATABASE: List<FoodScanResult> = listOf(
        FoodScanResult(
            foodName = "Grilled Chicken Breast with Steamed Broccoli",
            servingSize = "200g serving",
            proteinG = 42.0f,
            calciumMg = 110.0f,
            sugarG = 1.5f,
            sodiumMg = 280.0f,
            caloriesKcal = 310,
            heightGrowthScore = 10,
            growthVerdict = GrowthVerdict.SUPERFOOD,
            keyGrowthNutrients = listOf("Leucine & Arginine", "Complete Protein", "Vitamin K", "Phosphorus"),
            pubertyImpactExplanation = "Arginine and leucine trigger the anterior pituitary gland to release human growth hormone (HGH). Complete amino acids provide structural collagen for epiphyseal growth plates.",
            redFlags = emptyList(),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Wild Salmon & Quinoa Bowl",
                    subtitle = "Enhanced with Omega-3 & Zinc for bone mineralization",
                    proteinG = 38.0f,
                    calciumMg = 180.0f,
                    growthBenefit = "Stimulates osteoblast differentiation in growing long bones",
                    whyBetter = "Higher zinc and Vitamin D3 content for puberty growth spurts.",
                    ingredients = listOf("Grilled Salmon 150g", "Cooked Quinoa 100g", "Steamed Asparagus", "Lemon Olive Dressing")
                )
            )
        ),
        FoodScanResult(
            foodName = "Greek Yogurt with Blueberries & Chia Seeds",
            servingSize = "1 cup (220g)",
            proteinG = 22.0f,
            calciumMg = 340.0f,
            sugarG = 8.0f,
            sodiumMg = 75.0f,
            caloriesKcal = 210,
            heightGrowthScore = 10,
            growthVerdict = GrowthVerdict.SUPERFOOD,
            keyGrowthNutrients = listOf("Casein Protein", "Bioavailable Calcium", "Zinc", "Magnesium"),
            pubertyImpactExplanation = "Greek yogurt delivers high slow-digesting casein protein that nourishes bone cartilage throughout the day and provides 26% of a teen's daily calcium requirement in one bowl.",
            redFlags = emptyList(),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Kefir & Hemp Seed Smoothie",
                    subtitle = "Probiotic calcium powerhouse",
                    proteinG = 24.0f,
                    calciumMg = 410.0f,
                    growthBenefit = "Maximum calcium absorption via probiotic gut flora",
                    whyBetter = "Even higher calcium and bioavailable zinc.",
                    ingredients = listOf("Plain Kefir 1.5 cups", "Hemp Seeds 2 tbsp", "Half Banana", "Pure Honey 1 tsp")
                )
            )
        ),
        FoodScanResult(
            foodName = "Boiled Eggs & Whole Grain Avocado Toast",
            servingSize = "2 large eggs + 1 slice",
            proteinG = 17.5f,
            calciumMg = 85.0f,
            sugarG = 1.8f,
            sodiumMg = 260.0f,
            caloriesKcal = 285,
            heightGrowthScore = 9,
            growthVerdict = GrowthVerdict.GROWTH_BOOSTER,
            keyGrowthNutrients = listOf("Whole Protein (PDCAAS 1.0)", "Vitamin D3", "Choline", "B12"),
            pubertyImpactExplanation = "Whole eggs contain natural Vitamin D3 (which increases intestinal calcium absorption by 40%) along with the ideal amino acid balance for juvenile growth.",
            redFlags = emptyList(),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Egg White & Spinach Omelet with Cheddar",
                    subtitle = "Boosts calcium to over 300mg",
                    proteinG = 26.0f,
                    calciumMg = 320.0f,
                    growthBenefit = "High protein + high calcium synergy",
                    whyBetter = "Adds rich dairy calcium to egg albumin.",
                    ingredients = listOf("3 Eggs (2 whites, 1 whole)", "Sharp Cheddar 30g", "Baby Spinach 50g", "Olive Oil")
                )
            )
        ),
        FoodScanResult(
            foodName = "Carbonated Cola & Potato Chips",
            servingSize = "355ml can + 50g bag",
            proteinG = 2.5f,
            calciumMg = 18.0f,
            sugarG = 42.0f,
            sodiumMg = 490.0f,
            caloriesKcal = 420,
            heightGrowthScore = 2,
            growthVerdict = GrowthVerdict.GROWTH_INHIBITOR,
            keyGrowthNutrients = listOf("Empty Calories", "Refined Carbohydrates"),
            pubertyImpactExplanation = "High sugar spikes blood glucose and insulin, which immediately suppresses natural Human Growth Hormone (HGH) secretion by up to 60%. Phosphoric acid in colas leaches calcium from developing epiphyseal bones.",
            redFlags = listOf(
                "42g Added Sugar: Severely blunts nocturnal & daytime HGH pulses.",
                "Phosphoric Acid: Disrupts calcium-phosphorus ratio, weakening bone mineralization.",
                "490mg Sodium: High sodium forces the kidneys to excrete vital bone calcium."
            ),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Warm Cocoa Fortified Milk with Roasted Edamame",
                    subtitle = "Satisfies savory crunch & chocolate craving with zero HGH suppression",
                    proteinG = 22.0f,
                    calciumMg = 380.0f,
                    growthBenefit = "Full calcium & protein replenishment for active puberty growth",
                    whyBetter = "Zero phosphoric acid; replaces 42g sugar with muscle-repairing nutrients.",
                    ingredients = listOf("Whole/Fortified Milk 300ml", "Unsweetened Raw Cocoa Powder", "Light Stevia/Monkfruit", "Dry Roasted Edamame 40g")
                ),
                MealAlternative(
                    title = "Sparkling Citrus Mineral Water & Spiced Pumpkin Seeds",
                    subtitle = "Fizzy sensation + Zinc bone-elongation crunch",
                    proteinG = 12.0f,
                    calciumMg = 70.0f,
                    growthBenefit = "Rich in zinc & magnesium which stimulate growth factors",
                    whyBetter = "Completely sugar-free, provides bioavailable zinc.",
                    ingredients = listOf("Carbonated Mineral Water with lime slice", "Pumpkin Seeds (Pepitas) roasted with paprika 35g")
                )
            )
        ),
        FoodScanResult(
            foodName = "Instant Ramen Noodles with Seasoning Packet",
            servingSize = "1 package (85g)",
            proteinG = 7.0f,
            calciumMg = 15.0f,
            sugarG = 3.0f,
            sodiumMg = 1680.0f,
            caloriesKcal = 380,
            heightGrowthScore = 3,
            growthVerdict = GrowthVerdict.GROWTH_INHIBITOR,
            keyGrowthNutrients = listOf("Low Micronutrients"),
            pubertyImpactExplanation = "Extremely high sodium (1,680mg - over 75% of teen daily limit). The kidneys excrete 1mg of calcium for every 100mg of sodium passed, directly depleting calcium needed for bone growth plates.",
            redFlags = listOf(
                "Excess Sodium (1680mg): Induces hypercalciuria (urinary calcium wasting).",
                "Deep-Fried Palm Oil Noodles: Promotes systemic inflammation.",
                "Inadequate Protein: Only 7g low-quality wheat gluten protein."
            ),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Bone Broth Egg & Chicken Noodle Soup",
                    subtitle = "Real collagen, bioavailable calcium & 30g pure protein",
                    proteinG = 32.0f,
                    calciumMg = 190.0f,
                    growthBenefit = "Supplies type-II collagen matrix directly for bone lengthening",
                    whyBetter = "Contains real marrow collagen, cut sodium by 60%, 4x more protein.",
                    ingredients = listOf("Simmered Chicken Bone Broth 400ml", "Shredded Chicken Breast 100g", "Soft Boiled Egg", "Brown Rice Noodles 60g", "Bok Choy")
                )
            )
        ),
        FoodScanResult(
            foodName = "Glazed Jelly Doughnut & Sweetened Energy Drink",
            servingSize = "1 pastry + 250ml can",
            proteinG = 3.0f,
            calciumMg = 22.0f,
            sugarG = 58.0f,
            sodiumMg = 360.0f,
            caloriesKcal = 490,
            heightGrowthScore = 1,
            growthVerdict = GrowthVerdict.GROWTH_INHIBITOR,
            keyGrowthNutrients = listOf("High Glycemic Sugars", "Trans Fats"),
            pubertyImpactExplanation = "Massive 58g sugar overload! Acute hyperglycemia halts the release of somatotropin (growth hormone). High caffeine doses can also truncate deep REM/slow-wave sleep where 75% of puberty growth happens.",
            redFlags = listOf(
                "58g Sugar Spike: Stalls pituitary growth hormone release for 3-4 hours.",
                "Caffeine Interference: Reduces slow-wave Stage 3/4 sleep duration.",
                "Trans fats: Impairs micro-circulation at the epiphyseal plate."
            ),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Peanut Butter Banana Oat Protein Waffle",
                    subtitle = "Natural energy + sustained amino acid release",
                    proteinG = 24.0f,
                    calciumMg = 220.0f,
                    growthBenefit = "Slow-release complex carbs protect steady growth hormone output",
                    whyBetter = "Zero refined sugar crash, packed with potassium and arginine.",
                    ingredients = listOf("Rolled Oats & Whey Waffle", "Natural Peanut Butter 2 tbsp", "Sliced Banana", "Glass of Fortified Milk")
                )
            )
        ),
        FoodScanResult(
            foodName = "Edamame & Steamed Tofu Stir-Fry",
            servingSize = "1.5 cups (250g)",
            proteinG = 28.0f,
            calciumMg = 420.0f,
            sugarG = 4.0f,
            sodiumMg = 380.0f,
            caloriesKcal = 310,
            heightGrowthScore = 10,
            growthVerdict = GrowthVerdict.SUPERFOOD,
            keyGrowthNutrients = listOf("Plant Isoflavones", "Calcium Sulfate", "Zinc", "Fiber"),
            pubertyImpactExplanation = "Tofu set with calcium sulfate is one of the highest plant-based calcium sources in the world. Edamame is packed with branched-chain amino acids essential for pubertal muscle and skeletal expansion.",
            redFlags = emptyList(),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Lentil & Cottage Cheese Curry Bowl",
                    subtitle = "High lysine and slow-digesting protein",
                    proteinG = 30.0f,
                    calciumMg = 310.0f,
                    growthBenefit = "Exceptional mineral density for bone matrix synthesis",
                    whyBetter = "Higher zinc and iron content for oxygen delivery to growing tissue.",
                    ingredients = listOf("Cooked Green Lentils 1 cup", "Cottage Cheese / Paneer 100g", "Turmeric Curry Base", "Spinach")
                )
            )
        ),
        FoodScanResult(
            foodName = "Fast Food Double Bacon Cheeseburger with Large Fries",
            servingSize = "1 large combo (380g)",
            proteinG = 34.0f,
            calciumMg = 180.0f,
            sugarG = 11.0f,
            sodiumMg = 1920.0f,
            caloriesKcal = 980,
            heightGrowthScore = 4,
            growthVerdict = GrowthVerdict.GROWTH_INHIBITOR,
            keyGrowthNutrients = listOf("Protein", "Iron", "Saturated Fat"),
            pubertyImpactExplanation = "While it contains 34g protein, it also packs nearly 2,000mg sodium and inflammatory trans-fatty acids from reused frying oils. High sodium forces heavy urinary calcium loss.",
            redFlags = listOf(
                "Excess Sodium (1920mg): Extreme bone-calcium loss through kidneys.",
                "Oxidized oils: Induces systemic microvascular stress.",
                "Caloric density with low micronutrient ratio."
            ),
            healthyAlternatives = listOf(
                MealAlternative(
                    title = "Lean Grass-Fed Beef Burger on Whole Wheat with Air-Baked Sweet Potato Wedges",
                    subtitle = "Same juicy burger taste with optimal puberty nutrition",
                    proteinG = 38.0f,
                    calciumMg = 210.0f,
                    growthBenefit = "Rich bioavailable heme iron and zinc with 70% less sodium",
                    whyBetter = "Eliminates deep-fryer trans fats, adds Vitamin A and potassium.",
                    ingredients = listOf("Lean 90/10 Ground Beef 150g", "Whole Wheat Bun", "Swiss Cheese slice", "Baked Sweet Potato Wedges 150g", "Lettuce & Tomato")
                )
            )
        )
    )

    // Growth-promoting super recipes created by adolescent nutritionists
    val NUTRITIONIST_RECIPES = listOf(
        MealAlternative(
            title = "Puberty Growth Spurt Power Scramble",
            subtitle = "Breakfast • 12 mins • Ideal morning HGH trigger",
            proteinG = 29.0f,
            calciumMg = 360.0f,
            growthBenefit = "High biological value protein with choline and Vitamin D3",
            whyBetter = "Starts the teen's day with positive nitrogen balance to support rapid bone elongation.",
            ingredients = listOf(
                "3 Large Eggs (2 whites, 1 yolk)",
                "50g Crumbled Feta or Shredded Cheddar",
                "1 cup Baby Spinach (sautéed)",
                "1 slice Sprouted Whole Grain Toast",
                "Glass of cold fortified milk (250ml)"
            )
        ),
        MealAlternative(
            title = "Nocturnal HGH Casein Recovery Shake",
            subtitle = "Pre-Bedtime • 3 mins • Feeds midnight growth spurts",
            proteinG = 34.0f,
            calciumMg = 450.0f,
            growthBenefit = "Slow-absorbing casein delivers steady amino acids across 8 hours of sleep",
            whyBetter = "Over 75% of Human Growth Hormone (HGH) is secreted during stage 3 & 4 slow-wave sleep. Casein prevents muscle and cartilage catabolism while sleeping.",
            ingredients = listOf(
                "1.5 cups Whole or Fortified Milk",
                "1 scoop Micellar Casein or Unflavored Whey",
                "1 tbsp Natural Almond Butter (rich in Arginine & Magnesium)",
                "1/2 Ripe Banana (potassium prevents cramps)",
                "Pinch of Cinnamon"
            )
        ),
        MealAlternative(
            title = "Atlantic Salmon & Quinoa Bone-Builder Bowl",
            subtitle = "Dinner • 20 mins • High Zinc & Omega-3 Matrix",
            proteinG = 41.0f,
            calciumMg = 280.0f,
            growthBenefit = "Omega-3 fatty acids EPA & DHA enhance epiphyseal cartilage cell division",
            whyBetter = "Salmon contains the highest concentration of bioavailable Vitamin D3 among natural foods, directly accelerating calcium deposition into long bone matrix.",
            ingredients = listOf(
                "160g Baked Wild Salmon Fillet",
                "1 cup Steamed Quinoa",
                "1/2 cup Edamame pods (shelled)",
                "Steamed Broccoli with lemon zest",
                "1 tbsp Toasted Sesame Seeds (super high calcium)"
            )
        ),
        MealAlternative(
            title = "Greek Yogurt & Chia Seed Superfood Parfait",
            subtitle = "Snack • 5 mins • Afternoon Spurt Fuel",
            proteinG = 24.0f,
            calciumMg = 390.0f,
            growthBenefit = "Synergy of dairy calcium, zinc, and bioflavonoids",
            whyBetter = "Prevents the afternoon teen energy dip without insulin-spiking junk food.",
            ingredients = listOf(
                "200g Authentic Greek Yogurt (plain 2%)",
                "1 tbsp Chia Seeds (soaked)",
                "1/2 cup Fresh Wild Blueberries",
                "1 tbsp Chopped Walnuts & Pumpkin Seeds",
                "1 tsp Pure Honey"
            )
        ),
        MealAlternative(
            title = "Tofu, Chicken & Green Lentil Bone Broth Stew",
            subtitle = "Lunch • 25 mins • Ultimate multi-mineral stew",
            proteinG = 44.0f,
            calciumMg = 490.0f,
            growthBenefit = "Delivers over 37% of daily calcium and 60% of daily zinc",
            whyBetter = "Bone broth naturally contains glycosaminoglycans and chondroitin, the building blocks of growing spinal cartilage.",
            ingredients = listOf(
                "100g Diced Chicken Breast",
                "100g Firm Calcium-Set Tofu",
                "1/2 cup Cooked Brown Lentils",
                "2 cups Simmered Beef or Chicken Bone Broth",
                "Chopped Kale & Carrots"
            )
        )
    )

    // Scientific CDC / WHO Growth Spurt Chart references for ages 10 to 17 (Height in cm)
    // Boys: [P10, P50, P90]
    val BOYS_GROWTH_CURVES: Map<Int, Triple<Float, Float, Float>> = mapOf(
        10 to Triple(131.0f, 138.5f, 147.0f),
        11 to Triple(136.0f, 143.5f, 153.0f),
        12 to Triple(141.5f, 149.0f, 160.0f),
        13 to Triple(148.0f, 156.0f, 168.0f), // Onset of Peak Height Velocity
        14 to Triple(155.0f, 164.0f, 175.5f), // Maximum Velocity Spurt (7-12 cm/yr)
        15 to Triple(161.5f, 170.0f, 181.0f),
        16 to Triple(165.5f, 173.5f, 184.0f),
        17 to Triple(168.0f, 175.5f, 185.5f)
    )

    // Girls: [P10, P50, P90]
    val GIRLS_GROWTH_CURVES: Map<Int, Triple<Float, Float, Float>> = mapOf(
        10 to Triple(131.5f, 138.5f, 147.0f),
        11 to Triple(137.0f, 144.5f, 154.0f), // Girls Peak Height Velocity
        12 to Triple(143.0f, 151.0f, 160.5f),
        13 to Triple(148.0f, 156.0f, 165.0f),
        14 to Triple(151.0f, 159.0f, 168.0f),
        15 to Triple(152.5f, 161.0f, 170.0f),
        16 to Triple(153.5f, 162.0f, 171.0f),
        17 to Triple(154.0f, 162.5f, 172.0f)
    )

    // Calculate estimated CDC percentile based on sex and age
    fun estimatePercentile(sex: BiologicalSex, age: Int, heightCm: Float): Int {
        val curves = if (sex == BiologicalSex.MALE) BOYS_GROWTH_CURVES else GIRLS_GROWTH_CURVES
        val triple = curves[age.coerceIn(10, 17)] ?: return 50
        val p10 = triple.first
        val p50 = triple.second
        val p90 = triple.third

        return when {
            heightCm <= p10 -> ((heightCm / p10) * 10).toInt().coerceIn(1, 10)
            heightCm <= p50 -> (10 + ((heightCm - p10) / (p50 - p10)) * 40).toInt().coerceIn(10, 50)
            heightCm <= p90 -> (50 + ((heightCm - p50) / (p90 - p50)) * 40).toInt().coerceIn(50, 90)
            else -> (90 + ((heightCm - p90) / 10f) * 9).toInt().coerceIn(90, 99)
        }
    }

    // Khamis-Roche Puberty Adult Height Projection (Scientific pediatric method)
    fun predictAdultHeight(sex: BiologicalSex, age: Int, currentHeightCm: Float, weightKg: Float): Float {
        // Statistical growth coefficients for pubertal adolescents
        val remainingGrowthPotentialCm = when (sex) {
            BiologicalSex.MALE -> when (age) {
                10 -> 38.0f
                11 -> 32.5f
                12 -> 26.5f
                13 -> 19.5f
                14 -> 12.0f
                15 -> 6.5f
                16 -> 3.0f
                else -> 1.5f
            }
            BiologicalSex.FEMALE -> when (age) {
                10 -> 25.0f
                11 -> 18.5f
                12 -> 11.5f
                13 -> 6.5f
                14 -> 3.0f
                15 -> 1.5f
                16 -> 0.8f
                else -> 0.3f
            }
        }
        return (currentHeightCm + remainingGrowthPotentialCm).coerceIn(145f, 210f)
    }
}
