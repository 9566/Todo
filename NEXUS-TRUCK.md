# NEXUS-TRUCK — Secure Logistics Workflow Platform

> A secure, OTP-verified logistics workflow platform that prevents cargo theft, fake-transporter
> activity, and financial fraud in long-haul transportation. It manages the full shipment lifecycle
> — from shipment creation to final payment settlement — with a transparent digital expense ledger,
> electronic Proof of Delivery (ePOD), immutable audit records, and suspicious-activity detection.

---

## 1. The Problem It Solves

Long-haul road logistics in many markets runs on paper, phone calls, and trust. That creates:

- **Cargo theft & diversion** — goods picked up by the wrong person or "lost" en route.
- **Fake transporters** — fraudsters posing as legitimate fleet operators.
- **Expense fraud** — inflated or fabricated fuel/toll claims with no receipt trail.
- **Delayed & disputed payments** — no single source of truth for what was delivered and what is owed.
- **No accountability** — when something goes wrong, there is no immutable record of who did what, when.

NEXUS-TRUCK replaces paperwork and verbal trust with a **controlled digital workflow + cryptographic
verification + an immutable audit trail**.

---

## 2. The Three Core Roles

The platform is built around three primary actors. Everything below is the *content* you asked for —
features and value for each role.

### 2.1 Lorry Owner (Fleet Owner / Transporter)
The person or company that owns the trucks and employs/contracts drivers.

**What they need:**
- **Fleet dashboard** — every truck's live status (idle / loading / in-transit / delivered / maintenance).
- **Driver management** — onboard drivers with KYC (license, Aadhaar/ID, photo), assign them to trucks,
  block/unblock, and see each driver's trust score.
- **Trip assignment** — accept shipment requests from stockholders and assign a truck + driver.
- **Financial control** — per-trip profit/loss: freight earned − (fuel + tolls + driver advance + expenses).
- **Advance & settlement** — issue trip advances to drivers, reconcile against logged expenses, settle the balance.
- **Vehicle compliance** — track RC, insurance, fitness certificate, permit, and PUC expiry with alerts.
- **Earnings & payouts** — invoices to stockholders, payment status, outstanding receivables.
- **Fraud alerts** — get notified when an OTP fails repeatedly, an expense exceeds its limit, or spend goes over the trip allowance.

### 2.2 Driver
The person physically operating the truck and handling cargo.

**What they need (mobile-first, simple UI):**
- **My trips** — assigned trips with pickup/drop locations, cargo details, and contact info.
- **OTP pickup verification** — at the loading point, enter the OTP shared with the stockholder/consignor to
  confirm the *right* cargo is loaded by the *right* driver.
- **Live trip status** — start trip, mark checkpoints, mark arrival, request OTP for unloading.
- **OTP unloading verification** — at delivery, the receiver's OTP confirms handover; this triggers ePOD generation.
- **Digital expense ledger** — log fuel, toll, food, repair, parking, and miscellaneous expenses with a
  **receipt photo** and amount; running balance against the advance is always visible.
- **ePOD capture** — photo of delivered goods, receiver signature, timestamp, geolocation.
- **Trip wallet / balance** — how much advance is left, how much is reimbursable, what's owed.
- **SOS / panic** — emergency button that alerts the owner and logs location.
- **Live location pings** — the app sends GPS periodically so owner & stockholder can follow the truck on a
  map. (Simple pings — no real-time route-matching.)
- **Online-first** — the MVP works online. Offline queue-and-sync is a *future* enhancement, not required.

### 2.3 Stockholder (Cargo Owner / Consignor / Stakeholder)
The party whose goods ("stock") are being transported — the customer paying for delivery.

**What they need:**
- **Create shipment** — origin, destination, cargo type, weight, value, pickup window, special handling.
- **Choose transporter** — see verified fleet owners with trust scores, ratings, and pricing.
- **Pickup OTP** — receive the OTP that the driver must enter to load — proves goods left in the right hands.
- **Live tracking** — follow the truck's latest location on a map (periodic GPS pings).
- **Delivery confirmation** — issue/share the unloading OTP to the receiver; receive ePOD on completion.
- **Expense transparency** — for cost-plus contracts, view the validated expense ledger for their trip.
- **Payments** — pay freight, view invoices, settlement history.
- **Dispute & rating** — raise a dispute backed by the audit trail; rate the transporter and driver.

