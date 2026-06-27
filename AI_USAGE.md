# AI_USAGE.md

## Tool AI yang Digunakan

Selama pengerjaan project ini saya menggunakan:

- ChatGPT (OpenAI GPT-5.5)

AI digunakan sebagai pendamping belajar (pair programmer), terutama untuk berdiskusi mengenai konsep, membantu debugging, melakukan review implementasi, dan memberikan masukan terhadap desain program. Seluruh kode tetap saya implementasikan, jalankan, dan uji sendiri.

---

## Bagian yang Dibantu AI

AI digunakan pada beberapa bagian berikut.

- Memahami arsitektur microservices menggunakan dua Spring Boot service dengan database yang terpisah.
- Menentukan struktur package project agar konsisten (controller, service, repository, dto, mapper, exception, client).
- Memahami implementasi komunikasi HTTP antar service menggunakan RestClient.
- Berdiskusi mengenai best practice dan meminta contoh potongan kode untuk layer Controller, Service, dan pemetaan DTO.
- Membantu memperbaiki beberapa error saat proses implementasi, seperti validasi request, enum, JPA, dan exception handling.
- Memberikan penjelasan mengenai business rule sebelum kode diimplementasikan.
- Membantu menyusun skenario pengujian menggunakan Postman berdasarkan requirement.

---

## Prompt Penting yang Digunakan

Beberapa prompt yang digunakan selama proses pengerjaan antara lain:

```text
Jelaskan struktur project Spring Boot microservices yang baik untuk catalog-service dan order-service.

Bagaimana cara menghubungkan order-service ke catalog-service menggunakan RestClient?

Bagaimana konsep dan contoh logika mengimplementasikan pengurangan stok antar service menggunakan HTTP request agar aman?

Bagaimana membuat GlobalExceptionHandler yang menghasilkan response error tanpa stack trace?

Review implementasi entity, repository, service, dan DTO apakah sudah sesuai best practice Spring Boot.

Bagaimana cara menyusun test case pada postman collection yang baik dan benar?
```

---

## Modifikasi yang Dilakukan Sendiri

Jawaban dari AI tidak langsung digunakan seluruhnya. Beberapa penyesuaian yang saya lakukan sendiri antara lain:

- Menyesuaikan package dengan namespace `com.mahardikapratama`.
- Menyesuaikan endpoint agar sama dengan requirement.
- Menyesuaikan nama DTO, Entity, dan Response agar konsisten dengan project.
- Menulis dan merangkai sendiri logika bisnis (business rule) ke dalam kode, memastikan status PENDING, PAID, dan CANCELLED serta pengurangan stok benar-benar berjalan sesuai aturan tugas, tidak sekadar menyalin dari AI.
- Melakukan debugging terhadap error compile, import, validasi enum, serta komunikasi antar service.
- Menguji ulang setiap endpoint menggunakan Postman dan memperbaiki apabila hasilnya belum sesuai.

---

## Bagian yang Sudah Dipahami

Setelah proses implementasi dan diskusi, saya memahami beberapa hal berikut.

- Konsep dasar arsitektur microservices dengan database yang terpisah.
- Perbedaan fungsi Entity, DTO, Repository, Service, Controller, dan Mapper.
- Cara komunikasi antar service menggunakan HTTP melalui RestClient.
- Alasan order-service tidak boleh mengakses database catalog-service secara langsung.
- Cara menerapkan validasi menggunakan Jakarta Bean Validation.
- Cara menangani exception menggunakan GlobalExceptionHandler.
- Alur bisnis create order, pay order, cancel order, serta mekanisme pengurangan dan pengembalian stok.
- Konsep penyimpanan snapshot data produk pada saat order dibuat.

---

## Bagian yang Masih Membingungkan

Terdapat beberapa konsep yang perlu saya pelajari lebih lanjut, dikarenakan lupa meskipun dulu pernah dipelajari pada mata kuliah PPLBO.

- Implementasi distributed transaction pada arsitektur microservices, terutama Saga Pattern dan Outbox Pattern.
- Strategi menjaga konsistensi data apabila salah satu service gagal ketika proses transaksi sedang berlangsung.