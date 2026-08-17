package com.imposter.app.game

/**
 * Curated German word pools for every category. Words are kept concrete and
 * well-known so that the "describe without saying it" game works for all ages.
 */
object Categories {

    val ALL: List<Category> = listOf(
        Category(
            id = "tiere", name = "Tiere", emoji = "🐾",
            words = listOf(
                "Hund", "Katze", "Elefant", "Löwe", "Tiger", "Giraffe", "Zebra", "Affe",
                "Pinguin", "Delfin", "Hai", "Adler", "Eule", "Fuchs", "Wolf", "Bär",
                "Kaninchen", "Igel", "Eichhörnchen", "Pferd", "Kuh", "Schwein", "Schaf",
                "Ziege", "Ente", "Frosch", "Schildkröte", "Schlange", "Krokodil", "Känguru",
                "Koala", "Papagei", "Biene", "Schmetterling", "Fledermaus",
            ),
        ),
        Category(
            id = "essen", name = "Essen", emoji = "🍔",
            words = listOf(
                "Pizza", "Hamburger", "Spaghetti", "Sushi", "Schnitzel", "Pommes", "Salat",
                "Suppe", "Brot", "Käse", "Apfel", "Banane", "Erdbeere", "Schokolade", "Eis",
                "Kuchen", "Döner", "Currywurst", "Lasagne", "Reis", "Kartoffel", "Ei",
                "Pfannkuchen", "Müsli", "Joghurt", "Croissant", "Bretzel", "Popcorn",
                "Wassermelone", "Honig", "Nudelsuppe", "Bratwurst", "Waffel", "Muffin",
            ),
        ),
        Category(
            id = "gegenstaende", name = "Gegenstände", emoji = "📦",
            words = listOf(
                "Stuhl", "Tisch", "Lampe", "Uhr", "Schlüssel", "Brille", "Regenschirm",
                "Kissen", "Spiegel", "Kerze", "Buch", "Handy", "Fernseher", "Kühlschrank",
                "Besen", "Eimer", "Flasche", "Tasse", "Teller", "Gabel", "Messer", "Löffel",
                "Rucksack", "Geldbeutel", "Schere", "Klebeband", "Batterie", "Taschenlampe",
                "Wecker", "Koffer", "Bilderrahmen", "Vase", "Fernbedienung", "Zahnbürste",
            ),
        ),
        Category(
            id = "filme", name = "Filme", emoji = "🎬",
            words = listOf(
                "Titanic", "Avatar", "Matrix", "Gladiator", "Shrek", "Frozen", "Batman",
                "Spiderman", "Jurassic Park", "Star Wars", "Herr der Ringe", "Harry Potter",
                "Fluch der Karibik", "Findet Nemo", "Der König der Löwen", "Toy Story",
                "Das Dschungelbuch", "Die Eiskönigin", "Ratatouille", "Cars", "Minions",
                "James Bond", "Rocky", "Terminator", "Jumanji", "Ghostbusters",
                "Zurück in die Zukunft", "E.T.", "Forrest Gump", "Der Pate", "Alien",
                "Indiana Jones", "Die Unglaublichen",
            ),
        ),
        Category(
            id = "orte", name = "Orte", emoji = "📍",
            words = listOf(
                "Strand", "Berg", "Wald", "Wüste", "Insel", "Schule", "Krankenhaus",
                "Bahnhof", "Flughafen", "Museum", "Kino", "Restaurant", "Supermarkt",
                "Bibliothek", "Schwimmbad", "Zoo", "Park", "Bauernhof", "Schloss", "Kirche",
                "Stadion", "Hotel", "Fabrik", "Leuchtturm", "Höhle", "Vulkan", "Friedhof",
                "Markt", "Hafen", "Spielplatz", "Zeltplatz", "U-Bahn", "Büro", "Werkstatt",
            ),
        ),
        Category(
            id = "sport", name = "Sportarten", emoji = "⚽",
            words = listOf(
                "Fußball", "Basketball", "Tennis", "Volleyball", "Handball", "Schwimmen",
                "Boxen", "Golf", "Skifahren", "Snowboarden", "Reiten", "Radfahren", "Turnen",
                "Klettern", "Rudern", "Segeln", "Surfen", "Tauchen", "Eishockey", "Baseball",
                "Rugby", "Badminton", "Tischtennis", "Karate", "Judo", "Fechten",
                "Leichtathletik", "Marathon", "Bogenschießen", "Gewichtheben", "Skateboarden",
                "Bowling", "Darts", "Angeln",
            ),
        ),
        Category(
            id = "berufe", name = "Berufe", emoji = "👷",
            words = listOf(
                "Arzt", "Lehrer", "Polizist", "Feuerwehrmann", "Koch", "Bäcker", "Metzger",
                "Friseur", "Pilot", "Anwalt", "Richter", "Krankenpfleger", "Zahnarzt",
                "Elektriker", "Klempner", "Maler", "Tischler", "Bauer", "Fischer", "Gärtner",
                "Kellner", "Verkäufer", "Programmierer", "Journalist", "Architekt",
                "Ingenieur", "Musiker", "Schauspieler", "Fotograf", "Astronaut", "Soldat",
                "Briefträger", "Busfahrer", "Bibliothekar",
            ),
        ),
        Category(
            id = "marken", name = "Marken", emoji = "™️",
            words = listOf(
                "Coca-Cola", "Pepsi", "Nike", "Adidas", "Puma", "Apple", "Samsung", "Google",
                "Microsoft", "Amazon", "Netflix", "Disney", "McDonald's", "Burger King",
                "Starbucks", "Ikea", "Lego", "Ferrari", "Porsche", "BMW", "Mercedes",
                "Volkswagen", "Audi", "Tesla", "Sony", "Nintendo", "Lidl", "Aldi", "Rewe",
                "Milka", "Haribo", "Nutella", "Red Bull", "Rossmann",
            ),
        ),
        Category(
            id = "werkzeuge", name = "Werkzeuge", emoji = "🔧",
            words = listOf(
                "Hammer", "Schraubenzieher", "Zange", "Säge", "Bohrmaschine", "Wasserwaage",
                "Meißel", "Feile", "Schraubenschlüssel", "Akkuschrauber", "Nagel", "Schraube",
                "Dübel", "Zollstock", "Cuttermesser", "Schleifpapier", "Pinsel", "Rolle",
                "Spachtel", "Kelle", "Schaufel", "Spaten", "Harke", "Axt", "Beil",
                "Kreissäge", "Winkelschleifer", "Lötkolben", "Maßband", "Wagenheber",
                "Schraubstock", "Schmirgel", "Stemmeisen", "Kneifzange",
            ),
        ),
        Category(
            id = "superkraefte", name = "Superkräfte", emoji = "🦸",
            words = listOf(
                "Fliegen", "Unsichtbarkeit", "Superstärke", "Gedankenlesen", "Teleportation",
                "Zeitreise", "Feueratem", "Eiskraft", "Heilung", "Superschnelligkeit",
                "Röntgenblick", "Telekinese", "Formwandlung", "Unverwundbarkeit",
                "Wetterkontrolle", "Elektrizität", "Schrumpfen", "Wachsen", "Tarnung",
                "Tierkommunikation", "Wasseratmung", "Magnetismus", "Laseraugen",
                "Regeneration", "Schweben", "Wandkriechen", "Traummanipulation",
                "Schallwellen", "Duplikation", "Energiebälle", "Zeitstopp", "Gedankenkontrolle",
            ),
        ),
        Category(
            id = "aengste", name = "Ängste", emoji = "😱",
            words = listOf(
                "Höhenangst", "Spinnenangst", "Platzangst", "Flugangst", "Dunkelheit",
                "Schlangen", "Zahnarzt", "Prüfungsangst", "Gewitter", "Tiefes Wasser",
                "Clowns", "Feuer", "Einsamkeit", "Krankheit", "Insekten", "Menschenmengen",
                "Blut", "Spritzen", "Geister", "Haie", "Nadeln", "Enge Räume", "Achterbahn",
                "Öffentliches Reden", "Hunde", "Mäuse", "Erdbeben", "Ertrinken",
                "Versagen", "Verlust", "Tunnel", "Aufzüge", "Brücken",
            ),
        ),
        Category(
            id = "erfindungen", name = "Erfindungen", emoji = "💡",
            words = listOf(
                "Rad", "Glühbirne", "Telefon", "Buchdruck", "Dampfmaschine", "Auto",
                "Flugzeug", "Computer", "Internet", "Fernseher", "Radio", "Kühlschrank",
                "Waschmaschine", "Penicillin", "Impfung", "Batterie", "Kompass", "Uhr",
                "Fotoapparat", "Mikroskop", "Teleskop", "Rakete", "Roboter", "Smartphone",
                "Aufzug", "Fahrrad", "Nähmaschine", "Klimaanlage", "Mikrowelle",
                "Taschenrechner", "Papier", "Schießpulver", "Laser", "Satellit",
            ),
        ),
        Category(
            id = "koerper", name = "Körper & Gesundheit", emoji = "🩺",
            words = listOf(
                "Herz", "Gehirn", "Lunge", "Leber", "Niere", "Magen", "Muskel", "Knochen",
                "Haut", "Auge", "Ohr", "Nase", "Zunge", "Zahn", "Hand", "Fuß", "Knie",
                "Ellbogen", "Schulter", "Rücken", "Blut", "Ader", "Nerv", "Fieber",
                "Erkältung", "Husten", "Impfung", "Verband", "Pflaster", "Vitamine",
                "Schlaf", "Bewegung", "Ernährung", "Skelett",
            ),
        ),
        Category(
            id = "laender", name = "Länder", emoji = "🌍",
            words = listOf(
                "Deutschland", "Frankreich", "Italien", "Spanien", "Portugal", "England",
                "Irland", "Schweden", "Norwegen", "Finnland", "Polen", "Österreich",
                "Schweiz", "Niederlande", "Belgien", "Griechenland", "Türkei", "Russland",
                "China", "Japan", "Indien", "Australien", "Kanada", "USA", "Mexiko",
                "Brasilien", "Argentinien", "Ägypten", "Südafrika", "Kenia", "Marokko",
                "Thailand", "Island", "Kroatien",
            ),
        ),
        Category(
            id = "superhelden", name = "Superhelden", emoji = "🦸‍♂️",
            words = listOf(
                "Superman", "Batman", "Spider-Man", "Iron Man", "Hulk", "Thor",
                "Captain America", "Wonder Woman", "The Flash", "Aquaman", "Green Lantern",
                "Black Panther", "Doctor Strange", "Ant-Man", "Black Widow", "Hawkeye",
                "Captain Marvel", "Deadpool", "Wolverine", "Cyclops", "Storm", "Professor X",
                "Robin", "Batgirl", "Supergirl", "Groot", "Star-Lord", "Nightwing",
                "Daredevil", "Ghost Rider", "Silver Surfer", "Green Arrow", "Vision",
                "Scarlet Witch",
            ),
        ),
        Category(
            id = "beruehmtheiten", name = "Berühmtheiten", emoji = "🌟",
            words = listOf(
                "Albert Einstein", "Michael Jackson", "Madonna", "Elvis Presley", "Beyoncé",
                "Lady Gaga", "Cristiano Ronaldo", "Lionel Messi", "Usain Bolt", "Roger Federer",
                "Barack Obama", "Angela Merkel", "Queen Elizabeth", "Leonardo DiCaprio",
                "Brad Pitt", "Angelina Jolie", "Tom Cruise", "Will Smith", "Johnny Depp",
                "Taylor Swift", "Ed Sheeran", "Adele", "Rihanna", "Eminem", "Steve Jobs",
                "Bill Gates", "Elon Musk", "Oprah Winfrey", "Michael Jordan", "Muhammad Ali",
                "Charlie Chaplin", "Mozart", "Beethoven", "Marilyn Monroe",
            ),
        ),
    )

    fun byId(id: String): Category? = ALL.firstOrNull { it.id == id }
}
