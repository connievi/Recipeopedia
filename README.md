Original App Design Project - README
===

# Recipeopedia

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This Android mobile app will serve as a platform for users to search up recipes based off their unique food tastes.

### App Evaluation
- **Category:** Health
- **Mobile:** This app will be very user friendly to mobile users. Users can easily search through recipes and tap on recipes to learn more specific information about them, such as instructions, ingredients, and health labels. 
- **Story:** This app is very compelling towards users as they can easily find new recipes based on their personal preferences. 
- **Market:** The market for this app is typically teenagers or adults who want to learn/explore new recipes. 
- **Habit:** Users will use this app when they want to discover new recipes. They can consume and create content on this app.
- **Scope:** I believe the scope is realistic for this app and allows me to incorporate many different useful and relevant features.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can log in
* User can sign up to create a new account
* User can log out
* User can search keywords or choose categories (breakfast, lunch, dinner, etc.) to navigate to a screen of recipes
* User can view/scroll through generated recipes
* User can tap on a recipe and view detailed recipe information, users' reviews
* User can leave reviews on a recipe (comment + image) to share their experience
* User can double tap to like other users' reviews
* User can long press to save recipes to favorite
* User can save favorite recipes
* Incorporate at least one external library to add visual polish
* Incorporate animated loading symbol while waiting for recipes to load

**Optional Nice-to-have Stories**

* Filter recipes by total ingredient cost and other factors
* User can receive suggested grocery information for guidance where to buy ingredients
* User can view generated “Recipes for You” and a daily suggested recipe
* User can add/change a profile image
* User can create/edit a description for themselves
* User can follow/unfollow other users
* Animate a screen growing to fill screen when user navigates to it

### 2. Screen Archetypes

* Login screen 
   * User can log in
   * User can sign up to create a new account
* New account screen
    * User can create a new account
    * User can create a description for themselves (OPTIONAL)
    * User can add a profile image (OPTIONAL)
* Profile screen
   * User can edit a description for themselves (OPTIONAL)
   * User can add/change profile image (OPTIONAL)
   * User can log out
   * User can delete their account (OPTIONAL)
* Recipe List Search screen
    * User can search keywords to view list of recipes
    * Filter recipes by total ingredient cost and other factors (OPTIONAL)
    * User can view/scroll through generated recipes
    * User can tap on a recipe and view detailed recipe information and users' reviews    
* Recipe Details screen
    * User can leave reviews on a recipe to share their experience
    * User can double tap to like other users' reviews
    * User can save favorite recipes
    * User can receive suggested grocery information for guidance where to buy ingredients (OPTIONAL)
* Recipes for You Screen (OPTIONAL)
    * User can view generated “Recipes for You” and a daily suggested recipe (OPTIONAL)
* Viewing other users' profiles screen (OPTIONAL)
    * User can follow/unfollow other users (OPTIONAL)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Profile tab
* Recipe List Search tab
* Saved Recipes tab

**Flow Navigation** (Screen to Screen)
* Login page 
  * Navigate to search/category screen
* Create account page 
  * Navigate to profile screen
* Recipe List Search screen 
  * Navigate to specific recipe information screen
* Recipe Information screen
  * Navigate to comment section
* Saved Recipes screen
  * Navigate to basic recipe information screen


## Wireframes
![](https://i.imgur.com/QRDCuVO.jpg)


## Schema 
### Models
User
| Property | Type | Description |
| -------- | -------- | -------- |
| username | String | user's account username |
| password | String | user's account password |
| firstName | String | user's first name |
| lastName | String | user's last name |
| email | String | user's email address associated with account |
| phoneNumber | String | user's phone number |
| bio | String | user's self description |

Recipe
| Property | Type | Description |
| -------- | -------- | -------- |
| recipeName | String | name of recipe |
| image | String | picture of dish |
| instructions | String | website link to recipe instructions |
| ingredients | String | recipe ingredients |
| href | String | API href |
| healthLabels | String | health labels |

FavoriteRecipe
| Property | Type | Description |
| -------- | -------- | -------- |
| recipeName | String | name of recipe |
| image | String | picture of dish |
| instructions | String | website link to recipe instructions |
| ingredients | String | recipe ingredients |
| healthLabels | String | health labels |
| user | Pointer to User | user |

Review
| Property | Type | Description |
| -------- | -------- | -------- |
| user | pointer | pointer to user who left the review |
| username | String | user's username | 
| review | String | user's review |
| recipeId | String | recipe's unique external ID | 


### Networking
* Login screen 
   * (Read/GET) Query username to log in
   * (Read/GET) Query password to log in
* New account screen
   * (Update/PUT) Update user profile image, description
* Profile screen
   * (Read/GET) Query logged in user object
   * (Update/PUT) Update user profile image
* Recipe List Search screen
    * (Read/GET) Query keywords in search bar
    * (Read/GET) Query recipe information
* Favorite Recipe screen
  * (READ/GET) Query favorite recipes from database


API: https://developer.edamam.com/edamam-docs-recipe-api