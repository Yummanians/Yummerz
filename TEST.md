# Poročilo o testiranju aplikacije Yummerz

## 1. Opis ustvarjenih testov

Za zagotavljanje kakovosti kode smo se osredotočili na testiranje poslovne logike v razredu `RecipeService`. Uporabili smo ogrodje **JUnit 5** in knjižnico **Mockito** za izolacijo testov (mockanje podatkovne baze).

Ustvarjeni testi pokrivajo naslednja področja:

1.  **Iskanje receptov (Pozitiven scenarij):**
    *   Preverja, ali metoda `getAllRecipes` pravilno vrne vse zapise, če uporabnik ne poda iskalnega niza.
    *   Preverja, ali metoda pravilno kliče filtrirno metodo v repozitoriju, če je iskalni niz podan.

2.  **Uvoz Markdown datoteke (Pozitiven scenarij in parsanje):**
    *   Testira logiko parsanja datoteke. Preverili smo, ali metoda pravilno prebere naslov (vrstica z `#`), sestavine (pod `## Ingredients`) in navodila (pod `## Instructions`) ter ali se ti podatki pravilno shranijo v objekt `Recipe`. To je ključno, saj je to edinstvena funkcionalnost naše aplikacije.

3.  **Posodabljanje recepta (Negativen scenarij):**
    *   Preverja obnašanje sistema, ko uporabnik poskuša posodobiti recept z ID-jem, ki ne obstaja. Test zagotavlja, da metoda vrne `Optional.empty()` in ne sproži nepričakovane izjeme (NullPointerException).

4.  **Brisanje recepta:**
    *   Preverja logični izhod (`true/false`) metode za brisanje glede na obstoj zapisa v bazi.

**Uporabljene anotacije:**
*   `@ExtendWith(MockitoExtension.class)`: Za inicializacijo Mockito okolja.
*   `@Mock`: Za simulacijo `RecipeRepository`.
*   `@InjectMocks`: Za vstavljanje simuliranega repozitorija v `RecipeService`.
*   `@Test`: Za označevanje testnih metod.
*   `@DisplayName`: Za berljivejše opise testov v poročilih izvajanja.

## 2. Člani skupine in odgovornosti

*   **Nikolaj Logar:** Priprava okolja za testiranje, pisanje testov za `getAllRecipes` in `deleteRecipe`.
*   **Jure Antolic:** Pisanje kompleksnejšega testa za `importRecipeFromMarkdown` in negativnega testa za `updateRecipe`. Priprava poročila.

## 3. Analiza uspešnosti testov

Po zagonu unit testov smo ugotovili naslednje:

*   **Uspešnost:** Vsi testi so uspešno opravljeni (passed). Logika za iskanje in osnovne CRUD operacije delujejo po pričakovanjih.
*   **Odkrite napake/tveganja:**
    *   Pri testiranju uvoza Markdown datoteke smo opazili, da je logika parsanja zelo občutljiva na formatiranje. Če je med `#` in imenom recepta več presledkov ali pa nobenega (npr. `#Ime`), trenutna koda `line.substring(2)` morda ne bo delovala pravilno ali pa bo odrezala napačen znak.
    *   *Popravek:* V prihodnosti bi bilo smiselno izboljšati parser, da uporablja regularne izraze (Regex) namesto fiksnega rezanja nizov (`substring`), kar bi naredilo uvoz bolj robusten. Trenutno smo zagotovili, da vsaj pravilen format deluje brezhibno.

Testiranje je potrdilo stabilnost trenutne implementacije servisa in nam dalo zaupanje za nadaljnji razvoj.
