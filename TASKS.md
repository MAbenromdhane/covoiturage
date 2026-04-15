# 🚗 Carpooling App - Roadmap & Tasks

Welcome! This file tracks our progress on the Carpooling App.

---

## 👥 Work Split
- **Frontend (Android)**: @USER (Android Layouts, Activities, UI/UX)
- **Backend (PHP/SQL)**: @FRIEND (APIs, Database, Business Logic)

---

## 🛠 Project Phases

### Phase 1: Authentication & Identity ✅
- [x] Initial Project Structure
- [x] database Schema (SQL)
- [x] Login Activity & API
- [x] Registration Activity & API
- [x] Session Management

### Phase 2: User Dashboards 🟡 (Next)
- [ ] Create `PassengerDashboardActivity` - **@USER**
- [ ] Create `DriverDashboardActivity` - **@USER**
- [ ] Add logout functionality - **@USER**
- [ ] Backend: Get user profile info - **@FRIEND**

### Phase 3: Ride Posting (Driver Flow) ⚪
- [ ] `PostRideActivity` (Form to offer a ride) - **@USER**
- [ ] API: `create_ride.php` - **@FRIEND**
- [ ] API: `get_driver_rides.php` - **@FRIEND**

### Phase 4: Finding Rides (Passenger Flow) ⚪
- [ ] `FindRideActivity` (Search and list view) - **@USER**
- [ ] API: `search_rides.php` - **@FRIEND**
- [ ] `RideDetailsActivity` - **@USER**

### Phase 5: Booking & Requests ⚪
- [ ] Button to "Request Seat" - **@USER**
- [ ] API: `book_seat.php` - **@FRIEND**
- [ ] Driver view to "Accept/Reject" requests - **@USER** & **@FRIEND**

### Phase 6: Final Polish ⚪
- [ ] App Icon and Branding
- [ ] Input Validation improvements
- [ ] Error handling & Offline support

---

## 📝 How to use this file
1. Every time a step is done, change `[ ]` to `[x]`.
2. Commit and push the change to GitHub so everyone stays updated.