> **Note on the term "stockholder":** in this platform it means the *owner of the stock/cargo* being
> shipped (consignor/shipper), not a company shareholder. If you ever expand to investors, keep that a
> separate role to avoid confusion.

---

## 3. The Secure Shipment Workflow (Lifecycle)

This is the controlled operational flow that ties the three roles together:

```
[Stockholder] Create Shipment
        │
        ▼
[Lorry Owner] Accept & Assign Truck + Driver
        │
        ▼
[System] Generate PICKUP OTP  ──►  shared with Stockholder/Consignor
        │
        ▼
[Driver] At pickup: enter PICKUP OTP  ──►  ✅ Cargo loaded (logged + audited)
        │
        ▼
[Driver] Trip in transit  ──►  GPS pings, checkpoints, expense logging
        │
        ▼
[System] Generate UNLOAD OTP  ──►  shared with Receiver/Stockholder
        │
        ▼
[Driver] At delivery: enter UNLOAD OTP  ──►  ✅ Cargo unloaded
        │
        ▼
[System] Generate ePOD (photo + signature + geo + timestamp)
        │
        ▼
[Owner ↔ Stockholder] Expense reconciliation + Payment settlement
        │
        ▼
[System] Close trip → immutable audit record finalized
```

**State machine (suggested statuses):**
`CREATED → ASSIGNED → PICKUP_VERIFIED → IN_TRANSIT → ARRIVED → UNLOAD_VERIFIED → DELIVERED → SETTLED → CLOSED`
(plus side states: `CANCELLED`, `DISPUTED`, `FLAGGED`)

### 3.1 Two ways to use NEXUS-TRUCK (platform load **or** your own load)

The app must be useful **even when the load did not come through the app**. Many owners and drivers get
trips from brokers, regular clients, or direct deals — they should still be able to **maintain everything
here**: trips, allowances, expenses, receipts, documents, reports, and the audit trail. This makes
NEXUS-TRUCK a daily-use tool, not just a marketplace.

So a trip can be created in **two modes**:

| | **Platform load** (`trip_type = PLATFORM`) | **Own / manual load** (`trip_type = MANUAL`) |
|---|---|---|
| Who creates it | Stockholder creates a shipment → owner assigns | **Owner or driver** creates the trip directly |
| Stockholder on app? | Yes | **Not required** |
| Origin / destination / cargo | From the shipment | Entered manually on the trip |
| OTP pickup/unload | Full OTP flow with the consignor/receiver | **Optional** — use it if the other party has the app; otherwise skip |
| ePOD | Generated from the unload OTP | **Optional** — driver can still capture photo + signature as proof |
| Everything else **(identical)** | ✅ allowance, expense ledger, receipts, document expiry, reports, comparison, payment ledger, **immutable audit log** | ✅ same |

**Key point:** the **money + records features are the same in both modes.** Whether or not a stockholder
is involved, the owner and driver get the full **allowance → expense → receipt → settlement** ledger,
**lorry-document expiry tracking**, **trip comparison/reports**, and the **audit trail**. The only things
that need the other party on the app are the *marketplace booking* and the *OTP/ePOD verification* — and
those gracefully become **optional** for manual trips.

**Manual-trip flow (simplified):**
```
[Owner/Driver] Create trip (enter route, cargo, allowance)  →  ASSIGNED
        →  (optional OTP if both sides on app)  →  IN_TRANSIT
        →  log expenses + receipts  →  (optional ePOD photo)  →  DELIVERED
        →  settle allowance vs. spend  →  CLOSED   (all audit-logged)
```

> This is what lets a lorry owner run **his whole fleet's bookkeeping** in NEXUS-TRUCK from day one —
> even before any stockholder ever joins the platform.

---

## 4. Cross-Cutting Platform Features

These serve all three roles and are where the "secure" in the name lives.

- **OTP verification engine** — time-bound, single-use OTPs for pickup and unloading; configurable expiry,
  max-attempt lockout, and resend throttling.
