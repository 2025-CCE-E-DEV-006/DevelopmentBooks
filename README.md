# 📚Development Books 

This simple coding kata is designed to be solved using **Test-Driven Development (TDD)**.

A publisher offers a series of **five books on software development**, loved by developers who strive to sharpen their craft. To encourage readers to buy more, the publisher offers **discounts for purchasing multiple different titles**.

The goal is to implement a system that calculates the total price of any basket of books according to the discount rules.

## Book List

Each book costs **50 EUR** when bought individually. The available books are:

1. *Clean Code* (Robert Martin, 2008)
2. *The Clean Coder* (Robert Martin, 2011)
3. *Clean Architecture* (Robert Martin, 2017)
4. *Test Driven Development by Example* (Kent Beck, 2003)
5. *Working Effectively with Legacy Code* (Michael C. Feathers, 2004)

##  Discount Rules

- 1 unique book: **No discount**
- 2 unique books: **5% discount**
- 3 unique books: **10% discount**
- 4 unique books: **20% discount**
- 5 unique books: **25% discount**

📝 **Note:**
If the basket contains multiple copies of some books, the discounts only apply to the **unique** books within each set. The goal is to organize books into sets that maximize the overall discount.

📄 Problem description :  
 [Development Books – stephane-genicot.github.io](https://stephane-genicot.github.io/DevelopmentBooks.html)
---

## Requirements

- **Java** : 1.8
- **Maven** : For Dependency management
- **JUnit** : 5.x

## Commit Message Style Guide
The project have followed the [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/), which provides a consistent format for writing commit messages.
Each commit messages contains **Title**. The title consists of the type of the message and subject. `type: Subject`

#### Commit Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Changes to documentation
- **style**: Code formatting changes (e.g., fixing indentation, removing spaces, etc.)
- **refactor**: Code refactoring without affecting functionality
- **test**: Adding or refactoring tests
- **chore**: Updates to build processes or auxiliary tools (e.g., package manager configs)

## How to Build the Application

- Clone this repository:
   ```bash
   https://github.com/2025-CCE-E-DEV-006/DevelopmentBooks
- Build the project and run the tests by running
    ```bash
    mvn clean install
- The **Model Classes** used in the project are generated from the **OpenAPI** specification during the build process. Running `mvn clean install` will regenerate the models as part of the build.

##  API Details
**POST** :  `/api/bookstore/calculateprice`

Calculates the final price for a given set of books with applicable discounts.

**Input Request (JSON)** : Send a list of books and their quantities. Request contains below fields.


| Field      | Description                                                 |
|------------|-------------------------------------------------------------|
| `bookId`   | Unique identifier of the book.     |
| `quantity` | Number of copies being purchased for the given `bookId`.    |

📁 Sample input is available at : `src/main/resources/examples/sample-input.json`

**Response  (JSON)** : Returns a detailed response with grouped pricing information based on the applied discounts. Response contains below fields.


| Field                                 | Description |
|---------------------------------------|-------------|
| `listOfBookGroups`                    | List of groups formed based on unique book sets. |
| `listOfBookGroups[].bookIds`          | IDs of the unique books in that group. |
| `listOfBookGroups[].numberOfBooks`    | Number of unique books in the group. |
| `listOfBookGroups[].discountPercentage` | Discount applied to this group based on size. |
| `listOfBookGroups[].actualPrice`      | Group price before discount (e.g., 4 × 50 EUR = 200). |
| `listOfBookGroups[].discountAmount`   | Discount amount for the group. |
| `listOfBookGroups[].finalGroupPrice`  | Final price of the group after discount. |
| `actualPrice`                         | Total price before discounts (sum of all group actual prices). |
| `totalDiscount`                       | Total discount applied across all groups. |
| `finalPrice`                          | Total amount to be paid after all discounts. |

📁 Sample output is available at : `src/main/resources/examples/sample-output.json`

## Test reports

- Once after successful build of
  `mvn clean install`, navigate to target folder of the project root directory
- **Jacoco code coverage report :** Code Coverage report will be available in `target\site\jacoco` folder. View the report by launching **index.html**
- **pi test coverage report:** Mutation Coverage report will be available in `target\pit-reports` folder. View the report by launchig **index.html**