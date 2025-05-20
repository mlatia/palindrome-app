## Palindrome App 🔁 
The Palindrome App is a mobile application that allows users to check whether a sentence is a palindrome and select user data from an external API. Initially, the user enters their name and a sentence to be checked, then proceeds to the next screen to choose a user whose data is fetched from the API.

## How to Run the Program

```bash
./gradlew clean
./gradlew build
```

---

### **Tech Stack**
1. XML View.
2. Kotlin Coroutines & Flow are used for API calls and pagination.
3. Retrofit is used to fetch data from the API https://reqres.in/api/users.
4. Coil / Glide is used to load user avatars.
5. Material Components are used for form inputs, buttons, and dialogs.

### **Features Overview**
1. Palindrome check with dialog result ("isPalindrome"/"notPalindrome").
2. Navigation between three screens.
3. Display user list from API with pagination and pull-to-refresh.
4. Show selected user name on the second screen without opening a new screen.
5. Empty state handling when no user data is available.