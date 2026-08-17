package com.imposter.app.game

/**
 * Curated German word pairs for Undercover. The majority receives the [WordPair.civilian]
 * word, the undercover player(s) the closely related [WordPair.undercover] word.
 */
object UndercoverCategories {

    val ALL: List<PairCategory> = listOf(
        PairCategory(
            id = "tiere", name = "Tiere", emoji = "🐾",
            pairs = listOf(
                WordPair("Katze", "Tiger"),
                WordPair("Hund", "Wolf"),
                WordPair("Pferd", "Esel"),
                WordPair("Löwe", "Leopard"),
                WordPair("Frosch", "Kröte"),
                WordPair("Hase", "Kaninchen"),
                WordPair("Krokodil", "Alligator"),
                WordPair("Delfin", "Hai"),
                WordPair("Biene", "Wespe"),
                WordPair("Adler", "Falke"),
                WordPair("Schmetterling", "Motte"),
                WordPair("Maus", "Ratte"),
            ),
        ),
        PairCategory(
            id = "essen", name = "Essen", emoji = "🍔",
            pairs = listOf(
                WordPair("Pizza", "Flammkuchen"),
                WordPair("Hamburger", "Sandwich"),
                WordPair("Pommes", "Kroketten"),
                WordPair("Eis", "Sorbet"),
                WordPair("Kuchen", "Torte"),
                WordPair("Tee", "Kaffee"),
                WordPair("Cola", "Limonade"),
                WordPair("Nudeln", "Spaghetti"),
                WordPair("Brot", "Brötchen"),
                WordPair("Apfel", "Birne"),
                WordPair("Schokolade", "Praline"),
                WordPair("Suppe", "Eintopf"),
            ),
        ),
        PairCategory(
            id = "sport", name = "Sportarten", emoji = "⚽",
            pairs = listOf(
                WordPair("Fußball", "Handball"),
                WordPair("Tennis", "Badminton"),
                WordPair("Ski", "Snowboard"),
                WordPair("Boxen", "Ringen"),
                WordPair("Schwimmen", "Tauchen"),
                WordPair("Joggen", "Walken"),
                WordPair("Basketball", "Volleyball"),
                WordPair("Golf", "Minigolf"),
                WordPair("Klettern", "Bouldern"),
                WordPair("Segeln", "Surfen"),
                WordPair("Judo", "Karate"),
                WordPair("Eishockey", "Feldhockey"),
            ),
        ),
        PairCategory(
            id = "orte", name = "Orte", emoji = "📍",
            pairs = listOf(
                WordPair("Strand", "Wüste"),
                WordPair("Berg", "Hügel"),
                WordPair("Kino", "Theater"),
                WordPair("Schule", "Universität"),
                WordPair("Krankenhaus", "Arztpraxis"),
                WordPair("Restaurant", "Café"),
                WordPair("See", "Meer"),
                WordPair("Wald", "Park"),
                WordPair("Bahnhof", "Flughafen"),
                WordPair("Museum", "Galerie"),
                WordPair("Hotel", "Hostel"),
                WordPair("Markt", "Supermarkt"),
            ),
        ),
        PairCategory(
            id = "gegenstaende", name = "Gegenstände", emoji = "📦",
            pairs = listOf(
                WordPair("Stuhl", "Hocker"),
                WordPair("Tasse", "Becher"),
                WordPair("Sofa", "Sessel"),
                WordPair("Handy", "Tablet"),
                WordPair("Auto", "Motorrad"),
                WordPair("Uhr", "Wecker"),
                WordPair("Buch", "Zeitung"),
                WordPair("Lampe", "Kerze"),
                WordPair("Messer", "Schere"),
                WordPair("Rucksack", "Handtasche"),
                WordPair("Fernseher", "Monitor"),
                WordPair("Brille", "Sonnenbrille"),
            ),
        ),
        PairCategory(
            id = "berufe", name = "Berufe", emoji = "👷",
            pairs = listOf(
                WordPair("Arzt", "Krankenpfleger"),
                WordPair("Lehrer", "Professor"),
                WordPair("Koch", "Bäcker"),
                WordPair("Polizist", "Detektiv"),
                WordPair("Pilot", "Flugbegleiter"),
                WordPair("Maler", "Anstreicher"),
                WordPair("Anwalt", "Richter"),
                WordPair("Friseur", "Barbier"),
                WordPair("Gärtner", "Bauer"),
                WordPair("Musiker", "Sänger"),
                WordPair("Fotograf", "Kameramann"),
                WordPair("Kellner", "Barkeeper"),
            ),
        ),
    )
}
