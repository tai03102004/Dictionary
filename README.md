# Author

  1.Dinh Duc Tai - 22021173<br>
  2.Nguyen Huu Thang - 22021154<br>

# Description
  The application is designed to support learning English. The application is written in Java and uses the JavaFX library. The application is based on the MVC model. The application has one        types of dictionaries: English-Vietnamese . The application use SQL(Dictionary) to store data.<br>

  1.The application is designed to support learning English.<br>
  2.The application is written in Java and uses the JavaFX library.<br>
  3.The application is based on the MVC model.<br>
  4.The application has one types of dictionaries: English-Vietnamese.<br>
  5.The application use SQL(Dictionary) to store data.<br>

# UML diagram

<img width="1021" alt="Ảnh màn hình 2023-11-22 lúc 09 32 15" src="https://github.com/tai03102004/Dictionary/assets/124711101/f9a8de52-3efb-4dee-a42a-a575a317fef0"><br>

# Installation

  1.Clone the project from the repository.<br>
  2.Open the project in the IDE.<br>
  3.Run the project.<br>
  4.And you must install sql with the same name as the image file below. You add words in the Words file and if you want an account, you can register to add it. And you need to set the Mysql       name and password like the image file below, otherwise you can modify it in the DatabaseConnection class in the Model section.<br>

# Usage
  *)Client :<br>
    1. First you need to create an account in the registration section.<br>
    2. After successfully creating an account, log in and experience the application's features. (But there is a note when logging in that the Question and Answer sections should be related to things that are easy to remember. Because when you forget your password, there is a Forgot Password section so you can change your password based on your UserName, Question and Answer).<br>
    3. After successfully logging in, let's now experience the functions of the Dictionary. (note: users should only log in on the Client side).<br>
    4. First, by default, the dictionary will go to the home page first. This is a page designed to please the viewer, so there are no important features.<br>
    5. Next, let's experience the word lookup feature (Eng-Viet). In this word lookup section, users will experience features such as saving words and commenting if words have any errors so that the Admin can know and promptly correct the errors. The dictionary has added the Hunspell library in java to be able to detect word errors and find words with similar formats.<br>
    6. In this section you will experience the text translation feature with many options for you and many languages. You can download and experience this unique feature (including voice so you can read along).<br>
    7. game :<br>
    8. Next is the history you looked up. When you just click or enter in the word lookup section, the words you have looked up will appear in the history section. In this section, there is also a search for the searched word (it will filter the words to suggest which word you should choose or if not, it will return an empty ListView) or sort by criteria (in ascending alphabetical order). (or descending)<br>
    9. Right after that, you will get to the saved words section. The logic is no different from History, which also has sorting and searching (When you save words in the word lookup section, just click on the star and your words will be saved here).<br>
    10. The next section will be the User Profile section. Here you have 2 main items:<br>
          . Profile (It is the profile you registered except for the password, Question and Answer (So you should think carefully about the Question and Answer you wrote))<br>
            - Here you can review what you have registered and can edit it (except your username) if you are not satisfied or satisfied.<br>
         . Change password (You only need to remember the old password to change the password<br>
    11. Immediately after that it will be logged out, you just need to press immediately the application will be lost<br>
    12. Setting <br>
    13. Finally, there will be user reviews and you can give us 5 stars to give us more motivation to carry out other projects. When you leave a review and want to review it, you must log in again to view it (possibly due to an error I haven't been able to fix).<br>

  *) Admin:<br>
    Note: In admin there will be no registration and forgotten password. Admin is a pre-designated account with only username and password<br>
    -> Now let's get to the admin features<br>
    -) Once you have been granted an account, it will have special privileges that only the admin has<br>
      1. First you will see a series of user lists (and you will not be able to see passwords, questions and answers) and you can search for names (only userName is in the 2nd position for each user). so you can find acquaintances :))<br>
      2. Before entering the two features of adding, editing, and deleting words, I will default to this:<br>
        1.1 . The default field word is English - Vietnamese, can be added or not<br>
        2.1 . Deleted word status ( true : word does not exist , false : word exists )<br>
   +) First : Add words<br>
      1. If you tick deleted, it means true that the word will not exist and vice versa<br>
      2. There are additional words in the vocabulary (if you add the same, it will appear as a duplicate) but if you add the same and want to leave the status true, it's okay, it will               change the word's status.<br>
      3 . In the HTML Editor, it will add the definitions you add to the word (You can add whatever makes it meaningful and interesting).<br>
         => If you want to save a word, just press the button on the Save icon<br>
         => To reset the word, just click on the Reset word icon<br>

   +) Second: Edit, delete words<br>
      1. It is similar to adding words about code logic<br>
        But there are a few other features that are only changing the Deleted Word icon on the login, which I find quite interesting because it will not delete the word from the                       database but only change the deleted status to true so that the word does not appear (meaning the word does not exist )<br>

  3. Next will be the section showing user feedback when the program has errors or something wrong<br>

  4. Finally comes the admin logout section<br>

  # Demo

  # Future improvements
    1. Future improvements
    2. Add more complex games.
    3. Improve the user interface.
    4. Fix unexpected errors
    5. Improved code to run even faster

  # Contributing
    Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

  # Project status
    The project is completed.
    
  # Notes
    The application is written for educational purposes.


  Thank you everyone for visiting. 


    
    













      
