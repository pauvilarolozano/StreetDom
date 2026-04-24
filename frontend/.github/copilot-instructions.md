# Copilot instructions for MyFootballApp (frontend)

## Project overview

This is a small React + Vite web application for a football-related app. The project uses React Router v7 and standard JavaScript/JSX conventions.

The most relevant files are:
- `src/App.jsx`
- `src/components/login/Login.jsx`

## What Copilot should help with

- Suggest code improvements for React components in `src/`
- Propose changes to the login flow and command parsing logic
- Recommend fixes or enhancements for `function suma` style commands
- Keep the app working with Vite and React Router v7

## How to run the project

- Install dependencies: `npm install`
- Start dev server: `npm run dev`
- Build for production: `npm run build`

## Code style and conventions

- Prefer functional React components and hooks
- Keep JSX simple and maintainable
- Use `react-router-dom` `Routes` / `Route` patterns
- Follow existing Standard.js style as configured in `package.json`

## Editing guidance

- Make suggestions in the current file when the user is editing it
- Prefer small, incremental improvements over large rewrites
- If a file is missing a logical rendering path, suggest adding one instead of removing existing scaffolding
- When in doubt, keep changes minimal and focused on the user's requested behavior
