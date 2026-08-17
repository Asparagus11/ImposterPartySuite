package com.imposter.app.ui

import com.imposter.app.game.GameType

/** Static content: per-game rules and the "about the app" section. */
object GameGuide {

    fun rules(type: GameType): List<String> = when (type) {
        GameType.IMPOSTER -> listOf(
            "Alle Spieler außer dem/den Imposter(n) bekommen dasselbe geheime Wort.",
            "Das Gerät wird herumgereicht – jeder deckt seine Rolle nur für sich auf (gedrückt halten).",
            "Reihum beschreibt jeder das Wort mit einem Hinweis, ohne es direkt zu nennen.",
            "Die Imposter kennen das Wort nicht und müssen so tun, als ob – oder es erraten.",
            "Danach wird diskutiert und abgestimmt, wer der Imposter ist.",
            "Tipp: Nicht zu offensichtlich sein – sonst fliegt der Imposter sofort auf.",
        )
        GameType.UNDERCOVER -> listOf(
            "Die Mehrheit (Bürger) bekommt ein Wort, die Undercover ein ähnliches, aber anderes Wort.",
            "Niemand weiß zunächst, ob er Bürger oder Undercover ist – die Wörter sind sich ähnlich.",
            "Reihum gibt jeder einen Hinweis auf sein Wort, ohne es zu nennen.",
            "Aus den Hinweisen versucht ihr herauszuhören, wer ein abweichendes Wort hat.",
            "Dann wird abgestimmt: Wer ist Undercover?",
            "Tipp: Zu genaue Hinweise verraten dich, zu vage machen dich verdächtig.",
        )
        GameType.SPYFALL -> listOf(
            "Alle Spieler kennen denselben geheimen Ort – nur der/die Spion(e) nicht.",
            "Das Gerät wird herumgereicht – jeder sieht seinen Ort bzw. seine Spion-Rolle.",
            "Reihum stellt ihr euch Fragen zum Ort (z.B. \"Was machst du hier?\").",
            "Die Spione müssen mitraten und dürfen nicht auffliegen.",
            "Die anderen versuchen, die Spione zu enttarnen – die Spione, den Ort zu erraten.",
            "Tipp: Fragen sollten zeigen, dass du den Ort kennst, ohne ihn zu verraten.",
        )
    }

    object About {
        const val codename = "Asparagus"
        const val version = "2.0"
        const val description =
            "Eine Sammlung geselliger Party-Ratespiele für ein Gerät (Pass-and-Play). " +
                "Komplett offline, ohne Werbung, ohne Tracking, ohne benötigte Berechtigungen."

        /** Used open-source libraries and their licenses. */
        val libraries: List<Pair<String, String>> = listOf(
            "Kotlin" to "Apache-2.0",
            "Jetpack Compose (UI, Foundation, Material 3)" to "Apache-2.0",
            "AndroidX Activity Compose" to "Apache-2.0",
            "AndroidX Lifecycle / ViewModel" to "Apache-2.0",
            "AndroidX Navigation Compose" to "Apache-2.0",
            "AndroidX DataStore Preferences" to "Apache-2.0",
            "kotlinx.serialization" to "Apache-2.0",
            "kotlinx.coroutines" to "Apache-2.0",
        )
    }
}
