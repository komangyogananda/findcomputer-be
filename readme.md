# Daftar isi
- [Daftar isi](#daftar-isi)
- [Find Computer Backend](#find-computer-backend)
  - [Deskripsi](#deskripsi)
- [Local Deployment](#local-deployment)
  - [Google Cloud Storage](#google-cloud-storage)
  - [Database](#database)
  - [Others (optional)](#others-optional)
- [Run](#run)
  
# Find Computer Backend

## Deskripsi
Find Computer Backend dikerjakan dengan menggunakan framework Spring Boot. Arsitektur yang digunakan adalah REST Api dengan authentikasi menggunakan JWT. Database yang digunakan pada proyek ini adalah MySQL serta Storage dari Google Cloud Storage yang digunakan untuk menyimpan gambar Items pengguna.

# Local Deployment

  ## Google Cloud Storage
<<<<<<< HEAD
  - Unduh google-services.json dari akun google cloud platform sesuai instruksi [ini](https://developers.google.com/identity/protocols/oauth2/service-account).
  - Copy file google-services.json dan letakkan pada root project.
=======
>>>>>>> a8719fb8b6b82e28c9cc7e327b6ff16d7bde38ed
  - Buat bucket pada Google Cloud Storage sesuai instruksi [ini](https://cloud.google.com/storage/docs/creating-buckets).
  - Buka application-dev.properties dan ubahlah variable berikut sesuai dengan environtment anda  
    ```properties
    app.gcpbucketname=#namabucket
    app.projectid=#namaprojectgcp
    ```

  ## Database
  - Buat database baru dengan nama ```findcomputer```
  - Buka application-dev.properties dan ubahlah variable berikut sesuai dengan environtment anda  
    ```properties
    spring.datasource.username=#databaseusername
    spring.datasource.password=#databasepassword
    ```

  ## Others (optional)
  - Buka application-dev.properties dan ubahlah variable berikut sesuai dengan preferensi masing masing  
    ```properties
    app.jwtSecret=#JWTsecret
    app.jwtExpirationInMs=#Jwtexpiration
    server.port=#portdev
    ```

# Run
Jalankan project dengan perintah
- Linux
  ```bash
  ./gradlew bootRun
  ```

- Windows
  ```bash
  ./gradlew.bat bootRun
  ```