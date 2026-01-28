# Blooms Frontend - React Demo UI

A simple React frontend application built with Vite that demonstrates how a UI can interact with Spring Boot REST APIs. This is designed as a teaching tool to show students how frontend and backend work together.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (version 16 or higher) - [Download here](https://nodejs.org/)
- **npm** (comes with Node.js) or **yarn** or **pnpm**
- The **Blooms backend** Spring Boot application running on `http://localhost:8080`

## Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```
   
   Or if you prefer yarn:
   ```bash
   yarn install
   ```
   
   Or if you prefer pnpm:
   ```bash
   pnpm install
   ```

## Running the Application

1. **Start the Spring Boot backend first** (from the root `blooms` directory):
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend should be running on `http://localhost:8080`

2. **Start the React development server** (from the `frontend` directory):
   ```bash
   npm run dev
   ```
   
   Or with yarn:
   ```bash
   yarn dev
   ```
   
   Or with pnpm:
   ```bash
   pnpm dev
   ```

3. **Open your browser** and navigate to:
   ```
   http://localhost:5173
   ```
   (The exact port may vary - check the terminal output)

## Available Scripts

- `npm run dev` - Start the development server
- `npm run build` - Build the app for production (outputs to `dist/`)
- `npm run preview` - Preview the production build locally

## Project Structure

```
frontend/
├── src/
│   ├── App.jsx          # Main application component with all screens
│   ├── main.jsx         # React entry point
│   ├── apiClient.js     # Axios configuration for API calls
│   └── styles.css       # Global styles
├── index.html           # HTML template
├── vite.config.mts      # Vite configuration (includes proxy to backend)
├── package.json         # Dependencies and scripts
└── README.md           # This file
```

## Features

The app includes three different views to demonstrate different UI patterns:

1. **Quick Demo** - Simple form-based interface showing basic API interactions
2. **Manage (Tabs)** - Tabbed interface for managing categories, subcategories, and blogs
3. **App Dashboard** - SaaS-style dashboard with sidebar navigation and data tables

### Authentication

- **Register**: Create a new account via `POST /api/account/signup`
- **Login**: Login using mobile number and password via `POST /api/account/login`

### CRUD Operations

The UI demonstrates CRUD operations for:

- **Categories**: Create, Read, Delete (Edit available in modal)
- **Subcategories**: Create (Edit/Delete modals ready for backend implementation)
- **Blogs**: Read category tree (Create/Edit/Delete modals ready for backend implementation)

## API Integration

The frontend is configured to proxy all `/api/*` requests to `http://localhost:8080` during development. This is configured in `vite.config.mts`:

```typescript
server: {
  proxy: {
    "/api": {
      target: "http://localhost:8080",
      changeOrigin: true
    }
  }
}
```

### API Endpoints Used

- `POST /api/account/signup` - User registration
- `POST /api/account/login` - User login
- `GET /api/categories/all` - List all categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category
- `POST /api/subcategories` - Create subcategory
- `GET /api/blogs/categories` - Get blog category tree

**Note**: Some endpoints may return 404 if not yet implemented in the backend. This is intentional and serves as a teaching moment for students to implement the missing APIs.

## Development Notes

- The app uses **React 18** with functional components and hooks
- **Axios** is used for HTTP requests
- All API calls are centralized in `apiClient.js`
- The UI includes helpful text explaining which API endpoints are being called
- Modal forms map 1:1 to Java model fields for easy backend implementation

## Troubleshooting

### Port Already in Use

If port 5173 is already in use, Vite will automatically try the next available port. Check the terminal output for the actual URL.

### Backend Connection Issues

- Ensure the Spring Boot backend is running on `http://localhost:8080`
- Check browser console for CORS errors (shouldn't happen with the proxy)
- Verify the backend APIs are accessible via `curl` or Postman

### Build Issues

If you encounter dependency issues:

```bash
rm -rf node_modules package-lock.json
npm install
```

## For Students

This frontend is designed to work with the existing Spring Boot backend. As you implement new APIs in the backend:

1. The UI forms are already set up with the correct field names matching your Java models
2. When you create a new endpoint, the corresponding UI action will automatically work
3. If an endpoint doesn't exist yet, you'll see a 404 error - this is expected and shows you what needs to be implemented

## License

This is a demo project.
