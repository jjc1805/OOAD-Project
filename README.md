# OOAD-Project
Our topic for the OOAD project is "Knowledge Repository".

# Technologies used:
1. Eclipse is the framework used to implement this project.
2. Java Swing to implement the frontend GUI.
3. PostgresSQL is used to implement the database.

# What is our project about?
A Knowledge repository is an online database that systematically captures, organizes,
and categorizes knowledge-based information. Knowledge repositories are most often
private databases that manage enterprise and proprietary information, but public
repositories also exist to manage public domain intelligence.

Our knowledge repository is going to have a login page for a user. The user’s details
like name and email are stored. A unique id is stored on each article to differentiate.
A user is allowed to publish their own articles onto the repository and edit it at any
time as an author, whereas other users would only be able to view them.

When submitting an article the user must specify the topic name and the category. A
view count will also be assigned to keep track of the number of views the article gets.
They will also be a like button to generate how many likes the article gets. These
articles will be stored in a database and sorted based on various criteria like Latest
Publications, Recently Viewed, Most Popular.

Under Category we have different types of papers or articles submitted by users
describing what their article is about. The category specified by the user is stored and
all kinds of articles of the same category are grouped together and ordered. For
example, say a user stores an article and gives the category as novel. This will be
recorded and all articles with the same category will be grouped together. Under
Latest Publications the articles which come in first are stored first. It contains details
like date, time.

Recently Viewed allows the user to view the articles that he/she has already looked at
and would like to look at again.

Most Popular articles are those articles which get the most number of views or likes
from the readers. The higher the number of views and likes the more popular the
article is.

A Reader is one who is casually browsing through the repository(no login required).
He cannot edit the articles but whichever articles he views will get their view count
added by 1. He also has the option to click on the like button to raise the article's
popularity. He may also comment on the article to show what he thinks of it.

A Reader can also subscribe to a certain category of articles so that he may receive
updates on articles being published in that domain.

The Maintainer is one who checks the articles to make sure that any inappropriate
and harmful articles are removed and checks if the articles being published are true
or not.
