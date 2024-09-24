#Bati-Cuisine
Bati-Cuisine is a kitchen renovation and construction cost estimation application designed for professionals in the construction and renovation industries. This tool helps manage clients, materials, labor costs, and generates precise estimates (devis) and invoices for renovation projects. The project uses technologies like Java, Java Streams, Collections, HashMap, Optional, and PostgreSQL, and is structured following best design patterns like Singleton and Repository Pattern.

#Features
Client Management: Register and manage client details.
Material and Labour Cost Estimation: Calculate costs for materials and labor based on user input.
Cost Breakdown: Detailed breakdown of total project cost, including beneficial margins and taxes.
Devis Management: Generate, manage, and save project cost estimates (devis) in a well-structured format.
Validation and Error Handling: Date and input validation to ensure data integrity.
Project Status Management: Update project status (e.g., completed, canceled) based on client input.
Reduction for Professional Clients: Option to grant discounts to professional clients.
Data Persistence: Store data using PostgreSQL database for easy access and manipulation.
#Technologies
Backend: Java (Streams API, Collections, HashMap, Optional, Enums)
Database: PostgreSQL
Design Patterns: Singleton, Repository Pattern
Project Management: JIRA, Git
Version Control: GitHub
Project Structure
The project is structured into multiple layers:

#Service Layer: Contains business logic (e.g., calculating costs, applying discounts).
DAO/Repository Layer: Handles data access and interaction with the database.
Model Layer: Defines entities such as Client, Material, Labourer, and Devis.
Controller Layer: Manages user input and processes data from the front-end (CLI-based in this case).
#Installation
git clone https://github.com/mouadHILLALI/bati-cuisine
cd bati-cuisine
Set up the PostgreSQL database:

Create a database and configure the connection in the application.properties file:
spring.datasource.url=jdbc:postgresql://localhost:5432/bati_cuisine
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
Contact
For questions, issues, or contributions, reach out to:

Mouad Toto - Full Stack Developer at YouCode
GitHub: mouadHILLALI
LinkedIn: https://www.linkedin.com/in/mouad-toto-b6484a220/
