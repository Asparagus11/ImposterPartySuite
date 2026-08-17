package com.imposter.app.game

/** The three supported game modes. */
enum class GameType(
    val id: String,
    val displayName: String,
    val emoji: String,
    val tagline: String,
) {
    IMPOSTER(
        id = "imposter",
        displayName = "Imposter",
        emoji = "🕵️",
        tagline = "Alle kennen das Wort – einer nicht.",
    ),
    UNDERCOVER(
        id = "undercover",
        displayName = "Undercover",
        emoji = "🥸",
        tagline = "Fast alle haben dasselbe Wort … fast.",
    ),
    SPYFALL(
        id = "spyfall",
        displayName = "Spyfall",
        emoji = "🌐",
        tagline = "Alle kennen den Ort – außer dem Spion.",
    );

    companion object {
        fun byId(id: String): GameType? = entries.firstOrNull { it.id == id }
    }
}

/** Anything that can be shown as a selectable tile (has id, name, emoji). */
interface Topic {
    val id: String
    val name: String
    val emoji: String
}

/** A single participant in the local pass-and-play group. */
data class Player(
    val id: Int,
    val name: String,
)

/** A word category with a display emoji and its curated German word pool (Imposter). */
data class Category(
    override val id: String,
    override val name: String,
    override val emoji: String,
    val words: List<String>,
) : Topic

/** A civilian/undercover word pair (Undercover). */
data class WordPair(
    val civilian: String,
    val undercover: String,
)

/** A category of Undercover word pairs. */
data class PairCategory(
    override val id: String,
    override val name: String,
    override val emoji: String,
    val pairs: List<WordPair>,
) : Topic

/**
 * The role handed to one player for a round.
 *  - [word] is what the player sees (null means they get no word, e.g. imposter/spy).
 *  - [isSpecial] marks the odd one out (imposter / spy / undercover).
 */
data class RoleAssignment(
    val player: Player,
    val word: String?,
    val isSpecial: Boolean,
)

/** The full result of dealing a round, game-agnostic. */
data class RoundResult(
    val gameType: GameType,
    val topicName: String,   // e.g. "Tiere" or "Ort"
    val topicEmoji: String,  // e.g. "🐾"
    val secretWord: String,  // main / civilian word / location
    val altWord: String?,    // undercover word (Undercover only)
    val assignments: List<RoleAssignment>,
) {
    val specials: List<Player> get() = assignments.filter { it.isSpecial }.map { it.player }
}