- **Immutable audit log** — every state change, OTP event, expense entry, and payment is appended to a
  simple **append-only table with a SHA-256 `prevHash` chain** (`hash = SHA256(prevHash + payload)`) so
  records can't be silently edited. *This is "blockchain-inspired" tamper-evidence — not a real blockchain.*
- **Digital expense ledger** — categorized expenses with **manual receipt validation** (owner marks each
  receipt Valid/Reject — no OCR) and running balances per trip and per driver.
- **ePOD service** — generates a tamper-evident delivery proof (PDF/JSON) with photo, signature, location, time.
- **Suspicious-activity detection (rule-based only)** — simple threshold rules, no ML:
  - repeated OTP failures, expense amount over a category limit, spend exceeding the trip allowance,
    mismatched geolocation at pickup/unload. *(Route-deviation / impossible-travel checks are a later, optional add.)*
- **Trust score** — per driver and per fleet owner, computed from on-time delivery, OTP-clean trips,
  expense accuracy, and dispute history.
- **Notifications** — SMS / push / in-app for OTPs, status changes, alerts, and payment events.
- **Role-based access control (RBAC)** — strict separation of what each role can see and do.
- **Reporting & analytics** — trip P&L, fuel efficiency, on-time %, fraud incidents, receivables aging.

### 4.1 Trip Expense Statement (downloadable, anytime)

Both the **owner and the driver** can download a trip's expense statement as **PDF or Excel/CSV** —
**during the trip (interim statement)** and **after it ends (final statement)**. Same data, just a
snapshot at the moment of download.

**Who can download what:**
- **Driver** — their own trips' statements.
- **Owner** — statements for any trip on their fleet.
- (Optional) **Stockholder** — only on cost-plus contracts where expenses are shared.

**What the statement contains:**
- Trip header — trip ID, route (origin → destination), truck, driver, start date, status, advance amount.
- Expense table — for each entry: date/time, category (fuel / toll / food / parking / repair / misc),
  amount, receipt attached (Y/N), and **expense status**.
- Totals — total per category, total spent, **advance given**, and **balance remaining** (advance − spent).
- Split summary — **Completed** vs **Pending** expense totals (see below).
- Footer — generated-on timestamp + "interim" or "final" label, so an in-progress download is never
  mistaken for the closed account.

**What "completed" means — the expense status lifecycle:**

| Status | Meaning | Counts as |
|--------|---------|-----------|
| `RECORDED` | Driver entered amount, **no receipt yet** | Pending |
| `RECEIPT_ATTACHED` | Receipt photo uploaded, awaiting check | Pending |
| `VALIDATED` | Owner/system verified the receipt — accepted | **Completed** |
| `REJECTED` | Receipt invalid/missing — not counted | Excluded |
| `SETTLED` | Included in final settlement with the driver | **Completed** (closed) |

So **"completed expenses"** = entries that are `VALIDATED` (and later `SETTLED`) — receipt checked and
accepted into the ledger. **"Pending"** = `RECORDED` or `RECEIPT_ATTACHED` (still need a valid receipt or
a review). The statement shows both totals so the owner instantly sees how much is verified vs. unverified.

> **Mid-trip value:** the owner can download today's statement to see live spend vs. advance and catch a
> problem before the trip ends; the driver can download proof of what they've spent at any point.

### 4.2 Lorry Document Validity & Expiry Alerts

Every truck carries legally-required documents that **expire**. If the owner misses a renewal, the
vehicle can be **fined, detained, or grounded**. NEXUS-TRUCK keeps every lorry document on file, **shows
its validity status**, and **notifies the owner before it expires** — for **all** lorry documents.

**Lorry documents tracked (each with issue date, expiry date, and an uploaded copy):**
- **RC** (Registration Certificate)
- **Insurance** (third-party / comprehensive)
- **Fitness Certificate (FC)**
- **Permit** (National / State goods-carriage permit)
- **PUC** (Pollution Under Control certificate)
- **Road Tax / Tax token**
- **National Permit Authorization**
- **Speed Governor / GPS compliance certificate** (where applicable)
- _(extensible — add any state-specific document)_

