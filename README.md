# Car Dealership

A full-stack car dealership application with inventory management, user authentication, and role-based access control. Customers can browse and purchase vehicles; administrators can manage stock, add vehicles, and restock inventory.

## Tech Stack

| Layer | Technologies |
|-------|-------------|
| **Backend** | Java 21, Spring Boot 3.5, Spring Security, JWT, Spring Data JPA, MySQL |
| **Frontend** | React 19, Vite, JSX |
| **Testing** | JUnit 5, Mockito |

## Project Structure

```
car-dealership/
├── src/main/java/          # Spring Boot backend (controllers, services, security)
├── src/test/java/          # Unit tests
├── Car_Dealership_Client/
│   └── frontend/           # React frontend (JSX components + API client)
└── README.md
```

## Getting Started

### Prerequisites

- Java 21
- Maven
- MySQL (database: `car_dealer`)
- Node.js 18+

### Backend

1. Configure MySQL in `src/main/resources/application.properties`
2. Run the application:

```bash
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

### Frontend

```bash
cd Car_Dealership_Client/frontend
npm install
npm run dev
```

The UI runs at `http://localhost:5173` and proxies `/api` requests to the backend.

## API Endpoints

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `GET` | `/api/vehicles` | Public | List all vehicles |
| `GET` | `/api/vehicles/search` | Public | Search by make, model, category, or price range |
| `POST` | `/api/vehicles` | Admin | Add a vehicle |
| `PUT` | `/api/vehicles/{id}` | Admin | Update a vehicle |
| `DELETE` | `/api/vehicles/{id}` | Admin | Delete a vehicle |
| `POST` | `/api/vehicles/{id}/purchase` | Authenticated | Purchase a vehicle (decreases quantity) |
| `POST` | `/api/vehicles/{id}/restock` | Admin | Restock a vehicle (increases quantity) |
| `POST` | `/api/auth/register` | Public | Register a new user |
| `POST` | `/api/auth/login` | Public | Login and receive JWT token |

## My AI Usage

I used AI assistance as a development accelerator throughout this project, while retaining ownership of architecture decisions, business logic, and final code review.

**Test cases** — AI helped generate structured unit tests for the service layer, including validation scenarios, purchase/restock inventory logic, and edge cases such as out-of-stock purchases and invalid restock quantities. I reviewed each test to ensure it matched the intended behavior and project conventions before committing.

**Frontend** — AI supported the design and implementation of the React frontend, including JSX components, API integration, authentication flow, and inventory UI (vehicle cards, purchase/restock modals, and admin controls). I used the generated output as a starting point and refined layout, user experience, and integration with the backend API.

AI was used to improve productivity and consistency, not to replace understanding of the codebase.

## AI Tools Used

| Tool | How I Used It |
|------|---------------|
| **ChatGPT** | Brainstorming API design, drafting test case structures, debugging guidance, and clarifying Spring Security and JWT patterns |
| **Cursor** | In-editor code generation, refactoring frontend components to JSX, wiring API calls to controllers, and iterating on implementation within the project workspace |

## How AI Impacted Project Workflow

AI had a meaningful impact on how this project moved from idea to working software:

1. **Faster iteration** — Repetitive tasks such as boilerplate components, fetch wrappers, and test scaffolding were completed more quickly, leaving more time for core business logic and security rules.

2. **Improved consistency** — AI helped keep naming, response formats (`MessageResponse`), and frontend patterns aligned across files, which reduced drift between backend and frontend.

3. **Better test coverage** — Service-layer tests for purchase, restock, validation, and error paths were drafted earlier in the cycle, making regressions easier to catch during development.

4. **Clearer separation of concerns** — AI-assisted frontend work reinforced a clean split: `api.js` for HTTP calls, JSX components for UI, and Spring controllers/services for server-side logic.

5. **Learning and validation** — When implementing protected routes and admin-only endpoints, AI served as a quick reference for best practices; I still verified behavior manually and through tests.

Overall, AI shortened development time on the frontend and test suite while I remained responsible for requirements, security configuration, and final quality checks.
