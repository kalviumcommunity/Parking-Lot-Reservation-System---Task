# Parking Lot Reservation System (Task 1)

A robust, production-ready Spring Boot REST API designed to replace manual spreadsheet-based parking management. This system ensures zero double-bookings, automates fee calculations, and validates all inputs for high-reliability parking facilities.

## 🚀 Features

- **Admin Control**: Create parking floors and individual slots.
- **Smart Reservation**: Book slots for specific time windows (up to 24 hours).
- **Zero-Overlap Logic**: Advanced algorithm prevents any slot from being booked twice for the same time.
- **Automated Billing**: Flat rate of ₹20 per hour with partial hours always rounded up (e.g., 61 minutes = 2 hours = ₹40).
- **Input Validation**: Strict Regex validation for Indian vehicle formats (e.g., KA05MH1234).
- **Live Availability**: Search for free slots within any time window.
- **Soft Cancellation**: Cancel reservations without losing historical data.

---

## 🛠 Tech Stack

- **Java 17 (LTS)**: High-performance backend runtime.
- **Spring Boot 3.2.4**: Modern application framework.
- **Spring Data JPA**: Database management and persistence.
- **H2 Database**: High-speed, in-memory storage for testing and development.
- **Maven**: Dependency and build management.

---

## 📋 Prerequisites

Before starting, ensure you have **Java 17 SDK** installed on your machine.
- **How to check?** Open your terminal and type `java -version`.
- **Download Link**: [OpenJDK 17](https://adoptium.net/temurin/releases/?version=17)

---

## 💻 How to Run the Project

### **Option 1: Using IntelliJ IDEA (Recommended for Beginners)**
1.  Open IntelliJ IDEA and select **Open**.
2.  Navigate to the `task1-parking-basic` folder and click **OK**.
3.  **Wait** for the progress bar at the bottom right to finish. IntelliJ will automatically download Maven and all required libraries for you.
4.  Navigate to `src/main/java/com/parking/ParkingApplication.java`.
5.  Click the **Green Play Button** next to the `main` method.
6.  The app is live when the console says: `Started ParkingApplication`.

### **Option 2: Using VS Code**
1.  Open the folder in VS Code.
2.  Install the **"Extension Pack for Java"** and **"Spring Boot Extension Pack"**.
3.  **Note**: If you don't have Maven installed on your system, you must [download it here](https://maven.apache.org/download.cgi) and add the `bin` folder to your Windows Environment Variables (Path).
4.  Once Maven is set up, run the project by pressing `F5` or typing `mvn spring-boot:run` in the terminal.

---

## 🧪 Detailed Testing Guide (All 10 Steps)

Use **Postman** or any API client to test the following sequence:

### **1. Setup: Create Floor**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/floors`
- **Body**: `{"floorNumber": 1, "name": "Basement 1"}`
- **Expect**: `200 OK` and a JSON response with ID.

### **2. Setup: Create Slot**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/slots`
- **Body**: `{"floorId": 1, "slotNumber": "B1-A001"}`
- **Expect**: Confirmation that Slot B1-A001 is linked to Floor 1.

### **3. Initial Availability Check**
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/availability?start=2024-04-10T10:00:00&end=2024-04-10T12:00:00`
- **Expect**: `B1-A001` appears in the list.

### **4. Booking & Fee Calculation (Partial Hour)**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/reservations`
- **Body**:
  ```json
  {
    "slotId": 1,
    "vehicleNumber": "KA01HH1234",
    "startTime": "2024-04-10T10:00:00",
    "endTime": "2024-04-10T11:15:00"
  }
  ```
- **Expect**: `totalFee: 40.00` (1hr 15m rounds to 2hrs).

### **5. Conflict Test (Overlap Rejection)**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/reservations`
- **Body**: Use same body as Step 4.
- **Expect**: `409 Conflict`. Message should say slot is already reserved.

### **6. Invalid Vehicle Validation**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/reservations`
- **Body**: Edit Step 4 body with `"vehicleNumber": "ABC-123"`.
- **Expect**: `400 Bad Request` with validation error.

### **7. Over 24h Restriction**
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/reservations`
- **Body**: Edit Step 4 body with `endTime` set to the next day.
- **Expect**: `400 Bad Request`.

### **8. Get Booking Details**
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/reservations/1`
- **Expect**: Complete JSON response showing the booking with a DTO pattern.

### **9. Cancel Reservation**
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/reservations/1`
- **Expect**: `status` updates to `CANCELLED`.

### **10. Final Availability Check**
- **Method**: `GET`
- **URL**: (Run URL from Step 3 again)
- **Expect**: `B1-A001` is free and visible again!

---

## 📊 Database Access
To see the tables in your browser while the app is running:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:parkingdb`
- **User**: `sa` | **Password**: (leave blank)

## 🧪 Running Unit Tests
Right-click on the `src/test/java` folder in IntelliJ and select **'Run All Tests'**.
- **Coverage**: Service layer logic is tested to >80% coverage (Overlap logic, Fee math, Validation).

---
Developed as part of the Kalvium Spring Boot Bootcamp.
