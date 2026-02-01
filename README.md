
# Final Project – Shopping Website (Backend - nit - shop)

## Project Description

This project is the **backend implementation** of a full end-to-end shopping website, developed as a final project for the course.

The system is inspired by common e-commerce websites such as **Amazon, Ebay, Shufersal and Rami Levi**, and provides all the required features described in the assignment, including user management, item browsing, favorites, stock handling, orders, and checkout.

The backend is implemented using **Java and Spring Boot**, following the architecture and coding principles learned in the course (MVC, JDBC, Mappers, Enums, proper naming, etc.).

---

## System Logic and Features

### 1. Main Page (Backend Support)

The backend provides APIs to support the main page, including:

* Fetching all available items
* Searching items by name
* Returning item details such as:

    * Title
    * Image URL
    * Price (USD)
    * Stock amount

Items with zero stock are still returned, allowing the frontend to display
**“0 items left in stock”**.

---

### 2. Items Management

Each item in the system contains:

* Item ID
* Title
* Description
* Image URL
* Price
* Stock
* Category

Stock is fully managed by the system and updated automatically when orders are completed.

---

### 3. Search Bar

The backend exposes an API that allows searching items by name.

* The search returns all items that contain the requested text in their title.
* If no items are found, an empty list is returned so the frontend can notify the user.

---

### 4. Login & User Management

* Users can **register**, **login**, and **delete their account**
* Authentication is handled using **JWT (JSON Web Token)**
* Each user account stores:

    * User ID
    * First name
    * Last name
    * Email
    * Phone
    * Address (country & city)
* When a user deletes their account, **all associated data is deleted automatically**, including:

    * Favorites
    * Orders
    * Order items

---

### 5. Favorite List

* Each logged-in user can add or remove items from a favorites list
* Each item can appear **only once per user**
* Favorites are saved in the database and persist after logout/login
* Only authenticated users can access and modify favorites
* Favorite items are returned with the same data as items in the main page

---

### 6. Stock Management

* Each item has a stock quantity
* Users cannot order more items than available in stock
* Stock decreases when an order is completed
* If stock reaches zero, the item remains visible with a “0 items left in stock” status
* Stock is validated again during checkout to prevent invalid orders

---

### 7. Order List

For each order, the system stores:

* Order ID
* User ID
* Order date
* Shipping address
* Total price
* Order status (`TEMP`, `CLOSE`)

Each user can view **all their orders**, including:

* A single pending (TEMP) order
* All closed (CLOSE) orders

The pending order always appears first in the order list.

---

### 8. Order Process (Cart)

the 

* Each user can have **only one pending (TEMP) order**
* The TEMP order acts as a shopping cart
* Adding the first item automatically creates a TEMP order
* Adding the same item again increases its quantity
* Removing all items deletes the TEMP order
* The cart is saved even after logout/login

#### Checkout:

* Stock is re-validated
* Stock is reduced
* Order status changes from `TEMP` to `CLOSE`
* After checkout, the user can create a new pending order

Closed orders are **read-only**.

---

## ️ Database 

* The project uses **H2 database**
* Access is implemented using **Spring JDBC**
* Data is mapped using custom **RowMappers**
* Foreign keys include **ON DELETE CASCADE** to ensure data integrity

Main tables:

* users
* items
* favorites
* orders
* order_items

---

##  Project Architecture

The backend follows the **MVC pattern** as learned in the course:

* **Controllers** – REST endpoints
* **Services** – business logic
* **Repositories (JDBC)** – database access
* **Mappers** – SQL → Java mapping
* **Models & Enums** – domain objects

Security is implemented using:

* Spring Security
* JWT authentication
* Role-based authorization (`USER`, `ADMIN`)

---

##  Admin Features

Users with the `ADMIN` role can:

* Create new items
* Update existing items
* Update item stock
* Delete items

Admin endpoints are protected and not accessible to regular users.
This is added to the admin User managing capabilities.

---

## Technology Stack

### Backend

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring JDBC
* H2 Database

### Tools

* Maven
* Postman
* Git & GitHub

---

##  API Overview (Examples)

### Public / User APIs

* `POST /auth/register`
* `POST /auth/login`
* `GET /items`
* `GET /items/search?query=...`
* `POST /favorites/{itemId}`
* `DELETE /favorites/{itemId}`
* `GET /favorites`
* `POST /orders/cart/items`
* `DELETE /orders/cart/items/{itemId}`
* `POST /orders/checkout`
* `GET /orders`
* `GET /orders/{orderId}`

### Admin APIs

* `POST /admin/items`
* `PUT /admin/items/{id}`
* `PUT /admin/items/{id}/stock`
* `DELETE /admin/items/{id}`

---

## Technology Stack

### Backend

* **Java**
* **Spring Boot**
* **Spring Security**
* **JWT Authentication**
* **Spring JDBC**
* **H2 Database**

### Tools

* **Postman** (API testing)
* **Maven**
* **Git & GitHub**

---

## ️ How to Run

1. Clone the repository
2. Open in IntelliJ / IDE
3. Run the Spring Boot application
4. H2 Console available at:

   ```
   http://localhost:8080/h2-console
   ```
5. API available at:

   ```
   http://localhost:8080
   ```
---

## Notes

* This repository contains **only the backend**
* The frontend is implemented in a **separate React repository**


## Things i wish to implemet -

* better API calling for the items (scalability)
* image host solution and implementation.


