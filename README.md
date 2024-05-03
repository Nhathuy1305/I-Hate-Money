<div align="center">

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Actions][actions-shield]][actions-url]

</div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://ihatemoney.live/">
    <img src="./images/IHateMoney_logo.png" alt="Logo" width="250">
</a>

<h3 align="center">WEB APPLICATION DEVELOPMENT</h3>
<h4 align="center">Project Name: ihatemoney.live</h4>

  <p align="center">
    A comprehensive full-stack web application designed to empower users in effectively managing their finances and budget. The project serves for the Web Application Development course at International University - VNUHCM!
  </p>
    <a href="https://ihatemoney.live/">View Website</a>

</div>

<!-- TABLE OF CONTENTS -->

# Table of contents

1. [Introduction](#Introduction)
2. [Techniques](#Techniques)
3. [Features](#Features)
4. [Contributing](#Contributing)
5. [Acknowledgments](#Acknowledgments)
6. [Contact](#Contact)

# Introduction <a name="Introduction"></a>

<div align="justify">
IHateMoney stands as an all-encompassing web-based solution crafted to empower individuals in the meticulous management
of their finances and the adept navigation of budgetary matters. Offering an intuitively designed interface 
coupled with a robust toolkit, IHateMoney facilitates seamless tracking of income streams, expenditures, and savings 
endeavors. By harnessing the capabilities of IHateMoney, users are bestowed with the invaluable ability to exercise 
precise control over their financial landscape, thus enabling them to embark on a journey of informed decision-making 
about their monetary affairs.
</div>

### Team Members

| Order |     Name      |     ID      |         Email         |                     Github account                      |                            
|:-----:|:-------------:|:-----------:|:---------------------:|:-------------------------------------------------------:|                          
|   1   | Dang Nhat Huy | ITITIU20043 | dnhuy.ityu@gmail.com  |      [Nhathuy1305](https://github.com/Nhathuy1305)      |
|   2   |  Ho Huu Hiep  | ITITIU20202 | hieptotheho@gmail.com |      [HieptotheHo](https://github.com/HieptotheHo)      |

### Installation

1. Open the terminal on your IDE
2. Clone the repo
   ```sh
   git clone https://github.com/Nhathuy1305/I-Hate-Money.git
   ```
3. Navigate to the project directory
   ```sh
   cd I-Hate-Money
   ```
4. Change branches (We have 2 branches: `main` and `staging`)
   - The `main` branch is used for building on the production environment (Choose it if you want to deploy for
     public access, the current workflow is set up for Microsoft Azure based on `.github/workflows/main_i-hate-money.yml`)
   ```sh
   git checkout main
   ```
   - The `staging` branch is used for testing and debugging purposes (Choose it if you want to test the application, or
     hobby purposes)
   ```sh
   git checkout staging
   ```
5. Ensure you have Node.js installed by running node -v in your terminal. If you don't have Node.js, you can download
   and install it from the official Node.js website (https://nodejs.org).
6. Install dependencies
   ```sh
   npm install
   ```
7. Edit the `src/main/resources/application.properties` for personal use. You need to modify:
    ```
   server.port=
   spring.datasource.url=
   spring.datasource.username=
   spring.datasource.password=
   ```
   based on your local database configuration (MySQL, PostgreSQL, etc).
8. Open the project in Intellij IDEA and run the `Application.java` file.
9. Open your web browser and visit `http://localhost:8081` to access the app.

### Motivation

<div align="justify">
Imagine a world where individuals feel empowered and in control of their financial destinies, where budgeting becomes a 
seamless and intuitive part of daily life rather than a burdensome chore. This is the vision driving the creation of 
IHateMoney. By crafting a comprehensive web application that not only simplifies financial management but also educates 
and empowers users, we have the opportunity to revolutionize the way people interact with their finances. With 
IHateMoney, users can effortlessly track their income, monitor expenses, and strategically plan their savings, all 
within a user-friendly interface designed to foster a sense of control and confidence. By embarking on this journey to 
create IHateMoney, we are not just developing software; we are catalyzing a movement towards financial liberation and 
empowerment.
</div>


# Techniques <a name="Techniques"></a>

- **Front-end**: HTML, CSS, JavaScript, Vaadin (TypeScript)
- **Back-end**: Java, Spring Boot, Vaadin (Java)
- **Database**: MySQL
- **Authentication**: Spring Security
- **Charts**: Vaadin/Highcharts
- **Deployment**: Microsoft Azure


# Features <a name="Features"></a>
- **User Registration and Authentication**: The app enables users to securely create an account and log in, granting
  them access to their financial details.
- **Dashboard**: Providing a comprehensive snapshot of the user's financial status, the dashboard showcases income,
  expenses, and savings.
- **Expense Tracking**: Simplifying the process, users can monitor their income and expenses by categorizing them and
  establishing budgets.
- **Income Management**: Users can effortlessly add and manage their income sources within the app, facilitating a
  better grasp of their cash flow.
- **Budgeting**: Prospero App presents budgeting features, empowering users to define personalized budgets for various
  expense categories and monitor their progress.
- **Reports and Analytics**: Detailed reports and visually appealing analytics aid users in scrutinizing their financial
  information, identifying spending trends.


# Contributing<a name="Contributing">

Contributions to IHateMoney are welcome! If you find any issues or have suggestions
for improvements, please submit an issue or a pull request to
the [GitHub repository](https://github.com/your-username/ihatemoney-fork).


# Acknowledgments<a name="Acknowledgments">
<div align="justify">
We would want to express our gratitude to <b>Assoc. Prof. Nguyen Van Sinh</b> and <b>MSc. Tran Khai Minh</b> for providing us with the chance to participate in this
project and apply what we learned in theory into practice. This project's learning curve was steep, but it was well
worth it for all of us. We have learned more about interface technologies to construct a functioning application that
interacts with our project :heart:.
</div>


# Contact<a name="Contact">

If you have any questions or need assistance, feel free to reach out to the
project maintainer:

- Email: dnhuy.ityu@gmail.com
- GitHub: [github.com/Nhathuy1305](https://github.com/Nhathuy1305)

<br />

<p align="right"><a href="#top">Back to top ↑</a></p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/Nhathuy1305/I-Hate-Money.svg?style=for-the-badge

[contributors-url]: https://github.com/Nhathuy1305/I-Hate-Money/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/Nhathuy1305/I-Hate-Money.svg?style=for-the-badge

[forks-url]: https://github.com/Nhathuy1305/I-Hate-Money/network/members

[stars-shield]: https://img.shields.io/github/stars/Nhathuy1305/I-Hate-Money.svg?style=for-the-badge

[stars-url]: https://github.com/Nhathuy1305/I-Hate-Money/stargazers

[issues-shield]: https://img.shields.io/github/issues/Nhathuy1305/I-Hate-Money.svg?style=for-the-badge

[issues-url]: https://github.com/Nhathuy1305/I-Hate-Money/issues

[actions-shield]: https://img.shields.io/github/actions/workflow/status/Nhathuy1305/I-Hate-Money/main_i-hate-money.yml.svg?style=for-the-badge

[actions-url]: https://github.com/Nhathuy1305/I-Hate-Money/actions/workflows/main_i-hate-money.yml