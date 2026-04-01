# Java BB84 Quantum Simulation

A console-based simulation of the BB84 Quantum Key Distribution protocol built using Java.
This project demonstrates how two parties can securely generate a shared secret key using principles of quantum mechanics, and how any eavesdropping attempt can be detected.

---

## • Features

- Simulation of the full BB84 protocol (6 phases)
- Random bit and basis generation
- Optional eavesdropper (Eve) simulation
- Quantum measurement behavior (correct vs random collapse)
- Public basis comparison (sifting phase)
- Error detection for eavesdropping
- Final secure key generation
- Step-by-step interactive execution
- Colored terminal output for better visualization

---

## • Notes

- This is a simulation, not a real quantum system
- Real-world implementations require actual quantum hardware
- Quantum behavior is represented using randomness
- Designed to help understand how BB84 works conceptually

---

## • How It Works

1. Sender generates random bits and bases
2. Optional: Eve intercepts and measures (introducing errors)
3. Receiver measures using random bases
4. Both compare bases publicly
5. Matching bits are kept
6. A subset is checked for tampering
7. If safe → remaining bits form the secret key

---

## • About This Project

This project was created as part of a Data Security class, where I chose to explore a modern and interesting topic: Quantum Cryptography.

I wanted to challenge myself with something more advanced. The BB84 protocol require several concepts before actually understanding it, such as: Quantum computing, qubits, superposition, quantum measurements and heisenberg uncertainty principle.

---

## • How to Run

1. Clone the repository

2. Open the project in your IDE

3. Run the `BB84.java` file

---

## • License

This project is licensed under the MIT License — feel free to use and modify it with proper credit.