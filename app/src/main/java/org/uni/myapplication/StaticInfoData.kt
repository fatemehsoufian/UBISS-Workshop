package org.uni.myapplication

import org.uni.mobilecomputinghomework1.R

object StaticInfoData {
    val InfoList: List<InfoData> = listOf(
        InfoData(
            image = R.drawable.happy_face,
            title = "Feel Happier",
            content = "Sunlight increases your serotonin levels, lifting your mood naturally.\n" +
                    "Let the sun put a smile on your face!"
        ),
        InfoData(
            image = R.drawable.sleeping,
            title = "Sleep Deeper",
            content = "Morning light resets your body clock, helping you fall asleep more easily.\n" +
                    "Catch the light early, sleep better tonight."
        ),
        InfoData(
            image = R.drawable.vitamin,
            title = "Make Vitamin D",
            content = "Just a little sun helps your body produce Vitamin D.\n" +
                    "Stronger bones, better immunity — powered by sunlight."
        ),
        InfoData(
            image = R.drawable.target,
            title = "Stay Focused",
            content = "Natural light sharpens your thinking and boosts concentration.\n" +
                    "Let the sun fuel your mind."
        ),
        InfoData(
            image = R.drawable.immune_system,
            title = "Boost Immunity",
            content = "Sunlight strengthens your immune system.\n" +
                    "Feel stronger, fight harder, stay well."
        ),
        InfoData(
            image = R.drawable.magic,
            title = "Glow Naturally",
            content = "Sunlight in small doses can improve some skin conditions.\n" +
                    "Let your skin catch a gentle glow."
        ),
        InfoData(
            image = R.drawable.eyes,
            title = "Support Your Vision",
            content = "Time in natural light helps protect your eyes, especially from screen fatigue.\n" +
                    "Your eyes will thank you."
        ),
        InfoData(
            image = R.drawable.stress_management,
            title = "Lower Stress",
            content = "Sun exposure helps reduce blood pressure and calm your nerves.\n" +
                    "Take a break — breathe, relax, and reset."
        ),
        InfoData(
            image = R.drawable.flash,
            title = "Energize Your Body",
            content = "Even a short sun break can boost your energy.\n" +
                    "Step into the light and feel the spark."
        ),
        InfoData(
            image = R.drawable.nature,
            title = "Connect with Nature",
            content = "Sunlight invites you to move, explore, and feel alive.\n" +
                    "Let every ray be a reason to thrive."
        ),

    )
}

data class InfoData(
    val image: Int,
    val title: String,
    val content: String
) {}