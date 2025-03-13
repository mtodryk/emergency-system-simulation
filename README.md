# 🚨 Emergency System Simulation 🚨

## 📌 Overview
This project simulates an emergency management system handling incidents such as fires, accidents, etc. 🚑🚒🚓  
**Additionally, this project demonstrates key concepts of OOP principles.**
---
### ⚙️ System Architecture
- 🚨 **Incident Processing** – The dispatcher converts reported incidents into service requests.
- 📊 **Monitoring & Coordination** – The dispatcher assigns and oversees rescue units until the situation is resolved.
- 🏥 **Hospitalization** – Injured individuals are treated on-site or transported to hospitals with limited capacity.
- ⏳ **Turn-Based Assistance** – Each rescue unit has a specific response time, and multiple units can operate at the same location simultaneously.

This system ensures efficient resource management based on **incident priority** and **unit proximity**. 🚀
___
### 🔥 Key Objectives
- ✅ **Incident Management** – Track active and historical accidents.
- ✅ **Dispatcher Coordination** – Assign rescue units based on priority and location.
- ✅ **Rescue Operations** – Simulate real-time movement and response of police, firefighters, and ambulances.
- ✅ **Hospital System** – Handle patient admissions and hospital assignments.
- ✅ **Turn-Based Execution** – Each turn updates statuses, dispatches units, and records actions.

---

### 🔄 How It Works
1. **Accidents are generated** *(either randomly or predefined)*.
2. **Dispatcher analyzes** incidents and assigns **available rescue units**.
3. **Units move to the accident scene**, perform rescue actions, and **return to base**.
4. **Hospitals manage** patients requiring hospitalization.
5. Each turn generates a **log of actions**, visualizing the system in motion.

![gif](https://github.com/user-attachments/assets/8d48bdf1-9b53-47ad-8c79-b7444651211e)

---

## 🚀 Getting Started

### 💻 How to Run

```bash
# Step 1: Clone the repository
git clone https://github.com/mtodryk/emergency-system-simulation.git
cd emergency-system-simulation

# Step 2: Navigate to the correct directory
cd src/SystemRatunkowy

# Step 3: Compile and run the simulation
javac Symulacja.java
java Symulacja
