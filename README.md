<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Issues][issues-shield]][issues-url]
[![AGPL-3.0][license-shield]][license-url]




<!-- PROJECT LOGO -->
<br />
<h3 align="center">Yummerz</h3>

  <p align="center">
    Projekt yummerz je aplikacija za pridobivanje prilagojenih receptov
    <br />
    <a href="https://github.com/nikolajFERI/Yummerz"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/nikolajFERI/Yummerz/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/nikolajFERI/Yummerz/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## Besednjak

- **Recept** – navodila za pripravo jedi; vsak recept vsebuje ključne *tag-e*.  
- **Tag** – kategorični opis jedi, npr. *visokobeljakovinska*, *vlakninasta*, *vegetarijanska*, *veganska*, *nizkokalorična* ipd.  
- **Sestavina** – posamezen del recepta, ki ga uporabnik lahko vnese v aplikacijo.

### Stopnje kritičnosti sestavin
- **Osrednji del** – ključna sestavina recepta  
- **Opcijsko** – sestavina, ki jo lahko nadomestimo ali izpustimo  
- **Dodatek** – sestavina za izboljšanje okusa ali teksture, ni nujna

### Stopnje stanja recepta
- **Odobreno** – Recept je bil odobren
- **V čakanju** – Recept čaka na odobritev
- **Pomanjkljivo** – Moderator je ocenil da je recept preveč pomanjkljiv za objavo
- **Zavrnjeno** – Moderator je recept zavrnil

---

<!-- ## Vizija Projekta -->

Cilj projekta **Yummerz** je omogočiti uporabnikom enostavno in hitro pripravo obrokov iz sestavin, ki jih že imajo doma. Aplikacija poenostavlja načrtovanje obrokov, zmanjšuje zapravljanje hrane in prihrani čas, ki bi ga uporabnik porabil za iskanje ustreznih receptov, po spletu, to je namreč lahko zamudno saj moramo prečesati ogromno strani da najdemo recepte katere lahko prirpavimo z sestavinami, ki so nam v trenutku na voljo, še posebej če se nam mudi.  

Namenjena je vsem, ki želijo jesti bolj načrtno, zdravo ali ekonomično, ne da bi se morali odpovedati raznolikosti in okusu.

---

## DPU

