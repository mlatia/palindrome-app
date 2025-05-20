## Palindrome App
Aplikasi Palindrome merupakan aplikasi mobile yang memungkinkan pengguna mengecek kalimat palindrome dan memilih data pengguna dari API eksternal. Awalnya, pengguna mengisi nama dan kalimat untuk dicek, lalu melanjutkan ke layar berikutnya untuk memilih user yang datanya diambil dari API.

## How to Run the Program

```bash
./gradlew clean
./gradlew build
```

---

### 2. **Tech Stack**
1. XML View (sesuai requirement) 
2. Kotlin Coroutines & Flow yang digunakan untuk API call & pagination
3. Retrofit digunakan untuk konsumsi API https://reqres.in/api/users
4. Coil / Glide digunakan untuk load avatar user
5. Material Components digunakan untuk form input, button, dan dialog
