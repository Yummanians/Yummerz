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
    Nothing yet
    <br />
    <a href="https://github.com/nikolajFERI/Yummerz"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/nikolajFERI/Yummerz">View Demo</a>
    &middot;
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
## Project descrition

Projekt je zasnovan kot monorepo, kar pomeni, da sta tako frontend kot backend koda shranjena v istem Git repozitoriju. To poenostavlja upravljanje in razvoj.

- (Korenski imenik):
	- .gitignore: Določa, katere datoteke se ne sledijo v Git.

	- LICENSE: Licenca projekta.

	- README.md: Glavna dokumentacija projekta.

- /backend:

	- Vsebuje strežniško (backend) aplikacijo, zgrajeno s Spring Boot.

	- pom.xml: Osrednja konfiguracijska datoteka za Maven, ki definira odvisnosti in način gradnje projekta.

	- mvnw, mvnw.cmd: Maven ovoj (wrapper), ki omogoča gradnjo projekta brez globalne namestitve Mavna.

	- src/main/java: Izvorna koda aplikacije v jeziku Java.

	- src/main/resources: Konfiguracijske datoteke, kot je application.properties.

- /frontend:

	- Vsebuje odjemalsko (frontend) aplikacijo, zgrajeno z ogrodjem SvelteKit.

	- package.json: Definira odvisnosti in skripte za Node.js okolje (upravljano z Bun).

	- svelte.config.js: Konfiguracijska datoteka za SvelteKit.

	- vite.config.ts: Konfiguracijska datoteka za orodje za gradnjo Vite.

	- src/routes: Struktura datotek in map v tem imeniku določa poti (routes) v aplikaciji.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Svelte][Svelte.dev]][Svelte-url]
* [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)][spring-url]
* [![MariaDB](https://img.shields.io/badge/MariaDB-003545?logo=mariadb&logoColor=white)][mariadb-url]
* [![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)][java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Installation

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

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
