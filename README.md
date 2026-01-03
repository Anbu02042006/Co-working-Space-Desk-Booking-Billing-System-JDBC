<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    
</head>
<body>

<h1>🏢 Co-working Space Desk Booking & Billing System (JDBC)</h1>

<p>
    A <strong>console-based Java application</strong> developed using <strong>JDBC and Oracle Database</strong>
    to manage members, desk bookings, and usage-based billing in a co-working space environment.
</p>

<p class="badge">Java</p>
<p class="badge">JDBC</p>
<p class="badge">Oracle DB</p>
<p class="badge">Console Application</p>

<hr>

<h2>📌 Project Overview</h2>
<p>
    This project simulates a real-world co-working space management system where members can book desks,
    cancel bookings, and receive usage-based bills. The application enforces strong validation rules
    and ensures data consistency using JDBC transactions.
</p>

---

<h2>✨ Key Features</h2>

<h3>👤 Member Management</h3>
<ul>
    <li>Add new members with validated details</li>
    <li>View individual member details</li>
    <li>View all registered members</li>
    <li>Remove members only if no active bookings or pending bills exist</li>
</ul>

<h3>🪑 Desk Booking</h3>
<ul>
    <li>Create desk bookings transactionally</li>
    <li>Prevent overlapping bookings</li>
    <li>Cancel existing bookings</li>
    <li>Status tracking: BOOKED, CANCELLED, COMPLETED</li>
</ul>

<h3>💰 Billing System</h3>
<ul>
    <li>Generate usage-based bills</li>
    <li>Membership tier pricing logic</li>
    <li>Maintain outstanding balances</li>
    <li>Bill status tracking (PENDING / PAID / CANCELLED)</li>
</ul>

<h3>🔐 Transaction & Validation</h3>
<ul>
    <li>JDBC transaction handling using commit & rollback</li>
    <li>Custom exception handling for business rules</li>
</ul>

---

<h2>🧱 Technology Stack</h2>
<table>
    <tr>
        <th>Layer</th>
        <th>Technology</th>
    </tr>
    <tr>
        <td>Programming Language</td>
        <td>Java</td>
    </tr>
    <tr>
        <td>Database</td>
        <td>Oracle Database</td>
    </tr>
    <tr>
        <td>Connectivity</td>
        <td>JDBC</td>
    </tr>
    <tr>
        <td>Architecture</td>
        <td>Layered (Controller → Service → DAO)</td>
    </tr>
    <tr>
        <td>Interface</td>
        <td>Console-based</td>
    </tr>
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

<h2>🗃️ Database Tables</h2>

<h3>MEMBER_TBL</h3>
<table>
    <tr>
        <th>Column</th>
        <th>Type</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>MEMBER_ID</td>
        <td>VARCHAR2(12)</td>
        <td>Primary Key</td>
    </tr>
    <tr>
        <td>FULL_NAME</td>
        <td>VARCHAR2(150)</td>
        <td>Member Name</td>
    </tr>
    <tr>
        <td>EMAIL</td>
        <td>VARCHAR2(200)</td>
        <td>Email Address</td>
    </tr>
    <tr>
        <td>MOBILE</td>
        <td>VARCHAR2(20)</td>
        <td>Contact Number</td>
    </tr>
    <tr>
        <td>MEMBERSHIP_TIER</td>
        <td>VARCHAR2(30)</td>
        <td>BASIC / STANDARD / PREMIUM</td>
    </tr>
    <tr>
        <td>OUTSTANDING_BALANCE</td>
        <td>NUMBER(12,2)</td>
        <td>Pending Amount</td>
    </tr>
</table>

---

<h2>▶️ How to Run</h2>
<ol>
    <li>Install Oracle Database</li>
    <li>Create a database user</li>
    <li>Create tables using provided SQL</li>
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
    <li>Web-based UI (Spring Boot)</li>
    <li>User authentication & roles</li>
    <li>Online payment integration</li>
    <li>Reporting dashboard</li>
</ul>

---

<h2>👨‍💻 Author</h2>
<p>
    <strong>Anbumani S</strong><br>
    Java & Full-Stack Developer
</p>

<hr>

<p><strong>⭐ If you like this project, give it a star on GitHub!</strong></p>

</body>
</html>
