android_twitter_client_redux
============================

Twitter Client Redux Android App

## Overview

Extend simple Twitter Client app that supports tabbed interface and fragments.

## User Stories

* User can sign in using OAuth login flow
* User can view last 25 tweets from their home timeline
* User can compose a new tweet
* User can click a "Compose" icon in the Action Bar on the top right
* User will have a Compose view opened
* User can enter a message and hit a button to Post
* User should be taken back to home timeline with new tweet visible
* User can switch between Timeline and Mention views using tabs.
* User can view their home timeline tweets.
* User can view the recent mentions of their username.
* User can click icon on Action Bar to view their profile
* User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* User can click on the profile image in a tweet to see that user's profile.
* Optional: User can optn the twitter app offline and see recent tweets
* Optional: Tweets are persisted into sqlite and displayedd from the local DB
* Optional: User can load more tweets once they reach the bottom

## Learning 
* Explore the OAuth HTTP Client for accessing authenticated APIs
* Explore adding clickable icons to the action bar menu
* Explore using intents to open the compose view
* Explore persisting the tweet objects into sqlite and fetching them out
* Explore sending API calls for creation using an asynchronous HTTP call
* Explore how to define and load fragment views both statically and dynamically
* Explore using fragments for the purpose of reusability with listview of tweets in home timeline and user profile
* Explore using fragments for navigation with action bar with tabs
