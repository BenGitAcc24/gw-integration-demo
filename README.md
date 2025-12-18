# Guidewire-Style Integration Demo (Spring Boot + Apache Camel)

This project demonstrates a **Guidewire-like external system integration flow** using **Spring Boot**, **Apache Camel**, and **STS IDE**.  
It accepts XML requests, transforms them, calls an external address standardization API, and persists both the original XML request and the external API response to the local filesystem.

---

## ✨ Features
- Spring Boot REST endpoint (`/gw/address/standardize`) that accepts XML input.
- JAXB unmarshalling of XML into a POJO (`AddressRequest`).
- Transformation logic to build an external API request (Zippopotam.us ZIP lookup).
- Apache Camel route orchestration:
  - Persist original XML request.
  - Call external REST API.
  - Persist external JSON response.
  - Map response into a standardized DTO (`StandardizedAddress`).
- Local persistence under `data/requests` and `data/responses`.

---

## 📂 Project Structure
