# NightLife

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
[NightLife is a crime reporting app that allows users to see crime information and a map displaying this data for their location.\
Users can report crimes they have seen and upload video/picture evidence for other users of the app to see.
There will be functionality for emergency calls to the authorities and close friends/loved ones.]

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category: Personal Safety / Community Safety**
- **Mobile: This app would be designed for mobile first but can be displayed on all devices. The reporting aspect would only really make sense for mobile though.**
- **Story: Gets current crime data and displays it on a list and a map for users to see easily. Users can report any crime they have witnessed.**
- **Market: This app can be used by every single individual. Women, children, and the elderly may find it especially useful.**
- **Habit: Any time a person goes out they could use this app to ensure their safety.**
- **Scope: This app can be used by everyone and eventually if it becomes large enough and reliable enough the authorites could even partner with the makers of this app to make their jobs easier. **

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [ ] [User can view crime information in a list view.]
- [ ] [User can view crime information on a map of there current or searched location.]
- [x] [User can report a crime with a location, description, and time.]
- [x] [User can emergency call the authorites.]
- [x] User can register a new account and login with that account.

**Optional Nice-to-have Stories**

* [User can view locations of emergency contacts]
* [User can sort crime information by type on list view and map view.]
* [Map areas with heavy activity show as one big icon with a list of activity upon clicking.]
* [Crime types are shown as different icons on the map.]

### 2. Screen Archetypes

* Login/Register
 - [x] User should be able to login to their account.
 - [x] User should be able to register a new account.
   
* Profile
   * User should be able to edit their profile.
   * User should be able to see emergency contacts.
  - [x] User should see their name and email address.
 
* Report
 - [x] User can give a decription of the crime.
 - [x] User can give the location of the crime.
 - [x] User can give the time the crime occurred. 
 - [x] User can give the crime type.
 - [x] User should be able to submit the response.
   
* Map
   - [x] Screen should show the map within a predetermined radius of the user's current location.
   * User should be able to filter the map by location.
   * User can search a location and be directed to that location on the map.
   
* Home
   * Home page should show the location, time and description of all recent crimes.
   * User should be able to infinitely scroll down to see all crimes.
  
* Settings
   * User should be able to change settings for things like  location or in-app settings like light/dark mode.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* To view the map:
    - On the bottom view bar, click on map tab -> map page 
* To report an incident:
    - On the bottom bar, click on Report icon -> Report screen.
    
* To open profile page:
    - On the bottom bar, click Profile tab -> Profile page
* To view incidents:
    - On the bottom bar, click Home tab -> Home page

**Flow Navigation** (Screen to Screen)

* Login/Register -> Home/Map/Report/Profile/Settings
* Home -> view list of incidents -> view a detail of an incident
* Map -> map page with search and filter
* Report -> a form to report an incident
* Profile -> view profile and edit info
* Settings -> app settings

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://user-images.githubusercontent.com/106134136/200468287-671bf86d-4fa3-4c76-a71a-de2b4b43fb6e.png" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="https://user-images.githubusercontent.com/106134136/200468258-0a453584-6548-4462-a8fe-31851814507d.png" width=600>
### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]

### Models


#### User Table
| Type   | Property      | Description                           |
| ------ | ------------- | ------------------------------------- |
| Integer    | user_id        |   Primary key for each user                                    |
| String | firstname     | user's firstname                      |
| String | lastname      | user's lastname                       |
| String | email_address | user's email address                  |
| File   | profile_image | user's profile image _(optional)_     |
| File   | user_location | user's profile image _(optional)_     |
| String | password      | User's password _(Encrypted)_         |
| Date   | createdAt     | The date when the account was created |


#### Emergency Contacts

| Type   | Property     | Description               |
| ------ | ------------ | ------------------------- |
| Integer    | user_id      | foreign key to User Table                          |
| String | firstname    | user's firstname          |
| String | lastname     | user's lastname           |
| String | phone_number | Phone number of a contact |
| String | relation     | Relationship to the user  |

#### User Settings

| Type    | Property       | Description               |
| ------- | -------------- | ------------------------- |
| Integer | user_id        | foreign key to User Table |
| Integer | screen_display | App theme (Dark/Light)    |


#### Crime Report

| Type      | Property    | Description                      |
| --------- | ----------- | -------------------------------- |
| Integer   | report_id   | Primary key for table            |
| Integer   | user_id     | Foreign key to User table to know which user submitted the report._(optional)_        |
| String    | crime_type  | Type of crime that was committed |
| String    | location    | location of crime                |
| String    | description | Description of crime             |
| Timestamp | time        | Time that crime occurred         |





### Networking
* Login/Register
    * (Update/POST)Allow user to register for a Nightlife Account
    
* Profile
    * (Read/GET)Query logged in user object.
    ```swift
         let query = PFQuery(className:"Users")
         query.whereKey("user_id", equalTo: currentUser.id)
         query.findObjectsInBackground { (user: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let user = user {
               
           // Display User data
            }
         }
         ``` 
* Report
    * (Update/Post)User can report a crime to be displayed on the NightLife app to all.
* Map
    * (Read/GET)Query crime data to be displayed from the database for a specific location.
    * ```swift
         let query = PFQuery(className:"CrimeReport")
         query.whereKey("city", equalTo: location)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (crumes: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let crimes = crimes {
               print("Successfully retrieved \(crimes.count) crimes.")
            }
         }
         ```
* Home
    * (Read/GET)Query crime data to be displayed from the database for a specific location.
     ```swift
         let query = PFQuery(className:"CrimeReport")
         query.whereKey("city", equalTo: location)
         query.order(byDescending: "createdAt")
         query.findObjectsInBackground { (crumes: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let crimes = crimes {
               print("Successfully retrieved \(crimes.count) crimes.")
            }
         }
         ```
* Settings
    * (Read/GET)Query logged in user object. 
    * (Read/GET)Query emergency contacts.
    * (Update/Post)Allow user to update profile image.
    * (Update/Post)Allow user to add emergency contacts.
    * (Delete)Allow user to remove emergency contacts.

    ```swift
         let query = PFQuery(className:"EmergencyContacts")
         query.whereKey("user_id", equalTo: currentUser.id)
         query.findObjectsInBackground { (contacts: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let contacts = contacts {
               print("Successfully retrieved \(contacts.count) contacts.")
           // Display Emergency Contatcs
            }
         }
         ```
    ```swift
         let query = PFQuery(className:"Users")
         query.whereKey("user_id", equalTo: currentUser.id)
         query.findObjectsInBackground { (user: [PFObject]?, error: Error?) in
            if let error = error { 
               print(error.localizedDescription)
            } else if let user = user {
               
           // Display User Data
            }
         }
         ```
- [OPTIONAL: List endpoints if using existing API such as Yelp]


[Milestone 1 Gif]
![Nightlife](https://user-images.githubusercontent.com/98711133/203696925-4242192a-14f6-4795-b78a-532cbfc48e27.gif)


[Milestone 2 Gif]
![Nightlife2](https://user-images.githubusercontent.com/98711133/204967956-4b3c5990-0e1d-4c6c-b24c-ee5e8e2c3749.gif)
