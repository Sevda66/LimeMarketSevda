package com.example.catalog

object BonusManager {
    private var bonusPoints: Int = 100 // Стартовые бонусы

    fun getBonus(): Int {
        return bonusPoints
    }

    fun applyBonus(amount: Int): Boolean {
        return if (bonusPoints >= amount) {
            bonusPoints -= amount
            true
        } else {
            false
        }
    }

    fun addBonus(amount: Int) {
        bonusPoints += amount
    }
}
