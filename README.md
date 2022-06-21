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
This Android mobile app will serve as a platform for users to generate recipes based off their unique food tastes and diets.

### App Evaluation
- **Category:** Health
- **Mobile:** This app will be very user friendly to mobile users. Users can easily input their diet information and ingredients into the app, scroll through recipes, and tap on recipes to learn more specific information about them, such as ingredients and nutrition information. 
- **Story:** This app is very compelling towards users as they can easily find new recipes based on their peresonal preferences. 
- **Market:** The market for this app is typically teenagers or adults who are more health-conscious or want to learn/explore new recipes. 
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
* User can rate recipes out of 5 stars
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
* Category/search screen
    * User can search keywords or choose categories (breakfast, lunch, dinner, etc.) to navigate to a screen of recipes
    * Filter recipes by total ingredient cost and other factors (OPTIONAL)
* Recipe screen
    * User can view/scroll through generated recipes
    * User can tap on a recipe and view detailed recipe information, users' reviews    
* Recipe Details screen
    * User can leave reviews on a recipe (comment + image) to share their experience
    * User can double tap to like other users' reviews
    * User can save favorite recipes
    * User can rate recipes out of 5 stars
    * User can receive suggested grocery information for guidance where to buy ingredients (OPTIONAL)
* Recipes for You Screen (OPTIONAL)
    * User can view generated “Recipes for You” and a daily suggested recipe (OPTIONAL)
* Viewing other users' profiles screen (OPTIONAL)
    * User can follow/unfollow other users (OPTIONAL)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Profile tab
* Category/search tab
* Recipe tab

**Flow Navigation** (Screen to Screen)
* Login page
    * Navigate to search/category screen
* Create account page
    * Navigate to profile screen
* Category/search screen
   * Navigate to recipes
* Recipe screen
    * Navigate to specific recipe information screen


## Wireframes
![](https://i.imgur.com/QRDCuVO.jpg)


### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
### Models
User
| Property | Type | Description |
| -------- | -------- | -------- |
| firstName | String | user's first name |
| lastName | String | user's last name |
| userName | String | user's account username |
| password | String | user's account password |
| email | String | user's email address associated with account |
| savedRecipes | List of Pointers | list of user's saved recipes |

Recipe
| Property | Type | Description |
| -------- | -------- | -------- |
| name | String | name of recipe |
| picture | jpg | picture of re
| ingredients | List of Strings | recipe ingredients |
| nutrition | List of Strings | nutrition facts |
| instructions | String | recipe instructions |
| mealType | String | type of meal (breakfast, lunch, dinner, etc.) |
| cuisineType | String | type of cuisine | 
| dishType | List of Strings | type of dish | 
| dietLabels | List of Strings | diet label | 
| healthLabels | List of Strings | health labels |


### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]

* Login screen 
   * (Read/GET) Query username to log in
   * (Read/GET) Query password to log in
* New account screen
   * (Update/PUT) Update user profile image, description
* Profile screen
   * (Read/GET) Query logged in user object
   * (Update/PUT) Update user profile image
* Category/search screen
    * (Read/GET) Query keywords in search bar
    * (Read/GET) Query category
* Recipe screen
    * (Read/GET Query recipe information

```
private void queryRecipes() {
    ParseQuery<Recipe> query = ParseQuery.getQuery(Recipe.class);
    query.include(Recipe.KEY_USER);
    query.setLimit(20);
    query.findInBackground(new FindCallback<Recipe>() {
        @Override
        public void done(List<Recipe> recipes, ParseException e) {
            if (e != null) {
                Log.e(TAG, "Issue with getting recipes", e);
                return;
            }
            allRecipes.addAll(recipes);
            adapter.notifyDataSetChanged();
        }
    });
}
```


API: https://developer.edamam.com/edamam-docs-recipe-api
