<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
   
</head>
<body>

<h1>🏢 Co-working Space Desk Booking & Billing System (JDBC)</h1>

<p>
    A <strong>console-based Java application</strong> built using <strong>JDBC and Oracle Database</strong>
    to manage members, desk bookings, and usage-based billing in a co-working space.
</p>

<p class="badge">Java</p>
<p class="badge">JDBC</p>
<p class="badge">Oracle DB</p>
<p class="badge">Console App</p>

<hr>

<h2>📌 Project Overview</h2>
<p>
    This system allows co-working space administrators to handle member profiles,
    manage desk bookings transactionally, and generate accurate usage-based bills
    using membership-tier pricing.
</p>

---

<h2>✨ Key Features</h2>

<h3>👤 Member Management</h3>
<ul>
    <li>Add, view, list, and remove members</li>
    <li>Strong validation on member details</li>
</ul>

<h3>🪑 Desk Booking</h3>
<ul>
    <li>Create desk bookings with overlap prevention</li>
    <li>Cancel bookings transactionally</li>
    <li>Status tracking: BOOKED / CANCELLED / COMPLETED</li>
</ul>

<h3>💰 Billing System</h3>
<ul>
    <li>Generate usage-based bills</li>
    <li>Membership tier pricing</li>
    <li>Outstanding balance tracking</li>
</ul>

---

<h2>🧱 Technology Stack</h2>
<table>
    <tr><th>Layer</th><th>Technology</th></tr>
    <tr><td>Language</td><td>Java</td></tr>
    <tr><td>Database</td><td>Oracle DB</td></tr>
    <tr><td>Connectivity</td><td>JDBC</td></tr>
    <tr><td>Architecture</td><td>Layered (Controller → Service → DAO)</td></tr>
    <tr><td>Interface</td><td>Console-based</td></tr>
</table>

---
<h2>🗂️ Project Structure</h2>
<pre>
com.cowork
│
├── app
│   └── CoworkMain.java
│
├── service
│   └── BookingService.java
│
├── dao
│   ├── MemberDAO.java
│   ├── BookingDAO.java
│   └── BillDAO.java
│
├── bean
│   ├── Member.java
│   ├── Booking.java
│   └── Bill.java
│
└── util
    ├── DBUtil.java
    ├── ValidationException.java
    ├── SlotAlreadyBookedException.java
    └── ActiveBillingException.java
</pre>

---

<h2>🗃️ Database Design</h2>

<h3>📘 MEMBER_TBL</h3>
<table>
    <tr><th>Column</th><th>Datatype</th><th>Description</th></tr>
    <tr><td>MEMBER_ID</td><td>VARCHAR2(12)</td><td>Primary Key</td></tr>
    <tr><td>FULL_NAME</td><td>VARCHAR2(150)</td><td>Member full name</td></tr>
    <tr><td>EMAIL</td><td>VARCHAR2(200)</td><td>Email address</td></tr>
    <tr><td>MOBILE</td><td>VARCHAR2(20)</td><td>Contact number</td></tr>
    <tr><td>MEMBERSHIP_TIER</td><td>VARCHAR2(30)</td><td>BASIC / STANDARD / PREMIUM</td></tr>
    <tr><td>OUTSTANDING_BALANCE</td><td>NUMBER(12,2)</td><td>Pending amount</td></tr>
</table>

---

<h3>📗 BOOKING_TBL</h3>
<table>
    <tr><th>Column</th><th>Datatype</th><th>Description</th></tr>
    <tr><td>BOOKING_ID</td><td>NUMBER(10)</td><td>Primary Key</td></tr>
    <tr><td>MEMBER_ID</td><td>VARCHAR2(12)</td><td>Foreign Key → MEMBER_TBL</td></tr>
    <tr><td>DESK_CODE</td><td>VARCHAR2(20)</td><td>Desk/Pod identifier</td></tr>
    <tr><td>BOOKING_DATE</td><td>DATE</td><td>Date of booking</td></tr>
    <tr><td>START_TIME</td><td>VARCHAR2(10)</td><td>Start time (HH:MM)</td></tr>
    <tr><td>END_TIME</td><td>VARCHAR2(10)</td><td>End time (HH:MM)</td></tr>
    <tr><td>STATUS</td><td>VARCHAR2(20)</td><td>BOOKED / CANCELLED / COMPLETED</td></tr>
</table>

---

<h3>📕 BILL_TBL</h3>
<table>
    <tr><th>Column</th><th>Datatype</th><th>Description</th></tr>
    <tr><td>BILL_ID</td><td>NUMBER(10)</td><td>Primary Key</td></tr>
    <tr><td>MEMBER_ID</td><td>VARCHAR2(12)</td><td>Foreign Key → MEMBER_TBL</td></tr>
    <tr><td>BILLING_PERIOD_FROM</td><td>DATE</td><td>Billing start date</td></tr>
    <tr><td>BILLING_PERIOD_TO</td><td>DATE</td><td>Billing end date</td></tr>
    <tr><td>TOTAL_HOURS</td><td>NUMBER(10,2)</td><td>Total booked hours</td></tr>
    <tr><td>AMOUNT</td><td>NUMBER(12,2)</td><td>Total bill amount</td></tr>
    <tr><td>STATUS</td><td>VARCHAR2(20)</td><td>PENDING / PAID / CANCELLED</td></tr>
</table>

---

<h2>▶️ How to Run</h2>
<ol>
    <li>Create Oracle DB user</li>
    <li>Create tables using SQL</li>
    <li>Update DB credentials in <code>DBUtil.java</code></li>
    <li>Run <code>CoworkMain.java</code></li>
</ol>

---

<h2>📌 Sample Output</h2>
<pre>
--- Co-working Space Booking Console ---
BOOKING SUCCESS
CANCEL FAILED
BILL GENERATED
</pre>

---

<h2>🚀 Future Enhancements</h2>
<ul>
    <li>Spring Boot Web Application</li>
    <li>Role-based authentication</li>
    <li>Online payments</li>
    <li>Admin dashboard</li>
</ul>

---

<h2>👨‍💻 Author</h2>
<p><strong>Anbumani S</strong><br>Java & Full-Stack Developer</p>

<hr>

<p><strong>⭐ If you like this project, give it a star on GitHub!</strong></p>

</body>
</html>
