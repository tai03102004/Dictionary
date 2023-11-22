<h1>  Application to support learning English using Java</h1>

<h2>Author</h2>

  1.Dinh Duc Tai - 22021173<br>
  2.Nguyen Huu Thang - 22021154<br>

<h2>Description</h2>
  The application is designed to support learning English. The application is written in Java and uses the JavaFX library. The application is based on the MVC model. The application has one        types of dictionaries: English-Vietnamese . The application use SQL(Dictionary) to store data.<br>

  <pre>1.The application is designed to support learning English.<br> </pre>
  <pre>2.The application is written in Java and uses the JavaFX library.<br> </pre>
  <pre>3.The application is based on the MVC model.<br> </pre>
  <pre>4.The application has one types of dictionaries: English-Vietnamese.<br> </pre>
  <pre>5.The application use SQL(Dictionary) to store data.<br> </pre>

<h2>UML diagram</h2>
    
<p><a href="https://github.com/tai03102004/Dictionary/files/13440706/ImageUML.zip">Download UML diagram</a></p>


<h2>Installation</h2>

  <pre>1.Clone the project from the repository.<br></pre>
  <pre>2.Open the project in the IDE.<br></pre>
  <pre>3.Run the project.<br></pre>
  <pre>4.And you must install sql with the same name as the image file below. You add words in the Words file and if you want an account, you can register to add it. And you need to set the         Mysql name and password like the image file below, otherwise you can modify it in the DatabaseConnection class in the Model section.<br> </pre>

<h2>Usage</h2> 
   <h4><u> Client : </u></h4>
    <pre>1. First you need to create an account in the registration section.<br></pre>
    <pre>2. After successfully creating an account, log in and experience the application's features. (But there is a note when logging in that the Question and Answer sections should be related to things that are easy to remember. Because when you forget your password, there is a Forgot Password section so you can change your password based on your UserName, Question and Answer).<br></pre>
    <pre>3. After successfully logging in, let's now experience the functions of the Dictionary. (note: users should only log in on the Client side).<br></pre>
    <pre>4.First, by default, the dictionary will return to the home page first. This is a page designed to please viewers and has a few highlights such as having DarkMode and LightMode modes when bored, which also helps relieve traffic.<br></pre>
    <pre>5.Next, let's experience the word lookup feature (Eng-Viet). In this word lookup section, users will experience features such as saving words and commenting if words have any errors so that the Admin can know and promptly correct the errors. The dictionary has added the Hunspell library in java to be able to detect word errors and find words with similar formats. (users cannot leave comments blank)<br></pre>
    <pre>6. In this section you will experience the text translation feature with many options for you and many languages. You can download and experience this unique feature (including voice so you can read along).<br></pre>
    <pre>7. game : in this client, you can entertain yourself or learn somw words from the topic you like, Right after you click on the "Game" button, you will get to the Topic screen where you can choose the topic that you like. Then it will lead you to another screen where you have 3 options to choose :
      + Topic: You can return to the topic screen and choose another topic.
      +English learning: Where you can learn some Topic's words that you like.
      +Speed key test: Where you can test your ability in speed typing and learn those words from English learning that you have learnt.<br></pre>
    <pre>8. Next is the history you looked up. When you just click or enter in the word lookup section, the words you have looked up will appear in the history section. In this section, there is also a search for the searched word (it will filter the words to suggest which word you should choose or if not, it will return an empty ListView) or sort by criteria (in ascending alphabetical order). (or descending)<br></pre>
    <pre>9. Right after that, you will get to the saved words section. The logic is no different from History, which also has sorting and searching (When you save words in the word lookup section, just click on the star and your words will be saved here).<br></pre>
    <pre>10. The next section will be the User Profile section. Here you have 2 main items:<br></pre>
          <pre>. Profile (It is the profile you registered except for the password, Question and Answer (So you should think carefully about the Question and Answer you wrote))<br></pre>
          <pre> - Here you can review what you have registered and can edit it (except your username) if you are not satisfied or satisfied.<br></pre>
         <pre>. Change password (You only need to remember the old password to change the password<br></pre>
    <pre>11. Immediately after that it will be logged out, you just need to press immediately the application will be lost<br></pre>
    <pre>12. Setting <br></pre>
    <pre>13. Finally, there will be user reviews and you can give us 5 stars to give us more motivation to carry out other projects. When you leave a review and want to review it, you must log in again to view it (possibly due to an error I haven't been able to fix).<br></pre>

  <h4><u>Admin: </u></h4> 
    <pre>Note: In admin there will be no registration and forgotten password. Admin is a pre-designated account with only username and password<br></pre>
    <pre>-> Now let's get to the admin features<br></pre>
    <pre>-) Once you have been granted an account, it will have special privileges that only the admin has<br></pre>
      <pre>1. First you will see a series of user lists (and you will not be able to see passwords, questions and answers) and you can search for names (only userName is in the 2nd position for each user). so you can find acquaintances :))<br></pre>
      <pre>2. Before entering the two features of adding, editing, and deleting words, I will default to this:<br></pre>
        <pre>+) . The default field word is English - Vietnamese, can be added or not<br></pre>
        <pre>+) . Deleted word status ( true : word does not exist , false : word exists )<br></pre>
   <h5> First : Add words</h5>
      <pre>1. If you tick deleted, it means true that the word will not exist and vice versa<br></pre>
      <pre>2. There are additional words in the vocabulary (if you add the same, it will appear as a duplicate) but if you add the same and want to leave the status true, it's okay, it will               change the word's status.<br></pre>
      <pre>3 . In the HTML Editor, it will add the definitions you add to the word (You can add whatever makes it meaningful and interesting).<br></pre>
        <pre> => If you want to save a word, just press the button on the Save icon<br></pre>
         <pre>=> To reset the word, just click on the Reset word icon<br></pre>

   <h5> Second: Edit, delete words </h5>
      <pre>1. It is similar to adding words about code logic<br></pre>
        <pre>But there are a few other features that are only changing the Deleted Word icon on the login, which I find quite interesting because it will not delete the word from the                       database but only change the deleted status to true so that the word does not appear (meaning the word does not exist )<br></pre>

  <pre>3. Next will be the section showing user feedback when the program has errors or something wrong<br></pre>

  <pre>4. Finally comes the admin logout section<br></pre>

  <h2>Demo</h2>

  <h2>Future improvements</h2>
    <p>1. Future improvements</p>
    <p>2. Add more complex games.</p>
    <p>3. Improve the user interface.</p>
    <p>4. Fix unexpected errors</p>
    <p>5. Improved code to run even faster</p>

  <h2>Contributing</h2>
    <p>Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.</p>

  <h2>Project status</h2>
    <p>The project is completed.</p>
    
  <h2>Notes</h2>
    <p>Here I used the search algorithm Radix Tree which is quite fast to find words. But when I click on it, it takes a while to load because it has to load everything from the database</p>
    <ul>
      <li>
          Next, I will send you the SQL files I used.
          <ul>
              <li>
                  This is a collection of images in MySQL that I use.
                  <a href="https://github.com/tai03102004/Dictionary/files/13440378/SQL.zip">SQL.zip</a>
              </li>
          </ul>
          <ul>
              <li>
                  This is a Words file (save it to run from, remember to add a deleted field to make it false).
                  <a href="https://raw.githubusercontent.com/HynDuf/dictionary/ui/src/main/resources/sql/dictionary.sql">Words file</a>
              </li>
          </ul>
      </li>
  </ul>


<h3>Thank you everyone for visiting.</h3>


    
    













      
