# HackerNews
Hacker News client (Android Interview Task):

https://github.com/HackerNews/API

Displays list of New, Top and Best Stories from Hacker News (https://github.com/HackerNews/API#new-top-and-best-stories) in three different tabs. 

Handles two types of clicks on item:
OnItemClick: open a WebView with a story itself.
OnCommentClick: open a screen with comments section.

All response results was saved/cached into the local storage for offline usage.

Also impletemented pagination

Implemented using mostly Kotlin then Java, uses MVP pattern

Uses libraries such as: <br />
Retrofit for handling requests; <br />
Realm for offline usage; <br />
RxJava for asynchronous requests abstracting away concerns about things like low-level threading;<br />
Dagger for DI;<br />


### Attention: 
When application first started (first time after installation), device must have internet access otherwise app throws exception (I forgot consider this case, i can fix it, but for now decided do not update last commit time on repo files) 