![alt text](https://github.com/nikolajFERI/Yummerz/blob/main/dpu.png)




## CLASS DIAGRAM

![alt text](https://github.com/nikolajFERI/Yummerz/blob/main/ClassDiagram.png)


### Opis razredov

#### **Uporabnik**
Razred **Uporabnik** predstavlja osnovnega uporabnika aplikacije. Uporabnik lahko ustvarja nove recepte ter določa in ureja svoje preference, ki vplivajo na priporočila in filtriranje vsebin v aplikaciji.

#### **Vsebinski administrator**
Razred **Vsebinski administrator** razširja funkcionalnosti razreda *Uporabnik*. Poleg vseh uporabniških pravic ima administrator možnost urejati in brisati recepte drugih uporabnikov, s čimer skrbi za kakovost in ustreznost vsebin v aplikaciji.

#### **Recept**
**Recept** je osrednji razred aplikacije. Vsebuje podatke, kot so naslov recepta, seznam sestavin in pripadajoči tagi. Recept je mogoče objaviti ali izvoziti v datoteko. Pred objavo lahko prehaja skozi različna stanja.

#### **Preferenca**
Razred **Preferenca** predstavlja uporabnikove prehranske in življenjske navade. Ključni elementi preferenc so izbrane sestavine ter opis življenjskega sloga. Sestavine lahko uporabnik izbere iz nabora že obstoječih sestavin, življenjski slog pa poljubno opiše.

#### **Stanje**
**Stanje** je enumeracija, ki določa vsa možna stanja recepta pred objavo (npr. osnutek, v pregledu, zavrnjen, pripravljen za objavo).

#### **Tag**
**Tag** predstavlja kratek opis ali kategorijo recepta. Uporablja se za označevanje tipa jedi, kuhinje ali drugih značilnosti recepta, kar olajša iskanje in filtriranje.

#### **Sestavina**
Razred **Sestavina** predstavlja posamezno sestavino recepta in uporabnikovih preferenc. Vsaka sestavina ima ime in ceno ter je ključen element pri sestavljanju receptov.





| Primer uporabe: Ustvarjanje recepta                                                                                                                                                                                    | ID: 1 |
| ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Ustvariti nov recept z vsemi potrebnimi podatki.                                                                                                                                                             |       |
| **Akterji:** Uporabnik, Sistem                                                                                                                                                                                         |       |
| **Predpogoji:** Uporabnik mora biti prijavljen.                                                                                                                                                                        |       |
| **Stanje sistema po PU:** Recept je shranjen v bazo (stanje se spremeni).                                                                                                                                              |       |
| **Scenarij:**<br>1. Uporabnik izbere možnost »Dodaj recept«. <br>2. Sistem prikaže obrazec. <br>3. Uporabnik vnese podatke. <br>4. Uporabnik potrdi vnos. <br>5. Sistem validira podatke. <br>6. Sistem shrani recept. |       |
| **Alternativni tokovi:**<br>1: Uporabnik prekliče dodajanje. <br>2: Recept se shrani kot osnutek.                                                                                                                      |       |
| **Izjeme:**<br>1: Neveljavni podatki → sistem zahteva popravek. <br>2: Napaka baze → sistem prikaže obvestilo.                                                                                                         |       |



| Primer uporabe: Dodajanje razpoložljivih sestavin                                                                                           | ID: 2 |
| ------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Dodati sestavine, ki jih ima uporabnik doma.                                                                                      |       |
| **Akterji:** Uporabnik, Sistem                                                                                                              |       |
| **Predpogoji:** Uporabnik mora biti prijavljen.                                                                                             |       |
| **Stanje sistema po PU:** Posodobljen seznam sestavin.                                                                                      |       |
| **Scenarij:**<br>1. Uporabnik odpre seznam sestavin. <br>2. Doda ali vnese sestavine. <br>3. Potrdi spremembe. <br>4. Sistem shrani stanje. |       |
| **Alternativni tokovi:**<br>1: Uporabnik odstrani sestavino.                                                                                |       |
| **Izjeme:**<br>1: Neveljaven vnos. <br>2: Napaka pri shranjevanju.                                                                          |       |


| Primer uporabe: Izbira prehrambenih preferenc                                                                    | ID: 3 |
| ---------------------------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Nastaviti prehranske potrebe (vegansko, alergije…).                                                    |       |
| **Akterji:** Uporabnik, Sistem                                                                                   |       |
| **Predpogoji:** Uporabnik je prijavljen.                                                                         |       |
| **Stanje sistema po PU:** Preference so shranjene.                                                               |       |
| **Scenarij:**<br>1. Uporabnik odpre razdelek preferenc. <br>2. Izbere možnosti. <br>3. Sistem shrani nastavitve. |       |
| **Alternativni tokovi:**<br>1: Uporabnik spremeni napačno izbiro.                                                |       |
| **Izjeme:**<br>1: Napaka pri shranjevanju podatkov.                                                              |       |
|                                                                                                                  |       |


| Primer uporabe: Dodajanje tag-ov                                     | ID: 4 |
| -------------------------------------------------------------------- | ----- |
| **Cilj:** Oznaka recepta s ključnimi besedami.                       |       |
| **Akterji:** Uporabnik, Sistem                                       |       |
| **Predpogoji:** Uporabnik ureja ali ustvarja recept.                 |       |
| **Stanje sistema po PU:** Tag-i so shranjeni v receptu.              |       |
| **Scenarij:**<br>1. Uporabnik vnese tag-e. <br>2. Sistem jih shrani. |       |
| **Alternativni tokovi:**<br>1: Uporabnik odstrani tag.               |       |
| **Izjeme:**<br>1: Tag presega dovoljeno dolžino.                     |       |



| Primer uporabe: Objavljanje recepta                                                                           | ID: 5 |
| ------------------------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Objaviti recept za javnost.                                                                         |       |
| **Akterji:** Uporabnik, Sistem                                                                                |       |
| **Predpogoji:** Recept mora biti popoln.                                                                      |       |
| **Stanje sistema po PU:** Recept postane javen.                                                               |       |
| **Scenarij:**<br>1. Uporabnik izbere »Objavi«. <br>2. Sistem preveri celovitost. <br>3. Sistem objavi recept. |       |
| **Alternativni tokovi:**<br>1: Recept se objavi kot nepopoln (če je dovoljeno).                               |       |
| **Izjeme:**<br>1: Manjkajo podatki → objava zavrnjena.                                                        |       |



| Primer uporabe: Urejanje javnih receptov                                                       | ID: 6 |
| ---------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Posodobiti podatke javnih receptov.                                                  |       |
| **Akterji:** Vsebinski administrator, Sistem                                                   |       |
| **Predpogoji:** Admin je prijavljen.                                                           |       |
| **Stanje sistema po PU:** Recept je posodobljen.                                               |       |
| **Scenarij:**<br>1. Admin izbere recept. <br>2. Uredi podatke. <br>3. Sistem shrani spremembe. |       |
| **Alternativni tokovi:**<br>1: Admin prekliče urejanje.                                        |       |
| **Izjeme:**<br>1: Konflikt sočasnega urejanja.                                                 |       |


| Primer uporabe: Brisanje javnih receptov                                                                                            | ID: 7 |
| ----------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Odstraniti recept iz sistema.                                                                                             |       |
| **Akterji:** Vsebinski administrator, Sistem                                                                                        |       |
| **Predpogoji:** Admin prijavljen; recept obstaja.                                                                                   |       |
| **Stanje sistema po PU:** Recept izbrisan iz baze.                                                                                  |       |
| **Scenarij:**<br>1. Admin izbere recept. <br>2. Klikne »Izbriši«. <br>3. Sistem zahteva potrditev. <br>4. Recept se trajno izbriše. |       |
| **Alternativni tokovi:**<br>1: Preklic brisanja.                                                                                    |       |
| **Izjeme:**<br>1: Recept ni mogoče izbrisati zaradi odvisnosti.                                                                     |       |

| Primer uporabe: Pregled receptov                                                       | ID: 8 |
| -------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Prikaz vseh javnih receptov.                                                 |       |
| **Akterji:** Uporabnik, Sistem                                                         |       |
| **Predpogoji:** Ni posebnih pogojev.                                                   |       |
| **Stanje sistema po PU:** Stanje ostane enako.                                         |       |
| **Scenarij:**<br>1. Uporabnik klikne »Pregled receptov«. <br>2. Sistem prikaže seznam. |       |
| **Alternativni tokovi:**<br>1: Ni receptov → prikaže se prazno stanje.                 |       |
| **Izjeme:**<br>1: Napaka povezave → prikaz se ne naloži.                               |       |


| Primer uporabe: Filtriranje receptov                                                     | ID: 9 |
| ---------------------------------------------------------------------------------------- | ----- |
| **Cilj:** Prikaz receptov po izbranih kriterijih.                                        |       |
| **Akterji:** Uporabnik, Sistem                                                           |       |
| **Predpogoji:** Recepti morajo obstajati.                                                |       |
| **Stanje sistema po PU:** Ni trajnih sprememb.                                           |       |
| **Scenarij:**<br>1. Uporabnik izbere filter. <br>2. Sistem prikaže filtrirane rezultate. |       |
| **Alternativni tokovi:**<br>1: Filter vrne 0 zadetkov.                                   |       |
| **Izjeme:**<br>1: Napaka pri iskanju.                                                    |       |


| Primer uporabe: Uploadanje datoteke                                                                                                                 | ID: 10 |
| --------------------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| **Cilj:** Naložiti datoteko z receptom.                                                                                                             |        |
| **Akterji:** Uporabnik, Sistem                                                                                                                      |        |
| **Predpogoji:** Uporabnik mora biti prijavljen.                                                                                                     |        |
| **Stanje sistema po PU:** Datoteka je naložena in pripravljena za branje.                                                                           |        |
| **Scenarij:**<br>1. Uporabnik klikne »Naloži datoteko«. <br>2. Izbere datoteko. <br>3. Sistem preveri veljavnost. <br>4. Preide na branje datoteke. |        |
| **Alternativni tokovi:**<br>1: Napačen format datoteke.                                                                                             |        |
| **Izjeme:**<br>1: Napačna struktura datoteke. <br>2: Prekinjen prenos.                                                                              |        |


| Primer uporabe: Branje recepta iz datoteke                                                                                                     | ID: 11 |
| ---------------------------------------------------------------------------------------------------------------------------------------------- | ------ |
| **Cilj:** Pretvoriti vsebino datoteke v podatkovni model recepta.                                                                              |        |
| **Akterji:** Sistem                                                                                                                            |        |
| **Predpogoji:** Datoteka je bila uspešno naložena.                                                                                             |        |
| **Stanje sistema po PU:** Podatki recepta pripravljeni za ustvarjanje.                                                                         |        |
| **Scenarij:**<br>1. Sistem odpre datoteko. <br>2. Prebere podatke. <br>3. Pretvori jih v strukturo. <br>4. Nadaljuje na »Ustvarjanje recepta«. |        |
| **Izjeme:**<br>1: Datoteka se ne da odpreti. <br>2: Neveljavna vsebina.                                                                        |        |


| Primer uporabe: Pretvorba recepta v datoteko                                                                                      | ID: 12 |
| --------------------------------------------------------------------------------------------------------------------------------- | ------ |
| **Cilj:** Izvoziti recept v datoteko.                                                                                             |        |
| **Akterji:** Uporabnik, Sistem                                                                                                    |        |
| **Predpogoji:** Recept mora obstajati.                                                                                            |        |
| **Stanje sistema po PU:** Ustvarjena datoteka; baza ostane nespremenjena.                                                         |        |
| **Scenarij:**<br>1. Uporabnik izbere »Izvozi«. <br>2. Sistem ponudi format. <br>3. Ustvari datoteko. <br>4. Uporabnik jo prenese. |        |
| **Izjeme:**<br>1: Napaka pri generiranju. <br>2: Manjkajo podatki v receptu.                                                      |        |


## Project layout

```
.
└── Yummerz
    ├── backend
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   ├── pom.xml
    │   ├── src
    │   │   ├── main
    │   │   │   ├── java
    │   │   │   │   └── com
    │   │   │   │       └── yummerz
    │   │   │   │           └── yummerz
    │   │   │   │               ├── Recipe.java
    │   │   │   │               ├── RecipeController.java
    │   │   │   │               ├── RecipeRepository.java
    │   │   │   │               ├── RecipeService.java
    │   │   │   │               └── YummerzApplication.java
    │   │   │   └── resources
    │   │   │       └── application.properties
    │   │   └── test
    │   │       └── java
    │   │           └── com
    │   │               └── yummerz
    │   │                   └── yummerz
    │   │                       └── YummerzApplicationTests.java
    │   └── yummerz.zip
    ├── frontend
    │   ├── bun.lock
    │   ├── eslint.config.js
    │   ├── package.json
    │   ├── README.md
    │   ├── src
    │   │   ├── app.d.ts
    │   │   ├── app.html
    │   │   ├── lib
    │   │   │   ├── assets
    │   │   │   │   └── favicon.svg
    │   │   │   └── index.ts
    │   │   └── routes
    │   │       ├── +layout.svelte
    │   │       └── +page.svelte
    │   ├── static
    │   │   └── robots.txt
    │   ├── svelte.config.js
    │   ├── tsconfig.json
    │   └── vite.config.ts
    ├── LICENSE
    └── README.md
```
<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Svelte][Svelte.dev]][Svelte-url]
* [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)][spring-url]
* [![MariaDB](https://img.shields.io/badge/MariaDB-003545?logo=mariadb&logoColor=white)][mariadb-url]
* [![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)][java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting started

### Prerequisits
   - Git

   - Java JDK 25 ali novejša

   - Bun

   - Delujoč strežnik MariaDB

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/nikolajFERI/Yummerz.git
   cd Yumerz
   ```
2. Nastavitev baze podatkov:

    Prijavite se v vaš MariaDB strežnik.

    Ustvarite novo bazo podatkov z imenom yummerz_db.
    code SQL
    ```sql
	CREATE DATABASE yummerz_db;
	```
	Ustvarite uporabnika in mu dodelite vse pravice za to bazo podatkov.

3. Konfiguracija za Backend:

    Pojdite v mapo backend.

    V src/main/resources/ uredite datoteko application.properties.

    Vnesite pravilno pristanišče (portnumber), uporabniško ime (hidden_username) in geslo (hidden_password) za dostop do vaše MariaDB baze.
	```properties
	spring.datasource.url=jdbc:mariadb://localhost:3306/yummerz_db
	spring.datasource.username=VAŠE_UPORABNIŠKO_IME
	spring.datasource.password=VAŠE_GESLO
	```
	***Pomembno:***  Teh podatkov nikoli ne shranjujte (commit) v Git!
4. Zagon Backend aplikacije:
    V terminalu se pomaknite v mapo backend.
    Zaženite aplikacijo z Maven ovojem:
    ```sh
	./mvnw spring-boot:run
	```
5.  Zagon Frontend aplikacije:
    Odprite nov terminal in se pomaknite v mapo frontend.
    Namestite vse potrebne odvisnosti:
    ```sh
	bun install
	```
	Zaženite razvojni strežnik:
	```sh
	bun run dev
	```
	Aplikacija bo dostopna na naslovu, ki ga izpiše terminal (običajno http://localhost:5173).

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [x] Funkcija iskanja
- [ ] Kategorije
- [ ] ...

See the [open issues](https://github.com/nikolajFERI/Yummerz/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Prispevki so tisto, zaradi česar je odprtokodna skupnost tako neverjeten kraj za učenje, navdih in ustvarjanje. Vsak vaš prispevek je zelo cenjen.

Če imate predlog, ki bi to izboljšal, vas prosimo, da ustvarite "fork" repozitorija in odprete "pull request". Lahko pa preprosto odprete "issue" z oznako "enhancement". Ne pozabite projektu dati zvezdice! Hvala še enkrat!

1. Naredite "fork" projekta
2. Ustvarite svojo vejo za novo funkcionalnost (```git checkout -b feature/AmazingFeature```)
3. Potrdite svoje spremembe (```git commit -m 'Add some AmazingFeature'```)
4. Potisnite spremembe v vejo (```git push origin feature/AmazingFeature```)
5. Odprite "Pull Request"

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Top contributors

<a href="https://github.com/nikolajFERI/Yummerz/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=nikolajFERI/Yummerz" alt="contrib.rocks image" />
</a>



<!-- LICENSE -->
## License

Distributed under the AGPL-3.0. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Avaiable soon
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Template used](https://github.com/othneildrew/Best-README-Template/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/nikolajFERI/Yummerz.svg?style=for-the-badge
[contributors-url]: https://github.com/nikolajFERI/Yummerz/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/nikolajFERI/Yummerz.svg?style=for-the-badge
[forks-url]: https://github.com/nikolajFERI/Yummerz/network/members
[stars-shield]: https://img.shields.io/github/stars/nikolajFERI/Yummerz.svg?style=for-the-badge
[stars-url]: https://github.com/nikolajFERI/Yummerz/stargazers
[issues-shield]: https://img.shields.io/github/issues/nikolajFERI/Yummerz.svg?style=for-the-badge
[issues-url]: https://github.com/nikolajFERI/Yummerz/issues
[license-shield]: https://img.shields.io/github/license/nikolajFERI/Yummerz.svg?style=for-the-badge
[license-url]: https://github.com/nikolajFERI/Yummerz/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
<!-- Shields.io badges. You can a comprehensive list with many more badges at: https://github.com/inttter/md-badges -->

[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[spring-url]: https://spring.io/
[mariadb-url]: https://mariadb.org/
[java-url]: https://openjdk.org/index.html