**Display (owner's fleet dashboard):** each truck shows a color-coded status per document so the owner
sees problems at a glance:

| Status | Rule | Color |
|--------|------|-------|
| **Valid** | expiry is more than 30 days away | 🟢 Green |
| **Expiring Soon** | expiry within the next 30 days | 🟡 Yellow |
| **Critical** | expiry within the next 7 days | 🟠 Orange |
| **Expired** | expiry date has passed | 🔴 Red |

A dedicated **"Documents Expiring" panel** lists everything due across the whole fleet, sorted by
soonest expiry, so the owner has one place to act.

**Notifications to the owner** (in-app + push + free email):
- Reminders fire at **30 / 15 / 7 / 1 days before** expiry, and **on the expiry day**.
- A **daily scheduled job** checks every document, updates its status, and sends only the due reminders
  (each reminder is sent once — tracked via `last_notified_at` to avoid spam).

**Enforcement (optional but powerful):**
- **Block trip assignment** for a truck whose RC, insurance, fitness, or permit is **expired** —
  preventing an illegal vehicle from being dispatched.
- Log every expiry/renewal event to the **immutable audit log** for compliance proof.

### 4.3 Trip Allowance Reports, Conclusion & Per-Lorry Comparison

The **allowance** is the money the owner allots to a trip (advance for fuel, tolls, and on-road
expenses). The owner needs to (a) **download the allowance document per trip**, (b) see an **overall
conclusion across trips**, and (c) **compare trips — allowance vs. actual spend — for a specific lorry**.

**(a) Trip-wise allowance document (download — PDF / Excel):**
- Allowance (advance) given for the trip.
- Expenses by category (fuel / toll / food / parking / repair / misc) with completed-vs-pending split.
- **Balance** = allowance − validated spend (amount the driver returns, or extra owed to the driver).
- Trip header (route, truck, driver, dates, status). One document per trip; downloadable anytime.

**(b) Overall conclusion (across all trips, or a filtered set):**
- Totals — total allowance given, total spent, total balance.
- Averages — average allowance per trip, average spend per trip, **average spend per km**.
- Highlights — **most economical trip**, **most expensive trip**, and any **over-budget trips**
  (where spend exceeded allowance — auto-flagged).
- Trend — is per-km cost improving or worsening over time?

**(c) Per-lorry trip comparison:**
Pick a lorry (e.g. `TN-01-AB-1234`) → see all its trips side-by-side and compare allowance vs. spend:

| Trip | Route | Distance | Allowance | Spent | Balance | Fuel/km | Days | Status |
|------|-------|----------|-----------|-------|---------|---------|------|--------|
| T-101 | Chennai→Mumbai | 1330 km | ₹40,000 | ₹37,200 | +₹2,800 | ₹12.1 | 3 | On time |
| T-118 | Chennai→Mumbai | 1330 km | ₹40,000 | ₹44,500 | −₹4,500 | ₹15.8 | 4 | ⚠ Over-budget |
| T-130 | Chennai→Pune | 1180 km | ₹35,000 | ₹33,800 | +₹1,200 | ₹11.6 | 3 | On time |

This instantly shows the owner which trips on the **same lorry** cost more than expected — useful for
spotting fuel leakage, inflated expenses, or an inefficient route/driver. The comparison is also
**downloadable** as PDF/Excel, and can be filtered by lorry, driver, route, or date range.

> All three reports are computed from existing trip + expense + allowance data — **no extra cost** to
> build (server-side aggregation + a free PDF/Excel export library).

---

## 5. Suggested Data Model (entities)

A starting schema — adjust as you design:

- **User** (id, name, phone, email, role, kyc_status, trust_score)
- **FleetOwner** (user_id, company, gst, rating)
- **Driver** (user_id, license_no, license_expiry, assigned_truck_id, trust_score)
- **Stockholder** (user_id, company, gst)
- **Truck** (id, owner_id, reg_no, capacity, status)
- **TruckDocument** (id, truck_id, doc_type[RC|INSURANCE|FITNESS|PERMIT|PUC|ROAD_TAX|NAT_PERMIT|OTHER], doc_number, issue_date, expiry_date, file_url, status[VALID|EXPIRING_SOON|CRITICAL|EXPIRED], last_notified_at)
- **Shipment** (id, stockholder_id, origin, destination, cargo_type, weight, declared_value, status, freight_amount) — _only for platform loads_
- **Trip** (id, **trip_type[PLATFORM|MANUAL]**, **shipment_id (nullable)**, truck_id, driver_id, status, allowance_amount, **origin, destination, cargo_note, party_name** (used when manual), started_at, delivered_at) — _manual trips fill route/cargo here instead of via a Shipment_
- **OtpToken** (id, trip_id, type[PICKUP|UNLOAD], code_hash, expires_at, attempts, used_at)
- **Expense** (id, trip_id, driver_id, category, amount, receipt_url, status[RECORDED|RECEIPT_ATTACHED|VALIDATED|REJECTED|SETTLED], created_at, geo)
- **EpodRecord** (id, trip_id, photo_url, signature_url, geo, signed_at, pdf_url)
- **AuditLog** (id, entity, entity_id, action, actor_id, payload, prev_hash, hash, created_at)
- **Payment** (id, trip_id, payer_id, payee_id, amount, type[ADVANCE|FREIGHT|SETTLEMENT], status[PENDING|PAID]) — _ledger record only; no gateway / money movement_
- **Alert** (id, trip_id, type, severity, details, resolved)

---

## 6. Suggested Technical Architecture

You have a Spring Boot guide in this folder, so here's a Spring-oriented stack:

- **Backend:** Spring Boot (REST API), Spring Security + JWT for auth/RBAC, Spring Data JPA.
- **Database:** PostgreSQL/MySQL (transactional data); the `AuditLog` hash chain gives tamper-evidence
  without needing a real blockchain — call it "blockchain-inspired immutable ledger."
- **OTP / SMS:** an SMS gateway (Twilio / MSG91 / Fast2SMS) + a scheduled job for OTP expiry.
- **Scheduler:** Spring `@Scheduled` (cron) daily job to recompute document statuses and fire expiry reminders.
- **File storage:** receipt & ePOD images on local disk (demo) or a free tier (Cloudinary / Supabase / MinIO).
- **Maps & tracking:** **OpenStreetMap + Leaflet** showing **periodic GPS pings** — no Google Maps, no
  real-time route-matching. (Routing/ETA via OSRM is an *optional* later add.)
- **Payments:** **ledger only** — record advance / freight / settlement as paid-unpaid. **No payment
  gateway, no real money movement** (avoids cost + KYC/PCI complexity).
- **Frontend:**
  - Driver app — mobile-first, **online-first** (React Native / Flutter / responsive web PWA).
  - Owner & Stockholder portals — web dashboard (React / Angular / Thymeleaf).
- **Notifications:** Firebase Cloud Messaging (push) + in-app + free email. (Paid SMS deferred.)
- **Reporting:** server-side aggregation; export to PDF/Excel.

**Suggested module/package layout (Spring Boot):**
```
auth/  user/  fleet/  driver/  shipment/  trip/  otp/  expense/  epod/  audit/  payment/  alert/  notification/  report/
```

---

## 7. Roadmap (build it in phases)

**Phase 1 — MVP (core trust loop)**
- User registration + RBAC for the 3 roles
- Shipment creation → assignment → trip
- Pickup & unload OTP verification
- Basic expense ledger with receipt upload
- Immutable audit log
- Simple ePOD (photo + timestamp + geo)

**Phase 2 — Financial & tracking**
- Allowance/settlement reconciliation, invoices, payments **as a ledger** (paid/unpaid — no gateway)
- **Location pings on a Leaflet/OpenStreetMap map** (no real-time route-matching)
- Notifications (in-app + push + free email)
- Lorry document expiry reminders + downloadable expense/allowance reports

**Phase 3 — Intelligence & trust**
- **Rule-based** suspicious-activity alerts (no ML)
- Trust scores & two-way ratings
- Analytics dashboards (P&L, fuel/km, on-time %, over-budget trips)
- Dispute management backed by audit trail

**Phase 4 — Later / if budget allows (the complex tail)**
- ML-based anomaly detection
- Real payment gateway, paid SMS, paid maps
- Route-deviation / ETA via a routing engine
- Offline-first sync, multi-tenant fleets, marketplace matching

---

## 8. Build It for Free (Zero-Cost Stack)

Everything below can be built and run **without paying for anything**, using free tiers and
open-source tools. The whole platform is buildable for ₹0 if you make a few smart choices.

### 8.1 Free stack mapping

| Need | Free choice | Notes |
|------|-------------|-------|
| Backend | **Spring Boot** | Free, open-source (you have the guide). |
| Auth / RBAC | **Spring Security + JWT** | Built in code, no service. |
| Database | **PostgreSQL / MySQL** (self-host) or **Supabase / Neon / Aiven** free tier | H2 in-memory is fine for a demo. |
| Image storage (receipts, ePOD) | **Local disk** (demo) or **Cloudinary / Supabase Storage** free tier | Or store small images as DB blobs. |
| Maps & tracking | **Leaflet + OpenStreetMap** tiles | 100% free, no API key. |
| Geocoding (address → coords) | **Nominatim (OSM)** | Free; respect usage limits. |
| Routing / ETA | **OSRM** (public demo or self-host) | Free. |
| Live driver location | **Browser Geolocation API** | Free; phone sends its own GPS. |
| Push notifications | **Firebase Cloud Messaging** | Free. |
| Email (OTP backup, invoices) | **Gmail SMTP** (~500/day) or **Brevo** free (300/day) | Free. |
| Frontend hosting | **Vercel / Netlify / GitHub Pages** | Free. |
| Backend hosting | **Oracle Cloud Always Free**, **Render**, or **Fly.io** | Oracle gives a generous always-free VM. |
| Audit ledger | **Hash-chain in code (SHA-256)** | No blockchain/service needed — free. |

### 8.2 The one tricky part: OTP without paying for SMS

SMS gateways cost money. You **don't need them** for the core security model:

- The system shows the **PICKUP OTP inside the stockholder/consignor's app**.
- When the driver physically arrives, the consignor reads out the OTP; the **driver types it into the driver app**.
- ✅ This still proves "the right driver is physically present with the right consignor" — which is the
  whole point. It works entirely **in-app, for free**.
- The **UNLOAD OTP** works the same way: shown in the receiver's app, typed by the driver at delivery.
- **Free backup channel:** if a party can't open the app, send the OTP by **email (free SMTP)**.
- Only switch to paid SMS later, if/when you need to reach users who have no app and no email.

> This means your secure OTP workflow — the heart of the platform — costs **nothing** to build and run.

### 8.3 What we can build for free vs. what to defer

**Fully buildable for free (do these):**
- All 3 role portals + RBAC, full shipment → trip lifecycle, in-app/email OTP verification,
  digital expense ledger with receipt photos, immutable audit log, ePOD (photo + signature + geo + time),
  rule-based suspicious-activity detection, trust scores, live tracking on OpenStreetMap, push notifications,
  invoices & settlement tracking, analytics dashboards.

**Defer until there's a budget (not needed for a complete, working product):**
- Paid SMS OTP delivery, paid Google Maps, paid managed cloud/scale infra, ML-based anomaly detection.

### 8.4 Recommended free MVP (what to build first)

1. User signup + login + the 3 roles (Spring Security + JWT).
2. Create a trip **two ways**: (a) stockholder shipment → owner assigns, **or** (b) owner/driver creates a
   **manual trip** for an off-platform load. Both feed the same ledger.
3. In-app **pickup OTP** → driver verifies → status `PICKUP_VERIFIED` (OTP optional for manual trips).
4. Driver logs expenses with receipt photos (running balance).
5. In-app **unload OTP** → driver verifies → auto-generate **ePOD**.
6. Immutable **audit log** behind every step.
7. Simple dashboards for each role.

That MVP is a complete, demonstrable, secure product — and it costs nothing to build or run.

---

## 9. Why It Matters (pitch / report summary)

NEXUS-TRUCK turns an opaque, paper-based, trust-dependent process into a **verifiable digital workflow**.
Every critical handoff (pickup, unload) is cryptographically confirmed; every rupee of expense is
receipt-backed and reconciled; every action is written to an immutable ledger. The result is **less theft,
less fraud, faster payments, and measurable accountability** for lorry owners, drivers, and the
stockholders whose cargo is on the line.

---

## 10. Enhancement Ideas (free, high-value)

Strong additions that fit the three users and cost nothing to build. ⭐ = high impact, low effort —
good to add early.

| Idea | Value | Effort | For |
|------|-------|--------|-----|
| ⭐ **QR code for pickup/unload** | Driver scans a QR instead of typing the OTP — faster, no typos. (free `zxing` library) | Low | Driver |
| ⭐ **Regional-language UI (Tamil/Hindi/…)** | Drivers adopt it far more readily; pure i18n, free | Low | Driver |
| ⭐ **Photo-first expense entry** | Snap receipt + enter amount, minimal typing — works for low-literacy drivers | Low | Driver |
| ⭐ **Digital consignment note (LR)** | Auto-generate a printable LR / delivery note per shipment (PDF) | Low | All |
| ⭐ **Per-category expense limits** | Owner sets caps (e.g. food ≤ ₹500/day); alert when exceeded | Low | Owner |
| **Mileage / fuel-efficiency tracking** | km-per-litre per truck; flag sudden drops = possible fuel theft | Med | Owner |
| **Maintenance & service reminders** | Service due by km or date (reuses the document-expiry engine) | Low | Owner |
| **Two-way ratings** | Stockholder rates driver/owner; owner rates stockholder as a reliable payer | Low | All |
| **Driver incentive / leaderboard** | Reward on-time + clean-OTP + accurate-expense trips → retention | Med | Owner/Driver |
| **Export to Excel/Tally (CSV)** | Owners keep books in Tally/Excel — one-click export | Low | Owner |
| **Daily summary email to owner** | One digest: trips today, spend, expiring docs, alerts | Low | Owner |
| **Tamper-evident ePOD (hash-linked)** | Tie each ePOD into the audit hash chain for stronger proof | Low | All |

---

## 11. Complexity Check — what's genuinely hard (keep simple or defer)

> ✅ **Scope decision (adopted):** we are building the **simplified versions** below. These choices are
> already reflected throughout this document — sections 2, 4, 6, and 7 describe the simplified design,
> and the complex tail is moved to **Phase 4 (later / if budget allows)**.

An honest scope review so a small/free build stays achievable. Each item: why it's hard, and the
recommendation.

| Feature | Why it's complex | Recommendation |
|---------|------------------|----------------|
| **Live GPS tracking + route-deviation detection** | Real-time location streaming, map-matching, geofencing, and "distance from planned route" geometry are the hardest parts of the whole project | **Simplify:** start with periodic location pings shown on a Leaflet map. **Defer** automatic route-deviation detection (needs a routing engine + geometry math). |
| **Offline-first driver app with sync** | Local queue + sync + conflict resolution + dedupe is a hard distributed-systems problem | **Defer.** Build online-first. Add a simple "retry on reconnect" later — not true offline sync. |
| **Receipt validation** | Auto-validating receipts implies OCR/ML — error-prone and heavy | **Keep simple:** "validation" = receipt photo present + owner marks Valid/Reject. **Defer** OCR. |
| **ML-based fraud/anomaly detection** | Needs data, training, tuning; easy to get wrong | **Defer.** Use **rule-based** checks only (thresholds, repeated OTP fails, over-budget). |
| **Real payment gateway / money movement** | Gateway integration costs money and adds KYC, settlement, refund, and PCI complexity | **Don't build money movement.** Keep payments as a **ledger** (mark advance/freight/settlement paid-unpaid). |
| **"Blockchain" audit log** | A real blockchain is huge over-engineering here | **Keep it a simple append-only table with a SHA-256 `prevHash` chain.** That already gives tamper-evidence. Call it "blockchain-inspired," not a blockchain. |
| **Real-time websockets everywhere** | Live push for tracking/notifications adds infra complexity | **Start with polling** + FCM push for key events. Add websockets only if needed. |

**Rule of thumb:** the **OTP workflow + expense ledger + audit log + reports** are the *core and the easy
wins* — build those first. The **GPS/tracking, offline sync, payments-as-money, and ML** are the
*complex tail* — simplify or defer them so they never block a working demo.
