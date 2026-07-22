# RKR Transports

Full-stack logistics website for **RK & R Transports and Logistics**, Tasmania, Australia.

## Project Structure

```
RKR-Transports/
├── frontend/     Angular 20 — Customer-facing website
└── backend/      Spring Boot 3 — REST API + Email + MySQL
```

---

## Prerequisites

| Tool | Version |
|---|---|
| Node.js | 18+ |
| Angular CLI | 20+ |
| Java | 17+ |
| Maven | 3.8+ |
| MySQL | 8+ |

---

## Backend Setup

### 1. Configure credentials

Open `backend/src/main/resources/application.properties` and set:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.mail.password=YOUR_GMAIL_APP_PASSWORD
```

> Generate a Gmail App Password at: https://myaccount.google.com/apppasswords

### 2. Run the backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs at: `http://localhost:8080`

---

## Frontend Setup

### 1. Install dependencies

```bash
cd frontend
npm install
```

### 2. Run the frontend

```bash
ng serve
```

Frontend runs at: `http://localhost:4200`

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings/enquiry` | Submit booking enquiry |

---

## Contact

- Email: mounisha.chebrolu@gmail.com
- Phone: +61 451 655 415
- ABN: 51 680 337 204
